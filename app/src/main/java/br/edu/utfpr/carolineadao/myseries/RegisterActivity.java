package br.edu.utfpr.carolineadao.myseries;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private EditText txtName;
    private EditText txtSeason;
    private EditText txtEpisode;
    private Spinner spinnerCategory;
    private Spinner spinnerStatus;
    ArrayList<Serie> series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setTitle(R.string.register);

        txtName = findViewById(R.id.editTextName);
        txtSeason = findViewById(R.id.editTextSeason);
        txtEpisode = findViewById(R.id.editTextEpisode);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerStatus = findViewById(R.id.spinnerStatus);


        Intent intent = getIntent();

        if(intent != null){
           // series = intent.getSerializableExtra("Series");
        }



        initSpinner();
    }

    public void initSpinner(){
        ArrayList <Category> category = new ArrayList<>();

        String[] categoryName = getResources().getStringArray(R.array.category);
        int[]    categoryId = getResources().getIntArray(R.array.idCategory);

        for(int i=0; i<categoryName.length; i++){
            category.add(new Category(categoryName[i],categoryId[i]));
        }



        ArrayAdapter<Category> adapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1,
                        category);

        spinnerCategory.setAdapter(adapter);
    }


}