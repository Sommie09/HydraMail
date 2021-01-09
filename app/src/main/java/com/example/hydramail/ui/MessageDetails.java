package com.example.hydramail.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.hydramail.R;
import com.example.hydramail.fragments.EmailFragment;
import com.example.hydramail.fragments.ToFragment;

public class MessageDetails extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragment_container_message_details);

        if(fragment == null){
            fragment = new EmailFragment();
            manager.beginTransaction().add(R.id.fragment_container_message_details, fragment).commit();// Start the transaction
        }
    }




}
