package com.example.hydramail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.hydramail.sentmails.view.SentMailsActivity;

import java.util.Locale;

public class ChooseAccount extends AppCompatActivity {
    private TextToSpeech tts;
    private boolean IsInitialVoiceFinished;
    private Button chooseAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_account);

        chooseAccount = findViewById(R.id.chooseAccount);
        chooseAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseAccount.this, SentMailsActivity.class);
                startActivity(intent);
            }
        });


//        IsInitialVoiceFinished = false;
//
//        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
//            @Override
//            public void onInit(int status) {
//                if (status == TextToSpeech.SUCCESS) {
//                    int result = tts.setLanguage(Locale.ENGLISH);
//                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
//                        Log.e("TTS", "This Language is not supported");
//                    }
//                    speak("Please choose a mail account");
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            IsInitialVoiceFinished=true;
//                        }
//                    }, 1000);
//                } else {
//                    Log.e("TTS", "Initilization Failed!");
//                }
//            }
//        });
    }

//    private void speak(String text){
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
//        }else{
//            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
//        }
//    }



}
