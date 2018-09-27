package com.example.gity.bmi;
/*
  this application wrote by monireh ghasemi in android studio 2.3.3 .

  - in CalculateBmi class calculate and display "MBI" and "BMR" and "calories" and "weight status" for user.
  - in RecommendDiet class calculate and recommend amount of carbohydrate, protein and fat needed to stay constant
        or decrease or increase weight to the user.in addition this class calculate the number of weeks for increas or decreas weight (+_0.5 kg per week)
*/

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
Button bmi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bmi= (Button) findViewById(R.id.bmi);
        bmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(MainActivity.this,CalculateBmi.class);
                startActivity(in);
            }
        });
    }


}
