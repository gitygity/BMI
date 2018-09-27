package com.example.gity.bmi;
/*
 this class display amount of calories needed to stay constant
        or decrease or increase weight according the level of user's activity in during the day.
        in addition,it recommend amount of carbohydrate, protein and fat needed to stay constant
        or decrease or increase weight to the user.
         in addition this class calculate the number of weeks for increas or decreas weight (+_0.5 kg per week) according status weight.
*/


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class RecommendDiet extends AppCompatActivity implements View.OnClickListener {

    ImageView statusUSER;
    //there are for display satuse and recommendations to user
    TextView statusw, diffw, idealw, calery, nomweek, carbo, pro, fatt, bmr;
    //this button is for tracking weight in weekly.
    Button btntrack;
    Double carbohydarat;
    Double protein;
    Double fat;
    //the number of week that user needed to increas or decreas her/his weight.
    int numberWeek;


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btntrack) {
            Toast.makeText(this, "this feature will be added to the app very soon", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_diet);
        //define widgets to java file
        define();

        if (!getIntent().equals(null)) {

            Double BMR = Double.parseDouble(getIntent().getStringExtra(CalculateBmi.KEY_bmr));
            Double caleryUser = Double.parseDouble(getIntent().getStringExtra(CalculateBmi.KEY_CALERY));
            Double idealWeight = Double.parseDouble(getIntent().getStringExtra(CalculateBmi.KEY_IdealWeight));
            Double diff = Double.parseDouble(getIntent().getStringExtra(CalculateBmi.KEY_diffW));
            Double statusUser = Double.parseDouble(getIntent().getStringExtra(CalculateBmi.KEY_statusUser));
            Double StatusWeight = Double.parseDouble(getIntent().getStringExtra(CalculateBmi.KEY_statusWeight));
            //losing or increasing 0.5 kilos every week.
            numberWeek = (int) (diff / (0.5));
            //this for giving diet according status of weight user.
            //for increasing weight
            if (StatusWeight == 0.0) {
                diffw.setText("different weight: -" + diff);
                caleryUser = caleryUser + 550;
                carbohydarat = 55 * caleryUser / 100;
                protein = 15 * caleryUser / 100;
                fat = 30 * caleryUser / 100;
                //for normal user
            } else if (StatusWeight == 1.0) {
                diffw.setText("different weight: " + diff);
                carbohydarat = 55 * caleryUser / 100;
                protein = 15 * caleryUser / 100;
                fat = 30 * caleryUser / 100;
                //for increasing weight user
            } else if (StatusWeight == 2.0) {
                diffw.setText("different weight: +" + diff);
                caleryUser = caleryUser - 550;
                carbohydarat = 55 * caleryUser / 100;
                protein = 15 * caleryUser / 100;
                fat = 30 * caleryUser / 100;
            }

            if (statusUser == 1) {
                statusw.setText("status: Fat");
                statusUSER.setImageResource(R.drawable.fat);
            } else if (statusUser == 2) {
                statusw.setText("status: Thin");
                statusUSER.setImageResource(R.drawable.skinny);
            } else if (statusUser == 0) {
                statusw.setText("status: normal");
                statusUSER.setImageResource(R.drawable.weight);
            }

            bmr.setText("BMR: " + BMR);
            idealw.setText("Ideal weight: " + idealWeight);
            calery.setText("calery: " + caleryUser);
            if (numberWeek == 0) {
                nomweek.setText("number week: " + "continusly");
            } else {
                nomweek.setText("number week: " + numberWeek);
            }
            carbo.setText("carbohydarat: %" + (double) Math.round(carbohydarat * 100) / 100);
            pro.setText("protein: %" + (double) Math.round(protein * 100) / 100);
            fatt.setText("fat: %" + (double) Math.round(fat * 100) / 100);
        }
    }

    private void define() {
        btntrack = (Button) findViewById(R.id.btntrack);
        btntrack.setOnClickListener(this);
        statusUSER = (ImageView) findViewById(R.id.imagestatus);
        fatt = (TextView) findViewById(R.id.fat);
        statusw = (TextView) findViewById(R.id.statusw);
        bmr = (TextView) findViewById(R.id.bmr);
        calery = (TextView) findViewById(R.id.caley);
        carbo = (TextView) findViewById(R.id.carbo);
        pro = (TextView) findViewById(R.id.pro);
        diffw = (TextView) findViewById(R.id.diffw);
        idealw = (TextView) findViewById(R.id.idealw);
        nomweek = (TextView) findViewById(R.id.nomweek);

    }


}


