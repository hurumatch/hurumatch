package jp.ac.ncc.system.se1007.hurumatch;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 2019/05/28.
 */
public class CustomOpenHelper extends SQLiteOpenHelper {

	// データベース自体の名前(テーブル名ではない)
	static final private String DBName = "hurumatch_DB";
	// データベースのバージョン(2,3と挙げていくとonUpgradeメソッドが実行される)
	static final private int VERSION = 1;

	// コンストラクタ　以下のように呼ぶこと
	public CustomOpenHelper(Context context){
		super(context, DBName, null, VERSION);
	}

	// データベースが作成された時に実行される処理
	@Override
	public void onCreate(SQLiteDatabase db) {
		// 処理を記述
		/**
		 * テーブルを作成する
		 * execSQLメソッドにCREATET TABLE命令を文字列として渡すことで実行される
		 * 引数で指定されているものの意味は以下の通り
		 * 引数1 ・・・ id：列名 , INTEGER：数値型 , PRIMATY KEY：テーブル内の行で重複無し , AUTOINCREMENT：1から順番に振っていく
		 * 引数2 ・・・ name：列名 , TEXT：文字列型
		 * 引数3 ・・・ price：列名 , INTEGER：数値型
		 */

		db.execSQL("CREATE TABLE FOOD_TABLE (" +
				"id INTEGER PRIMARY KEY AUTOINCREMENT, " +
				"name TEXT, " +
				"price INTEGER)");
	}

	// データベースをバージョンアップした時に実行される処理
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// 処理を記述
	}

	// データベースが開かれた時に実行される処理
	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
	}
}
