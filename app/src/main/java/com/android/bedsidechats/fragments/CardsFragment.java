package com.android.bedsidechats.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.recyclerview.widget.PagerSnapHelper;

import com.android.bedsidechats.R;
import com.android.bedsidechats.data.CardAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import me.relex.circleindicator.CircleIndicator2;

public class CardsFragment extends Fragment implements View.OnClickListener {
    private String mLanguageChoice;
    private String mProviderChoice;
    private String mUsername;
    private String mEmail;
    private FirebaseFirestore mDatabase;
    private HashMap<String, String> mQuestionList;
    private TreeMap<String, String> mSavedQuestions;
    private TreeMap<String, String> mSavedNotes;
    private RecyclerView mCards;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private CircleIndicator2 indicator;
    private static String TAG = "CRD_FGMT";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v;

        // TODO handle rotation
        v = inflater.inflate(R.layout.activity_cards, container, false);

        mDatabase = FirebaseFirestore.getInstance();

        mQuestionList = new HashMap<>();
        mSavedQuestions = new TreeMap<>();
        mSavedNotes = new TreeMap<>();

        mLanguageChoice = getArguments().getString("Language") != null ? getArguments().getString("Language") : "";
        mProviderChoice = getArguments().getString("Provider") != null ? getArguments().getString("Provider") : "";
        mUsername = getArguments().getString("Username") != null ? getArguments().getString("Username") : "";
        mEmail = getArguments().getString("Email") != null ? getArguments().getString("Email") : "";

        indicator = v.findViewById(R.id.slider_cards_port);

        mCards = v.findViewById(R.id.cards_list);
        if (mCards != null){
            mLinearLayoutManager =  new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            mCards.setLayoutManager(mLinearLayoutManager);
            getCardDeck();
        }

        return v;
    }

    @Override
    public void onClick(View view) {
        Activity activity = getActivity();

        if (activity != null) {
            switch (view.getId()) {
                case R.id.done_button_cards_port:
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    Fragment fragment = new GuestFragment();
                    Log.d(TAG, mUsername);
                    if(mUsername != "") {
                        writeSavedQuestionsToDatabase();
                        fragment = new HomeFragment();
                    }
                        Bundle args = new Bundle();
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

    public void getCardDeck() {
        mDatabase.collection("languages").document(mLanguageChoice).collection("decks").document(mProviderChoice).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            mQuestionList = (HashMap) task.getResult().getData();
                            if(mQuestionList != null) {
                                mQuestionList.remove("instructions");
                                TreeMap<String, String> mQuestionMap = new TreeMap<>(mQuestionList);
                                Log.d(TAG, "" + mQuestionMap);
                                mAdapter = new CardAdapter(getActivity(), mQuestionMap, getActivity().getSupportFragmentManager(), mLanguageChoice, mSavedQuestions);
                                mCards.setAdapter(mAdapter);
                                SnapHelper snapHelper = new PagerSnapHelper();
                                snapHelper.attachToRecyclerView(mCards);
                                indicator.attachToRecyclerView(mCards, snapHelper);
                                Log.d(TAG, task.getResult().getId() + " => " + task.getResult().getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void writeSavedQuestionsToDatabase() {
        Map<String, String> savedQuestions = new HashMap<>();
        savedQuestions.put("q01", "Test question");
        savedQuestions.put("q02", "Test question 2");
        mDatabase.collection("patients").document(mEmail).collection("saved").document(mProviderChoice).collection("data").document("questions")
                .set(savedQuestions)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Selected questions successfully saved!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error saving selected questions.", e);
                    }
                });
        writeSavedNotesToDatabase();
    }

    public void writeSavedNotesToDatabase() {
        Map<String, String> savedNotes = new HashMap<>();
        savedNotes.put("n01", "Test note");
        mDatabase.collection("patients").document(mEmail).collection("saved").document(mProviderChoice).collection("data").document("notes")
                .set(savedNotes)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Notes successfully saved!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error saving notes.", e);
                    }
                });
        writeLanguageToDatabase();
    }

    public void writeLanguageToDatabase() {
        mDatabase.collection("patients").document(mEmail)
                .update("language", mLanguageChoice)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Language choice successfully saved!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error saving language choice.", e);
                    }
                });
        writeRecentDeckToDatabase();
    }

    public void writeRecentDeckToDatabase() {
        mDatabase.collection("patients").document(mEmail)
                .update("recent_deck", mProviderChoice)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Recent deck choice successfully saved!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error saving recent deck choice.", e);
                    }
                });

    }

    public int getCardDeckSize(){
        return mQuestionList.size();
    }

    public String getLanguage(){
        return mLanguageChoice;
    }

    public String getProvider(){
        return mProviderChoice;
    }

    public RecyclerView getRecyclerView(){
        return mCards;
    }

    public HashMap<String, String> getCardDeckMap(){
        return mQuestionList;
    }

}
