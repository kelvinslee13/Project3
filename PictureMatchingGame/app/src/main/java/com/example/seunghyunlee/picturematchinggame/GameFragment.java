package com.example.seunghyunlee.picturematchinggame;

import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.audiofx.BassBoost;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by seunghyunlee on 10/3/16.
 */

public class GameFragment extends Fragment{



    static private int picIds[]={R.id.large1,R.id.large2,R.id.large3,R.id.large4,
            R.id.large5,R.id.large6,R.id.large7,R.id.large8,R.id.large9,R.id.large10,
            R.id.large11,R.id.large12,R.id.large13,R.id.large14,R.id.large15,R.id.large16,
            R.id.large17,R.id.large18,R.id.large19,R.id.large20,R.id.large21,
            R.id.large22,R.id.large23,R.id.large24,};

    List<Drawable> some = new ArrayList<Drawable>();
    List<Drawable> pics = new ArrayList<Drawable>();
    List<Drawable> blank = new ArrayList<Drawable>();
    ArrayList<Drawable>compare = new ArrayList<Drawable>();
    ArrayList<ImageButton> img = new ArrayList<ImageButton>();
    Handler hand = new Handler();
    int counter =1;


    int number = 1;
    int click = 0;
    int right =0;
    int wrong = 0;
    SoundPool soundPool = null;
    float volumne = 1f;

    int checkNumber=24;
    int images;
    int countPic =1;
    int tries =0;
    TextView counttries = null;
    FragmentManager mFragmentMagener;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        this.soundPool = new SoundPool(3,AudioManager.STREAM_MUSIC,0);
        this.click = this.soundPool.load(getContext(),R.raw.click,1);
        this.right = this.soundPool.load(getContext(),R.raw.right,1);
        this.wrong = this.soundPool.load(getContext(),R.raw.wrong,1);
        //mFragmentMagener.beginTransaction();
        //Fragment fragment = new GameFragment();
       // mFragmentMagener.beginTransaction().add(R.layout.fragment_game,fragment);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_game,container);

        this.counttries = (TextView) rootView.findViewById(R.id.tries);




        int getPicture = getResources().getIdentifier("blank", "drawable", getActivity().getPackageName());
        while (countPic < 13) {
            images = getResources().getIdentifier("p" + countPic, "drawable", getActivity().getPackageName());
            if (images != 0) {
                Drawable dr = getResources().getDrawable(images);
                some.add(dr);
            }
            countPic++;
        }
        Collections.shuffle(some);
        for (int i = 0; i < 12; i++) {
            pics.add(some.get(i));
            pics.add(some.get(i));
        }
        Collections.shuffle(pics);
        Drawable addDrawable = getResources().getDrawable(getPicture);
        blank.add(addDrawable);


        playGame(rootView);

        return rootView;

    }

    private Boolean compare(ArrayList<Drawable> draw) {
        Drawable.ConstantState first = draw.get(0).getConstantState();
        Drawable.ConstantState second = draw.get(1).getConstantState();
        if (first.equals(second)) {
            return true;
        }
        else{
            return false;
        }
    }

    public void playGame(View rootView){
        for (int i = 0; i < 24; i++) {
            final ImageButton imageButton = (ImageButton) rootView.findViewById(picIds[i]);
            final int num = i;

            counttries.setText(Integer.toString(tries));
            imageButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    soundPool.play(click,volumne,volumne,1,0,1);

                    imageButton.setImageDrawable(pics.get(num));
                    compare.add(pics.get(num));
                    img.add(imageButton);

                    if (counter %2 == 0) {
                        hand.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (compare(compare) == true) {
                                    ImageButton first = img.get(1);
                                    ImageButton second = img.get(0);
                                    first.setImageDrawable(compare.get(1));
                                    second.setImageDrawable(compare.get(0));
                                    first.setEnabled(false);
                                    second.setEnabled(false);
                                    soundPool.play(right,volumne,volumne,1,0,1);
                                    compare.clear();
                                    img.clear();
                                    checkNumber=checkNumber-2;
                                    tries++;

                                } else {
                                    ImageButton first = img.get(1);
                                    ImageButton second = img.get(0);
                                    first.setImageDrawable(blank.get(0));
                                    second.setImageDrawable(blank.get(0));
                                    soundPool.play(wrong,volumne,volumne,1,0,1);
                                    compare.clear();
                                    img.clear();
                                    tries++;
                                }
                            }
                        }, 400);
                    }
                    counter++;
                }
            });
        }
    }
}


