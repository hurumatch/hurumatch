package jp.ac.ncc.system.se1007.hurumatch;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class mapActivity extends FragmentActivity implements OnMapReadyCallback,LocationListener,GoogleMap.OnInfoWindowClickListener {

	private GoogleMap mMap;
	private LatLng location;

	//位置情報を管理するクラス
	private LocationManager locationManager;
	//位置情報用リクエスト
	private static final int REQUEST_CODE_ACCESS_FINE_LOCATON = 1000;

	//改行コード
	String br = System.getProperty("line.separator");

	int i;

	boolean check = true;

	//QR元URLをテキストファイルから読み取る。。。
	InputStream is = null;
	BufferedReader brr = null;
	//complistを入れる箱
	String comp = "";
	//mainのurltxtと同じ役目
	String urltxt = "";



	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		// Obtain the SupportMapFragment and get notified when the map is ready to be used.
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);

		//QR元のURLをassetsのファイルから読み込む
		try{
			try {
				is = this.getAssets().open("urllist.txt");
				brr = new BufferedReader(new InputStreamReader(is));

				String qrurl;
				while ((qrurl = brr.readLine()) != null){
					urltxt += qrurl + "\n";
					
				}
			}finally {
				if(is != null)is.close();
				if(brr != null) brr.close();
			}
		}
		catch (Exception e){

		}

		//ファイル読み込み
		readFile();

		//引数にファイル名と検索条件の文字列
		putLine();

	}

	public void putLine(){

		try {
			//ファイルを読み込む
//			FileReader fr = new FileReader("/data/data/jp.ac.ncc.system.se1007.hurumatch/files/complist.txt");
			FileReader fr = new FileReader("/data/data/jp.ac.ncc.system.se1007.hurumatch/files/complist.txt");
			BufferedReader br = new BufferedReader(fr);

			String line;
			int count = 0;
			while ((line = br.readLine()) != null) {
				Pattern p = Pattern.compile(comp);
				Matcher m = p.matcher(line);

				if (m.find()){
					//URL元リスト(urllist)といったところのリスト(→complist)wo
					String getqr = line;

					//getqr→compと同じのが入ってる（はずなんだけど、、、） あと、一行ずつ読み込めてる
					Log.d("line_check",getqr+"mamamamamama");

					if(getqr.matches(".*"+ comp +".*"))
					{
						//通ったらここで、、、（7/23　ここ通過）
						Log.d("list_check...", comp+"....ok");

						//check=trueならマーカー変更
						check = false;

					}

				}else{
					Log.d("no_list...",comp+"ng");
				}
			}

			br.close();
			fr.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	// ファイルを読み出し
	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	public void readFile() {
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
		comp = text;

		Log.d("yomi！！！！！！",text + "nananananana");

	}


	@Override
	public void onMapReady(GoogleMap googleMap) {
		mMap = googleMap;
		Log.d("dokadoka", String.valueOf(check)+"desu");


        /*元
        緯度経度
        マーカー追加
        詳細をタップした際のログ検出
        タグをセットする

        location = new LatLng();
        Marker xx = mMap.addMarker(new MarkerOptions().position(location).title(""));
        mMap.setOnInfoWindowClickListener(this);
        .setTag();
        */

		//新潟市　マンガの家
		location = new LatLng(37.920598, 139.044751);
		//complist.txtにはいってるのだったらマーカーを変更（ifで分岐）
		Marker manga = null;
		if(check == false) {
			manga = mMap.addMarker(new MarkerOptions().position(location).title("新潟市　マンガの家").snippet(""));

		}else {
			manga = mMap.addMarker(new MarkerOptions().position(location).title("新潟市　マンガの家").snippet("").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
		}
		//詳細をタップした際のログ検出
		mMap.setOnInfoWindowClickListener(this);
		manga.setTag(0);

		// マリンピア日本海
		location = new LatLng(37.923688, 139.028049);
		// marker 追加
		Marker marin = null;
		if(check == false) {
			marin = mMap.addMarker(new MarkerOptions().position(location).title("マリンピア日本海").snippet(""));
		}else if(check==true){
			marin = mMap.addMarker(new MarkerOptions().position(location).title("マリンピア日本海").snippet("").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
		}
//		marin = mMap.addMarker(new MarkerOptions().position(location).title("マリンピア日本海").snippet(""));
			marin.setTag(1);


		// ドカベンロード
		location = new LatLng(37.919753, 139.044172);
		Marker dokaben = null;
		if(check == false) {
			dokaben = mMap.addMarker(new MarkerOptions().position(location).title("ドカベンロード").snippet(""));
		}else if(check==true){

			dokaben = mMap.addMarker(new MarkerOptions().position(location).title("ドカベンロード").snippet("").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
		}
		dokaben.setTag(2);

		//白山神社
		location = new LatLng(37.915403, 139.037202);
		Marker hakusan = null;
		if(check = false) {
			hakusan = mMap.addMarker(new MarkerOptions().position(location).title("白山神社").snippet(""));
		}else if(check=true){
			hakusan = mMap.addMarker(new MarkerOptions().position(location).title("白山神社").snippet("").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
		}
		hakusan.setTag(3);

		//旧斎藤家別荘
		location = new LatLng(37.925927, 139.040275);
		Marker saito = null;
		if(check == false) {
			saito = mMap.addMarker(new MarkerOptions().position(location).title("旧斎藤家別荘").snippet(""));
		}else if(check==true){
			saito = mMap.addMarker(new MarkerOptions().position(location).title("旧斎藤家別荘").snippet("").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
		}
		saito.setTag(4);

		//NEXT21　19F　展望ラウンジ
		location = new LatLng(37.922544, 139.043158);
		Marker next21 = null;
		if(check == false) {
			next21 = mMap.addMarker(new MarkerOptions().position(location).title("NEXT21　19F　展望ラウンジ").snippet(""));
		}else if(check==true){
			next21 = mMap.addMarker(new MarkerOptions().position(location).title("NEXT21　19F　展望ラウンジ").snippet("").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
		}
		next21.setTag(5);

		//とんかつ太郎
		location = new LatLng( 37.920641, 139.04398);
		Marker taro = null;
		if(check == false) {
			taro = mMap.addMarker(new MarkerOptions().position(location).title("とんかつ太郎").snippet(""));
		}else if(check==true){
			taro = mMap.addMarker(new MarkerOptions().position(location).title("とんかつ太郎").snippet("").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
		}
		taro.setTag(6);

		//港すし
		location = new LatLng(37.925019, 139.046068);
		Marker minato = null;
		if(check == false) {
			minato = mMap.addMarker(new MarkerOptions().position(location).title("港すし").snippet(""));
		}else if(check==true){
			minato = mMap.addMarker(new MarkerOptions().position(location).title("港すし").snippet("").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
		}
		minato.setTag(7);

		//砂丘館
		location = new LatLng(37.925791, 139.036232);
		Marker sakyu = null;
		if(check == false) {
			sakyu = mMap.addMarker(new MarkerOptions().position(location).title("砂丘館").snippet(""));
		}else if(check==true){
			sakyu = mMap.addMarker(new MarkerOptions().position(location).title("砂丘館").snippet("").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
		}
		sakyu.setTag(8);

		//北方文化博物資料館　分館
		location = new LatLng(37.925572, 139.04042);
		Marker hoppo = null;
		if(check == false) {
			hoppo = mMap.addMarker(new MarkerOptions().position(location).title("北方文化博物資料館 分館").snippet(""));
		}else if(check==true){
			hoppo = mMap.addMarker(new MarkerOptions().position(location).title("北方文化博物資料館 分館").snippet("").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
		}
		hoppo.setTag(9);

		//安吾風の館
		location = new LatLng(37.926679, 139.037725);
		Marker ango = null;
		if(check == false) {
			ango = mMap.addMarker(new MarkerOptions().position(location).title("安吾風の館").snippet(""));
		}else if(check==true){
			ango = mMap.addMarker(new MarkerOptions().position(location).title("安吾風の館").snippet("").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
		}
		ango.setTag(10);

		//香り小町
		location = new LatLng(37.920806, 139.044829);
		Marker kaori = null;
		if(check == false) {
			kaori = mMap.addMarker(new MarkerOptions().position(location).title("香り小町").snippet(""));
		}else if(check==true){
			kaori = mMap.addMarker(new MarkerOptions().position(location).title("香り小町").snippet("").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
		}
		kaori.setTag(11);

		//シャモニー　上大川前店
		location = new LatLng(37.921955, 139.048416);
		Marker shamo = null;
		if(check == false) {
			shamo = mMap.addMarker(new MarkerOptions().position(location).title("シャモニー　上大川前店").snippet(""));
		}else if(check==true){
			shamo = mMap.addMarker(new MarkerOptions().position(location).title("シャモニー　上大川前店").snippet("").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
		}
		shamo.setTag(12);

		//金巻屋
		location = new LatLng(37.918049, 139.042208);
		Marker kanemaki = null;
		if(check == false) {
			kanemaki = mMap.addMarker(new MarkerOptions().position(location).title("金巻屋").snippet(""));
		}else if(check==true){
			kanemaki = mMap.addMarker(new MarkerOptions().position(location).title("金巻屋").snippet("").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
		}
		kanemaki.setTag(13);

		//青島食堂
		location = new LatLng(37.915902, 139.041279);
		Marker aosima = null;
		if(check == false) {
			aosima = mMap.addMarker(new MarkerOptions().position(location).title("青島食堂").snippet(""));
		}else if(check==true){
			aosima = mMap.addMarker(new MarkerOptions().position(location).title("青島食堂").snippet("").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
		}
		aosima.setTag(14);

		//Map 拡大率
		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));


//         タップした時のリスナーをセット
//        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener()
//        {
//            @Override
//            public void onMapClick(LatLng tapLocation)
//            {
//                // tapされた位置の緯度経度
//                location = new LatLng(tapLocation.latitude, tapLocation.longitude);
//                String str = String.format(Locale.US, "37.923688 ,139.028049", tapLocation.latitude, tapLocation.longitude);
//                mMap.addMarker(new MarkerOptions().position(location).title(str));
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 14));
//            }
//        });
//
//         長押しのリスナーをセット
//        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener()
//        {
//            @Override
//            public void onMapLongClick(LatLng longpushLocation)
//            {
//                LatLng newlocation = new LatLng(longpushLocation.latitude, longpushLocation.longitude);
//                mMap.addMarker(new MarkerOptions().position(newlocation).title(""+longpushLocation.latitude+" :"+ longpushLocation.longitude));
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newlocation, 14));
//            }
//        });


	}


	@Override
	public void onLocationChanged(Location location) {

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

	@Override
	public void onProviderEnabled(String provider) {

	}

	@Override
	public void onProviderDisabled(String provider) {

	}

	@Override
	public void onInfoWindowClick(Marker marker) {

		//マンガの家
		Log.d("MAPddddddd", "詳細がタップされました。");
		//タグが個別に設定されていない場合は全部ここに飛ぶ
		Uri uri = Uri.parse("https://twitter.com/");

		//持ってきたタグに応じてURLを変更
		/**　デフォルト
		 if (marker.getTag() != null)
		 {
		 if (marker.getTag().equals())
		 {
		 uri = Uri.parse("");
		 }
		 }
		 **/

		//マリンピア
		if (marker.getTag() != null) {
			if (marker.getTag().equals(1)) {
				uri = Uri.parse("https://www.marinepia.or.jp/");
			}
		}

		//ドカベン
		if (marker.getTag() != null) {
			if (marker.getTag().equals(2)) {
				uri = Uri.parse("");
			}
		}

		//白山
		if (marker.getTag() != null) {
			if (marker.getTag().equals(3)) {
				uri = Uri.parse("http://www.niigatahakusanjinja.or.jp/index.html");
			}
		}

		//斎藤
		if (marker.getTag() != null) {
			if (marker.getTag().equals(4)) {
				uri = Uri.parse("http://saitouke.jp/");
			}
		}

		//NEXT21
		if (marker.getTag() != null) {
			if (marker.getTag().equals(5)) {
				uri = Uri.parse("http://www.next21-niigata.jp/");
			}
		}

		//とんかつ
		if (marker.getTag() != null) {
			if (marker.getTag().equals(6)) {
				uri = Uri.parse("");
			}
		}

		//港
		if (marker.getTag() != null) {
			if (marker.getTag().equals(7)) {
				uri = Uri.parse("https://3710-sushi.com/    ");
			}
		}

		//砂丘
		if (marker.getTag() != null) {
			if (marker.getTag().equals(8)) {
				uri = Uri.parse("https://www.sakyukan.jp/");
			}
		}

		//北方
		if (marker.getTag() != null) {
			if (marker.getTag().equals(9)) {
				uri = Uri.parse("http://hoppou-bunka.com/");
			}
		}

		//安吾
		if (marker.getTag() != null) {
			if (marker.getTag().equals(10)) {
				uri = Uri.parse("http://www.city.niigata.lg.jp/kanko/bunka/yukari/kazenoyakata/");
			}
		}

		//香り
		if (marker.getTag() != null) {
			if (marker.getTag().equals(11)) {
				uri = Uri.parse("http://www.kaori-komachi.com/");
			}
		}

		//シャモニー
		if (marker.getTag() != null) {
			if (marker.getTag().equals(12)) {
				uri = Uri.parse("");
			}
		}

		//金巻
		if (marker.getTag() != null) {
			if (marker.getTag().equals(13)) {
				uri = Uri.parse("https://kanemakiya.wixsite.com/mizukian");
			}
		}

		//青島食堂
		if (marker.getTag() != null) {
			if (marker.getTag().equals(14)) {
				uri = Uri.parse("");
			}
		}

		if (uri.toString().isEmpty() == false)
		{
			//一番最後にあること！
			Intent i = new Intent(Intent.ACTION_VIEW, uri);
			startActivity(i);
		}
		else
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("ごめんなさい");
			builder.setMessage("こちらはサイトがありませんでした。" + br +
					"詳細について確認することができません。");

			builder.setPositiveButton("確認", new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
				}
			});

			AlertDialog dialog = builder.create();
			dialog.show();
		}

	}
}