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
public class MessageFragment extends Fragment {
    private Button finishButton;
    private Button previousButton;
    private TextToSpeech tts;
    private boolean IsInitialVoiceFinished;

    public MessageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);


        finishButton = view.findViewById(R.id.finishButtonMessage);
        previousButton = view.findViewById(R.id.previousButtonMessage);
        final TextInputEditText messageEditText = view.findViewById(R.id.message_edit_text);


        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = requireArguments();

                String message = messageEditText.getText().toString();
                String recipient = bundle.getString("Recipient");
                String subject = bundle.getString("Subject");

                bundle.putString("Message", message);
                bundle.putString("Recipient", recipient);
                bundle.putString("Subject", subject);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                ConfirmFragment confirmFragment = new ConfirmFragment();
                confirmFragment.setArguments(bundle);

                fragmentTransaction.replace(R.id.fragment_container, confirmFragment);
                fragmentTransaction.commit();
            }


        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.showSubjectScreen();
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
                    speak("Tap on your screen, Please speak email message");
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




