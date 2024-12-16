package com.example.a20210305032_;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a20210305032_.R;
import com.example.a20210305032_.ImageBank;
import com.example.a20210305032_.ImageQuestion;

import java.util.Arrays;
import java.util.Locale;

public class CountryActivity extends AppCompatActivity implements View.OnClickListener {

    public static final long COUNTDOWN_IN_MILLIS = 11000;

    private TextView mCountryQuestion;
    private Button mCountryAnswer1;
    private Button mCountryAnswer2;
    private Button mCountryAnswer3;
    private Button mCountryAnswer4;
    private ImageView mImage;
    private UserRepository userRepository;

    private TextView mScoreDisplay;
    private TextView mNbrofQuestion;
    private TextView mCountDown;
    private ProgressBar mProgressBar;

    private ImageBank mImageBank;
    private ImageQuestion mImageQuestion;

    private int mScore;
    private int mNumberOfQuestions;

    public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";
    public static final String BUNDLE_STATE_SCORE = "currentScore";
    public static final String BUNDLE_STATE_QUESTION = "currentQuestion";

    private boolean mEnableTouchEvents;
    private int mQuestionTotal;
    private int mQuestionCounter;

    private ColorStateList CountDownColor;
    private CountDownTimer mCountDownTimer;
    private long timeLeftInMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        mImageBank = this.generateQuestions();
        mScore = 0;
        mNumberOfQuestions = 10;
        userRepository = new UserRepository(this);

        if (savedInstanceState != null) {
            mScore = savedInstanceState.getInt(BUNDLE_STATE_SCORE);
            mNumberOfQuestions = savedInstanceState.getInt(BUNDLE_STATE_QUESTION);
        } else {
            mScore = 0;
            mNumberOfQuestions = 10;
        }

        mEnableTouchEvents = true;

        mCountryQuestion = findViewById(R.id.country_question_txt);
        mImage = findViewById(R.id.country_image);
        mCountryAnswer1 = findViewById(R.id.country_answer1_btn);
        mCountryAnswer2 = findViewById(R.id.country_answer2_btn);
        mCountryAnswer3 = findViewById(R.id.country_answer3_btn);
        mCountryAnswer4 = findViewById(R.id.country_answer4_btn);

        mScoreDisplay = findViewById(R.id.country_score);
        mNbrofQuestion = findViewById(R.id.country_questions_count);
        mCountDown = findViewById(R.id.country_question_timer);
        mProgressBar = findViewById(R.id.country_progress_bar);

        mCountryAnswer1.setTag(0);
        mCountryAnswer2.setTag(1);
        mCountryAnswer3.setTag(2);
        mCountryAnswer4.setTag(3);

        mCountryAnswer1.setOnClickListener(this);
        mCountryAnswer2.setOnClickListener(this);
        mCountryAnswer3.setOnClickListener(this);
        mCountryAnswer4.setOnClickListener(this);

        mImageQuestion = mImageBank.getImageQuestion();
        this.displayImageQuestion(mImageQuestion);

        mQuestionTotal = 10;
        mQuestionCounter = 1;

        CountDownColor = mCountDown.getTextColors();
        timeLeftInMillis = COUNTDOWN_IN_MILLIS;
        startCountDown();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(BUNDLE_STATE_SCORE, mScore);
        outState.putInt(BUNDLE_STATE_QUESTION, mNumberOfQuestions);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        int responseIndex = (int) v.getTag();

        int taganswer1 = (int) mCountryAnswer1.getTag();
        int taganswer2 = (int) mCountryAnswer2.getTag();
        int taganswer3 = (int) mCountryAnswer3.getTag();
        int taganswer4 = (int) mCountryAnswer4.getTag();

        mCountDownTimer.cancel();

