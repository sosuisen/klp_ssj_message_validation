package com.example.model.user;

import com.example.model.validator.UniqueName;
import com.example.model.validator.ValidRole;

import jakarta.mvc.binding.MvcBinding;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.ws.rs.FormParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
	@MvcBinding
	// @NotBlank(message = "名前を入力してください。")
	@NotBlank(message = "{user.name.NotBlank}")
	@Size(min = 3, max = 30, message = "{user.name.Size}")
	@UniqueName(message = "{user.name.UniqueName}")
	@FormParam("name")
	private String name;

	@MvcBinding
	@NotBlank(message = "{user.role.NotBlank}")
	// @Pattern(regexp="(USER|ADMIN)", message="${validatedValue}は存在しないロールです。")
	@ValidRole(message = "{user.role.ValidRole}")
	@FormParam("role")
	private String role;

	@MvcBinding
	@NotBlank(message = "{user.password.NotBlank}")
	@Size(min = 8, max = 16, message = "{user.password.Size}")
	@Pattern(regexp = "[0-9a-zA-Z_\\-#\\^\\$%&@\\+\\*\\?]+", message = "{user.password.Pattern}")
	@FormParam("password")
	private String password;

	@MvcBinding
	@AssertTrue(message = "{assert.NameMustNotIncludeAdminIfNotAdminRole}")
	public boolean isNameMustNotIncludeAdminIfNotAdminRole() {
		var pattern = java.util.regex.Pattern.compile("ADMIN", java.util.regex.Pattern.CASE_INSENSITIVE);
		if (!role.equals("ADMIN") && pattern.matcher(name).find())
			return false;

		return true;
	}

}
