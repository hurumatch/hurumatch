package jp.ac.ncc.system.se1007.hurumatch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class shopActivity extends AppCompatActivity
		implements AdapterView.OnItemClickListener
{

	private static final String[] scenes =
			{
			"商品名１",
			"商品名２",
			"商品名３",
			"商品名４",
			"商品名５",
			"商品名６",
			"商品名７",
			"商品名８",
			"商品名９",
			"商品名１０",
			};

	// ちょっと冗長的ですが分かり易くするために
	private static final int[] photos =
			{
			R.mipmap.sake,
			R.mipmap.kome,
			R.mipmap.sake,
			R.mipmap.kome,
			R.mipmap.sake,
			R.mipmap.kome,
			R.mipmap.sake,
			R.mipmap.kome,
			R.mipmap.sake,
			R.mipmap.kome,
			};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop);

		// ListViewのインスタンスを生成
		ListView list = findViewById(R.id.list_view);

		// BaseAdapter を継承したadapterのインスタンスを生成
		// レイアウトファイル list.xml を activity_main.xml に
		// inflate するためにadapterに引数として渡す
		BaseAdapter adap = new CustomAdapter(this.getApplicationContext(),
				R.layout.list, scenes, photos);

		// ListViewにadapterをセット
		list.setAdapter(adap);

		// クリックリスナーをセット
		list.setOnItemClickListener(this);

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v,
							int position, long id)
	{

		Intent intent = new Intent(
				this.getApplicationContext(), kwskActivity.class);

		// clickされたpositionのtextとphotoのID
		String selectedText = scenes[position];
		int selectedPhoto = photos[position];
		// インテントにセット
		intent.putExtra("Text", selectedText);
		intent.putExtra("Photo", selectedPhoto);

		// SubActivityへ遷移
		startActivity(intent);
	}

	public void onback2Tapped(View view) //交換所ボタンを押されたら
	{
		Intent intent = new Intent(this,mainActivity.class);
		startActivity(intent);
	}
}
