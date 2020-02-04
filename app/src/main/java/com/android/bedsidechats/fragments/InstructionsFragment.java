package com.android.bedsidechats.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.android.bedsidechats.R;

public class InstructionsFragment extends Fragment implements View.OnClickListener {
    //private FirebaseAuth mAuth;
    //private FirebaseFirestore mDatabase;
    private static String TAG = "INSTR_FGMT";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v;

        // TODO handle rotation
        v = inflater.inflate(R.layout.activity_instructions, container, false);

        Button nextButton = v.findViewById(R.id.next_button);
        if (nextButton != null) {
            nextButton.setOnClickListener(this);
        }

        return v;
    }

    @Override
    public void onClick(View view) {
        Activity activity = getActivity();

        if (activity != null) {
            switch (view.getId()) {
                case R.id.next_button:
                    FragmentManager fragmentManager = getFragmentManager();
                    Fragment fragment = new CardsFragment();
                    Bundle args = new Bundle();
                    fragment.setArguments(args);
                    if (fragmentManager != null) {
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, fragment)
                                .addToBackStack("provider_fragment")
                                .commit();
                    }
                    break;
            }
        }
    }
}
