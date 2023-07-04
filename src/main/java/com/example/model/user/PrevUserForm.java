package com.example.model.user;

import java.io.Serializable;

import jakarta.inject.Named;
import jakarta.mvc.RedirectScoped;
import lombok.Data;

/**
 * UserControllerとJSPとの間の値の受け渡しにのみ用いる
 * リダイレクトスコープのCDI Beanです。
 */
@RedirectScoped
@Named
@Data
public class PrevUserForm implements Serializable {
	private UserDTO user;
}
