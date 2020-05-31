package Fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.hydramail.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragment_container);

        if(fragment == null){
            fragment = new ToFragment();
            manager.beginTransaction().add(R.id.fragment_container, fragment).commit();// Start the transaction
        }
    }


    public void showSubjectScreen(){
        SubjectFragment subjectFragment = new SubjectFragment();
        this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, subjectFragment).addToBackStack(null).commit();

    }

    public void showMessageScreen (){
        MessageFragment messageFragment = new MessageFragment();
        this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, messageFragment).addToBackStack(null).commit();

    }

    public void showToScreen(){
        ToFragment toFragment = new ToFragment();
        this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, toFragment).addToBackStack(null).commit();
    }
}
