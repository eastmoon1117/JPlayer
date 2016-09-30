package com.jared.jplayer.main;

import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jared.jplayer.R;
import com.jared.jplayer.app.BaseActivity;
import com.jared.jplayer.databinding.MainBinding;
import com.jared.jplayer.ui.InternetVideoFragment;
import com.jared.jplayer.ui.LocalVideoFragment;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private MainBinding binding;

    private List<Integer> bts = Arrays.asList(
            R.id.rb_local,
            R.id.rb_intenet);

    private float normalSize, normalSelected;

    @Override
    protected void initParams() {
        binding = DataBindingUtil.setContentView(getActivity(), R.layout.activity_main);
    }

    @Override
    protected void initViews() {
        super.initViews();

        normalSize = getResources().getDimension(R.dimen.normal_size);
        normalSelected = getResources().getDimension(R.dimen.selected_size);

        binding.viewpager.setOffscreenPageLimit(2);
        binding.viewpager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager()));
        initTabPagerListener();
        binding.rgTab.check(bts.get(0));
    }

    private void initTabPagerListener() {
        binding.viewpager.addOnPageChangeListener(this);
        binding.rgTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int i = bts.indexOf(checkedId);
                if (i != -1) {
                    selectTitle(checkedId);
                    binding.viewpager.setCurrentItem(i);
                }
            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        selectTitle(bts.get(position));
        binding.rgTab.check(bts.get(position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void selectTitle(int selectResId) {
        binding.rbLocal.setTextSize(TypedValue.COMPLEX_UNIT_PX, normalSize);
        binding.rbIntenet.setTextSize(TypedValue.COMPLEX_UNIT_PX, normalSize);
        ((TextView) findViewById(selectResId)).setTextSize(TypedValue.COMPLEX_UNIT_PX,
                normalSelected);
    }

    public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        LocalVideoFragment localVideoFragment = new LocalVideoFragment();
        InternetVideoFragment internetVideoFragment = new InternetVideoFragment();
        private List<? extends Fragment> list = Arrays.asList(
                localVideoFragment,
                internetVideoFragment);

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }
    }
}
