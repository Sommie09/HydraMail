package Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.hydramail.R;
import com.google.android.material.textfield.TextInputEditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class ToFragment extends Fragment {
    private Button nextButton;


    public ToFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_to, container, false);

        nextButton = view.findViewById(R.id.nextButtonTo);
        final TextInputEditText toEditText = view.findViewById(R.id.to_edit_text);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recipient = toEditText.getText().toString();

                Bundle bundle = new Bundle();
                bundle.putString("Recipient", recipient);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                SubjectFragment subjectFragment = new SubjectFragment();
                subjectFragment.setArguments(bundle);

                fragmentTransaction.replace(R.id.fragment_container, subjectFragment);
                fragmentTransaction.commit();

            }
        });
        return view;
    }
}








