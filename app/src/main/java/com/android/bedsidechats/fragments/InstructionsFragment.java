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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.android.bedsidechats.R;
import com.android.bedsidechats.data.ProviderAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class InstructionsFragment extends Fragment implements View.OnClickListener {
    private FirebaseFirestore mDatabase;
    private String mLanguage;
    private String mProvider;
    private String mCategory;
    private String mEmail = "";
    private String mUsername = "";
    private TextView mInstructionsTextView;
    private static String TAG = "INSTR_FGMT";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v;
        Activity activity = getActivity();

        if (activity != null)
        {
            int rotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();
            if (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) {
                v = inflater.inflate(R.layout.activity_instructions, container, false);
            } else {
                v = inflater.inflate(R.layout.activity_instructions, container, false);
            }
        }
        else{
            v = inflater.inflate(R.layout.activity_instructions, container, false);
        }
        v = inflater.inflate(R.layout.activity_instructions, container, false);

        mDatabase = FirebaseFirestore.getInstance();
        mProvider =  getArguments().getString("Provider");
        mCategory =  getArguments().getString("Category");
        mLanguage = getArguments().getString("Language");
        mEmail = getArguments().getString("Email") != null ? getArguments().getString("Email") : "";
        mUsername = getArguments().getString("Username") != null ? getArguments().getString("Username") : "";
        mInstructionsTextView = v.findViewById(R.id.instructions_textView_instructions_port);
        getInstructions(mLanguage, mProvider);

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
                    args.putString("Username", mUsername);
                    args.putString("Email", mEmail);
                    args.putString("Language", mLanguage);
                    args.putString("Provider", mProvider);
                    args.putString("Category", mCategory);
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

    public void getInstructions(String language, String provider){
        mDatabase.collection("languages").document(language).collection("categories").document(mCategory).collection("decks").document(provider).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            mInstructionsTextView.setText(task.getResult().getString("instructions"));
                            Log.d(TAG, "Instructions: " + task.getResult().getString("instructions"));
                        } else {
                            mInstructionsTextView.setText(getActivity().getString(R.string.failed_instructions));
                            Log.d(TAG, "Error getting data: ", task.getException());
                        }
                    }
                });
    }
}
