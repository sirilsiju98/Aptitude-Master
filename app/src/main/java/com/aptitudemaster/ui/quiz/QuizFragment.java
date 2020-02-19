package com.aptitudemaster.ui.quiz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.aptitudemaster.Dashboard;

import com.aptitudemaster.QuizActivity;
import com.aptitudemaster.R;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class QuizFragment extends Fragment implements ValueEventListener, AdapterView.OnItemClickListener {



    public static ArrayList<String> list;
    protected ListView listView;
    public static ArrayList<DataSnapshot> quiz_rec;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        boolean quiz_taken=false;
        DataSnapshot d =quiz_rec.get(position);
        DataSnapshot f =d.child("LeaderBoard");

        for(DataSnapshot r: f.getChildren())
        {
           if(r.child("user").getValue().toString().equals(Dashboard.loggedIn.getName())) {
               quiz_taken = true;
               String s=r.child("score").getValue().toString();

               new AlertDialog.Builder(getContext())
                       .setTitle("Test Already Attempted")
                       .setMessage("You have already taken this test.\n Score: "+s)
                       .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {



                           }
                       })
                       .show();
           }
        }
        if(!quiz_taken) {
            Intent intent = new Intent(getActivity(), QuizActivity.class);
            intent.putExtra("pos", position);
            startActivity(intent);
        }

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_quiz, container, false);
        listView=root.findViewById(R.id.list_item_quiz);
        list=new ArrayList<String>(1000);
        quiz_rec=new ArrayList<DataSnapshot>(1000);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Quiz");
        myRef.addValueEventListener(this);
        listView.setOnItemClickListener(this);


        return root;
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        list.clear();
        for (DataSnapshot ds:dataSnapshot.getChildren()) {
            list.add(ds.child("topic").getValue().toString());
            quiz_rec.add(ds);

        }
        Collections.reverse(list);
        Collections.reverse(quiz_rec);
        try {
            ArrayAdapter<String> arrayAdapter_tutorial = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_expandable_list_item_1,list);
            listView.setAdapter(arrayAdapter_tutorial);
        }
        catch (Exception e)
        {

        }

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}