package com.aptitudemaster.ui.send;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.aptitudemaster.Dashboard;

import com.aptitudemaster.R;

public class SendFragment extends Fragment {

    private SendViewModel sendViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendViewModel =
                ViewModelProviders.of(this).get(SendViewModel.class);
        View root = inflater.inflate(R.layout.fragment_send, container, false);
        final TextView textView = root.findViewById(R.id.text_send);
        sendViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","apptitudemaster21@gmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, Dashboard.loggedIn.getName()+" -Subject");
        emailIntent.putExtra(Intent.EXTRA_EMAIL,new String[]{"apptitudemaster21@gmail.com"});
        emailIntent.putExtra(Intent.EXTRA_TEXT, "I have the following issue on ....");
        startActivity(Intent.createChooser(emailIntent, "Email Us"));
        getFragmentManager().popBackStack();


         return root;
    }
}