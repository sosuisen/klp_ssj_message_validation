package com.example.model.user;

import java.io.Serializable;

import jakarta.inject.Named;
import jakarta.mvc.RedirectScoped;

/**
 * UserControllerとJSPとの間の値の受け渡しにのみ用いる
 * リダイレクトスコープのCDI Beanです。
 */
@RedirectScoped
@Named
public class UserForm extends UserBase implements Serializable {
	/**
	 * このCDI BeanはGETで呼ばれることがあります。
	 * (JSPの中で利用されているため）
	 * @FormParam はGETではエラーとなるため、
	 * @FormParam を指定したい場合は別のクラスを用いる必要があります。
	 * 今回はUserDTOクラス側で @FormParam を指定しています。
	 * 
	 * なお、@FormParam はサブクラスにも継承されるため、
	 * 継承を用いる場合は相互関係に注意が必要です。
	 */
	
	/**
	 * UserDTOからUserFormへデータを詰め替えるために用います。
	 */
	public void setUser(UserBase user) {
		setName(user.getName());
		setRole(user.getRole());
		setPassword(user.getPassword());
	}
}
