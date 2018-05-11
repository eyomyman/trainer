package com.example.tester.tester;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class MainMenu extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private Button accountButton;
    private Button addButton;
    private Button signOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        accountButton = (Button) findViewById(R.id.accountButton);
        addButton = (Button) findViewById(R.id.addRegisterButton);
        signOut = (Button) findViewById(R.id.signOutButton);

        //Checks if user is logged in, if not redirects them to login activity
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user == null) {
            Intent myIntent = new Intent(this, MainMenu.class);
            startActivity(myIntent);
        }


        //Listeners for menu buttons
        accountButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Intent myIntent = new Intent(v.getContext(), Account.class);
                        startActivity(myIntent);
                    }
                }
        );
        addButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Intent myIntent = new Intent(v.getContext(), AddTask.class);
                        startActivity(myIntent);
                    }
                }
        );

        signOut.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        FirebaseAuth.getInstance().signOut();
                        Intent myIntent = new Intent(v.getContext(), MainActivity.class);
                        startActivity(myIntent);
                    }
                }
        );
    }
}
