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

public class FlagsActivity extends AppCompatActivity implements View.OnClickListener {

    public static final long COUNTDOWN_IN_MILLIS = 11000;

    private TextView mFlagsQuestion;
    private Button mFlagsAnswer1;
    private Button mFlagsAnswer2;
    private Button mFlagsAnswer3;
    private Button mFlagsAnswer4;
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
        setContentView(R.layout.activity_flags);
        userRepository = new UserRepository(this);

        mImageBank = this.generateQuestions();
        mScore = 0;
        mNumberOfQuestions = 10;

        if (savedInstanceState != null) {
            mScore = savedInstanceState.getInt(BUNDLE_STATE_SCORE);
            mNumberOfQuestions = savedInstanceState.getInt(BUNDLE_STATE_QUESTION);
        } else {
            mScore = 0;
            mNumberOfQuestions = 10;
        }

        mEnableTouchEvents = true;

        mFlagsQuestion = findViewById(R.id.flags_question_txt);
        mImage = findViewById(R.id.flags_image);
        mFlagsAnswer1 = findViewById(R.id.flags_answer1_btn);
        mFlagsAnswer2 = findViewById(R.id.flags_answer2_btn);
        mFlagsAnswer3 = findViewById(R.id.flags_answer3_btn);
        mFlagsAnswer4 = findViewById(R.id.flags_answer4_btn);

        mScoreDisplay = findViewById(R.id.flags_score);
        mNbrofQuestion = findViewById(R.id.flags_questions_count);
        mCountDown = findViewById(R.id.flags_question_timer);
        mProgressBar =findViewById(R.id.flags_progress_bar);

        // Use the tag property to 'name' the buttons
        mFlagsAnswer1.setTag(0);
        mFlagsAnswer2.setTag(1);
        mFlagsAnswer3.setTag(2);
        mFlagsAnswer4.setTag(3);

        mFlagsAnswer1.setOnClickListener(this);
        mFlagsAnswer2.setOnClickListener(this);
        mFlagsAnswer3.setOnClickListener(this);
        mFlagsAnswer4.setOnClickListener(this);

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

