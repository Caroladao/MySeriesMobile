package br.edu.utfpr.carolineadao.myseries;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

public class InterestsActivity extends AppCompatActivity {

    private ListView listViewInterests = findViewById(R.id.listViewInterests);
    ArrayList<Serie> interestsSeries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interests);

        setTitle(R.string.interests);

        interestsSeries = new ArrayList<>();

        listarSeries();
    }

    public void goRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);

        startActivity(intent);
    }

    public void listarSeries(){
        //Serie[] series = new Serie();

        //ArrayAdapter<Category> adapter =
          //      new ArrayAdapter<>(this,
            //            android.R.layout.simple_list_item_1,
              //          series);

        //spinnerCategory.setAdapter(adapter);
    }
}
