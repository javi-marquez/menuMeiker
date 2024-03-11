package com.example.menumaker;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class AuthActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    EditText editTxt_emailAddress, editTxt_password;
    Button btn_login, btn_signup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        mAuth = FirebaseAuth.getInstance();

        editTxt_emailAddress = findViewById(R.id.editTxt_emailAddress);
        editTxt_password = findViewById(R.id.editTxt_password);
        btn_login = findViewById(R.id.btn_login);
        btn_signup = findViewById(R.id.btn_signup);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTxt_emailAddress.getText().toString();
                String password = editTxt_password.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()) signUp(email, password);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTxt_emailAddress.getText().toString();
                String password = editTxt_password.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()) logIn(email, password);
            }
        });

        //updateUITest();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            reload();
        }

    }

    private void signUp(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Toast.makeText(AuthActivity.this, "La contraseña debe ser de 6 caracteres como mínimo",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void logIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Toast.makeText(AuthActivity.this, "Usuario o contraseña incorrectos",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void reload() {
    }

    private void updateUI(FirebaseUser user) {
        Intent intent = new Intent(AuthActivity.this, MainActivity.class);
        intent.putExtra("email", user.getEmail());
        startActivity(intent);
    }

    private void updateUITest() {
        Intent intent = new Intent(AuthActivity.this, MainActivity.class);
        intent.putExtra("email", "paco@gmail.com");
        startActivity(intent);
    }

}