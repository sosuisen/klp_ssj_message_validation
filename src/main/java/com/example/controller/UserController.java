package com.example.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import com.example.auth.IdentityStoreConfig;
import com.example.model.user.ErrorBean;
import com.example.model.user.UserDTO;
import com.example.model.user.UsersDAO;
import com.example.model.validator.ValidRole;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.binding.BindingResult;
import jakarta.mvc.binding.MvcBinding;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import jakarta.servlet.ServletContext;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import lombok.NoArgsConstructor;

@Controller
@RequestScoped
@NoArgsConstructor(force = true)
@RolesAllowed("ADMIN")
@Path("/")
public class UserController {

	private final UsersDAO usersDAO;

	private final Pbkdf2PasswordHash passwordHash;

	private final BindingResult bindingResult;

	private final ErrorBean errorBean;

	private final ServletContext servletContext;


	@Inject
	public UserController(UsersDAO usersDAO, Pbkdf2PasswordHash passwordHash, BindingResult bindingResult,
			ErrorBean errorBean, ServletContext servletContext) {
		this.usersDAO = usersDAO;
		this.passwordHash = passwordHash;
		passwordHash.initialize(IdentityStoreConfig.HASH_PARAMS);
		this.bindingResult = bindingResult;
		this.errorBean = errorBean;
		this.servletContext = servletContext;
	}

	@GET
	@Path("users")
	public String getUsers() {
		usersDAO.getAll();
		return "users.jsp";
	}

	@POST
	@Path("users")
	public String createUser(@Valid @BeanParam UserDTO user) {
		if (bindingResult.isFailed()) {
			errorBean.addAll(bindingResult.getAllMessages());
			return "redirect:users";
		}

		var hash = passwordHash.generate(user.getPassword().toCharArray());
		user.setPassword(hash);
		usersDAO.create(user);
		return "redirect:users";
	}

	@POST
	@Path("user_delete")
	public String deleteUser(@FormParam("name") String name) {
		usersDAO.delete(name);
		return "redirect:users";
	}

	/**
	 * パラメータに Bean Validationアノテーションを付けることも可能です。
	 * 下記では name と role を Bean Validationでチェックしています。
	 * （nameは入力できませんが念のため。）
	 */
	@POST
	@Path("user_update")
	public String updateUser(@MvcBinding @Valid 
			@NotBlank(message = "{user.name.NotBlank}")
			@Size(min = 3, max = 30, message = "{user.name.Size}")
			@FormParam("name") String name,
			@MvcBinding @Valid
			@NotBlank(message = "{user.role.NotBlank}")
			@ValidRole(message = "{user.role.ValidRole}")
			@FormParam("role") String role,
			@FormParam("password") String password) {
		if (bindingResult.isFailed()) {
			errorBean.addAll(bindingResult.getAllMessages());
			return "redirect:users";
		}

		/**
		 * パスワードは空欄を許可し、空欄でない場合は長さとパターンをチェックします。
		 * ビルトインのバリデータでこのチェックはできないため、
		 * カスタムバリデータを作る必要がありますが、
		 * 参考のためバリデータを作らずに直接コードで書いてみました。
		 */
		if (!password.isEmpty()) {
			var pattern = java.util.regex.Pattern.compile(UserDTO.PASSWORD_REGEX);
			if (!pattern.matcher(password).matches()) {
				errorBean.add(getValidationMessage("user.password.Pattern"));
				return "redirect:users";
			}
			if (!(password.length() >= UserDTO.MIN_PASSWORD_LENGTH
					&& password.length() <= UserDTO.MAX_PASSWORD_LENGTH)) {
				errorBean.add(
						getValidationMessage("user.password.Size")
								.replace("{min}", String.valueOf(UserDTO.MIN_PASSWORD_LENGTH))
								.replace("{max}", String.valueOf(UserDTO.MAX_PASSWORD_LENGTH)));
				return "redirect:users";
			}

			var hash = passwordHash.generate(password.toCharArray());
			usersDAO.update(new UserDTO(name, role, hash));
		} else {
			usersDAO.update(new UserDTO(name, role, ""));
		}

		return "redirect:users";
	}

	private String getValidationMessage(String key) {
		var properties = new Properties();
		try (InputStream resourceStream = servletContext
				.getResourceAsStream("/WEB-INF/classes/ValidationMessages.properties");) {
			properties.load(resourceStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String message = properties.getProperty(key);
		var utf8Message = new String(message.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
		return utf8Message;
	}
}
