package com.example.gity.bmi;

/*
    bmi class calculate and display body mass index(BMI) and "BMR" and "calories" and "weight status" for user according to
     height, age, level of activity,gender and weight of user.

*/

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

public class CalculateBmi extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {


    //these TextViews used or displaying informations and errors to user
    TextView bmi, errorh, errorw, status, errora;
    //these EditTexts used for getting weight and height and age from user
    EditText weight, height, age;
    //image for user status
    ImageView statuspic;
    //0 is for normal status.1 is for fat status and 2 is for skinny status.
    static int status_user = 0;
    //spinner for selecting the type of user acticity
    Spinner activity;
    //these for selecting gender
    RadioButton female, male;
    RadioGroup sexRG;
    //this is a button for calculating mbi
    Button btnmbi;
    //this is a button for recommend diet to user
    Button btnrecomend;
    //this variable is for level of activity in daily
    long activityU = 0;
    //this is metabolism of user's body
    private Double bmr;
    //this is calery that user need that acoording level of activity and bmr.
    private Double cal;
    //this is ideal weight that it calculates with average of  minimum and maximum of ideal weight(W1+W2/2).
    private Double idealweight;
    //different between ideal weight and current weight.
    private Double diffWeight;
    //can user lose/increas weight or not.
    int status_weight;
    //is anydata insert in fileds by user or not
    boolean data;
    //these are keys for putextra
    static String KEY_CALERY = "calery";
    static String KEY_bmr = "bmr";
    static String KEY_IdealWeight = "idealweight";
    static String KEY_diffW = "diffweight";
    static String KEY_statusWeight = "Sweight";
    static String KEY_statusUser = "statususer";


    //hh and h is for height.
    //w, W1,W2 is for weght.
    //BMI IS for body mass index
    //flag is used for validating fileds that user entered


    //this method is for activity buttons.
    @Override
    public void onClick(View v) {
        //this button is for calculating BMI and evalusting fields that user filled them
        if (v.getId() == R.id.btnMBI) {


            //try/catch structure prevents entering character into fields by user
            try {
                //empty textviews
                errorh.setText("");
                errorw.setText("");
                errora.setText("");
                bmi.setText("");
                status.setText("");


                //get weight,height and age and then converting to string and finally delete space characters inside every fields
                String W = weight.getText().toString().trim();
                String H = height.getText().toString().trim();
                String A = age.getText().toString().trim();

                //this condition checks "w" is empty or not.
                if (TextUtils.isEmpty(W)) {
                    errorw.setText("Plz enter your weight!");
                }
                //this condition checks "h" is empty or not.
                if (TextUtils.isEmpty(H)) {
                    errorh.setText("Plz enter your height!");
                }
                //this condition checks "a" is empty or not.
                if (TextUtils.isEmpty(A)) {
                    errora.setText("Plz enter your AGE!");
                }


                if (!TextUtils.isEmpty(W) && !TextUtils.isEmpty(H) && !TextUtils.isEmpty(A)) {
                    //convert string to Double
                    Double w = Double.parseDouble(W);
                    Double h = Double.parseDouble(H);
                    Double a = Double.parseDouble(A);
                    //convert centimeter to meter
                    Double hh = h / 100;
                    Double BMI = 0.0;
                    boolean flag = true;
                    Double height1 = 0.0;
                    if (a == 0.0) {
                        errora.setText("plz enter age >0 ");
                        flag = false;
                    }


//checking weight that user entered
                    if (w <= 25) {
                        errorw.setText("Plz enter weight between 26 and 249 kg");
                        //setting flag to false if user entered small weight.
                        flag = false;
                    }

                    if (w >= 250) {
                        errorw.setText("Plz enter weight between 26 and 249 kg");
                        //setting flag to false if user entered large weight.
                        flag = false;
                    }
                    //checking height that user entered
                    if (h > 220 || h > 2.2 && !(h <= 220 && h >= 100)) {
                        errorh.setText("Plz enter height between 100 and 220 cm and 1 and 2.2 m");
                        //setting flag to false if user entered large height.
                        flag = false;
                    }
                    if (h < 1 || h < 100 && !(h <= 2.2 && h >= 1)) {
                        errorh.setText("Plz enter height between 100 and 220 cm and 1 and 2.2 m");
                        //setting flag to false if user entered small height.
                        flag = false;
                    }
//if flag has true value, calculate bmi index
                    if (flag) {

                        Double W1 = 0.0, W2 = 0.0;
                        //if user entered fields in meter unit.
                        if (h >= 1 && h <= 2.2) {
                            Double BMI_index = w / (h * h);

                            BMI = (double) Math.round(BMI_index * 100) / 100;
                            W1 = (double) Math.round((18 * (Math.pow(h, 2))) * 100) / 100;
                            W2 = (double) Math.round((25 * (Math.pow(h, 2))) * 100) / 100;
                            height1 = h / 100;
                        }
                        //if user entered fields in centimeter unit.
                        if (h >= 100 && h <= 220) {

                            Double BMI_index = w / (hh * hh);
                            BMI = (double) Math.round(BMI_index * 100) / 100;
                            W1 = (double) Math.round((18 * (Math.pow(hh, 2))) * 100) / 100;
                            W2 = (double) Math.round((25 * (Math.pow(hh, 2))) * 100) / 100;
                            height1 = h;

                        }
//setting bmi textview with calculated value
                        bmi.setText(BMI + "");

//if bmi has small value "<18" ,status will changed to "thin" and display ideal weight to having proper bmi
                        if (BMI < 18) {
                            status.setText("you are thin.your weight must be " + W1 + "<your weight<" + W2);
                            statuspic.setImageResource(R.drawable.skinny);
                            status_user = 2;

                        }
                        //if bmi has large value ">25" ,status will changed to "fat" and display ideal weight to having proper bmi

                        if (BMI > 25) {
                            status.setText("you are fat.your weight must be " + W1 + "<your weight<" + W2);
                            statuspic.setImageResource(R.drawable.fat);
                            status_user = 1;

                        }
                        //its for normal status
                        if (BMI <= 25 && BMI >= 18) {
                            status.setText("you are normal:)");
                            statuspic.setImageResource(R.drawable.weight);
                            status_user = 0;
                        }

                        //this methon is for calculate caleri
                        cal_caleri(w, height1, a, activityU);
                        //this method is for calculate ideal weight
                        cal_IdealWeight(W1, W2, w);
                        //it means data insert into fileds and user can use recommendation.
                        data = true;

                    }
                }
            } catch (Exception ex) {
                bmi.setText("dont enter character!");
            }

        }
        //this button is for recommending diet to user according data that user insert them
        else if (v.getId() == R.id.btnrecommend) {
            if (data) {
                Intent in = new Intent(CalculateBmi.this, RecommendDiet.class);

                in.putExtra(KEY_CALERY, (double) Math.round(cal * 100) / 100 + "");
                in.putExtra(KEY_IdealWeight, (double) Math.round(idealweight * 100) / 100 + "");
                in.putExtra(KEY_diffW, (double) Math.round(diffWeight * 100) / 100 + "");
                in.putExtra(KEY_bmr, (double) Math.round(bmr * 100) / 100 + "");
                in.putExtra(KEY_statusWeight, status_weight + "");
                in.putExtra(KEY_statusUser, status_user + "");

                startActivity(in);
            } else {
                //if user clicks recommendation button but he/she doesnt insert data, we display an alert dialog to his/her.
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Warning")
                        .setMessage("Pleas enter data in above canvas and press BMI button and then press recommendation button.")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();

            }

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_bmi);

        //this used for  identifying widgets to java file"MainActivity"
        define();

        //define alert dialog for giving information about this activity
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_info)

