package br.edu.utfpr.carolineadao.myseries;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import br.edu.utfpr.carolineadao.myseries.classDB.SeriesDatabase;
import br.edu.utfpr.carolineadao.myseries.models.Category;
import br.edu.utfpr.carolineadao.myseries.models.Serie;
import br.edu.utfpr.carolineadao.myseries.utils.UtilsGUI;

public class CategoryActivity extends AppCompatActivity {

    private static final int REQUEST_NEW_CATEGORY   = 1;
    private static final int REQUEST_EDIT_CATEGORY  = 2;

    private ListView                listViewCategories;
    private ArrayAdapter<Category>  listAdapter;
    private List<Category>          list;

    private ActionMode              actionMode;
    private int                     positionSelect = -1;
    private View                    viewSelect;

    SharedPref sharedPref;

    public static void open(Activity activity){

        Intent intent = new Intent(activity, CategoryActivity.class);

        activity.startActivity(intent);
    }

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {

            MenuInflater inflate = mode.getMenuInflater();
            inflate.inflate(R.menu.options_edit, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            Category category = list.get(positionSelect);

            switch(item.getItemId()){
                case R.id.menuItemEdit:
                    FormCategoryActivity.editCategory(CategoryActivity.this,
                            REQUEST_EDIT_CATEGORY,
                            category);
                    mode.finish();
                    return true;

                case R.id.menuItemDelete:
                    verifyCategory(category);
                    mode.finish();
                    return true;

                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

            if (viewSelect != null){
                viewSelect.setBackgroundColor(Color.TRANSPARENT);
            }

            actionMode         = null;
            viewSelect         = null;

            listViewCategories.setEnabled(true);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);
        if(sharedPref.loadDarkModeState() == true) {
            setTheme(R.style.darkTheme);
        }else {
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        listViewCategories = findViewById(R.id.listViewItens);

        listViewCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Category category = (Category) parent.getItemAtPosition(position);

                FormCategoryActivity.editCategory(CategoryActivity.this,
                        REQUEST_EDIT_CATEGORY,
                        category);
            }
        });

        listViewCategories.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (actionMode != null){
                    return false;
                }

                positionSelect = position;

                view.setBackgroundColor(Color.LTGRAY);

                viewSelect = view;

                listViewCategories.setEnabled(false);

                actionMode = startSupportActionMode(mActionModeCallback);

                return true;
            }
        });

        FloatingActionButton btn = (FloatingActionButton) findViewById(R.id.btn_register);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FormCategoryActivity.newCategory(CategoryActivity.this, REQUEST_NEW_CATEGORY);
            }
        });

        loadCategories();

        registerForContextMenu(listViewCategories);

        setTitle(R.string.categories);
    }

    private void loadCategories(){

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                SeriesDatabase database = SeriesDatabase.getDatabase(CategoryActivity.this);

                list = database.categoryDao().queryAll();

                CategoryActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listAdapter = new ArrayAdapter<>(CategoryActivity.this,
                                android.R.layout.simple_list_item_1,
                                list);

                        listViewCategories.setAdapter(listAdapter);
                    }
                });
            }
        });
    }

    private void verifyCategory(final Category category){

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                SeriesDatabase database = SeriesDatabase.getDatabase(CategoryActivity.this);

                List<Serie> list = database.serieDao().queryForCategoryId(category.getId());

                if (list != null && list.size() > 0){

                    CategoryActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            UtilsGUI.warningError(CategoryActivity.this, R.string.category_used);
                        }
                    });

                    return;
                }

                CategoryActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        deleteCategory(category);
                    }
                });
            }
        });
    }

    private void deleteCategory(final Category category){

        String message = getString(R.string.want_delete) + "\n" + category.getName();

        DialogInterface.OnClickListener listener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch(which){
                            case DialogInterface.BUTTON_POSITIVE:

                                AsyncTask.execute(new Runnable() {
                                    @Override
                                    public void run() {

                                        SeriesDatabase database =
                                                SeriesDatabase.getDatabase(CategoryActivity.this);

                                        database.categoryDao().delete(category);

                                        CategoryActivity.this.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                listAdapter.remove(category);
                                            }
                                        });
                                    }
                                });

                                break;

                            case DialogInterface.BUTTON_NEGATIVE:

                                break;
                        }
                    }
                };

        UtilsGUI.confirmAction(this, message, listener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if ((requestCode == REQUEST_NEW_CATEGORY || requestCode == REQUEST_EDIT_CATEGORY)
                && resultCode == Activity.RESULT_OK){

            loadCategories();
        }
    }
}