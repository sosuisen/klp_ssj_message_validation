package com.example.model.user;

import lombok.Data;

/**
 * ユーザの基本的なデータモデルを表す抽象クラスです。
 * （UserDTOとUserFormの共通のスーパークラス）
 * なくても作れるのですが、クラス間の関係を整理するため追加しました。
 */
@Data
abstract class UserBase {
	protected String name;
	protected String role;
	protected String password;
}
