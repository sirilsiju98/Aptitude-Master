package com.aptitudemaster.ui.leaderboard;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.aptitudemaster.R;
import com.aptitudemaster.Tutorial;
import com.aptitudemaster.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class LeaderBoardFragment extends Fragment implements ValueEventListener {

    ArrayList<Users> llist;
    ArrayList<String> dlist;
    ListView Leader_listview;
    ProgressDialog dialog;

    private void sort(ArrayList<Users> users)
    {
        for(int i=0;i<users.size()-1;i++)
        {
            for(int j=0;j<users.size()-i;j++)
            {
                if(users.get(j).getScore()>users.get(j+1).getScore())
                {
                    Collections.swap(users,j,j+1);
                }
            }
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_leaderboard, container, false);
        FirebaseDatabase database = com.google.firebase.database.FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("");
        DatabaseReference child = myRef.child("Users");
        child.addValueEventListener(this);
        dialog=new ProgressDialog(getActivity());
        dialog.setCancelable(false);
        dialog.setTitle("Please Wait");
        dialog.setMessage("Loading....");
        dialog.show();
        Leader_listview = root.findViewById(R.id.leaderboardview);



        return root;
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        llist=new ArrayList<Users>();
        for (DataSnapshot ds:dataSnapshot.getChildren()) {


            Users data=ds.getValue(Users.class);
            llist.add(data);

        }
        //sort(llist);
        dlist=new ArrayList<String>();
        for(Users u:llist)
        {
            dlist.add(u.getName()+"\t"+u.getScore());
        }


        try {
            ArrayAdapter<String> arrayAdapter_tutorial = new ArrayAdapter<String>(getContext(),android.R.layout.simple_expandable_list_item_1,dlist);
            Leader_listview.setAdapter(arrayAdapter_tutorial);
        }
        catch (Exception e)
        {

        }
        dialog.cancel();
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}