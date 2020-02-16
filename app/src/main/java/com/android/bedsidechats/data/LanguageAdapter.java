package com.android.bedsidechats.data;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.bedsidechats.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import java.util.ArrayList;


public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder> {
    private String TAG = "LANG_ADPTR";;
    private static FirebaseFirestore mDatabase;
    private ArrayList<String> mLanguages;

    public LanguageAdapter(ArrayList<String> languages) {
        mLanguages = languages;
        mDatabase = FirebaseFirestore.getInstance();
    }

    @Override
    public LanguageViewHolder onCreateViewHolder(ViewGroup group, int i){
        View view = LayoutInflater.from(group.getContext())
                .inflate(R.layout.language_holder, group, false);

        return new LanguageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LanguageViewHolder holder, int position) {
        Log.d(TAG, "inside onBindViewHolder");
        holder.mLanguageButton.setText(mLanguages.get(position));
    }

    @Override
    public int getItemCount() {
        return mLanguages != null ? mLanguages.size() : 0;
    }


    public static class LanguageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        protected Button mLanguageButton;
        private String TAG = "LANG_HLDR";
        public String mLanguageChoice;

        public LanguageViewHolder(View itemView) {
            super(itemView);
            mLanguageButton = itemView.findViewById(R.id.language_button_language_port);
            mLanguageButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            mLanguageChoice = mLanguageButton.getText().toString();
            //FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
        }
    }

}