        int taganswer1 = (int) mFlagsAnswer1.getTag();
        int taganswer2 = (int) mFlagsAnswer2.getTag();
        int taganswer3 = (int) mFlagsAnswer3.getTag();
        int taganswer4 = (int) mFlagsAnswer4.getTag();

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
                mFlagsAnswer1.setBackgroundColor(Color.parseColor("#008000"));
            }
            else if(taganswer2 == mImageQuestion.getAnswerIndex()){
                mFlagsAnswer2.setBackgroundColor(Color.parseColor("#008000"));
            }
            else if(taganswer3 == mImageQuestion.getAnswerIndex()){
                mFlagsAnswer3.setBackgroundColor(Color.parseColor("#008000"));
            }
            else if(taganswer4 == mImageQuestion.getAnswerIndex()){
                mFlagsAnswer4.setBackgroundColor(Color.parseColor("#008000"));
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

                    mFlagsAnswer1.setBackgroundResource(R.drawable.button_gradient);
                    mFlagsAnswer2.setBackgroundResource(R.drawable.button_gradient);
                    mFlagsAnswer3.setBackgroundResource(R.drawable.button_gradient);
                    mFlagsAnswer4.setBackgroundResource(R.drawable.button_gradient);
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

    private void  updateCountDown(){

        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        mCountDown.setText(timeFormatted);

        if(timeLeftInMillis < 6000){
            mCountDown.setTextColor(Color.RED);
        }
        else{
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

        if (mScore > flagsBestScore) {
            userRepository.saveBestScores(db, "Player", capitalsBestScore, mScore, countryBestScore);
            flagsBestScore = mScore;
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
        mFlagsQuestion.setText(imageQuestion.getImageQuestion());
        mImage.setImageResource(imageQuestion.getImage());
        mFlagsAnswer1.setText(imageQuestion.getChoiceList().get(0));
        mFlagsAnswer2.setText(imageQuestion.getChoiceList().get(1));
        mFlagsAnswer3.setText(imageQuestion.getChoiceList().get(2));
        mFlagsAnswer4.setText(imageQuestion.getChoiceList().get(3));
    }
    private ImageBank generateQuestions() {

        ImageQuestion imageQuestion1 = new ImageQuestion("Which country does this flag belong to?",
                R.drawable.ic_flag_of_argentina, Arrays.asList("Argentina", "Bolivia", "Uruguay", "Colombia"),
                0);

        ImageQuestion imageQuestion2 = new ImageQuestion("Which country does this flag belong to?",
                R.drawable.ic_flag_of_australia, Arrays.asList("Australia", "New Zealand", "Great Britain", "United States"),
                0);

        ImageQuestion imageQuestion3 = new ImageQuestion("Which country does this flag belong to?",
                R.drawable.ic_flag_of_belgium, Arrays.asList("Netherlands", "Germany", "Spain", "Belgium"),
                3);

        ImageQuestion imageQuestion4 = new ImageQuestion("Which country does this flag belong to?",
                R.drawable.ic_flag_of_brazil, Arrays.asList("Belize", "Bulgaria", "Brazil", "Bangladesh"),
                2);

        ImageQuestion imageQuestion5 = new ImageQuestion("Which country does this flag belong to?",
                R.drawable.ic_flag_of_denmark, Arrays.asList("Sweden", "Denmark", "Norway", "Iceland"),
                1);

        ImageQuestion imageQuestion6 = new ImageQuestion("Which country does this flag belong to?",
                R.drawable.ic_flag_of_fiji, Arrays.asList("Finland", "Cook Islands", "Fiji", "Faroe Islands"),
                2);

        ImageQuestion imageQuestion7 = new ImageQuestion("Which country does this flag belong to?",
                R.drawable.ic_flag_of_india, Arrays.asList("Iraq", "India", "Ghana", "Ethiopia"),
                1);

        ImageQuestion imageQuestion8 = new ImageQuestion("Which country does this flag belong to?",
                R.drawable.ic_flag_of_germany, Arrays.asList("Armenia", "Netherlands", "Belgium", "Germany"),
                3);

        ImageQuestion imageQuestion9 = new ImageQuestion("Which country does this flag belong to?",
                R.drawable.ic_flag_of_kuwait, Arrays.asList("Kuwait", "Iraq", "Afghanistan", "Egypt"),
                0);

        ImageQuestion imageQuestion10 = new ImageQuestion("Which country does this flag belong to?",
                R.drawable.ic_flag_of_new_zealand, Arrays.asList("Great Britain", "New Zealand", "Australia", "United States"),
                1);

        ImageQuestion imageQuestion11 = new ImageQuestion("Which country does this flag belong to?",
                R.drawable.ic_flag_of_south_africa, Arrays.asList("Botswana", "Cameroon", "South Africa", "Senegal"),
                2);

        ImageQuestion imageQuestion12 = new ImageQuestion("Which country does this flag belong to?",
                R.drawable.ic_flag_of_kiribati, Arrays.asList("Nauru", "Kiribati", "Seychelles", "Maldives"),
                1);

        ImageQuestion imageQuestion13 = new ImageQuestion("Which country does this flag belong to?",
                R.drawable.ic_flag_of_switzerland, Arrays.asList("Vatican", "Tonga", "Switzerland", "Liechtenstein"),
                2);

        ImageQuestion imageQuestion14 = new ImageQuestion("Which country does this flag belong to?",
                R.drawable.ic_flag_of_seychelles, Arrays.asList("Maldives", "Seychelles", "Comoros", "Tuvalu"),
                1);

        ImageQuestion imageQuestion15 = new ImageQuestion("Which country does this flag belong to?",
                R.drawable.ic_flag_of_albania, Arrays.asList("Albania", "Montenegro", "Macedonia", "Moldova"),
                0);

        ImageQuestion imageQuestion16 = new ImageQuestion("Which country does this flag belong to?",
                R.drawable.ic_flag_of_afghanistan, Arrays.asList("Iran", "Syria", "Iraq", "Afghanistan"),
                3);

        ImageQuestion imageQuestion17 = new ImageQuestion("Which country does this flag belong to?",
                R.drawable.ic_flag_of_azerbaijan, Arrays.asList("Turkey", "Syria", "Azerbaijan", "Armenia"),
                2);

        ImageQuestion imageQuestion18 = new ImageQuestion("Which country does this flag belong to?",
                R.drawable.ic_flag_of_bangladesh, Arrays.asList("Bangladesh", "Nepal", "Bhutan", "Sri Lanka"),
                0);

        ImageQuestion imageQuestion19 = new ImageQuestion("Which country does this flag belong to?",
                R.drawable.ic_flag_of_barbados, Arrays.asList("Cuba", "Puerto Rico", "Barbados", "Panama"),
                2);

        ImageQuestion imageQuestion20 = new ImageQuestion("Which country does this flag belong to?",
                R.drawable.ic_flag_of_belarus, Arrays.asList("Estonia", "Lithuania", "Latvia", "Belarus"),
                3);

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
                imageQuestion10,
                imageQuestion11,
                imageQuestion12,
                imageQuestion13,
                imageQuestion14,
                imageQuestion15,
                imageQuestion16,
                imageQuestion17,
                imageQuestion18,
                imageQuestion19,
                imageQuestion20
        ));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mCountDownTimer != null){
            mCountDownTimer.cancel();
        }
    }
}
