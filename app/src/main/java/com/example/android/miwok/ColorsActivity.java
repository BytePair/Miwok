package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {

    private String TAG = this.getClass().getSimpleName();

    /** Handles playback of all the sound files */
    private MediaPlayer mMediaPlayer;

    /** Handles audio focus when playing a sound file **/
    private AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange) {
                // gained or regained audio focus
                case AudioManager.AUDIOFOCUS_GAIN:
                    mMediaPlayer.start();
                    break;
                // permanently lost audio focus
                case AudioManager.AUDIOFOCUS_LOSS:
                    releaseMediaPlayer();
                    break;
                // temporarily lost audio focus
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    mMediaPlayer.pause();
                    mMediaPlayer.seekTo(0);
                    break;
                // temporarily lost audio focus (can turn down but we want to pause)
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    mMediaPlayer.pause();
                    mMediaPlayer.seekTo(0);
                    break;
                default:
                    Log.i(TAG, "could not find given focus change code: " + focusChange);
                    break;
            }
        }
    };

    /**
     * This listener gets triggered when the {@link MediaPlayer} has completed
     * playing the audio file.
     */
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };

    // When the activity is stopped, release the media player resources
    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        // Create and setup the {@link AudioManager} to request audio focus
        mAudioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);

        // Create the array list
        final ArrayList<Word> colors = new ArrayList<>();
        colors.add(new Word("red", "wetetti", R.drawable.color_red, R.raw.color_red));
        colors.add(new Word("green", "chokokki", R.drawable.color_green, R.raw.color_green));
        colors.add(new Word("brown", "takaakki", R.drawable.color_brown, R.raw.color_brown));
        colors.add(new Word("gray", "topoppi", R.drawable.color_gray, R.raw.color_gray));
        colors.add(new Word("black", "kululli", R.drawable.color_black, R.raw.color_black));
        colors.add(new Word("white", "kelelli", R.drawable.color_white, R.raw.color_white));
        colors.add(new Word("dusty yellow", "topiise", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        colors.add(new Word("mustard yellow", "chiwiite", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));

        // make adapter for ArrayList
        WordAdapter wordAdapter = new WordAdapter(this, 0, colors, R.color.category_colors);

        // assign to ListView
        ListView listView = (ListView) findViewById(R.id.list);
        if (listView != null) {
            listView.setAdapter(wordAdapter);
        }

        // Set a click listener to play the audio when the list item is clicked on
        if (listView != null) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    // Release the media player if it currently exists because we are about to
                    // play a different sound file
                    releaseMediaPlayer();

                    // Get the {@link Word} object at the given position the user clicked on
                    Word word = colors.get(position);

                    // Request audio focus for playback, will return AudioManager.AUDIOFOCUS_REQUEST_GRANTED or failed
                    Log.i(this.getClass().getSimpleName(), "requesting audio focus...");
                    int result = mAudioManager.requestAudioFocus(

                            mOnAudioFocusChangeListener,

                            // Use the music stream
                            AudioManager.STREAM_MUSIC,

                            // Request temporary focus
                            AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                    Log.i(this.getClass().getSimpleName(), "request for audio focus = " + result);

                    // if focus was granted play the sound
                    if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                        // We have focus, so start playing sound
                        // Create and setup the {@link MediaPlayer} for the audio resource associated with the current word
                        mMediaPlayer = MediaPlayer.create(ColorsActivity.this, word.getAudioResourceId());

                        // Start the audio file
                        mMediaPlayer.start();

                        // Setup a listener on the media player, so that we can stop and release the
                        // media player once the sound has finished playing.
                        mMediaPlayer.setOnCompletionListener(mCompletionListener);

                    }

                }
            });
        }

    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;
        }

        // abandon audio focus to free resources
        mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
    }

}
