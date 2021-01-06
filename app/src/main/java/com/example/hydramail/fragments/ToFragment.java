package com.example.hydramail.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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

        View.OnTouchListener otl = new View.OnTouchListener() {
            public boolean onTouch (View v, MotionEvent event) {
                return true; // the listener has consumed the event
            }
        };

        toEditText.setOnTouchListener(otl);


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
                Bundle bundle = requireArguments();

                String recipient = toEditText.getText().toString();
                String email = bundle.getString("Email");
                String password = bundle.getString("Password");


                bundle.putString("Recipient", recipient);
                bundle.putString("Email", email);
                bundle.putString("Password", password);


                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                SubjectFragment subjectFragment = new SubjectFragment();
                subjectFragment.setArguments(bundle);

                fragmentTransaction.replace(R.id.fragment_container, subjectFragment);
                fragmentTransaction.commit();

            }
        });


        IsInitialVoiceFinished = false;


        textToSpeech("Tap on your screen, Please speak recipient email, spell out email address carefully. \n Sample \n\n j \n\n o\n\n h\n\n n\n\n d\n\n o\n\n e\n\n at gmail.com \n\n johndoe@gmail.com. \n Tap in 3 \n\n 2 \n\n 1");

        return view;

    }

    public void textToSpeech(final String statement){
        tts = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.ENGLISH);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "This Language is not supported");
                    }
                    speak(statement);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            IsInitialVoiceFinished=true;
                        }
                    }, 1000);
                } else {
                    Log.e("TTS", "Initilization Failed!");
                }
            }
        });

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
            String rawInput = matches.get(0);
            String spaces = rawInput.replaceAll("\\s", "");
            String email = spaces.replaceAll("at", "@");
            toEditText.setText(email);

            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

            if(toEditText.getText().toString().isEmpty()) {
                textToSpeech("Please enter email address");
            }else {
                    if (toEditText.getText().toString().trim().matches(emailPattern)) {
                        textToSpeech("Valid Email, Please confirm message \n " + toEditText.getText() + "\n \n \nTap below screen to continue");
                    } else {
                        textToSpeech("Invalid Email, Please tap screen again");
                    }
                }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStop()
    {
        super.onStop();

        if(tts != null){
            tts.shutdown();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if(tts != null){
            tts.shutdown();
        }
    }
}