        if(responseIndex == mImageQuestion.getAnswerIndex()){
            Toast toast = new Toast(this);
            View toastView = getLayoutInflater().inflate(R.layout.toast_correct, null);
            TextView textView = toastView.findViewById(R.id.toast_correct_text);
            textView.setText("Correct!");
            toast.setView(toastView);
            toast.setGravity(Gravity.BOTTOM, 0, 100);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();

            v.setBackgroundColor(Color.parseColor("#008000"));
            mScore++;
        }
        else {
            Toast toast = new Toast(this);
            View toastView = getLayoutInflater().inflate(R.layout.toast_wrong, null);
            TextView textView = toastView.findViewById(R.id.toast_wrong_text);
            textView.setText("Wrong answer!");
            toast.setView(toastView);
            toast.setGravity(Gravity.BOTTOM, 0, 100);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();

            v.setBackgroundColor(Color.parseColor("#830000"));

            if(taganswer1 == mImageQuestion.getAnswerIndex()){
                mCountryAnswer1.setBackgroundColor(Color.parseColor("#008000"));
            }
            else if(taganswer2 == mImageQuestion.getAnswerIndex()){
                mCountryAnswer2.setBackgroundColor(Color.parseColor("#008000"));
            }
            else if(taganswer3 == mImageQuestion.getAnswerIndex()){
                mCountryAnswer3.setBackgroundColor(Color.parseColor("#008000"));
            }
            else if(taganswer4 == mImageQuestion.getAnswerIndex()){
                mCountryAnswer4.setBackgroundColor(Color.parseColor("#008000"));
            }
        }

