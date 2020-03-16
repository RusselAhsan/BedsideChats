package com.android.bedsidechats.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.bedsidechats.R;
import com.android.bedsidechats.data.LanguageAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class LanguageFragment extends Fragment implements View.OnClickListener {
    private String mLanguage = "English";
    private FirebaseFirestore mDatabase;
    private ArrayList<String> languageOptions;
    private RecyclerView mLanguages;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private static String TAG = "LANG_FGMT";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v;
        Activity activity = getActivity();

        if (activity != null)
        {
            int rotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();
            if (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) {
                v = inflater.inflate(R.layout.fragment_language, container, false);
            } else {
                v = inflater.inflate(R.layout.fragment_language, container, false);
            }
        }
        else{
            v = inflater.inflate(R.layout.fragment_language, container, false);
        }
        v = inflater.inflate(R.layout.fragment_language, container, false);

        mDatabase = FirebaseFirestore.getInstance();
        languageOptions = new ArrayList<>();
//        Button englishButton = v.findViewById(R.id.english_button);
//        if (englishButton != null) {
//        englishButton.setOnClickListener(this);
//        }
//        Button spanishButton = v.findViewById(R.id.spanish_button);
//        if (spanishButton != null) {
//        spanishButton.setOnClickListener(this);
//        }

        Button loginButton = v.findViewById(R.id.login_button);
        if (loginButton != null) {
        loginButton.setOnClickListener(this);
        }

        mLanguages = v.findViewById(R.id.languages_list);
        if (mLanguages != null){
            mLinearLayoutManager =  new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            mLanguages.setLayoutManager(mLinearLayoutManager);
            getListOfLanguages();
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
//                case R.id.english_button:
//                    mLanguage = "English";
//                    Log.d(TAG, "Language: " + mLanguage);
//                    fragmentManager = getFragmentManager();
//                    fragment = new ProviderFragment();
//                    Bundle args = new Bundle();
//                    args.putString("Language", mLanguage);
//                    fragment.setArguments(args);
//                    if (fragmentManager != null) {
//                        fragmentManager.beginTransaction()
//                                .replace(R.id.fragment_container, fragment)
//                                .addToBackStack("language_fragment")
//                                .commit();
//                    }
//                    break;
//                case R.id.spanish_button:
//                    mLanguage = "Spanish";
//                    Log.d(TAG, "Language: " + mLanguage);
//                    fragmentManager = getFragmentManager();
//                    fragment = new ProviderFragment();
//                    args = new Bundle();
//                    args.putString("Language", mLanguage);
//                    fragment.setArguments(args);
//                    if (fragmentManager != null) {
//                        fragmentManager.beginTransaction()
//                                .replace(R.id.fragment_container, fragment)
//                                .addToBackStack("language_fragment")
//                                .commit();
//                    }
//                    break;
            }
        }
    }

    public void getListOfLanguages(){
        mDatabase.collection("languages").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                languageOptions.add(document.getId());
                                mAdapter = new LanguageAdapter(getActivity(), languageOptions, getFragmentManager());
                                mLanguages.setAdapter(mAdapter);
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
