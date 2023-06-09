package com.example.model.user;

import org.hibernate.validator.constraints.Length;

import jakarta.mvc.binding.MvcBinding;
import jakarta.ws.rs.FormParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
	@MvcBinding
	@FormParam("name")
	@Length(min=3,max=30,message="名前は3文字以上30文字以内としてください。")
	private String name;
	@FormParam("role")
	private String role;
	@MvcBinding
	@Length(min=8,max=16)
	@FormParam("password")
	private String password;
}