        mEnableTouchEvents = false;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mEnableTouchEvents = true;
                mCountDownTimer.cancel();
                timeLeftInMillis = COUNTDOWN_IN_MILLIS;
                startCountDown();
                if (--mNumberOfQuestions == 0 && mQuestionCounter <= 10) {
                    endGame();
                } else {
                    mImageQuestion = mImageBank.getImageQuestion();
                    displayImageQuestion(mImageQuestion);
                    mQuestionCounter++;

                    mScoreDisplay.setText("Score : " + mScore);
                    mNbrofQuestion.setText(mQuestionCounter + "/10");

                    mProgressBar.setProgress(mQuestionCounter);

                    mCountryAnswer1.setBackgroundResource(R.drawable.button_gradient);
                    mCountryAnswer2.setBackgroundResource(R.drawable.button_gradient);
                    mCountryAnswer3.setBackgroundResource(R.drawable.button_gradient);
                    mCountryAnswer4.setBackgroundResource(R.drawable.button_gradient);
                }
            }
        }, 2000);
    }

    private void startCountDown() {
        mCountDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDown();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateCountDown();
                mCountDownTimer.cancel();
                if (--mNumberOfQuestions == 0) {
                    endGame();
                } else {
                    mImageQuestion = mImageBank.getImageQuestion();
                    displayImageQuestion(mImageQuestion);
                    mQuestionCounter++;
                    mScoreDisplay.setText("Score : " + mScore);
                    mNbrofQuestion.setText(mQuestionCounter + "/10");
                    timeLeftInMillis = COUNTDOWN_IN_MILLIS;
                    startCountDown();
                }
            }
        }.start();
    }

    private void updateCountDown() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        mCountDown.setText(timeFormatted);

        if (timeLeftInMillis < 6000) {
            mCountDown.setTextColor(Color.RED);
        } else {
            mCountDown.setTextColor(CountDownColor);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mEnableTouchEvents && super.dispatchTouchEvent(ev);
    }

    private void endGame() {
        mCountDownTimer.cancel();

        SQLiteDatabase db = userRepository.getWritableDatabase();
        int capitalsBestScore = userRepository.getCapitalsBestScore(db);
        int flagsBestScore = userRepository.getFlagsBestScore(db);
        int countryBestScore = userRepository.getCountryBestScore(db);

        if (mScore > countryBestScore) {
            userRepository.saveBestScores(db, "Player", capitalsBestScore, flagsBestScore, mScore);
            countryBestScore = mScore;
        }

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_well_played, null);
        TextView title = dialogView.findViewById(R.id.dialog_title);
        TextView message = dialogView.findViewById(R.id.dialog_message);
        Button button = dialogView.findViewById(R.id.dialog_button);

        message.setText("Your score is " + mScore + "/10\n" +
                "Best Scores:\n" +
                "Capitals: " + capitalsBestScore + "\n" +
                "Flags: " + flagsBestScore + "\n" +
                "Country: " + countryBestScore);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView)
                .setCancelable(false)
                .create();

        AlertDialog alertDialog = builder.show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                finish();
            }
        });
    }

    private void displayImageQuestion(final ImageQuestion imageQuestion) {
        mCountryAnswer1.setBackgroundResource(R.drawable.button_gradient);
        mCountryAnswer2.setBackgroundResource(R.drawable.button_gradient);
        mCountryAnswer3.setBackgroundResource(R.drawable.button_gradient);
        mCountryAnswer4.setBackgroundResource(R.drawable.button_gradient);

        mCountryQuestion.setText(imageQuestion.getImageQuestion());
        mImage.setImageResource(imageQuestion.getImage());
        mCountryAnswer1.setText(imageQuestion.getChoiceList().get(0));
        mCountryAnswer2.setText(imageQuestion.getChoiceList().get(1));
        mCountryAnswer3.setText(imageQuestion.getChoiceList().get(2));
        mCountryAnswer4.setText(imageQuestion.getChoiceList().get(3));
    }

    private ImageBank generateQuestions() {

        ImageQuestion imageQuestion1 = new ImageQuestion("What is this country?",
                R.drawable.ic_shape_of_france, Arrays.asList("France", "Germany", "Spain", "Belgium"),
                0);

        ImageQuestion imageQuestion2 = new ImageQuestion("In which country is this place located?",
                R.drawable.eiffel_tower, Arrays.asList("Germany", "France", "Belgium", "Italy"),
                1);

        ImageQuestion imageQuestion3 = new ImageQuestion("What is this country?",
                R.drawable.ic_shape_of_spain, Arrays.asList("Portugal", "Italy", "Spain", "Switzerland"),
                2);

        ImageQuestion imageQuestion4 = new ImageQuestion("In which country is this place located?",
                R.drawable.sagrada_familia, Arrays.asList("France", "Portugal", "Italy", "Spain"),
                3);

        ImageQuestion imageQuestion5 = new ImageQuestion("What is this country?",
                R.drawable.ic_shape_of_canada, Arrays.asList("Canada", "United States", "Russia", "Australia"),
                0);

        ImageQuestion imageQuestion6 = new ImageQuestion("In which country is this place located?",
                R.drawable.cn_tower, Arrays.asList("United States", "Canada", "Germany", "Australia"),
                1);

        ImageQuestion imageQuestion7 = new ImageQuestion("What is this country?",
                R.drawable.shape_of_russia, Arrays.asList("China", "Ukraine", "Russia", "Kazakhstan"),
                2);

        ImageQuestion imageQuestion8 = new ImageQuestion("In which country is this place located?",
                R.drawable.moscow_basile, Arrays.asList("Ukraine", "Bulgaria", "Romania", "Russia"),
                3);

        ImageQuestion imageQuestion9 = new ImageQuestion("What is this country?",
                R.drawable.shape_of_australia, Arrays.asList("Australia", "Canada", "United States", "Russia"),
                0);

        ImageQuestion imageQuestion10 = new ImageQuestion("In which country is this place located?",
                R.drawable.sydney_opera, Arrays.asList("United States", "Australia", "Canada", "Russia"),
                1);

        return new ImageBank(Arrays.asList(
                imageQuestion1,
                imageQuestion2,
                imageQuestion3,
                imageQuestion4,
                imageQuestion5,
                imageQuestion6,
                imageQuestion7,
                imageQuestion8,
                imageQuestion9,
                imageQuestion10
        ));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }
}
