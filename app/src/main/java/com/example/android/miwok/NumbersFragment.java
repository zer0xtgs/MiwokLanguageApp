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

public class NumbersFragment extends Fragment {

    public NumbersFragment() {}

    private MediaPlayer mediaPlayer;

    private AudioManager mAudioManager;

    private MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
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

    static NumbersFragment newInstance() {
        NumbersFragment f = new NumbersFragment();
        return f;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<UniversalObject> arrayList = new ArrayList<UniversalObject>();
        arrayList.add(new UniversalObject("one", "lutti", R.drawable.number_one, R.raw.number_one));
        arrayList.add(new UniversalObject("two", "otiiko", R.drawable.number_two, R.raw.number_two));
        arrayList.add(new UniversalObject("thre", "tolookosu", R.drawable.number_three, R.raw.number_three));
        arrayList.add(new UniversalObject("four", "oyyisa", R.drawable.number_four, R.raw.number_four));
        arrayList.add(new UniversalObject("five", "massokka", R.drawable.number_five, R.raw.number_five));
        arrayList.add(new UniversalObject("six", "temmokka", R.drawable.number_six, R.raw.number_six));
        arrayList.add(new UniversalObject("seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
        arrayList.add(new UniversalObject("eight", "kawinta", R.drawable.number_eight, R.raw.number_eight));
        arrayList.add(new UniversalObject("nine", "wo'e", R.drawable.number_nine, R.raw.number_nine));
        arrayList.add(new UniversalObject("ten", "na'aacha", R.drawable.number_ten, R.raw.number_ten));

        UniversalAdapter itemsAdapter = new UniversalAdapter(getActivity(), arrayList, R.color.category_numbers);
        ListView listView = (ListView) rootView.findViewById(R.id.word_list);
        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                reliseMediaPlayer();
                UniversalObject universalObject = arrayList.get(i);
                int resalt = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListner, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (resalt == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer = MediaPlayer.create(getActivity(), universalObject.getmSoundResorseId());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(mOnCompletionListener);
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
