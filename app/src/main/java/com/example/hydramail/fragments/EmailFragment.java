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
public class EmailFragment extends Fragment {
    private Button nextButton;
    private TextToSpeech tts;
    private boolean IsInitialVoiceFinished;
    private ConstraintLayout screenClick;
    TextInputEditText emailEditText;

    private static final int RECOGNIZER_RESULT = 1;


    public EmailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_email, container, false);

        screenClick = view.findViewById(R.id.email_fragment_screen);
        nextButton = view.findViewById(R.id.nextButtonEmail);
        emailEditText = view.findViewById(R.id.email_edit_text);

        View.OnTouchListener otl = new View.OnTouchListener() {
            public boolean onTouch (View v, MotionEvent event) {
                return true; // the listener has consumed the event
            }
        };

        emailEditText.setOnTouchListener(otl);


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
                String email = emailEditText.getText().toString();

                Bundle bundle = new Bundle();
                bundle.putString("Email", email);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                PasswordFragment passwordFragment = new PasswordFragment();
                passwordFragment.setArguments(bundle);

                fragmentTransaction.replace(R.id.fragment_container, passwordFragment);
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
                    speak("Tap on your screen, Please speak your email address, spell out email address carefully. \n Sample \n\n j \n\n o\n\n h\n\n n\n\n d\n\n o\n\n e\n\n at gmail.com \n\n johndoe@gmail.com. \n Tap in 3 \n\n 2 \n\n 1");
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


        return view;

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == RECOGNIZER_RESULT && resultCode == RESULT_OK){
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String rawInput = matches.get(0);
            String spaces = rawInput.replaceAll("\\s", "");
            String email = spaces.replaceAll("at", "@");
            emailEditText.setText(email);

            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

//            if(emailEditText.getText().toString().isEmpty()) {
//                speak("Please enter your email address");
//            }else {
                if (emailEditText.getText().toString().trim().matches(emailPattern)) {
                    speak("Valid Email, Please confirm email \n "+ emailEditText.getText()+ "\n \n \nTap below screen to continue");
                } else {
                    speak("Invalid Email, Please tap screen again");
                }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    private void speak(String text){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }else{
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
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








