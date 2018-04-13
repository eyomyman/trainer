package com.example.tester.tester;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Button accountButton = (Button)findViewById(R.id.accountButton);
        Button addButton = (Button)findViewById(R.id.addButton);


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
    }
}
