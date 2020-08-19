package com.example.hydramail.fragments;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.hydramail.MainActivity;
import com.example.hydramail.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class SubjectFragment extends Fragment {
    private Button nextButton;
    private Button previousButton;
    private TextToSpeech tts;
    private boolean IsInitialVoiceFinished;

    public SubjectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_subject, container, false);


        nextButton = view.findViewById(R.id.nextButtonSubject);
        previousButton = view.findViewById(R.id.previousButtonSubject);

        final TextInputEditText subjectEditText = view.findViewById(R.id.subject_edit_text);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = requireArguments();

                String subject = subjectEditText.getText().toString();
                String recipient = bundle.getString("Recipient");

                bundle.putString("Subject", subject);
                bundle.putString("Recipient", recipient);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                MessageFragment messageFragment = new MessageFragment();
                messageFragment.setArguments(bundle);

                fragmentTransaction.replace(R.id.fragment_container, messageFragment);
                fragmentTransaction.commit();
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.showToScreen();
            }
        });

        IsInitialVoiceFinished = false;

        tts = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.ENGLISH);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "This Language is not supported");
                    }
                    speak("Tap on your screen, Please speak subject of your mail");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            IsInitialVoiceFinished=true;
                        }
                    }, 3000);
                } else {
                    Log.e("TTS", "Initilization Failed!");
                }
            }
        });


        return view;
    }

    private void speak(String text){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }else{
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }
}
