package jp.ac.ncc.system.se1007.hurumatch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class qrActivity extends AppCompatActivity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qr);

	}
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
//		if(result != null) {
//			Log.d("readQR", result.getContents());
//		} else {
//			super.onActivityResult(requestCode, resultCode, data);
//		}
//	}
//
//	private class IntentIntegrator {
//		public IntentIntegrator(qrActivity activity) {
//		}
//	}
}
