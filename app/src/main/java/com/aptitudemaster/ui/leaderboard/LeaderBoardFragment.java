package com.aptitudemaster.ui.leaderboard;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;
import android.widget.SimpleAdapter;



import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.aptitudemaster.R;

import com.aptitudemaster.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeaderBoardFragment extends Fragment implements ValueEventListener {

    ArrayList<Users> llist;
    ListView Leader_listview;
    ProgressDialog dialog;
     List<Map<String,String>> lead ;
    Map<String,String> map;

    private void sort(ArrayList<Users> users)
    {
        for(int i=0;i<users.size()-1;i++)
        {
            for(int j=0;j<users.size()-i-1;j++)
            {
                if(users.get(j).getScore()<users.get(j+1).getScore())
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
        sort(llist);
        lead = new ArrayList<Map<String,String>>();
        for(Users u:llist)
        {
            String mainElt=u.getName();
            DecimalFormat df = new DecimalFormat("##.##");
            String subElt ="Score: "+df.format(u.getScore());
            map = new HashMap();
            map.put("var1", mainElt);
            map.put("var2", subElt);
            lead.add(map);

        }


        try {
            SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(),lead,android.R.layout.simple_list_item_2,new String[]{"var1","var2"},new int[]{android.R.id.text1,android.R.id.text2});
            Leader_listview.setAdapter(simpleAdapter);
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