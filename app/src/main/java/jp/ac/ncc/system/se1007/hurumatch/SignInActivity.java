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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends AppCompatActivity {

	private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

	// Authenticationにアクセスするための変数
	private FirebaseAuth mAuth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);

		// Authenticationの利用開始
		mAuth   = FirebaseAuth.getInstance();

		Button signInButton = findViewById(R.id.SignIn);
		signInButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				EditText emailEditText = findViewById(R.id.Email);
				EditText passwordEditText = findViewById(R.id.Password);

				signInAccount(emailEditText.getText().toString(), passwordEditText.getText().toString());
			}
		});

		Button signUpButton = findViewById(R.id.Create);
		signUpButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public void onStart() {
		super.onStart();

		// ログイン中かどうか調べる
		FirebaseUser currentUser = mAuth.getCurrentUser();

		if (currentUser!=null){
			Log.d("AUTH", "CurrentUser:" + currentUser.getDisplayName());
			Intent intent = new Intent(SignInActivity.this, mainActivity.class);
			startActivity(intent);
		}

		// mAuth.signOut();
	}


	// サインイン
	private void signInAccount(String email, String password) {

		mAuth.signInWithEmailAndPassword(email, password)
				.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						if (task.isSuccessful()) {
							// Sign in success, update UI with the signed-in user's information
							Log.d("AUTH", "signInWithEmail:success");

							// 画面遷移
							Intent intent = new Intent(SignInActivity.this, mainActivity.class);
							startActivity(intent);
						} else {
							// If sign in fails, display a message to the user.
							Log.w("AUTH", "signInWithEmail:failure", task.getException());
							Toast.makeText(SignInActivity.this, "Authentication failed.",
									Toast.LENGTH_SHORT).show();
						}

					}
				});

	}
}
