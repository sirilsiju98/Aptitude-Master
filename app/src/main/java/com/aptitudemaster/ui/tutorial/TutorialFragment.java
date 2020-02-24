package com.aptitudemaster.ui.tutorial;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.aptitudemaster.R;
import com.aptitudemaster.Tutorial;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;


public class TutorialFragment extends Fragment {

     private Tutorial data;
     ArrayList<Tutorial> tlist;
    ListView tutorial_listview;
    ProgressDialog dialog;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_tutorial, container, false);
        tutorial_listview = root.findViewById(R.id.tutorial_list);
        final ArrayList<String> tslist=new ArrayList<String>();
        dialog=new ProgressDialog(getActivity());
        dialog.setCancelable(false);
        dialog.setTitle("Please Wait");
        dialog.setMessage("Loading....");
        dialog.show();



        final FirebaseDatabase database = com.google.firebase.database.FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("");
        DatabaseReference child = myRef.child("Tutorial");
        child.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    data=new Tutorial();
                    tlist=new ArrayList<Tutorial>();
                for (DataSnapshot ds:dataSnapshot.getChildren()) {

                    data=ds.getValue(Tutorial.class);
                    tlist.add(data);

                }

                for(Tutorial t:tlist)
                {
                    tslist.add(t.getTopic());
                }
                Collections.reverse(tslist);
                try {
                    ArrayAdapter<String> arrayAdapter_tutorial = new ArrayAdapter<String>(getContext(),android.R.layout.simple_expandable_list_item_1,tslist);
                    tutorial_listview.setAdapter(arrayAdapter_tutorial);
                }
                catch (Exception e)
                {

                }

                dialog.cancel();

            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

         tutorial_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                  try {
                      Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(tlist.get(tlist.size() - position - 1).getUrl()));
                      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                      intent.setPackage("com.google.android.youtube");
                      startActivity(intent);
                  }
                  catch (Exception E)
                  {
                      Toast.makeText(getContext(), "Error Loading", Toast.LENGTH_SHORT).show();
                  }

             }
         });

        return root;
    }
}