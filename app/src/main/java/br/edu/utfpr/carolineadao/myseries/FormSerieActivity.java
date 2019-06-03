package br.edu.utfpr.carolineadao.myseries;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import br.edu.utfpr.carolineadao.myseries.classDB.SeriesDatabase;
import br.edu.utfpr.carolineadao.myseries.models.Category;
import br.edu.utfpr.carolineadao.myseries.models.Serie;
import br.edu.utfpr.carolineadao.myseries.utils.UtilsGUI;

public class FormSerieActivity extends AppCompatActivity {

    public static final String MODE    = "MODE";
    public static final String ID      = "ID";
    public static final int    NEW    = 1;
    public static final int    EDIT = 2;

    private EditText editTextName;
    private EditText editTextSeasons;
    private EditText editTextEpisodes;
    private Spinner  spinnerStatus;
    private Spinner    spinnerCategory;
    private List<Category> listCategories;

    private int    mode;
    private Serie serie;

    SharedPref sharedPref;

    public static void newSerie(Activity activity, int requestCode){

        Intent intent = new Intent(activity, FormSerieActivity.class);

        intent.putExtra(MODE, NEW);

        activity.startActivityForResult(intent, requestCode);
    }

    public static void editSerie(Activity activity, int requestCode, Serie serie){

        Intent intent = new Intent(activity, FormSerieActivity.class);

        intent.putExtra(MODE, EDIT);
        intent.putExtra(ID, serie.getId());

        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);
        if(sharedPref.loadDarkModeState() == true) {
            setTheme(R.style.darkTheme);
        }else {
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_serie);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        editTextName     = findViewById(R.id.editTextName);
        editTextSeasons  = findViewById(R.id.editTextSeason);
        editTextEpisodes  = findViewById(R.id.editTextEpisode);
        spinnerCategory  = findViewById(R.id.spinnerCategory);
        spinnerStatus    = findViewById(R.id.spinnerStatus);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
                R.array.status,
                R.layout.spinner_layolt);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
        spinnerStatus.setAdapter(adapter);


        Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();

        mode = bundle.getInt(MODE, NEW);

        loadCategories();

        if (mode == EDIT){

            setTitle(R.string.edit_serie);

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    int id = bundle.getInt(ID);

                    SeriesDatabase database = SeriesDatabase.getDatabase(FormSerieActivity.this);

                    serie = database.serieDao().queryForId(id);

                    FormSerieActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            editTextName.setText(serie.getName());
                            editTextSeasons.setText(String.valueOf(serie.getSeasons()));
                            editTextEpisodes.setText(String.valueOf(serie.getEpisodes()));

                            /* ARRUMAAAAAAAAR */

                            int positionStt = positionStatus(serie.getStatus());
                            spinnerStatus.setSelection(positionStt);

                            System.out.println("Status "+positionStt);

                            int positionCat = positionCategory(serie.getCategoryId());
                            spinnerCategory.setSelection(positionCat);
                        }
                    });
                }
            });

        }else{

            setTitle(R.string.new_serie);

            serie = new Serie("");
        }
    }

    private int positionCategory(int categoryId){

        for (int i = 0; i < listCategories.size(); i++){

            Category cat = listCategories.get(i);

            if (cat.getId() == categoryId){
                return i;
            }
        }

        return -1;
    }

    private int positionStatus(String status){
        System.out.println(status + " - " + getResources().getString(R.string.interests));

        if(status.equalsIgnoreCase(getResources().getString(R.string.interests))){
            return 0;
        }else if(status.equalsIgnoreCase(getResources().getString(R.string.watching))){
            return 1;
        }else if(status.equals(getResources().getString(R.string.completed))){
            return 2;
        }else{
            return 0;
        }

    }

    /* MUDAR */
    private void loadCategories(){

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                SeriesDatabase database = SeriesDatabase.getDatabase(FormSerieActivity.this);

                listCategories = database.categoryDao().queryAll();

                FormSerieActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayAdapter<Category> spinnerAdapter =
                                new ArrayAdapter<>(FormSerieActivity.this,
                                        R.layout.spinner_layolt,
                                        listCategories);
                        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown);

                        spinnerCategory.setAdapter(spinnerAdapter);
                    }
                });
            }
        });
    }

    private void registerSerie(){

        String nome  = UtilsGUI.checkText(this,
                editTextName,
                R.string.name_empty);
        if (nome == null){
            return;
        }

        String txtSeason = UtilsGUI.checkText(this,
                editTextSeasons,
                R.string.season_empty);
        if (txtSeason == null){
            return;
        }

        String txtEpisode = UtilsGUI.checkText(this,
                editTextEpisodes,
                R.string.episode_empty);
        if (txtEpisode == null){
            return;
        }

        int seasons = Integer.parseInt(txtSeason);
        int episodes = Integer.parseInt(txtEpisode);

        if (seasons <= 0 || seasons > 20){
            UtilsGUI.warningError(this, R.string.invalid_season);
            editTextSeasons.requestFocus();
            return;
        }
        if (episodes <= 0 || episodes > 1000){
            UtilsGUI.warningError(this, R.string.invalid_episode);
            editTextEpisodes.requestFocus();
            return;
        }


        serie.setName(nome);
        serie.setSeasons(seasons);
        serie.setEpisodes(episodes);
        serie.setStatus(spinnerStatus.getSelectedItem().toString());

        Category category = (Category) spinnerCategory.getSelectedItem();
        if (category != null){
            serie.setCategoryId(category.getId());
        }

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                SeriesDatabase database = SeriesDatabase.getDatabase(FormSerieActivity.this);

                if (mode == NEW) {

                    database.serieDao().insert(serie);

                } else {

                    database.serieDao().update(serie);
                }

                setResult(Activity.RESULT_OK);
                finish();
            }
        });
    }

    private void cancel(){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case R.id.menuItemSave:
                registerSerie();
                return true;
            case R.id.menuItemCancel:
                cancel();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
