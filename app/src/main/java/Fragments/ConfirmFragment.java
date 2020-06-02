package Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hydramail.R;

import co.nedim.maildroidx.MaildroidX;
import co.nedim.maildroidx.MaildroidXType;

public class ConfirmFragment extends Fragment {
    String subject;
    private TextView toTextView;
    private TextView subjectTextView;
    private TextView messageTextView;
    String recipient;
    String message;


    public ConfirmFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_confirm, container, false);

        toTextView = view.findViewById(R.id.to_text_view);
        subjectTextView = view.findViewById(R.id.subject_text_view);
        messageTextView = view.findViewById(R.id.message_text_view);


        Bundle bundle = getArguments();
        if(bundle != null) {
            recipient = bundle.getString("toKey");
            subject = bundle.getString("subjectKey");
            message = bundle.getString("messageKey");
        }

        toTextView.setText(recipient);
        subjectTextView.setText(subject);
        messageTextView.setText(message);

        sendMail(recipient, message);

        return view;
    }

    public void sendMail(String recipient, String message){
        new MaildroidX.Builder()
                .smtp("smtp.mailtrap.io")
                .smtpUsername("be2d320594386f")
                .smtpPassword("10ec68a869121f")
                .port("2525")
                .type(MaildroidXType.HTML)
                .to(recipient)
                .from("someoneover@interenet.com")
                .subject(subject)
                .body(message)
                .isJavascriptDisabled(true)
                .onCompleteCallback(new MaildroidX.onCompleteCallback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onFail(String s) {

                    }

                    @Override
                    public long getTimeout() {
                        return 0;
                    }
                })

            .mail();
    }
}