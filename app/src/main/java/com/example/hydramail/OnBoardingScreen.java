package com.example.hydramail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

public class OnBoardingScreen extends AppCompatActivity {

    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    private TextView[] mDots;
    private SliderAdapter sliderAdapter;
    private Button nextButton;
    private Button prevButton;
    private Button skipButton;
    private int currentPage;
    private TextToSpeech tts;
    private boolean IsInitialVoiceFinished;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding_screen);



        IsInitialVoiceFinished = false ;

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.US);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "This Language is not supported");
                    }
                    speak("Welcome to Hydra Mail, Hydra Mail has amazing features to send emails seamlessly for the visually impaired, " +
                            "Such as navigating through the app with just your voice");
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



        mSlideViewPager = findViewById(R.id.slide_view_pager);
        mDotLayout = findViewById(R.id.dot_layout);
        nextButton = findViewById(R.id.nextButton);
        prevButton = findViewById(R.id.prevButton);
        skipButton = findViewById(R.id.skipButton);

        sliderAdapter = new SliderAdapter(this);

        mSlideViewPager.setAdapter(sliderAdapter);

        addDotsIndictator(0);

        mSlideViewPager.addOnPageChangeListener(viewListener);

        //OnClickListener
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSlideViewPager.setCurrentItem(currentPage + 1);

            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSlideViewPager.setCurrentItem(currentPage -1 );
            }
        });

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OnBoardingScreen.this, ChooseAccount.class);
                startActivity(intent);
            }
        });



    }

    public void addDotsIndictator(int position) {
        mDots = new TextView[4];
        mDotLayout.removeAllViews();
        for (int i = 0; i < mDots.length; i++) {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));

            mDotLayout.addView(mDots[i]);
        }
        if (mDots.length > 0) {
            mDots[position].setTextColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndictator(position);

            currentPage = position;

            if(position == 0){
                nextButton.setEnabled(true);
                prevButton.setEnabled(false);
                prevButton.setVisibility((View.INVISIBLE));

                nextButton.setText("Next");
                prevButton.setText("");


            }else if (position == mDots.length - 1){
                nextButton.setEnabled(true);
                prevButton.setEnabled(true);
                prevButton.setVisibility(View.VISIBLE);

                nextButton.setText("Finish");
                prevButton.setText("Back");
                OnBoardingScreen.this.speak("Hello");

            } else{
                nextButton.setEnabled(true);
                prevButton.setEnabled(true);
                prevButton.setVisibility((View.VISIBLE));

                nextButton.setText("Next");
                prevButton.setText("Back");
                OnBoardingScreen.this.speak("Hi");

            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

    };

    private void speak(String text){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }else{
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }


    }

