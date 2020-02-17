package com.aptitudemaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.aptitudemaster.ui.quiz.QuizFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class QuizActivity extends AppCompatActivity {
    private DataSnapshot quiz;
    RadioGroup rg[];
    RadioButton r[][];
    TextView q[];
    int ids[][]={{R.id.radioButton11,R.id.radioButton12,R.id.radioButton13,R.id.radioButton14},
            {R.id.radioButton21,R.id.radioButton22,R.id.radioButton23,R.id.radioButton24},
            {R.id.radioButton31,R.id.radioButton32,R.id.radioButton33,R.id.radioButton34},
            {R.id.radioButton41,R.id.radioButton42,R.id.radioButton43,R.id.radioButton44},
            {R.id.radioButton51,R.id.radioButton52,R.id.radioButton53,R.id.radioButton54}
    };
    public void submit(View view)
    {
        float score=0;
        for(int i=0;i<5;i++)
        {
            int id=rg[i].getCheckedRadioButtonId();
            if(id==-1)continue;
            else{
                for(int j=0;j<4;j++)
                {
                    if(id==ids[i][j])
                    {
                       if(r[i][j].getTag().toString().equals(quiz.child("opt"+i).getValue().toString()))score++;
                       else score -=0.33f;
                    }
                }

            }
        }



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Quiz");
        Map<String,Object> taskMap = new HashMap<>();
        taskMap.put("user",MainActivity.userLoggedIn.getName());
        taskMap.put("score",score);
        myRef.child(quiz.getKey()).child("LeaderBoard").push().updateChildren(taskMap);
        Toast.makeText(this, ""+score, Toast.LENGTH_SHORT).show();

    }
    private void initialize()
    {
        r=new RadioButton[5][4];
        //1
        r[0][0]=findViewById(R.id.radioButton11);
        r[0][1]=findViewById(R.id.radioButton12);
        r[0][2]=findViewById(R.id.radioButton13);
        r[0][3]=findViewById(R.id.radioButton14);
        //2
        r[1][0]=findViewById(R.id.radioButton21);
        r[1][1]=findViewById(R.id.radioButton22);
        r[1][2]=findViewById(R.id.radioButton23);
        r[1][3]=findViewById(R.id.radioButton24);
        //3
        r[2][0]=findViewById(R.id.radioButton31);
        r[2][1]=findViewById(R.id.radioButton32);
        r[2][2]=findViewById(R.id.radioButton33);
        r[2][3]=findViewById(R.id.radioButton34);
        //4
        r[3][0]=findViewById(R.id.radioButton41);
        r[3][1]=findViewById(R.id.radioButton42);
        r[3][2]=findViewById(R.id.radioButton43);
        r[3][3]=findViewById(R.id.radioButton44);
        //4
        r[4][0]=findViewById(R.id.radioButton51);
        r[4][1]=findViewById(R.id.radioButton52);
        r[4][2]=findViewById(R.id.radioButton53);
        r[4][3]=findViewById(R.id.radioButton54);

        rg=new RadioGroup[5];
        rg[0]=findViewById(R.id.radio1);
        rg[1]=findViewById(R.id.radio2);
        rg[2]=findViewById(R.id.radio3);
        rg[3]=findViewById(R.id.radio4);
        rg[4]=findViewById(R.id.radio5);

        q=new TextView[5];
        q[0]=findViewById(R.id.QView1);
        q[1]=findViewById(R.id.QView2);
        q[2]=findViewById(R.id.QView3);
        q[3]=findViewById(R.id.QView4);
        q[4]=findViewById(R.id.QView5);


    }
    public void clear(View view)
    {
        for(int i=0;i<5;i++)
            rg[i].clearCheck();
    }
    private void setData()
    {
        int i,j;
        for(i=0;i<5;i++)
            q[i].setText((i+1)+". "+(quiz.child("quest"+i).getValue().toString()));
        for(i=0;i<5;i++)
        {
            for(j=0;j<4;j++)
            {
                r[i][j].setText(quiz.child("ans"+i+j).getValue().toString());
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Intent intent= getIntent();
        int pos=intent.getIntExtra("pos",0);
        quiz= QuizFragment.quiz_rec.get(pos);
        setTitle(quiz.child("topic").getValue().toString());
        initialize();
        setData();
        //Toast.makeText(this, ""+(1+2), Toast.LENGTH_SHORT).show();
    }
}
