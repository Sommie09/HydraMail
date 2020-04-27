package com.example.hydramail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context){
        this.context = context;
    }

    //Array
    public int [] slide_images = {
            R.drawable.ic_undraw_audio_conversation_dgtw,
            R.drawable.ic_undraw_speech_to_text_9uir,
            R.drawable.ic_undraw_authentication_fsn5,
            R.drawable.ic_undraw_message_sent_1030
    };

    public String [] slide_heading = {
            "VOICE COMMAND", "SPEECH TO TEXT COMPOSE", "SECURITY", "MESSAGE CONFIRMATION"
    };

    public String [] slide_descs = {
           "      Navigate easily using just your voice",
            "Compose mails using a speech to text technology",
            "An amazing password feature for top security",
            "Message confirmation using text to speech"

    };

    @Override
    public int getCount() {
        return slide_heading.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout,container, false);

        ImageView slideImageView = view.findViewById(R.id.imageView);
        TextView slideHeading = view.findViewById(R.id.slide_heading);
        TextView slideDescription = view.findViewById(R.id.slide_desc);

        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_heading[position]);
        slideDescription.setText(slide_descs[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
