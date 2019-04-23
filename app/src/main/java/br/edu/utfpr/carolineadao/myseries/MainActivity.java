package br.edu.utfpr.carolineadao.myseries;

import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout layolt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        //layolt.setBackground();
    }

    public void goInterests(View view) {
        Intent intent = new Intent(this, InterestsActivity.class);
        startActivity(intent);
    }

    public void goWatching(View view) {
        Intent intent = new Intent(this, WatchingActivity.class);
        startActivity(intent);
    }

    public void goCompleted(View view) {
        Intent intent = new Intent(this, CompletedActivity.class);
        startActivity(intent);
    }
}
