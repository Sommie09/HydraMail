package com.example.hydramail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.hydramail.fragments.EmailFragment;
import com.example.hydramail.fragments.ToFragment;

public class LoginDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_details);


        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragment_container_login_details);

        if(fragment == null){
            fragment = new EmailFragment();
            manager.beginTransaction().add(R.id.fragment_container_login_details, fragment).commit();// Start the transaction
        }
    }
}