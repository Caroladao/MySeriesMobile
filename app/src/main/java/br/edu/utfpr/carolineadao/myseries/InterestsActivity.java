package br.edu.utfpr.carolineadao.myseries;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class InterestsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interests);

        setTitle(R.string.interests);
    }
}
