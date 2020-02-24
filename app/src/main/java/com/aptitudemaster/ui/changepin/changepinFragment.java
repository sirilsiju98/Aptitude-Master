package com.aptitudemaster.ui.changepin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.aptitudemaster.MainActivity;
import com.aptitudemaster.R;
import com.goodiebag.pinview.Pinview;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class changepinFragment extends Fragment implements Pinview.PinViewEventListener, View.OnClickListener , OnSuccessListener {


    Button changePinB;
    Pinview pinview;
    ProgressDialog dialog;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dialog=new ProgressDialog(getActivity());
        dialog.setCancelable(false);
        dialog.setTitle("Please Wait");
        dialog.setMessage("Changing pin....");


        View root = inflater.inflate(R.layout.fragment_changepin, container, false);
         pinview = root.findViewById(R.id.cpinId);
        changePinB= root.findViewById(R.id.change);
        pinview.setPinViewEventListener(this);

        changePinB.setOnClickListener(this);

        return root;
    }


    @Override
    public void onDataEntered(Pinview pinview, boolean fromUser) {
        if(pinview.getValue().toString().length()!=4)
        {
            changePinB.setEnabled(false);
            changePinB.setAlpha(0.5f);
        }
        else
        {
            changePinB.setEnabled(true);
            changePinB.setAlpha(1f);
        }
    }

    @Override
    public void onClick(View v) {
        if(pinview.getValue().length()==4)
        {
            dialog.show();
            SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);
            String key=sharedPreferences.getString("key","");
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Users").child(key);
            myRef.child("pin").setValue(Long.parseLong(pinview.getValue())).addOnSuccessListener(this);

        }
    }

    @Override
    public void onSuccess(Object o) {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("log",false).apply();
        sharedPreferences.edit().putLong("key",0).apply();
        sharedPreferences.edit().putString("name","").apply();
        sharedPreferences.edit().putLong("id",0).apply();
        sharedPreferences.edit().putFloat("score",0).apply();
        sharedPreferences.edit().putLong("pin",0).apply();
        Toast.makeText(getContext(), "Pin Changed  Successfully\nPlease LOG IN again", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        dialog.cancel();
        getActivity().finish();
    }
}