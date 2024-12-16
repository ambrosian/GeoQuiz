package com.example.a20210305032_;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetNameActivity extends AppCompatActivity {
    private TextView mGreetingText;
    private EditText mNameInput;
    private Button mPlayButton;
    private Button mSelectCountryButton;
    private TextView selectedCountryTextView;
    private List<String> countryList;
    private List<Integer> countryFlags;
    private SharedPreferences mPreferences;

    private UserRepository userRepository;

    public static final String PREF_KEY_FIRSTNAME = "PREF_KEY_FIRSTNAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_name);

        selectedCountryTextView = findViewById(R.id.selectedCountryTextView);
        mGreetingText = findViewById(R.id.activity_greeting_text_txt);
        mNameInput = findViewById(R.id.activity_get_name_input);
        mPlayButton = findViewById(R.id.activity_get_name_play_btn);
        mSelectCountryButton = findViewById(R.id.selectCountryButton);

        mPreferences = getPreferences(MODE_PRIVATE);
        userRepository = new UserRepository(this);

        mPlayButton.setEnabled(false);

        countryList = new ArrayList<>(Arrays.asList(
                "Turkey", "Germany", "France", "United States", "Japan",
                "India", "China", "Italy", "Russia", "Brazil",
                "Australia", "Spain", "Mexico", "South Korea", "Canada",
                "United Kingdom", "Argentina", "South Africa", "Netherlands", "Albania", "Kuwait", "Other"
        ));

        countryFlags = new ArrayList<>();
        countryFlags.add(R.drawable.flag_turkey);
        countryFlags.add(R.drawable.ic_flag_of_germany);
        countryFlags.add(R.drawable.france);
        countryFlags.add(R.drawable.unitedstates);
        countryFlags.add(R.drawable.japan);
        countryFlags.add(R.drawable.ic_flag_of_india);
        countryFlags.add(R.drawable.china);
        countryFlags.add(R.drawable.italy);
        countryFlags.add(R.drawable.russia);
        countryFlags.add(R.drawable.ic_flag_of_brazil);
        countryFlags.add(R.drawable.ic_flag_of_australia);
        countryFlags.add(R.drawable.spain);
        countryFlags.add(R.drawable.mexico);
        countryFlags.add(R.drawable.southkorea);
        countryFlags.add(R.drawable.canada);
        countryFlags.add(R.drawable.unitedkingdom);
        countryFlags.add(R.drawable.ic_flag_of_argentina);
        countryFlags.add(R.drawable.ic_flag_of_south_africa);
        countryFlags.add(R.drawable.netherlands);
        countryFlags.add(R.drawable.ic_flag_of_albania);
        countryFlags.add(R.drawable.ic_flag_of_kuwait);
        countryFlags.add(R.drawable.ic_other);

        greetUser();

        mNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPlayButton.setEnabled(s.toString().length() != 0);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        mPlayButton.setOnClickListener(v -> {
            String firstname = mNameInput.getText().toString();
            String selectedCountry = selectedCountryTextView.getText().toString().replace("Your country is: ", "");

            userRepository.updateUser(firstname, selectedCountry);

            mPreferences.edit().putString(PREF_KEY_FIRSTNAME, firstname).apply();
            Intent MainActivityIntent = new Intent(GetNameActivity.this, MainActivity.class);
            startActivity(MainActivityIntent);
            greetUser();
        });

        mSelectCountryButton.setOnClickListener(v -> showCountrySelectionDialog());
    }

    private void greetUser() {
        String firstname = mPreferences.getString(PREF_KEY_FIRSTNAME, null);
        if (firstname != null) {
            String fulltext = "Welcome, " + firstname + "!";
            mGreetingText.setText(fulltext);
            mNameInput.setText(firstname);
            mNameInput.setSelection(firstname.length());
            mPlayButton.setEnabled(true);
        }
    }

    private void showCountrySelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_country, null);
        builder.setView(dialogView);

        RecyclerView recyclerView = dialogView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AlertDialog dialog = builder.create();

        CountryAdapter adapter = new CountryAdapter(this, countryList, countryFlags, country -> {
            if (country.equals("Other")) {
                showCustomCountryDialog();
            } else {
                selectedCountryTextView.setText("Your country is: " + country);
            }
            dialog.dismiss();
        });

        recyclerView.setAdapter(adapter);
        dialog.show();
    }

    private void showCustomCountryDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_custom_country, null);
        builder.setView(dialogView);

        final EditText input = dialogView.findViewById(R.id.customCountryInput);
        Button positiveButton = dialogView.findViewById(R.id.positiveButton);
        Button negativeButton = dialogView.findViewById(R.id.negativeButton);

        AlertDialog dialog = builder.create();

        positiveButton.setOnClickListener(v -> {
            String customCountry = input.getText().toString().trim();
            if (!customCountry.isEmpty()) {
                selectedCountryTextView.setText("Your country is: " + customCountry);
                dialog.dismiss();
            } else {
                input.setError("Please enter a country name");
            }
        });

        negativeButton.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

}
