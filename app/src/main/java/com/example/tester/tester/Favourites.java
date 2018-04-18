package com.example.tester.tester;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Favourites extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        Button backButton = (Button)findViewById(R.id.favouritesMenuButton);


        backButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Intent myIntent = new Intent(v.getContext(), MainMenu.class);
                        startActivity(myIntent);
                    }
                }
        );
    }
}
