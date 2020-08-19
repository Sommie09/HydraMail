package com.example.hydramail.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hydramail.R;

import co.nedim.maildroidx.MaildroidX;
import co.nedim.maildroidx.MaildroidXType;

public class ConfirmFragment extends Fragment {
    private TextToSpeech tts;
    private boolean IsInitialVoiceFinished;


    public ConfirmFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_confirm, container, false);

        Bundle bundle = getArguments();

        final String recipient = bundle.getString("Recipient");
        final String subject = bundle.getString("Subject");
        final String message = bundle.getString("Message");

        TextView toTextView = view.findViewById(R.id.to_text_view);
        TextView subjectTextView = view.findViewById(R.id.subject_text_view);
        TextView messageTextView = view.findViewById(R.id.message_text_view);
        Button sendButton = view.findViewById(R.id.sendButton);

        toTextView.setText(recipient);
        subjectTextView.setText(subject);
        messageTextView.setText(message);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail(recipient, message, subject);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                SuccessFragment successFragment = new SuccessFragment();

                fragmentTransaction.replace(R.id.fragment_container, successFragment);
                fragmentTransaction.commit();
            }
        });





        return view;

    }

    public void sendMail(String recipient, String message, String subject){
        new MaildroidX.Builder()
                .smtp("smtp.mailtrap.io")
                .smtpUsername("be2d320594386f")
                .smtpPassword("10ec68a869121f")
                .port("2525")
                .type(MaildroidXType.HTML)
                .to(recipient)
                .from("chisomnwokwu09@gmail.com")
                .subject(subject)
                .body(message)
                .isJavascriptDisabled(true)
                .onCompleteCallback(new MaildroidX.onCompleteCallback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onFail(String s) {
                        Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public long getTimeout() {
                        return 0;
                    }
                })

            .mail();
    }


}