package com.example.android.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ColorsFragment extends Fragment {

    public ColorsFragment() {}


    private MediaPlayer mediaPlayer;

    private AudioManager mAudioManaget;

    private MediaPlayer.OnCompletionListener mOnCompletionListner = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            reliseMediaPlayer();
        }
    };

    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListner = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                mediaPlayer.release();
            }
        }
    };

    static ColorsFragment newInstance() {
        return new ColorsFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        mAudioManaget = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<UniversalObject> arrayList = new ArrayList<UniversalObject>();
        arrayList.add(new UniversalObject("red", "wetti", R.drawable.color_red, R.raw.color_red));
        arrayList.add(new UniversalObject("green", "chokokki", R.drawable.color_green, R.raw.color_green));
        arrayList.add(new UniversalObject("brown", "yakaakki", R.drawable.color_brown, R.raw.color_brown));
        arrayList.add(new UniversalObject("gray", "topoppi", R.drawable.color_gray, R.raw.color_gray));
        arrayList.add(new UniversalObject("black", "kululli", R.drawable.color_black, R.raw.color_black));
        arrayList.add(new UniversalObject("white", "kelelli", R.drawable.color_white, R.raw.color_white));
        arrayList.add(new UniversalObject("dusty yellow", "topiisa", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        arrayList.add(new UniversalObject("mustard yellow", "chiwiita", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));

        UniversalAdapter universalAdapter = new UniversalAdapter(getActivity(), arrayList, R.color.category_colors);
        ListView listView = (ListView) rootView.findViewById(R.id.word_list);
        listView.setAdapter(universalAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                reliseMediaPlayer();
                UniversalObject universalObject = arrayList.get(i);

                int resalt = mAudioManaget.requestAudioFocus(mOnAudioFocusChangeListner, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (resalt == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    mediaPlayer = MediaPlayer.create(getActivity(), universalObject.getmSoundResorseId());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(mOnCompletionListner);
                }
            }
        });

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        reliseMediaPlayer();
    }

    private void reliseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            mAudioManaget.abandonAudioFocus(mOnAudioFocusChangeListner);
        }
    }


}
