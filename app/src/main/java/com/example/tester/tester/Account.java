package com.example.tester.tester;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class Account extends AppCompatActivity {

    private static final String TAG = "Account";
    private TextView phoneView;
    private TextView emailView;
    private TextView usernameView;
    private Button backButton;
    private Button saveButton;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;
    private FirebaseDatabase mFirebaseDatabase;
    private String userID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        backButton = (Button) findViewById(R.id.backButton);
        saveButton = (Button) findViewById(R.id.saveButton);
        phoneView = (TextView) findViewById(R.id.phoneAccount);
        usernameView = (TextView) findViewById(R.id.usernameAccount);
        emailView = (TextView) findViewById(R.id.emailAccount);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid().toString();


        backButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Intent myIntent = new Intent(v.getContext(), MainMenu.class);
                        startActivity(myIntent);
                    }
                }
        );
        saveButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        //here you save changes to db
                        UpdateUserData();
                    }
                }
        );

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                showData(dataSnapshot);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });

    }

    private void showData(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            User userInfo = new User();
            userInfo.setEmail(ds.child(userID).getValue(User.class).getEmail());
            userInfo.setPhone(ds.child(userID).getValue(User.class).getPhone());
            userInfo.setUsername(ds.child(userID).getValue(User.class).getUsername());

            Log.d(TAG, "showData: username: " + userInfo.getUsername());
            Log.d(TAG, "showData: phone: " + userInfo.getPhone());
            Log.d(TAG, "showData: email: " + userInfo.getEmail());

            ArrayList<String> array = new ArrayList<>();

            phoneView.setText(userInfo.getPhone());
            emailView.setText(userInfo.getEmail());
            usernameView.setText(userInfo.getUsername());
        }
    }

    private void UpdateUserData() {
        final String username = usernameView.getText().toString().trim();
        final String phone = phoneView.getText().toString().trim();
        final String email = emailView.getText().toString().trim();
        String userID = firebaseAuth.getCurrentUser().getUid();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Fields can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }


        mDatabase.child("users").child(userID).child("username").setValue(username);
        mDatabase.child("users").child(userID).child("phone").setValue(phone);
        mDatabase.child("users").child(userID).child("email").setValue(email);
    }
}
