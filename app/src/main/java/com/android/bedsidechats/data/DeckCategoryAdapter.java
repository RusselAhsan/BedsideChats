package com.android.bedsidechats.data;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.bedsidechats.R;
import com.android.bedsidechats.fragments.InstructionsFragment;

import java.util.HashMap;
import java.util.List;

public class DeckCategoryAdapter extends BaseExpandableListAdapter {

    private String mEmail;
    private String mUsername;
    private String mLanguage;
    private FragmentManager mFragmentManager;
    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;

    public DeckCategoryAdapter(Context context, List<String> listDataHeader,
                               HashMap<String, List<String>> listChildData, String email, String username, String language, FragmentManager fragmentManager) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        mEmail = email;
        mUsername = username;
        mLanguage = language;
        mFragmentManager = fragmentManager;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        RecyclerView.ViewHolder holder;
        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.deck_category_child, null);
        }

        //holder = new ViewHolder();
        //holder.Button = (Button) convertView.findViewById(R.id.deck_categories_decks);
        //convertView.setTag(holder);
        Button txtListChild = (Button) convertView
                .findViewById(R.id.deck_categories_decks);

        txtListChild.setText(childText);
        txtListChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = String.valueOf(getGroup(groupPosition));
                String deck = txtListChild.getText().toString();
                Fragment fragment = new InstructionsFragment();
                Bundle args = new Bundle();
                args.putString("Username", mUsername);
                args.putString("Email", mEmail);
                args.putString("Category", category.toLowerCase());
                args.putString("Provider", deck.toLowerCase());
                args.putString("Language", mLanguage);
                fragment.setArguments(args);
                if (mFragmentManager != null) {
                    mFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .addToBackStack("provider_fragment")
                            .commit();
                }
            }
        });
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.deck_category_header, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.deck_categories_category);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
