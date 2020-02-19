package com.aptitudemaster;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class Result_Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        TextView status = findViewById(R.id.status);
        String stst="";
        for(int i=0;i<5;i++)
        {
            stst=stst+(i+1)+". ";
            if(QuizActivity.ansStatus[i]==0)
                stst=stst+"Not Attemted\n\n";
            if(QuizActivity.ansStatus[i]==1)
                stst=stst+"Right\n\n";
            if(QuizActivity.ansStatus[i]==-1)
                stst=stst+"Wrong\n\n";
        }
        status.setText(stst);
        getSupportActionBar().setTitle("Result");
    }

}
