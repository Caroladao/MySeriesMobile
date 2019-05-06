package br.edu.utfpr.carolineadao.myseries;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class SeriesActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    List<Serie> lstAll;

    public List<Serie> getLstAll() {
        return lstAll;
    }

    public void setLstAll(Serie serie) {
        lstAll.add(serie);
    }

    public static final String NAME         = "NAME";
    public static final String SEASON       = "SEASON";
    public static final String EPISODE      = "EPISODE";
    public static final String CATEGORY     = "CATEGORY";
    public static final String STATUS       = "STATUS";


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

        lstAll = new ArrayList<>();

    }

    public void goAdd(View v) {
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra(RegisterActivity.EDIT, false);
        startActivity(intent);
    }

    public void goEdit(View v) {
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra(RegisterActivity.EDIT, true);
        startActivity(intent);
    }

    public void newSerie() {

        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            String name = bundle.getString(NAME, "");
            int episode = bundle.getInt(EPISODE, -1);
            int season = bundle.getInt(SEASON, -1);
            String category = bundle.getString(CATEGORY, "");
            String status = bundle.getString(STATUS, "");

            if (!name.isEmpty() && name != "") {

                if (season >= 0 && episode >= 0) {
                    setLstAll(new Serie(name, episode, season, new Category(category), status));
                } else if (season >= 0) {
                    setLstAll(new Serie(name, season, new Category(category), status));
                } else {
                    setLstAll(new Serie(name, new Category(category), status));
                }
            }
        }
    }
}
