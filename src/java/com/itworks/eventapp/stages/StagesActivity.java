package com.itworks.eventapp.stages;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.TypefaceSpan;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.itworks.eventapp.ActionBarActivity;
import com.itworks.eventapp.R;
import com.itworks.eventapp.helpers.CustomTypefaceSpan;
import com.itworks.eventapp.helpers.DateController;
import com.itworks.eventapp.helpers.TypefaceController;

public class StagesActivity extends ActionBarActivity implements View.OnClickListener {

    TextView tv1, tv2;
    CustomPagerAdapter mCustomPagerAdapter;
    ViewPager mViewPager;
    RelativeLayout b1, b2;
    private String[] tabTitles;
    protected int pagePosition;
    private TypefaceController typefaceController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stage_activity);
        setActionBar(this);
        tv1 = (TextView) findViewById(R.id.textView2);
        tv2 = (TextView) findViewById(R.id.textView5);
        typefaceController = new TypefaceController(getAssets());
        typefaceController.setFutura(tv1);
        typefaceController.setFutura(tv2);
        b1 = (RelativeLayout) findViewById(R.id.button7);
        b2 = (RelativeLayout) findViewById(R.id.button8);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);

        tabTitles = getResources().getStringArray(R.array.tabs_names);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        loadCustomPageAdapter(getDay(),0);
    }

    @Override
    public void onClick(View v) {
        int day;
        if(v.getId() == b1.getId()){
            day = 1;
        } else {
            day = 2;
        }
        setDayButtonBackground(day);
        pagePosition= mViewPager.getCurrentItem();
        loadCustomPageAdapter(day, pagePosition);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setPref(true);
    }

    private void setPref(boolean value) {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("SCROLL", value);
        editor.commit();
    }

    private int getDay(){
        int day = DateController.getDayForStage();
        setDayButtonBackground(day);
        return day;
    }

    private void loadCustomPageAdapter(int day, int tabNumber) {
        mCustomPagerAdapter = new CustomPagerAdapter(getSupportFragmentManager(), this, day);
        mViewPager.setAdapter(mCustomPagerAdapter);
        mViewPager.setCurrentItem(tabNumber);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                SharedPreferences preferences = getPreferences(MODE_PRIVATE);
                if (preferences.getBoolean("SCROLL", false)){
                    mCustomPagerAdapter.notifyDataSetChanged();
                    setPref(false);
                }
            }

            @Override
            public void onPageSelected(int i) {
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    private void setDayButtonBackground(int day){
        if(day ==1){
            b1.setBackgroundColor(getResources().getColor(R.color.blue_black));
            tv1.setTextColor(getResources().getColor(R.color.light_yellow));
            b2.setBackgroundDrawable(null);
            tv2.setTextColor(getResources().getColor(R.color.blue_black));
        }else{

            b2.setBackgroundColor(getResources().getColor(R.color.blue_black));
            tv2.setTextColor(getResources().getColor(R.color.light_yellow));
            b1.setBackgroundDrawable(null);
            tv1.setTextColor(getResources().getColor(R.color.blue_black));
        }
    }

    class CustomPagerAdapter extends FragmentStatePagerAdapter {

        Context mContext;
        private int day;

        public CustomPagerAdapter(FragmentManager fm, Context context, int day) {
            super(fm);
            mContext = context;
            this.day = day;
        }

        @Override
        public ListFragment getItem(int position) {
            StagesListAdapterFragment f = new StagesListAdapterFragment();
            f.setStage(position + 1);
            f.setDay(day);
            return f;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            SpannableStringBuilder sb = new SpannableStringBuilder(tabTitles[position]);
            TypefaceSpan futuraSpan = new CustomTypefaceSpan(typefaceController.getFutura());
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.blue_black));

            sb.setSpan(new RelativeSizeSpan(1.5f), 0, sb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            sb.setSpan(futuraSpan, 0, sb.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            sb.setSpan(colorSpan, 0, sb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return sb;
        }
    }
}

