package com.aptitudemaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LoginActivity extends AppCompatActivity {
    EditText userid;
    Pinview pinview;
    static String name;
    Button loginbutton;

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
    public void login(View view){
        final ProgressDialog dialog=new ProgressDialog(LoginActivity.this);
        dialog.setCancelable(false);
        dialog.setTitle("Please Wait");
        dialog.setMessage("Loading....");
        dialog.show();

        final Long id=Long.parseLong(userid.getText().toString());
        final Long pin=Long.parseLong(pinview.getValue().toString());
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final TextView txt = findViewById(R.id.invalidid);
        DatabaseReference myRef = database.getReference("Users");
         if(!isNetworkAvailable(getApplicationContext()))
         {
             new AlertDialog.Builder(LoginActivity.this)
                     .setTitle("Internet Required")
                     .setMessage("Please turn on your data")
                     .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialogInterface, int i) {
                             dialog.cancel();
                             return;

                         }
                     })
                     .show();
         }


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   String ar="Login Successful";
                   int k=0;
                  for(DataSnapshot ds:dataSnapshot.getChildren())
                  {
                      Users data=ds.getValue(Users.class);
                     if(data.getId()==id)
                     {
                         if(data.getPin()==pin)
                         {
                             Toast.makeText(LoginActivity.this, ar, Toast.LENGTH_SHORT).show();
                             k=1;
                             name=data.name;
                             SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
                             sharedPreferences.edit().putBoolean("log",true).apply();
                             sharedPreferences.edit().putString("key",ds.getKey()).apply();
                             sharedPreferences.edit().putString("name",data.getName()).apply();
                             sharedPreferences.edit().putLong("id",data.getId()).apply();
                             sharedPreferences.edit().putFloat("score",data.getScore()).apply();
                             sharedPreferences.edit().putLong("pin",data.getPin()).apply();
                             Intent intent = new Intent(LoginActivity.this,Dashboard.class);
                             startActivity(intent);
                             dialog.cancel();
                             finish();
                             break;
                         }
                     }


                  }
                  if(k!=1)
                  {
                      txt.setVisibility(View.VISIBLE);
                      dialog.cancel();
                  }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        userid=findViewById(R.id.userid);
        pinview=findViewById(R.id.pinId);
        loginbutton=findViewById(R.id.loginId);
        pinview.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean fromUser) {
                   if(pinview.getValue().toString().length()==pinview.getPinLength())
                   {
                       if(!userid.getText().toString().isEmpty())
                       {
                           loginbutton.setEnabled(true);
                           loginbutton.setAlpha(1f);
                           closeKeyboard();
                       }
                       else{
                           loginbutton.setEnabled(false);
                           loginbutton.setAlpha(0.5f);
                       }

                   }
                   else
                   {
                       loginbutton.setEnabled(false);
                       loginbutton.setAlpha(0.5f);
                   }


            }
        });
        userid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                      if(userid.getText().toString().isEmpty())
                      {
                          loginbutton.setEnabled(false);
                          loginbutton.setAlpha(0.5f);
                      }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }


    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
