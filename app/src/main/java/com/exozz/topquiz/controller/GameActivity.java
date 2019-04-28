package com.exozz.topquiz.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.exozz.topquiz.R;
import com.exozz.topquiz.model.Question;
import com.exozz.topquiz.model.QuestionBank;
import com.exozz.topquiz.model.User;


import java.util.Arrays;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{



    private TextView mQuestion1;
    private Button mReponse1, mReponse2, mReponse3, mReponse4;
    private QuestionBank mQuestionBank;
    private Question mCurrentQuestion;

    private int mScore;
    private int mNumberOfQuestions;

    public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";
    public static final String BUNDLE_STATE_SCORE = "currentScore";
    public static final String BUNDLE_STATE_QUESTION = "currentQuestion";
    private boolean mEnableTouchEvents;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        System.out.println("GameActivity::onCreate()");

        mQuestionBank = this.generateQuestions();

        if (savedInstanceState != null) {
            mScore = savedInstanceState.getInt(BUNDLE_STATE_SCORE);
            mNumberOfQuestions = savedInstanceState.getInt(BUNDLE_STATE_QUESTION);
        } else {
            mScore = 0;
            mNumberOfQuestions = 4;
        }

        mEnableTouchEvents = true;

        mQuestion1 = (TextView) findViewById(R.id.question1);
        mReponse1 = (Button) findViewById(R.id.reponse1);
        mReponse2 = (Button) findViewById(R.id.reponse2);
        mReponse3 = (Button) findViewById(R.id.reponse3);
        mReponse4 = (Button) findViewById(R.id.reponse4);


        mReponse1.setTag(0);
        mReponse2.setTag(1);
        mReponse3.setTag(2);
        mReponse4.setTag(3);


        mReponse1.setOnClickListener(this);
        mReponse2.setOnClickListener(this);
        mReponse3.setOnClickListener(this);
        mReponse4.setOnClickListener(this);

        mCurrentQuestion = mQuestionBank.getQuestion();
        this.displayQuestion(mCurrentQuestion);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(BUNDLE_STATE_SCORE, mScore);
        outState.putInt(BUNDLE_STATE_QUESTION, mNumberOfQuestions);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View view) {
        int reponseIndex = (int) view.getTag();

        if(reponseIndex == mCurrentQuestion.getAnswerIndex()){
            // good answer

            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
            mScore++;
        } else{
            //Wrong answer
            Toast.makeText(this, "Faux !", Toast.LENGTH_SHORT).show();
        }


        mEnableTouchEvents = false;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mEnableTouchEvents = true;

                // If this is the last question, ends the game.
                // Else, display the next question.
                if (--mNumberOfQuestions == 0) {
                    // End the game
                    endGame();
                } else {
                    mCurrentQuestion = mQuestionBank.getQuestion();
                    displayQuestion(mCurrentQuestion);
                }
            }
        }, 2000); // LENGTH_SHORT is usually 2 second long
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mEnableTouchEvents && super.dispatchTouchEvent(ev);
    }

    private void endGame() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Bien joué !")
                .setMessage("Ton score est de " + mScore)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // End the activity
                        Intent intent = new Intent();
                        intent.putExtra(BUNDLE_EXTRA_SCORE, mScore);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }

    private void displayQuestion(final Question question) {
        mQuestion1.setText(question.getQuestion());
        mReponse1.setText(question.getChoiceList().get(0));
        mReponse2.setText(question.getChoiceList().get(1));
        mReponse3.setText(question.getChoiceList().get(2));
        mReponse4.setText(question.getChoiceList().get(3));

    }

    private QuestionBank generateQuestions(){
        Question question1 = new Question("Quelle est le nom du président actuel ?",
                Arrays.asList("François hollande", "Emmanuel macron", "Jacques chirac", "François mitterand"),
                1);

        Question question2 = new Question("Quelle est la capitale de la France ?",
                Arrays.asList("Madrid", "Lyon", "Paris", "Berlin"),
                2);

        Question question3 = new Question("Qui était le deuxième président des États-Unis ?",
                Arrays.asList("Barack obama", "John Adams", "Wiston chruchill", "François mitterand"),
                1);

        Question question4 = new Question("Quelle est le numéro de la maison des simpsons ?",
                Arrays.asList("42", "101", "666", "742"),
                3);

        return new QuestionBank(Arrays.asList(  question1,
                                                question2,
                                                question3,
                                                question4));

    }
    @Override
    protected void onStart() {
        super.onStart();

        System.out.println("GameActivity::onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();

        System.out.println("GameActivity::onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();

        System.out.println("GameActivity::onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();

        System.out.println("GameActivity::onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        System.out.println("GameActivity::onDestroy()");
    }
}
