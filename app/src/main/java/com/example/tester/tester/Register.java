package com.example.tester.tester;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextPasswordRepeat;
    private EditText editTextUsername;
    private EditText editTextPhone;
    private ProgressDialog progressDialog;
    private Button completeRegistrationButton;

    private static final String d = "Register";


    private DatabaseReference nDatabase;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        editTextEmail = (EditText) findViewById(R.id.emailRegister);
        editTextPassword = (EditText) findViewById(R.id.passwordRegister);
        editTextPasswordRepeat = (EditText) findViewById(R.id.passwordRepeatRegister);
        editTextUsername = (EditText) findViewById(R.id.usernameRegister);
        editTextPhone = (EditText) findViewById(R.id.phoneRegister);
        completeRegistrationButton = (Button) findViewById(R.id.completeRegistrationButton);

        nDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        progressDialog = new ProgressDialog(this);
        completeRegistrationButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        //Getting string values from user input
                        String password = editTextPassword.getText().toString().trim();
                        String passwordRepeat = editTextPasswordRepeat.getText().toString().trim();
                        String username = editTextUsername.getText().toString().trim();
                        String phone = editTextPhone.getText().toString().trim();
                        String email = editTextEmail.getText().toString().trim();


                        nDatabase.child("Users").orderByChild("username").equalTo(username).addListenerForSingleValueEvent(
                                new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            // User Exists
                                            // Do your stuff here if user already exists
                                            Toast.makeText(getApplicationContext(), "Username already exists. Please try other username.", Toast.LENGTH_SHORT).show();
                                        } else {
                                            String password = editTextPassword.getText().toString().trim();
                                            String passwordRepeat = editTextPasswordRepeat.getText().toString().trim();
                                            String username = editTextUsername.getText().toString().trim();
                                            String phone = editTextPhone.getText().toString().trim();
                                            String email = editTextEmail.getText().toString().trim();
                                            registerUser(password, passwordRepeat, username, phone, email);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                }

                        );


                        registerUser(password, passwordRepeat, username, phone, email);
                    }
                }
        );
    }

    private void registerUser(String password, String passwordRepeat, String username, String phone, String email) {

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(passwordRepeat)
                || TextUtils.isEmpty(username) || TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Fields cant be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(passwordRepeat)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() > 15) {
            Toast.makeText(this, "Passwords is too long(longer than 15)", Toast.LENGTH_SHORT).show();
            return;
        }
        if (username.length() > 14) {
            Toast.makeText(this, "Username is too long(longer than 14)", Toast.LENGTH_SHORT).show();
            return;
        }
        if (email.length() > 35 || !email.matches("^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})")) {
            Toast.makeText(this, "E-mail is invalid/too long(longer than 35)", Toast.LENGTH_SHORT).show();
            return;
        }
        if (phone.length() > 25 || !phone.matches("\\d+")) {
            Toast.makeText(this, "Phone is not valid", Toast.LENGTH_SHORT).show();
            return;
        }


        progressDialog.setMessage("Working...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String userID = firebaseAuth.getCurrentUser().getUid();
                            nDatabase.child(userID);
                            DatabaseReference currentUserDB = nDatabase.child(userID);

                            final String usernameAdd = editTextUsername.getText().toString().trim();
                            final String phoneAdd = editTextPhone.getText().toString().trim();
                            final String emailAdd = editTextEmail.getText().toString().trim();

                            currentUserDB.child("username").setValue(usernameAdd);
                            currentUserDB.child("email").setValue(emailAdd);
                            currentUserDB.child("phone").setValue(phoneAdd);
                            Toast.makeText(Register.this, "Registration was successful", Toast.LENGTH_SHORT).show();
                            Log.i(d, "ERROR IN DISPLAYING TOAST?!?!?! ");
                        } else {
                            Toast.makeText(Register.this, "Error registering, please try again later...", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });


        Intent myIntent = new Intent(getApplicationContext(), MainMenu.class);
        startActivity(myIntent);

    }
}
