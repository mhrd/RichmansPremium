package android.mehrdad.richmanspremium;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MiddleTut extends AppCompatActivity {

    Button mtr, mtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_middle_tut);

        mtr = (Button) findViewById(R.id.mtr);
        mtp = (Button) findViewById(R.id.mtp);

        mtr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tran1();
            }
        });
        mtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tran2();
            }
        });

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    void tran1() {
        Intent i = new Intent(this, LearnPageActivity.class);
        i.putExtra("az", "r");
        startActivity(i);
    }

    void tran2() {
        Intent i = new Intent(this, LearnPageActivity.class);
        i.putExtra("az", "p");
        startActivity(i);
    }
}
