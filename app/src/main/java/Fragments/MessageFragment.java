package Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.hydramail.R;
import com.google.android.material.textfield.TextInputEditText;

import co.nedim.maildroidx.MaildroidX;
import co.nedim.maildroidx.MaildroidXType;


/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment {
    private Button finishButton;
    private Button previousButton;

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


        return view;
    }


}




