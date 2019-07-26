package jp.ac.ncc.system.se1007.hurumatch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

	// Authenticationにアクセスするための変数
	private FirebaseAuth mAuth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);

		// Authenticationの利用開始
		mAuth   = FirebaseAuth.getInstance();

		Button signUpButton = findViewById(R.id.Create);
		signUpButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				EditText editTextEmail = findViewById(R.id.SignupEmail);
				EditText editTextPassword = findViewById(R.id.SignupPassword);

				createAccount(editTextEmail.getText().toString(), editTextPassword.getText().toString());
			}
		});
	}

	@Override
	public void onStart() {
		super.onStart();

		// ログイン中かどうか調べる
		FirebaseUser currentUser = mAuth.getCurrentUser();

		// ログインしていないか
		if (currentUser==null){
			// していない
		} else {
			// しているのでサインアウト
			Log.d("AUTH", "Already Sign in");
			mAuth.signOut();
		}
	}

	// 新規アカウントを作成する
	private void createAccount(String email, String password) {
		Log.d("AUTH", email);

		// ユーザ登録されていなければ新規登録する
		mAuth.createUserWithEmailAndPassword(email, password)
				.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						if (task.isSuccessful()) {
							// Sign in success, update UI with the signed-in user's information
							Log.d("AUTH", "createUserWithEmail:success");
							FirebaseUser user = mAuth.getCurrentUser();
							Toast.makeText(SignUpActivity.this, "Sign in Success: " + user.getDisplayName(),
									Toast.LENGTH_SHORT).show();

							Intent intent = new Intent(SignUpActivity.this, mainActivity.class);
							startActivity(intent);
							finish();
						} else {
							// If sign in fails, display a message to the user.
							Log.w("AUTH", "createUserWithEmail:failure", task.getException());
							Toast.makeText(SignUpActivity.this, "Authentication failed.",
									Toast.LENGTH_SHORT).show();
						}
					}
				});
	}
}
