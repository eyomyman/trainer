package com.example.tester.tester;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by peteris on 4/3/18.
 */

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button registerButton = (Button)findViewById(R.id.register_button);
        Button loginButton = (Button)findViewById(R.id.loginButton);

        registerButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Intent myIntent = new Intent(v.getContext(), Register.class);
                        startActivity(myIntent);
                    }
                }
        );
        loginButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Intent myIntent = new Intent(v.getContext(), MainMenu.class);
                        startActivity(myIntent);
                    }
                }
        );
    }
}
