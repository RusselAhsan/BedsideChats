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

public class ProviderFragment extends Fragment implements View.OnClickListener {
    private String mProvider = "Default";
    //private FirebaseAuth mAuth;
    //private FirebaseFirestore mDatabase;
    private static String TAG = "LANG_FGMT";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v;

        // TODO handle rotation
        v = inflater.inflate(R.layout.activity_provider_selection, container, false);

        Button defaultButton = v.findViewById(R.id.default_button);
        if (defaultButton != null) {
            defaultButton.setOnClickListener(this);
        }
        Button nurseButton = v.findViewById(R.id.nurse_button);
        if (nurseButton != null) {
            nurseButton.setOnClickListener(this);
        }

        return v;
    }

    @Override
    public void onClick(View view) {
        Activity activity = getActivity();

        if (activity != null) {
            switch (view.getId()) {
                case R.id.default_button:
                    Log.d(TAG, "Provider: " + mProvider);
                    FragmentManager fragmentManager = getFragmentManager();
                    Fragment fragment = new InstructionsFragment();
                    Bundle args = new Bundle();
                    args.putString("Provider", mProvider);
                    fragment.setArguments(args);
                    if (fragmentManager != null) {
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, fragment)
                                .addToBackStack("provider_fragment")
                                .commit();
                    }
                    break;
                case R.id.nurse_button:
                    mProvider = "Nurse";
                    Log.d(TAG, "Provider: " + mProvider);
                    fragmentManager = getFragmentManager();
                    fragment = new InstructionsFragment();
                    args = new Bundle();
                    args.putString("Provider", mProvider);
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
