package com.android.bedsidechats.data;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.EditText;
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
    public TreeMap<String, String> mSavedQuestions;
    public TreeMap<String, String> mSavedNotes;
    private String mLanguageChoice;
    private Activity mContext;
    private FragmentManager mFragmentManager;



    public CardAdapter(Activity context, TreeMap<String, String> questions, FragmentManager fragmentManager, String language, TreeMap<String, String> saved, TreeMap<String, String> savedNotes) {
        mQuestionMap = questions;
        mDatabase = FirebaseFirestore.getInstance();
        mLanguageChoice =  language;
        mContext = context;
        mFragmentManager = fragmentManager;
        mSavedQuestions = saved;
        mSavedNotes = savedNotes;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup group, int i){
        View view = LayoutInflater.from(group.getContext())
                .inflate(R.layout.card_holder, group, false);

        return new CardViewHolder(view, mContext, mFragmentManager, mLanguageChoice);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        Log.d(TAG, "inside onBindViewHolder");
        String key = mQuestionMap.keySet().toArray()[position].toString();
        String header = mContext.getString(R.string.question) + " " + key.substring(1) + " / " + mQuestionMap.size();
        holder.mQuestionNumberTextView.setText(header);
        holder.mQuestionTextView.setText(mQuestionMap.get(key));
        holder.addTextChangeListener();
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
        protected EditText mNotesEditText;
        protected LinearLayout mBackground;
        protected Button mSavedButton;
        private String TAG = "CRD_ADPTR";
        public String mLanguageChoice;
        private Activity mContext;
        private FragmentManager mFragmentManager;
        protected int mSavedBorder;
        protected int mUnsavedBorder;
        private boolean saved = false;

        public CardViewHolder(View itemView, Activity context, FragmentManager fragmentManager, String language) {
            super(itemView);
            mQuestionNumberTextView = itemView.findViewById(R.id.questionNumber_textView_card_port);
            mQuestionTextView = itemView.findViewById(R.id.question_textView_card_port);
            mNotesEditText = itemView.findViewById(R.id.notes_EditText_card_port);
            mLanguageChoice =  language;
            mContext = context;
            mFragmentManager = fragmentManager;
            mSavedButton = itemView.findViewById(R.id.card_save_button);
            mSavedButton.setOnClickListener(this);
            mNotesEditText.setOnClickListener(this);
            mBackground = itemView.findViewById(R.id.card_inside);
            mSavedBorder = context.getResources().getIdentifier("card_border", "drawable", context.getPackageName());
            mUnsavedBorder = context.getResources().getIdentifier("card_no_border", "drawable", context.getPackageName());
        }

        @Override
        public void onClick(View v){
            Log.d(TAG, "inside onClick" + v.getId());
            String question = "q" + mQuestionNumberTextView.getText().toString().substring(9, 11);
            String note = "n" + question.substring(1);
            switch(v.getId()){
                case R.id.card_save_button:
                    //Log.d(TAG, "" + mBackground.getBackground());
                    if(saved){ // Want to unsave
                        // Remove from maps if maps have them already and there are no notes
                        if(mSavedQuestions.containsKey(question) && mNotesEditText.getText().length() == 0){
                            mSavedQuestions.remove(question);
                            if(mSavedNotes.containsKey(note)) {
                                mSavedNotes.remove(note);
                            }
                        }
                        // Visual updates for unsaving
                        mBackground.setBackgroundResource(mUnsavedBorder);
                        mSavedButton.setBackgroundResource(mContext.getResources().getIdentifier("save_prompt_button", "drawable", mContext.getPackageName()));
                        mSavedButton.setText(R.string.tap_to_save);
                        saved = false;
                    }else{
                        // Add to questions map if not already in map
                        if(!mSavedQuestions.containsKey(question)) {
                            mSavedQuestions.put(question, mQuestionTextView.getText().toString());
                        }
                        // Add to notes map if not already in map and if note exists
                        if(!mSavedNotes.containsKey(note) && mNotesEditText.getText().length() != 0) {
                            mSavedNotes.put(note, mNotesEditText.getText().toString());
                        }
                        else{
                            // Update note in notes map if previous note existed for this question
                            if(mSavedNotes.get(note) != mNotesEditText.getText().toString() && mNotesEditText.getText().length() != 0) {
                                mSavedNotes.remove(note);
                                mSavedNotes.put(note, mNotesEditText.getText().toString());
                            }
                            else if(mSavedNotes.get(note) != mNotesEditText.getText().toString()){
                                mSavedNotes.remove(note);
                            }
                        }

                        // Visual updates for saving
                        mBackground.setBackgroundResource(mSavedBorder);
                        mSavedButton.setBackgroundResource(mContext.getResources().getIdentifier("unsave_prompt_button", "drawable", mContext.getPackageName()));
                        mSavedButton.setText(R.string.tap_to_unsave);
                        saved = true;
                    }
                    break;
            }
        }

        public void addTextChangeListener(){
            String question = "q" + mQuestionNumberTextView.getText().toString().substring(9, 11);
            String note = "n" + question.substring(1);
            mNotesEditText.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {
                    if(mNotesEditText.getText().length() != 0){
                        if(!mSavedQuestions.containsKey(question)) {
                            mSavedQuestions.put(question, mQuestionTextView.getText().toString());
                        }
                        if(!mSavedNotes.containsKey(note)) {
                            mSavedNotes.put(note, mNotesEditText.getText().toString());
                        }
                        else{
                            if(mSavedNotes.get(note) != mNotesEditText.getText().toString() && mNotesEditText.getText().length() != 0) {
                                mSavedNotes.remove(note);
                                mSavedNotes.put(note, mNotesEditText.getText().toString());
                            }
                        }
                    }
                    else{
                        if(!saved && mSavedQuestions.containsKey(question)){
                            mSavedQuestions.remove(question);
                        }
                        if(mSavedNotes.containsKey(note)){
                            mSavedNotes.remove(note);
                        }
                    }
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                public void onTextChanged(CharSequence s, int start, int before, int count) {}
            });
        }

    }
}
