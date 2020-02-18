package com.android.bedsidechats.data;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.bedsidechats.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import java.util.TreeMap;


public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {
    private String TAG = "CRD_ADPTR";;
    private static FirebaseFirestore mDatabase;
    private TreeMap<String, String> mQuestionMap;
    private String mLanguageChoice;
    private String mProviderChoice;
    private Activity mContext;
    private FragmentManager mFragmentManager;

    public CardAdapter(Activity context, TreeMap<String, String> questions, FragmentManager fragmentManager, String language, String provider) {
        mQuestionMap = questions;
        mDatabase = FirebaseFirestore.getInstance();
        mLanguageChoice =  language;
        mProviderChoice =  provider;
        mContext = context;
        mFragmentManager = fragmentManager;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup group, int i){
        View view = LayoutInflater.from(group.getContext())
                .inflate(R.layout.card_holder, group, false);

        return new CardViewHolder(view, mContext, mFragmentManager, mLanguageChoice, mProviderChoice);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        Log.d(TAG, "inside onBindViewHolder");
        String key = mQuestionMap.keySet().toArray()[position].toString();
        holder.mQuestionNumberTextView.setText(key);
        holder.mQuestionTextView.setText(mQuestionMap.get(key));
    }

    @Override
    public int getItemCount() {
        return mQuestionMap != null ? mQuestionMap.size() : 0;
    }

    public class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        protected TextView mQuestionNumberTextView;
        protected TextView mQuestionTextView;
        //protected EditText mNotesEditText;
        private String TAG = "CRD_HLDR";
        public String mProviderChoice;
        public String mLanguageChoice;
        private Activity mContext;
        private FragmentManager mFragmentManager;

        public CardViewHolder(View itemView, Activity context, FragmentManager fragmentManager, String language, String provider) {
            super(itemView);
            mQuestionNumberTextView = itemView.findViewById(R.id.questionNumber_textView_card_port);
            mQuestionTextView = itemView.findViewById(R.id.question_textView_card_port);
            mLanguageChoice =  language;
            mContext = context;
            mFragmentManager = fragmentManager;
        }

        @Override
        public void onClick(View v){
//            chooseProvider(mProviderChoice);
//            Fragment fragment = new InstructionsFragment();
//            Bundle args = new Bundle();
//            args.putString("Provider", mProviderChoice);
//            args.putString("Language", mLanguageChoice);
//            fragment.setArguments(args);
//            if (mFragmentManager != null) {
//                mFragmentManager.beginTransaction()
//                        .replace(R.id.fragment_container, fragment)
//                        .addToBackStack("provider_fragment")
//                        .commit();
//            }

        }

        public void chooseProvider(String provider){
            Log.d(TAG, "Provider selected: " + provider);
        }
    }

}
