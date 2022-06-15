package com.example.farmersguide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    Button Login_bt;
    Button Account_bt;
    EditText Email_edtx, Password_edtx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Login_bt = findViewById(R.id.login_bt);
        Account_bt = findViewById((R.id.account_bt));

        Email_edtx = findViewById(R.id.email_edtx);
        Password_edtx = findViewById(R.id.password_edtx);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        Login_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Button Clicked", Toast.LENGTH_SHORT).show();
                String username = Email_edtx.getText().toString();
                String password = Password_edtx.getText().toString();
                mAuth.signInWithEmailAndPassword(username, password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information

                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Intent i = new Intent(MainActivity.this, HomePage.class);
                                    i.putExtra("email", username);
                                    startActivity(i);

                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });

        Account_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Button Clicked", Toast.LENGTH_SHORT).show();
                String username = Email_edtx.getText().toString();
                String password = Password_edtx.getText().toString();
                mAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("message");
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
// sign in success, update U with the signed in users information
                            Toast.makeText(MainActivity.this, "CreateUserWithEmail: success", Toast.LENGTH_SHORT).show();
                            currentUser = mAuth.getCurrentUser();
                            Intent i = new Intent( MainActivity.this, HomePage.class);
                            startActivity(i);


                        }
                        else{
                            Toast.makeText(MainActivity.this, "CreateUserWithEmail: failure", Toast.LENGTH_SHORT).show();
                            currentUser = null;
                        }
                    }
                });
            }
        });



    }
}