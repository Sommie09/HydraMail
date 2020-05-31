package Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.hydramail.R;


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
//
//        finishButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MainActivity mainActivity = (MainActivity) getActivity();
//                mainActivity.showSubjectScreen();
//            }
//        });

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

