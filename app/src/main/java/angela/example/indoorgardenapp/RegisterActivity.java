package angela.example.indoorgardenapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {

    public static String USER_COLLECTION = "User_Collection";
    EditText name, last_name, email, username, password, re_enter_password;
    Button sign_up;
    FirebaseAuth fAuth;
    String nameInput, lastNameInput, emailInput, usernameInput, passwordInput, passwordInput2;
    ProgressBar progressBar;
    FirebaseFirestore fs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = findViewById(R.id.register_name);
        last_name = findViewById(R.id.register_last_name);
        email = findViewById(R.id.register_email);
        username = findViewById(R.id.register_username);
        password = findViewById(R.id.register_password);
        re_enter_password = findViewById(R.id.register_password2);
        sign_up = findViewById(R.id.sign_up_button);
        progressBar = findViewById(R.id.progress_bar);

        fs = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        if (fAuth.getCurrentUser() != null) {
            Intent intent = new Intent(RegisterActivity.this, PlantsActivity.class);
            startActivity(intent);
            finish();
        }

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameInput = name.getText().toString().trim();
                lastNameInput = last_name.getText().toString().trim();
                emailInput = email.getText().toString().trim();
                usernameInput = username.getText().toString().trim();
                passwordInput = password.getText().toString().trim();
                passwordInput2 = re_enter_password.getText().toString().trim();

                // conditions
                if (TextUtils.isEmpty(nameInput)) {
                    name.setError("Name is required.");
                    return;
                }
                if (TextUtils.isEmpty(lastNameInput)) {
                    last_name.setError("Last name is required.");
                    return;
                }
                if (TextUtils.isEmpty(emailInput)) {
                    email.setError("Email is required.");
                    return;
                }
                if (TextUtils.isEmpty(usernameInput)) {
                    username.setError("Username is required.");
                    return;
                }
                if (TextUtils.isEmpty(passwordInput)) {
                    password.setError("Password is required.");
                    return;
                }
                if (passwordInput.length() < 6) {
                    password.setError("Password must be 6 characters or more");
                }

                if (TextUtils.isEmpty(passwordInput2)) {
                    re_enter_password.setError("Please re-enter password.");
                    return;
                }

                if (!passwordInput.equals(passwordInput2)) {
                    re_enter_password.setError("Passwords don't match.");
                    return;
                }

                registerUser();
                //register the user


            }
        });

    }


    public void registerUser() {
        progressBar.setVisibility(View.VISIBLE);
        emailInput = email.getText().toString().trim();
        passwordInput = password.getText().toString().trim();

        fAuth.createUserWithEmailAndPassword(emailInput, passwordInput).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    updateUserInfo(task);
                } else {
                    Toast.makeText(RegisterActivity.this, "User already exists!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    public void updateUserInfo(Task<AuthResult> task) {
        usernameInput = username.getText().toString().trim();
        FirebaseUser currentUser = task.getResult().getUser();

        if (currentUser != null)
        {
            UserProfileChangeRequest.Builder req = new UserProfileChangeRequest.Builder();
            req.setDisplayName(usernameInput);

            currentUser.updateProfile(req.build()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task1) {
                    if (task1.isSuccessful()){
                        addUserToDB(task);
                    }
                    else
                    {
                        Toast.makeText(RegisterActivity.this, "User update error!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void addUserToDB(Task<AuthResult> task){

        FirebaseFirestore fs = FirebaseFirestore.getInstance();
        CollectionReference users = fs.collection(USER_COLLECTION);

        String uid = task.getResult().getUser().getUid();

        UserProfile user = new UserProfile (uid, nameInput, lastNameInput, emailInput, usernameInput, passwordInput, 0, 0);

        users.document(uid).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task1) {
                if (task1.isSuccessful()){
                    Intent intent = new Intent(RegisterActivity.this, PlantsActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(RegisterActivity.this, "Error adding to database!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}