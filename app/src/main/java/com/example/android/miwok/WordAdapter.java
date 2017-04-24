package com.example.android.miwok;

import android.content.Context;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class WordAdapter extends ArrayAdapter<Word> {

    private int mBackgroundColor;

    /**
     * This is the constructor for the WordAdapter
     *
     * @param context       The current context. Used to inflate the layout file.
     * @param resource      This second argument is used when the ArrayAdapter is populating a single TextView.
     * @param words         An ArrayList of Word objects to display in a list
     */
    public WordAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList<Word> words, int colorID) {
        super(context, resource, words);
        mBackgroundColor = colorID;
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position      The position in the list of data that should be displayed in the list item view.
     * @param convertView   The recycled view to populate.
     * @param parent        The parent ViewGroup that is used for inflation.
     *
     * @return              The View for the position in the AdapterView.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.vocab_list_item, parent, false);
        }

        // Get the {@link Word} object located at this position in the list
        final Word currentWord = getItem(position);

        // Find the TextView in the vocab_list_item.xml layout with the ID english_text_view
        TextView englishTextView = (TextView) listItemView.findViewById(R.id.english_text_view);
        // Get the english word from the current Word object and
        // set this text on the english word TextView
        englishTextView.setText(currentWord.getEnglishWord());

        // Find the TextView in the vocab_list_item.xml layout with the ID miwok_text_view
        TextView miwokTextView = (TextView) listItemView.findViewById(R.id.miwok_text_view);
        // Get the miwok word from the current Word object and
        // set this text on the miwok word TextView
        miwokTextView.setText(currentWord.getMiwokWord());

        // Set the image
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);
        // if image exists set it
        if (currentWord.getImageResourceId() != 0) {
            imageView.setImageResource(currentWord.getImageResourceId());
        }
        // otherwise hide it
        else {
            imageView.setVisibility(View.GONE);
        }

        // set background color
        LinearLayout linearLayout = (LinearLayout) listItemView.findViewById(R.id.mainLayout);
        // use ContextCompat.getColor() which is part of the support v4 library
        linearLayout.setBackgroundColor(ContextCompat.getColor(getContext(), mBackgroundColor));

        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }
}
