package jp.ac.ncc.system.se1007.hurumatch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class pointActivity extends AppCompatActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_point);

		TextView pointView=findViewById(R.id.ptxt);
		pointView.setText("現在：" + User.point + "ポイント");
	}
	public void onpshopTapped(View view) //交換所ボタンを押されたら
	{
		Intent intent = new Intent(this,shopActivity.class);
		startActivity(intent);
	}
	public void onmainTapped(View view) //メニューへボタンを押されたら
	{
		Intent intent = new Intent(this,mainActivity.class);
		startActivity(intent);
	}

//	FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//		if (user != null) {
//	// Name, email address
//	String name = user.getDisplayName();
//	String email = user.getEmail();
//
//	// Check if user's email is verified
//	boolean emailVerified = user.isEmailVerified();
//
//	// The user's ID, unique to the Firebase project. Do NOT use this value to
//	// authenticate with your backend server, if you have one. Use
//	// FirebaseUser.getToken() instead.
//	String uid = user.getUid();
//}
}
