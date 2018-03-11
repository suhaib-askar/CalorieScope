package org.dynamicsoft.CalorieScope.HealthCalculator;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage, result_textview;
    private float weight, height, waist, hip, result;
    private Button calculate;
    private EditText weight_waist, height_hip;
    private int state = 0;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_bmi:
                    state = 0;
                    weight_waist.setHint("Weight (kg)");
                    height_hip.setHint("Height (m)");
                    weight_waist.setText(null);
                    height_hip.setText(null);
                    result_textview.setText(null);
                    return true;
                case R.id.navigation_whr:
                    state = 1;
                    weight_waist.setHint("Waist (inch)");
                    height_hip.setHint("Hip (inch)");
                    weight_waist.setText(null);
                    height_hip.setText(null);
                    result_textview.setText(null);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_whr);

        mTextMessage = findViewById(R.id.message);
        result_textview = findViewById(R.id.result_textview);
        weight_waist = findViewById(R.id.weight_waist);
        height_hip = findViewById(R.id.height_hip);
        calculate = findViewById(R.id.calculate);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if(state==0){
                    weight = Float.valueOf(weight_waist.getText().toString());
                    height = Float.valueOf(height_hip.getText().toString());
                    result=weight/(height*height);
                    result_textview.setText("BMI: "+String.valueOf(result));
                }
                if(state == 1){
                    waist = Float.valueOf(weight_waist.getText().toString());
                    hip = Float.valueOf(height_hip.getText().toString());
                    result=waist/hip;
                    result_textview.setText("WHR: "+String.valueOf(result));
                }
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
}