package org.dynamicsoft.caloriescope;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.dynamicsoft.accelerometerstepcounter.*;

public class MainActivity extends AppCompatActivity {

    TextView txt1;
    Button btn1;
    int val=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt1 = findViewById(R.id.txt1);
        btn1 = findViewById(R.id.btn1);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Main obj = new Main();

                val = obj.numSteps;

                obj.startCounting();
                txt1.setText(val);

            }
        });
    }
}
