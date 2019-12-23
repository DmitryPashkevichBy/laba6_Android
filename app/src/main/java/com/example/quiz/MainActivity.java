package com.example.quiz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_one, btn_two, btn_three;
    Button listButton[];
    TextView tv_question;
    private Question question = new Question();
    private String answer;

    @Override
    public void onClick(View v) {
        for (Button button: listButton) {
            if(v.getId() == button.getId()) {
                if(button.getText() == answer) {
                    question.setTrueAnswer(question.getTrueAnswer() + 1);
                }
                else {
                    question.setFalseAnswer(question.getFalseAnswer() + 1);
                }
            }

        }
        if(question.getAnsweredQuestions() < question.getLengthQuestions()) {
            NextQuestion(question.getAnsweredQuestions());
        }
        else {
            GameOver();
        }
    }

    private void GameOver(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder
                .setMessage("Правильных ответов: " + Integer.toString(question.getTrueAnswer())
                +"\nНеправильных ответов: " + Integer.toString(question.getFalseAnswer()))
                .setCancelable(false)
                .setPositiveButton("New Game", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                    }
                });
        alertDialogBuilder.show();

    }

    private void NextQuestion(int num){
        for(int i =0; i < 3; i++) {
            listButton[i].setText(question.getChoices(num,i));
        }
        tv_question.setText(question.getQuestion(num));
        answer = question.getCorrectAnswer(num);
        question.setAnsweredQuestions(question.getAnsweredQuestions() + 1);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn_one = (Button)findViewById(R.id.btn_one);
        btn_one.setOnClickListener(this);
        btn_two = (Button)findViewById(R.id.btn_two);
        btn_two.setOnClickListener(this);
        btn_three = (Button)findViewById(R.id.btn_three);
        btn_three.setOnClickListener(this);
        listButton = new Button[] {btn_one, btn_two, btn_three};
        tv_question = (TextView)findViewById(R.id.tv_question);
        NextQuestion(question.getAnsweredQuestions());
    }
}
