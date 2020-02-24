package com.android.bedsidechats.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.android.bedsidechats.R;
import com.android.bedsidechats.data.SavedCardAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.TreeMap;

public class SavedFragment extends Fragment implements View.OnClickListener {
    private String mLanguageChoice = "";
    private String mProviderChoice = "";
    private String mSavedCards = "";
    private String mUsername = "";
    private FirebaseFirestore mDatabase;
    private TreeMap<String, String> mQuestionMap;
    private TreeMap<String, String> mNoteMap;
    private RecyclerView mCards;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private static String TAG = "SVD_FGMT";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v;

        // TODO handle rotation
        v = inflater.inflate(R.layout.fragment_saved, container, false);

        mDatabase = FirebaseFirestore.getInstance();
        mQuestionMap = new TreeMap<>();
        mNoteMap = new TreeMap<>();

        mLanguageChoice = getArguments() != null ? getArguments().getString("Language") : "";
        mProviderChoice = getArguments().getString("Provider");
        mUsername = getArguments().getString("Username");

        mCards = v.findViewById(R.id.cards_list);
        if (mCards != null){
            mLinearLayoutManager =  new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            mCards.setLayoutManager(mLinearLayoutManager);
            getSavedCards();
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
            }
        }
    }

    public void getSavedCards(){
        mDatabase.collection("patient_decks").document(mSavedCards).collection("decks").document("questions").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            mQuestionMap = (TreeMap) task.getResult().getData();
                            Log.d(TAG, "" + mQuestionMap);
                            mDatabase.collection("patient_decks").document(mSavedCards).collection("decks").document("notes").get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if(task.isSuccessful()) {
                                                mNoteMap = (TreeMap) task.getResult().getData();
                                                Log.d(TAG, "" + mNoteMap);
                                                mAdapter = new SavedCardAdapter(getActivity(), mQuestionMap, mNoteMap, getFragmentManager(), mLanguageChoice, mProviderChoice);
                                                mCards.setAdapter(mAdapter);
                                                SnapHelper snapHelper = new PagerSnapHelper();
                                                snapHelper.attachToRecyclerView(mCards);
                                            }
                                        }
                                    });
                        }
                        else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

}
