package br.edu.utfpr.carolineadao.myseries;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SeriesActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series);

        tabLayout = (TabLayout) findViewById(R.id.tablayolt_id);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.AddFragment(new FragmentInterests(), getResources().getString(R.string.interests));
        adapter.AddFragment(new FragmentWatching(),getResources().getString(R.string.watching));
        adapter.AddFragment(new FragmentCompleted(),getResources().getString(R.string.completed));

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.star);
        tabLayout.getTabAt(1).setIcon(R.drawable.play);
        tabLayout.getTabAt(2).setIcon(R.drawable.check);
    }
}
