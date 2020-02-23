package com.aptitudemaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;


import java.text.DecimalFormat;

public class Result_Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        float score=intent.getFloatExtra("score",0.0f);
        TextView status = findViewById(R.id.status);
        TextView scoretxt=findViewById(R.id.score);
        TextView answers=findViewById(R.id.textViewans);
        String stst="";
        String ans="";
        for(int i=0;i<5;i++)
        {
            stst=stst+(i+1)+". ";
            if(QuizActivity.ansStatus[i]==0)
                stst=stst+"Not Attempted\n\n";
            if(QuizActivity.ansStatus[i]==1)
                stst=stst+"Right\n\n";
            if(QuizActivity.ansStatus[i]==-1)
                stst=stst+"Wrong\n\n";
            ans=ans+(i+1)+"."+QuizActivity.answers[i]+"  ";
        }
        status.setText(stst);
        DecimalFormat df = new DecimalFormat("###.##");
        scoretxt.setText("Score: "+df.format(score));
        answers.setText("Correct Answers: "+ans);
        getSupportActionBar().setTitle("Result");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
