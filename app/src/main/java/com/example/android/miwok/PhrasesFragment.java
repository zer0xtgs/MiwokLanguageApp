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

public class PhrasesFragment extends Fragment {

    public PhrasesFragment() {
    }

    private MediaPlayer mediaPlayer;

    private AudioManager mAudioManager;

    private MediaPlayer.OnCompletionListener mOnCompletionListner = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            reliseMediaPlayer();
        }
    };

    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<UniversalObject> arrayList = new ArrayList<UniversalObject>();
        arrayList.add(new UniversalObject("Where are you going?", "minto wuksus", R.raw.phrase_where_are_you_going));
        arrayList.add(new UniversalObject("What is your name?", "tinne oyaase'ne", R.raw.phrase_what_is_your_name));
        arrayList.add(new UniversalObject("My name is...", "oyaaset...", R.raw.phrase_my_name_is));
        arrayList.add(new UniversalObject("How are you feeling?", "micheksas?", R.raw.phrase_how_are_you_feeling));
        arrayList.add(new UniversalObject("I'm feeling good.", "kuchi achit", R.raw.phrase_im_feeling_good));
        arrayList.add(new UniversalObject("Are you coming?", "eenes'aa?", R.raw.phrase_are_you_coming));
        arrayList.add(new UniversalObject("Yes, I'm coming.", "hee'eenem", R.raw.phrase_yes_im_coming));
        arrayList.add(new UniversalObject("I'm coming.", "eenem", R.raw.phrase_im_coming));
        arrayList.add(new UniversalObject("Let's go.", "yoowutis", R.raw.phrase_lets_go));
        arrayList.add(new UniversalObject("Come here", "emmi'nem", R.raw.phrase_come_here));

        UniversalAdapter universalAdapter = new UniversalAdapter(getActivity(), arrayList, R.color.category_phrases);
        ListView listView = (ListView) rootView.findViewById(R.id.word_list);
        listView.setAdapter(universalAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                reliseMediaPlayer();
                UniversalObject universalObject = arrayList.get(i);

                int resalt = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (resalt == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer = MediaPlayer.create(getActivity(), universalObject.getmSoundResorseId());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(mOnCompletionListner);

                }
            }
        });
        return rootView;
    }

    static PhrasesFragment newInstance() {
        return new PhrasesFragment();
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
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }


}
