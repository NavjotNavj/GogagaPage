package com.navisingh.gogagapage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    Adapter adapter;
    List<Model> models;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();


    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private static final Integer[] IMAGES= {R.drawable.namecard,R.drawable.poster,R.drawable.sticker,R.drawable.brochure};
    private ArrayList<Integer> ImagesArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setPager();

        models = new ArrayList<>();
        models.add(new Model(R.drawable.video, "Video", "Description"));
        models.add(new Model(R.drawable.music, "Music", "Description"));
        models.add(new Model(R.drawable.poster, "Poster", "Poster is any piece of printed paper designed to be attached to a wall or vertical surface."));
        models.add(new Model(R.drawable.namecard, "Namecard", "Business cards are cards bearing business information about a company or individual."));

        adapter = new Adapter(models, this);

        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(130, 0, 130, 0);

        Integer[] colors_temp = {
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color2),
                getResources().getColor(R.color.color3),
                getResources().getColor(R.color.color4)
        };

        colors = colors_temp;

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (position < (adapter.getCount() -1) && position < (colors.length - 1)) {
                    viewPager.setBackgroundColor(

                            (Integer) argbEvaluator.evaluate(
                                    positionOffset,
                                    colors[position],
                                    colors[position + 1]
                            )
                    );
                }

                else {
                    viewPager.setBackgroundColor(colors[colors.length - 1]);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void setPager() {
        for(int i=0;i<IMAGES.length;i++)
            ImagesArray.add(IMAGES[i]);

        mPager = findViewById(R.id.pager);
        mPager.setAdapter(new SlidingViewAdapter(MainActivity.this,ImagesArray));

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        NUM_PAGES =ImagesArray.size();

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        // Pager listener over indicator

    }
    }

