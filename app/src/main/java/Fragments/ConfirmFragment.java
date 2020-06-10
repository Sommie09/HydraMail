package Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hydramail.R;

import co.nedim.maildroidx.MaildroidX;
import co.nedim.maildroidx.MaildroidXType;

public class ConfirmFragment extends Fragment {
    String subject;
    private TextView toTextView;
    private TextView subjectTextView;
    private TextView messageTextView;
    String recipient;


    public ConfirmFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_confirm, container, false);

        Bundle bundle = getArguments();

        String recipient = bundle.getString("Recipient");
        String subject = bundle.getString("Subject");
        String message = bundle.getString("Message");

        toTextView = view.findViewById(R.id.to_text_view);
        subjectTextView = view.findViewById(R.id.subject_text_view);
        messageTextView = view.findViewById(R.id.message_text_view);

        toTextView.setText(recipient);
        subjectTextView.setText(subject);
        messageTextView.setText(message);

        sendMail(recipient, message, subject);

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
                        Toast.makeText(getActivity(), "Successful!", Toast.LENGTH_SHORT).show();
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