                .setMessage("you should enter weight, height, age, gender and the level of your activity at the first " +
                        "and then touch bmi button. your status will be displayed into below canvas. \n" +
                        "if you want a diet recommendation for your situation, you must touch recommendation button.")
                .setTitle("information")
                .setPositiveButton("got it", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
        builder.create().show();

        //define spinner for selecting the level of activity by user
        activity.setOnItemSelectedListener(this);
        List<String> list_activity = new ArrayList<String>();
        list_activity.add("Sedentary-little or no exercice");
        list_activity.add("Lightly-exercice/sports 1-3 times/week");
        list_activity.add("Moderately-exercice/sports 3-5 times/week");
        list_activity.add("Very Active-hard exercice/sports 6-7 times/week");
        list_activity.add("Extra Active-very hard exercice/sports or physical job");
        ArrayAdapter<String> dataadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list_activity);
        dataadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activity.setAdapter(dataadapter);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        activityU = parent.getItemIdAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //calculating ideal weight for men and women and than calculate different current weight and ideal weight.this method determin status weight.
    private void cal_IdealWeight(Double W1, Double W2, Double w) {
        int id = sexRG.getCheckedRadioButtonId();
        //for woman
        if (id == R.id.F) {
            idealweight = (W1 + W2) / 2;
        }
        //for men
        if (id == R.id.M) {
            idealweight = (W1 + W2) / 2;
        }


        if (w < idealweight) {
            status_weight = 0;
            diffWeight = Math.abs(w - idealweight);
        } else if (w > idealweight) {
            status_weight = 2;
            diffWeight = Math.abs(w - idealweight);
        } else {
            status_weight = 1;
            diffWeight = Math.abs(w - idealweight);
        }
    }


    private void cal_caleri(Double w, Double height1, Double a, long activityU) {
        //get id of radiobutton
        int id = sexRG.getCheckedRadioButtonId();


        if (id == R.id.F) {
            //calculate bmr for women
            bmr = 655 + (9.6 * w) + (1.8 * height1) - (4.7 * a);
        }

        if (id == R.id.M) {
            //calculate bmr for men
            bmr = 66 + (13.7 * w) + (5 * height1) - (6.8 * a);
        }
        //calculate calery according to level of activity
        switch ((int) activityU) {
            case 0:
                cal = bmr * 1.2;
                break;
            case 1:
                cal = bmr * 1.375;
                break;
            case 2:
                cal = bmr * 1.55;
                break;
            case 3:
                cal = bmr * 1.725;
                break;
            case 4:
                cal = bmr * 1.9;
                break;
        }
    }

    //method for identifing widgets to java file.
    private void define() {
        btnmbi = (Button) findViewById(R.id.btnMBI);
        btnrecomend = (Button) findViewById(R.id.btnrecommend);
        btnmbi.setOnClickListener(this);
        btnrecomend.setOnClickListener(this);
        bmi = (TextView) findViewById(R.id.bmi);
        status = (TextView) findViewById(R.id.status);
        errorh = (TextView) findViewById(R.id.errorh);
        errorw = (TextView) findViewById(R.id.errorw);
        errora = (TextView) findViewById(R.id.errora);
        weight = (EditText) findViewById(R.id.weight);
        height = (EditText) findViewById(R.id.height);
        age = (EditText) findViewById(R.id.age);
        statuspic = (ImageView) findViewById(R.id.statuspicc);
        activity = (Spinner) findViewById(R.id.activity_user);
        sexRG = (RadioGroup) findViewById(R.id.rg);
        female = (RadioButton) findViewById(R.id.F);
        male = (RadioButton) findViewById(R.id.M);
    }
}
