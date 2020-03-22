package com.android.bedsidechats.data;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.bedsidechats.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.api.Distribution;
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
        holder.mQuestionNumberTextView.setText("Question " + key.substring(1) + " / " + mQuestionMap.size());
        holder.mQuestionTextView.setText(mQuestionMap.get(key));
    }

    @Override
    public int getItemCount() {
        return mQuestionMap != null ? mQuestionMap.size() : 0;
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public int getItemViewType(int position){
        return position;
    }

    public class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        protected TextView mQuestionNumberTextView;
        protected TextView mQuestionTextView;
        //protected EditText mNotesEditText;
        protected LinearLayout mBackground;
        protected Button mSavedButton;
        private String TAG = "CRD_HLDR";
        public String mProviderChoice;
        public String mLanguageChoice;
        private Activity mContext;
        private FragmentManager mFragmentManager;
        protected int mSavedBorder;
        protected int mUnsavedBorder;
        private boolean saved = false;

        public CardViewHolder(View itemView, Activity context, FragmentManager fragmentManager, String language, String provider) {
            super(itemView);
            mQuestionNumberTextView = itemView.findViewById(R.id.questionNumber_textView_card_port);
            mQuestionTextView = itemView.findViewById(R.id.question_textView_card_port);
            mLanguageChoice =  language;
            mContext = context;
            mFragmentManager = fragmentManager;
            mSavedButton = itemView.findViewById(R.id.card_save_button);
            mSavedButton.setOnClickListener(this);
            mBackground = itemView.findViewById(R.id.card_inside);
            mSavedBorder = context.getResources().getIdentifier("card_border", "drawable", context.getPackageName());
            mUnsavedBorder = context.getResources().getIdentifier("card_no_border", "drawable", context.getPackageName());
        }

        @Override
        public void onClick(View v){
            Log.d(TAG, "inside onClick" + v.getId());
            switch(v.getId()){
                case R.id.card_save_button:
                    Log.d(TAG, "" + mBackground.getBackground());
                    if(saved){
                        mBackground.setBackgroundResource(mUnsavedBorder);
                        mSavedButton.setBackgroundResource(mContext.getResources().getIdentifier("save_prompt_button", "drawable", mContext.getPackageName()));
                        mSavedButton.setText("Tap to Save");
                        saved = false;
                    }else{
                        mBackground.setBackgroundResource(mSavedBorder);
                        mSavedButton.setBackgroundResource(mContext.getResources().getIdentifier("unsave_prompt_button", "drawable", mContext.getPackageName()));
                        mSavedButton.setText("Tap to Unsave");
                        saved = true;
                    }
                    break;

            }
        }

    }
}
