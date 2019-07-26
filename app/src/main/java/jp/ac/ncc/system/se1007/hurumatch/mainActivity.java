package jp.ac.ncc.system.se1007.hurumatch;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class mainActivity extends AppCompatActivity {

	// RealtimeDatabaseにアクセスするための変数
	DatabaseReference mDatabase;
	// このユーザのID（仮）
	String uid = "one";

	//QRコードのURL
	static String URL = "bakabakabaka";
	//QR元URLをテキストファイルから読み取る。。。
	InputStream is = null;
	BufferedReader br = null;

	//↓のやつはmapでも使う
	String urltxt = "";

	static String test="";

	//改行用
	String kaigyou = "\n";

	//Map表示用（読み取り済みならマーカー変更）
//	public static boolean mkflag = false;
//	static String fileName = "complist.txt";

	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mDatabase = FirebaseDatabase.getInstance().getReference();
		Button QR = findViewById(R.id.qr);
		Listener listener = new Listener();
		QR.setOnClickListener(listener);
		readFile();

		//QR元のURLをassetsのファイルから読み込む
		try{
			try {
				is = this.getAssets().open("urllist.txt");
				br = new BufferedReader(new InputStreamReader(is));

				String qrurl;
				while ((qrurl = br.readLine()) != null){
					urltxt += qrurl + "\n";

//→ここおｋ					Log.d("uoooooo!!!",urltxt);
				}
			}finally {
				if(is != null)is.close();
				if(br != null)br.close();
			}
		}
		catch (Exception e){

		}
	}

	//デバッグ用
	public void ontestTapped(View view) //交換所ボタンを押されたら
	{
		savedesuFile();
	}
	private void savedesuFile() {

		FileOutputStream fileOutputStream=null;

		// try-with-resources
		try {

			fileOutputStream = openFileOutput("complist.txt",
					Context.MODE_PRIVATE);

			String teststst="\n";

			fileOutputStream.write(teststst.getBytes());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//ここまで

	private class Listener implements View.OnClickListener
	{
		@Override
		public void onClick(View view)
		{
			scanBarcode(view);
		}
	}
	public void scanBarcode(View view)
	{
		new IntentIntegrator(this).initiateScan();
	}
	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
		if(result != null)
		{
			URL = result.getContents();

			if(result.getContents() == null)
			{
				Log.d("MainActivity", "Cancelled scan");
				Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
			}
			else
			{
				if(urltxt.contains(URL)) {
					Log.d("MainActivity", "Scanned");
					addPost();
					saveFile();

					AlertDialog.Builder builder = new AlertDialog.Builder(this);
					builder.setMessage("ポイントが加算されました")
							.setPositiveButton("OK", new DialogInterface.OnClickListener() {@Override
							public void onClick(DialogInterface dialog, int which) {
							}
							});
					builder.show();
				}
				else{
					Log.d("MainActivity", "URL対象外だぞ");

					AlertDialog.Builder builder = new AlertDialog.Builder(this);
					builder.setMessage("対象外のQRコードです")
							.setPositiveButton("OK", new DialogInterface.OnClickListener() {@Override
							public void onClick(DialogInterface dialog, int which) {
							}
							});
					builder.show();
//					Toast.makeText(this, "対象外のQRコードです", Toast.LENGTH_LONG).show();
				}

			}
		}
		else
		{
			// This is important, otherwise the result will not be passed to the fragment
			super.onActivityResult(requestCode, resultCode, data);
		}
	}//QR読み取りカメラ起動↑

	// ポイント追加メソッド
	private void addPost() {

		// /users/uid 以下を参照
		// addListenerForSingleValueEvent　⇒　1回だけ読み込み
		mDatabase.child("users").child(uid)
				.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot dataSnapshot) {

						// 読み込んだユーザ情報をUserクラスに変換
						User user = dataSnapshot.getValue(User.class);
						// ポイント加算
						user.point++;

						// 第1引数（Key）:変更加える要素を指定
						// 第2引数（Value）：設定する値
						Map<String, Object> childUpdates = new HashMap<>();
						childUpdates.put("/users/" + uid + "/point", user.point);

						// データベースを更新
						mDatabase.updateChildren(childUpdates);

					}

					@Override
					public void onCancelled(DatabaseError databaseError) {

					}
				});
	}

	// ファイルを保存
	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	public void saveFile() {

		FileOutputStream fileOutputStream=null;

		// try-with-resources
		try {

			fileOutputStream = openFileOutput("complist.txt",
					Context.MODE_APPEND);

			fileOutputStream.write(URL.getBytes());
			fileOutputStream.write(kaigyou.getBytes());


			Log.d("うらるちぇ", URL);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ファイルを読み出し
	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	public String readFile() {
		String text = null;

		// try-with-resources
		try (FileInputStream fileInputStream = openFileInput("complist.txt");
			 BufferedReader reader= new BufferedReader(
					 new InputStreamReader(fileInputStream, StandardCharsets.UTF_8))) {

			String lineBuffer;
			while( (lineBuffer = reader.readLine()) != null ) {
				text = lineBuffer ;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

//		Log.d("よみこんだやつ！！！！！！",text);

		return text;
	}

	public void onshopTapped(View view) //交換所ボタンを押されたら
	{
		Intent intent = new Intent(this,shopActivity.class);
		startActivity(intent);
	}
	public void onmapTapped(View view) //観光ボタンを押されたら
	{
		Intent intent = new Intent(this,mapActivity.class);
		startActivity(intent);
	}
	public void onppTapped(View view) //ポイント確認ボタンを押されたら
	{
		Intent intent = new Intent(this,pointActivity.class);
		startActivity(intent);
//		Log.d("MainActivity", String.valueOf(point));
	}

	public void onSignoutTapped(View view) //サインアウト
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("サインアウトしますか？")
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {@Override
					public void onClick(DialogInterface dialog, int which) {
					//サインアウト処理
					FirebaseAuth mAuth = FirebaseAuth.getInstance();
					mAuth.signOut();
					//タイトルへ
					Intent intent = new Intent(mainActivity.this,titleActivity.class);
					startActivity(intent);
					}
				})
				.setNegativeButton("Cancel", null);
				builder.show();
	}
}
