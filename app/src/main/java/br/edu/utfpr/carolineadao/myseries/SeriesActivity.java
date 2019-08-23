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
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import br.edu.utfpr.carolineadao.myseries.classDB.SeriesDatabase;
import br.edu.utfpr.carolineadao.myseries.models.Serie;
import br.edu.utfpr.carolineadao.myseries.utils.UtilsGUI;

public class SeriesActivity extends AppCompatActivity {

    private static final int REQUEST_NEW_SERIE    = 1;
    private static final int REQUEST_EDIT_SERIE   = 2;

    private ListView             listViewSerie;
    private ArrayAdapter<Serie>  listAdapter;
    private List<Serie>          list;


    private ActionMode              actionMode;
    private int                     positionSelect = -1;
    private View                    viewSelect;

    SharedPref sharedPref;

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

            Serie serie = list.get(positionSelect);

            switch(item.getItemId()){
                case R.id.menuItemEdit:
                    FormSerieActivity.editSerie(SeriesActivity.this,
                            REQUEST_EDIT_SERIE,
                            serie);
                    mode.finish();
                    return true;

                case R.id.menuItemDelete:
                    deleteSerie(serie);
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

            listViewSerie.setEnabled(true);
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

        setContentView(R.layout.activity_series);

        listViewSerie = findViewById(R.id.listViewSerie);

        listViewSerie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Serie serie = (Serie) parent.getItemAtPosition(position);

                FormSerieActivity.editSerie(SeriesActivity.this,
                        REQUEST_EDIT_SERIE,
                        serie);
            }
        });

        listViewSerie.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (actionMode != null){
                    return false;
                }

                positionSelect = position;

                view.setBackgroundColor(Color.LTGRAY);

                viewSelect = view;

                listViewSerie.setEnabled(false);

                actionMode = startSupportActionMode(mActionModeCallback);

                return true;
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        FloatingActionButton btn = (FloatingActionButton) findViewById(R.id.btn_register);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkCategories();
            }
        });

        loadSeries();

        registerForContextMenu(listViewSerie);
    }

    /* MUDAR */
    private void loadSeries(){

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                SeriesDatabase database = SeriesDatabase.getDatabase(SeriesActivity.this);

                list = database.serieDao().queryAll();

                SeriesActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listAdapter = new itemSerie(SeriesActivity.this, list);

                        listViewSerie.setAdapter(listAdapter);
                    }
                });
            }
        });
    }

    private void deleteSerie(final Serie serie){

        String message = getString(R.string.want_delete)
                + "\n" + serie.getName();

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
                                                SeriesDatabase.getDatabase(SeriesActivity.this);

                                        database.serieDao().delete(serie);

                                        SeriesActivity.this.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                listAdapter.remove(serie);
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

        if ((requestCode == REQUEST_NEW_SERIE || requestCode == REQUEST_EDIT_SERIE)
                && resultCode == Activity.RESULT_OK){

            /* MUDAR */
            loadSeries();
        }
    }

    private void checkCategories(){

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                SeriesDatabase database = SeriesDatabase.getDatabase(SeriesActivity.this);

                int total = database.categoryDao().total();

                if (total == 0){

                    SeriesActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            UtilsGUI.warningError(SeriesActivity.this, R.string.empty_categories);
                        }
                    });

                    return;
                }

                FormSerieActivity.newSerie(SeriesActivity.this, REQUEST_NEW_SERIE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case R.id.menuItemCategory:
                CategoryActivity.open(this);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.options_edit, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info;

        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        Serie serie = (Serie) listViewSerie.getItemAtPosition(info.position);

        switch(item.getItemId()){

            case R.id.menuItemEdit:
                FormSerieActivity.editSerie(this,
                        REQUEST_EDIT_SERIE,
                        serie);
                return true;

            case R.id.menuItemDelete:
                deleteSerie(serie);
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }
}