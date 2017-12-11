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

public class FamilyFragment extends Fragment {

    public FamilyFragment() {}

    private MediaPlayer mediaPlayer;

    private AudioManager mAudioManager;

    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
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

    static FamilyFragment newInstance() {
        FamilyFragment f = new FamilyFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<UniversalObject> arrayList = new ArrayList<UniversalObject>();
        arrayList.add(new UniversalObject("father", "epa", R.drawable.family_father, R.raw.family_father));
        arrayList.add(new UniversalObject("mother", "eta", R.drawable.family_mother, R.raw.family_mother));
        arrayList.add(new UniversalObject("son", "angsi", R.drawable.family_son, R.raw.family_son));
        arrayList.add(new UniversalObject("daughter", "tune", R.drawable.family_daughter, R.raw.family_daughter));
        arrayList.add(new UniversalObject("older brother", "taachi", R.drawable.family_older_brother, R.raw.family_older_brother));
        arrayList.add(new UniversalObject("younger brother", "chaliti", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        arrayList.add(new UniversalObject("older sister", "tete", R.drawable.family_older_sister, R.raw.family_older_sister));
        arrayList.add(new UniversalObject("younger sister", "kolliti", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        arrayList.add(new UniversalObject("grandmother", "ama", R.drawable.family_grandmother, R.raw.family_grandmother));
        arrayList.add(new UniversalObject("grandfather", "paapa", R.drawable.family_grandfather, R.raw.family_grandfather));

        UniversalAdapter universalAdapter = new UniversalAdapter(getActivity(), arrayList, R.color.category_family);
        reliseMediaPlayer();
        ListView listView = (ListView) rootView.findViewById(R.id.word_list);
        listView.setAdapter(universalAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                reliseMediaPlayer();
                UniversalObject universalObject = arrayList.get(i);

                int resalt = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListner, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (resalt == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer = MediaPlayer.create(getActivity(), universalObject.getmSoundResorseId());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(onCompletionListener);
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
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListner);
        }
    }


}
