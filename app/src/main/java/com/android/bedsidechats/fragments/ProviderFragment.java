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
import com.android.bedsidechats.data.ProviderAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ProviderFragment extends Fragment implements View.OnClickListener {
    private String mProvider = "Physician";
    private String mLanguage = "English";
    private FirebaseFirestore mDatabase;
    private ArrayList<String> providerOptions;
    private RecyclerView mProviders;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private static String TAG = "PRVDR_FGMT";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v;
        Activity activity = getActivity();

        if (activity != null)
        {
            int rotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();
            if (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) {
                v = inflater.inflate(R.layout.activity_provider_selection, container, false);
            } else {
                v = inflater.inflate(R.layout.activity_provider_selection, container, false);
            }
        }
        else{
            v = inflater.inflate(R.layout.activity_provider_selection, container, false);
        }
        v = inflater.inflate(R.layout.activity_provider_selection, container, false);

        mDatabase = FirebaseFirestore.getInstance();
        providerOptions =  new ArrayList<>();
//        Button defaultButton = v.findViewById(R.id.default_button);
//        if (defaultButton != null) {
//            defaultButton.setOnClickListener(this);
//        }
//        Button nurseButton = v.findViewById(R.id.nurse_button);
//        if (nurseButton != null) {
//            nurseButton.setOnClickListener(this);
//        }

        mLanguage =  getArguments().getString("Language");

        mProviders = v.findViewById(R.id.providers_list);
        if (mProviders != null){
            mLinearLayoutManager =  new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            mProviders.setLayoutManager(mLinearLayoutManager);
            getListOfProviders();
        }

        return v;
    }

    @Override
    public void onClick(View view) {
        Activity activity = getActivity();

        if (activity != null) {
            switch (view.getId()) {
//                case R.id.default_button:
//                    Log.d(TAG, "Provider: " + mProvider);
//                    FragmentManager fragmentManager = getFragmentManager();
//                    Fragment fragment = new InstructionsFragment();
//                    Bundle args = new Bundle();
//                    args.putString("Provider", mProvider);
//                    fragment.setArguments(args);
//                    if (fragmentManager != null) {
//                        fragmentManager.beginTransaction()
//                                .replace(R.id.fragment_container, fragment)
//                                .addToBackStack("provider_fragment")
//                                .commit();
//                    }
//                    break;
//                case R.id.nurse_button:
//                    mProvider = "Nurse";
//                    Log.d(TAG, "Provider: " + mProvider);
//                    fragmentManager = getFragmentManager();
//                    fragment = new InstructionsFragment();
//                    args = new Bundle();
//                    args.putString("Provider", mProvider);
//                    fragment.setArguments(args);
//                    if (fragmentManager != null) {
//                        fragmentManager.beginTransaction()
//                                .replace(R.id.fragment_container, fragment)
//                                .addToBackStack("provider_fragment")
//                                .commit();
//                    }
//                    break;
                default:
                    break;
            }
        }
    }

    public void getListOfProviders(){
        mDatabase.collection("languages").document(mLanguage).collection("decks").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                providerOptions.add(document.getId());
                                mAdapter = new ProviderAdapter(getActivity(), providerOptions, getFragmentManager(), mLanguage);
                                mProviders.setAdapter(mAdapter);
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
