package jp.ac.ncc.system.se1007.hurumatch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class kwskActivity extends AppCompatActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kwsk);

		Intent intent = getIntent();
		// MainActivityからintentで受け取ったものを取り出す
		String selectedText = intent.getStringExtra("Text");
		int selectedPhoto = intent.getIntExtra("Photo", 0);

		TextView textView = findViewById(R.id.selected_text);
		textView.setText(selectedText);
		ImageView imageView = findViewById(R.id.selected_photo);
		imageView.setImageResource(selectedPhoto);
	}
	public void onbackTapped(View view) //交換所ボタンを押されたら
	{
		Intent intent = new Intent(this,shopActivity.class);
		startActivity(intent);
	}
}