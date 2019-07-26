package jp.ac.ncc.system.se1007.hurumatch;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.FileOutputStream;
import java.io.IOException;

public class titleActivity extends AppCompatActivity
{

	String str="\n";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_title);
	}

	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	public void onstartTapped(View view) //とりあえずSignInActivityへ
	{
		saveFile();
		Intent intent = new Intent(this,SignInActivity.class);
		startActivity(intent);
	}
	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	public void saveFile() {

		FileOutputStream fileOutputStream=null;

		// try-with-resources
		try {

			fileOutputStream = openFileOutput("complist.txt",
					Context.MODE_APPEND);

			fileOutputStream.write(str.getBytes());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
