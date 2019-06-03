package br.edu.utfpr.carolineadao.myseries;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.List;

import br.edu.utfpr.carolineadao.myseries.classDB.SeriesDatabase;
import br.edu.utfpr.carolineadao.myseries.models.Category;
import br.edu.utfpr.carolineadao.myseries.utils.UtilsGUI;

public class FormCategoryActivity extends AppCompatActivity {

    public static final String MODE    = "MODE";
    public static final String ID      = "ID";
    public static final int    NEW    = 1;
    public static final int    EDIT = 2;

    private EditText editTextCategory;

    private int  mode;
    private Category category;

    SharedPref sharedPref;

    public static void newCategory(Activity activity, int requestCode) {

        Intent intent = new Intent(activity, FormCategoryActivity.class);

        intent.putExtra(MODE, NEW);

        activity.startActivityForResult(intent, requestCode);
    }

    public static void editCategory(Activity activity, int requestCode, Category category){

        Intent intent = new Intent(activity, FormCategoryActivity.class);

        intent.putExtra(MODE, EDIT);
        intent.putExtra(ID, category.getId());

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
        setContentView(R.layout.activity_form_category);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        editTextCategory = findViewById(R.id.editTextCategory);

        Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();

        if (bundle != null){
            mode = bundle.getInt(MODE, NEW);
        }else{
            mode = NEW;
        }

        if (mode == EDIT){

            setTitle(R.string.edit_category);

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {

                    int id = bundle.getInt(ID);

                    SeriesDatabase database = SeriesDatabase.getDatabase(FormCategoryActivity.this);

                    category = database.categoryDao().queryForId(id);

                    FormCategoryActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            editTextCategory.setText(category.getName());
                        }
                    });
                }
            });

        }else{

            setTitle(R.string.new_category);

            category = new Category("");
        }
    }

    private void register(){

        final String name  = UtilsGUI.checkText(this,
                editTextCategory,
                R.string.name_empty);
        if (name == null){
            return;
        }

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                SeriesDatabase database = SeriesDatabase.getDatabase(FormCategoryActivity.this);

                List<Category> list = database.categoryDao().queryForName(name);

                System.out.println(list);

                if (mode == NEW) {

                    if (list.size() > 0){

                        FormCategoryActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                UtilsGUI.warningError(FormCategoryActivity.this, R.string.category_exists);
                            }
                        });

                        return;
                    }

                    category.setName(name);

                    database.categoryDao().insert(category);

                } else {

                    if (!name.equals(category.getName())){

                        if (list.size() >= 1){

                            FormCategoryActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    UtilsGUI.warningError(FormCategoryActivity.this, R.string.category_exists);
                                }
                            });

                            return;
                        }

                        category.setName(name);

                        database.categoryDao().update(category);
                    }
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
                register();
                return true;
            case R.id.menuItemCancel:
                cancel();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
