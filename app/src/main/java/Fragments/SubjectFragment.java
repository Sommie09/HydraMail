package Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.hydramail.R;
import com.google.android.material.textfield.TextInputEditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class SubjectFragment extends Fragment {
    private Button nextButton;
    private Button previousButton;

    public SubjectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_subject, container, false);


        nextButton = view.findViewById(R.id.nextButtonSubject);
        previousButton = view.findViewById(R.id.previousButtonSubject);
        final TextInputEditText subjectEditText = view.findViewById(R.id.subject_edit_text);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = requireArguments();

                String subject = subjectEditText.getText().toString();
                String recipient = bundle.getString("Recipient");

                bundle.putString("Subject", subject);
                bundle.putString("Recipient", recipient);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                MessageFragment messageFragment = new MessageFragment();
                messageFragment.setArguments(bundle);

                fragmentTransaction.replace(R.id.fragment_container, messageFragment);
                fragmentTransaction.commit();
            }
        });

//



        return view;
    }
}
