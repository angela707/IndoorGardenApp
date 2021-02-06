package angela.example.indoorgardenapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.firestore.FirebaseFirestore;

public class LogInActivity extends AppCompatActivity {
    Button log_in;
    EditText username, password;
    ProgressBar progressBar;
    FirebaseFirestore fs;
    FirebaseAuth fAuth;
    String usernameInput, passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in2);


        log_in = findViewById(R.id.log_in_button);
        username = findViewById(R.id.log_in_username);
        password = findViewById(R.id.log_in_password);
        progressBar = findViewById(R.id.progress_bar);
        fAuth = FirebaseAuth.getInstance();

        if (fAuth.getCurrentUser() != null) {
            Intent intent = new Intent(LogInActivity.this, PlantsActivity.class);
            startActivity(intent);
            finish();
        }


        log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                usernameInput = username.getText().toString().trim();
                passwordInput = password.getText().toString().trim();

                if (TextUtils.isEmpty(usernameInput)) {
                    username.setError("Username is required.");
                    return;
                }
                if (TextUtils.isEmpty(passwordInput)) {
                    password.setError("Password is required.");
                    return;
                }

                fAuth.signInWithEmailAndPassword(usernameInput, passwordInput).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LogInActivity.this, "Logged in successfully!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LogInActivity.this, PlantsActivity.class);
                            startActivity(intent);
                            finish();

                        }else
                        {
                            Exception exception = task.getException();
                            if  (exception instanceof FirebaseAuthInvalidUserException){
                                username.setError("Email does not exist!");
                            }
                            if (exception instanceof FirebaseAuthInvalidCredentialsException){
                                password.setError("Email does not exist!");
                            }

                            Toast.makeText(LogInActivity.this, "ERROR!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }
}