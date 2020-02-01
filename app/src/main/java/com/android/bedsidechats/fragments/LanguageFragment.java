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

public class LanguageFragment extends Fragment implements View.OnClickListener {
    private String mLanguage;
    //private FirebaseAuth mAuth;
    //private FirebaseFirestore mDatabase;
    private static String TAG = "LANG_FGMT";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v;

        // TODO handle rotation
        v = inflater.inflate(R.layout.fragment_language, container, false);

        Button englishButton = v.findViewById(R.id.english_button);
        if (englishButton != null) {
        englishButton.setOnClickListener(this);
        }
        Button spanishButton = v.findViewById(R.id.spanish_button);
        if (spanishButton != null) {
        spanishButton.setOnClickListener(this);
        }

        Button loginButton = v.findViewById(R.id.login_button);
        if (loginButton != null) {
        loginButton.setOnClickListener(this);
        }

        return v;
        }

    @Override
    public void onClick(View view) {
        Activity activity = getActivity();

        if (activity != null) {
            switch (view.getId()) {
                case R.id.login_button:
                    FragmentManager fragmentManager = getFragmentManager();
                    Fragment fragment = new LoginFragment();
                    if (fragmentManager != null) {
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, fragment)
                                .addToBackStack("language_fragment")
                                .commit();
                    }
                    break;
                case R.id.english_button:
                    mLanguage = "English";
                    Log.d(TAG, "Language: " + mLanguage);
                    fragmentManager = getFragmentManager();
                    fragment = new ProviderFragment();
                    if (fragmentManager != null) {
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, fragment)
                                .addToBackStack("language_fragment")
                                .commit();
                    }
                    break;
                case R.id.spanish_button:
                    fragmentManager = getFragmentManager();
                    fragment = new ProviderFragment();
                    if (fragmentManager != null) {
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, fragment)
                                .addToBackStack("language_fragment")
                                .commit();
                    }
                    break;
            }
        }
    }
}
