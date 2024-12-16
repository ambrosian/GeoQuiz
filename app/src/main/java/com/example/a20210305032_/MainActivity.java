package com.example.a20210305032_;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button mCapitals;
    private Button mCountry;
    private Button mFlags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCapitals = findViewById(R.id.capitals_btn);
        mCountry = findViewById(R.id.country_btn);
        mFlags = findViewById(R.id.flags_btn);

        mCapitals.setOnClickListener(view -> startQuizActivity(CapitalsActivity.class));
        mCountry.setOnClickListener(view -> startQuizActivity(CountryActivity.class));
        mFlags.setOnClickListener(view -> startQuizActivity(FlagsActivity.class));
    }

    private void startQuizActivity(Class<?> activityClass) {
        Intent intent = new Intent(MainActivity.this, activityClass);
        startActivity(intent);
    }
}
