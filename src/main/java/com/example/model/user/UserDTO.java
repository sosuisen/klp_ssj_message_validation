package com.example.model.user;

import jakarta.mvc.binding.MvcBinding;
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
	@NotBlank(message = "名前は必須です。")
	@Size(min = 3, max = 30, message = "名前は3文字以上30文字以内としてください。")
	@FormParam("name")	
	private String name = "";

	@MvcBinding
	@NotBlank
	@ValidRole(message="{user_role_invalid}")
//	@Pattern(regexp="(USER|ADMIN)", message="使用できるロールは USER, ADMIN のみです。")
	@FormParam("role")
	private String role = "";

	@MvcBinding
	@NotBlank(message = "パスワードは必須です。")
	@Size(min = 8, max = 16, message = "パスワードは8文字以上16文字以内としてください。")
	@Pattern(regexp = "[0-9a-zA-Z_\\-#\\^\\$%&@\\+\\*\\?]+", message = "パスワードに使える文字は、数字、アルファベット、記号 _-#^$%&+*? のみです")
	@FormParam("password")
	private String password = "";

	/*
	@AssertTrue(message = "ロールに使える")
	public boolean isPasswordMustNotEmptyIfNamePresents() {
		if(name.isEmpty()) return false;
	
		// nameが空欄ではなく、かつpasswordが空欄
		if(password.isEmpty()) return true;
		
		return false;
	}
	*/
}
