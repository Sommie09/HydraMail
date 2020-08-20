package com.example.hydramail.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.hydramail.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class ToFragment extends Fragment {
    private Button nextButton;
    private TextToSpeech tts;
    private boolean IsInitialVoiceFinished;
    private ConstraintLayout screenClick;
    TextInputEditText toEditText;

    private static final int RECOGNIZER_RESULT = 1;


    public ToFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_to, container, false);

        screenClick = view.findViewById(R.id.to_fragment_screen);
        nextButton = view.findViewById(R.id.nextButtonTo);
        toEditText = view.findViewById(R.id.to_edit_text);

        screenClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                speechIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech to Text");
                startActivityForResult(speechIntent, 1);
            }
        });


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recipient = toEditText.getText().toString();

                Bundle bundle = new Bundle();
                bundle.putString("Recipient", recipient);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                SubjectFragment subjectFragment = new SubjectFragment();
                subjectFragment.setArguments(bundle);

                fragmentTransaction.replace(R.id.fragment_container, subjectFragment);
                fragmentTransaction.commit();

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
                    //speak("Tap on your screen, Please speak recipient email, spell out email address carefully");
                    speak("Tap on your screen, Please speak recipient email, spell out email address carefully");
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == RECOGNIZER_RESULT && resultCode == RESULT_OK){
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            toEditText.setText(matches.get(0));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}








