package br.edu.utfpr.carolineadao.myseries.classDB;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.concurrent.Executors;

import br.edu.utfpr.carolineadao.myseries.R;
import br.edu.utfpr.carolineadao.myseries.models.Serie;
import br.edu.utfpr.carolineadao.myseries.models.Category;

@Database(entities = {Serie.class, Category.class}, version = 1)
public abstract class SeriesDatabase extends RoomDatabase {

    public abstract SerieDao serieDao();

    public abstract CategoryDao categoryDao();

    private static SeriesDatabase instance;

    public static SeriesDatabase getDatabase(final Context context) {

        if (instance == null) {

            synchronized (SeriesDatabase.class) {
                if (instance == null) {
                    RoomDatabase.Builder builder =  Room.databaseBuilder(context,
                            SeriesDatabase.class,
                            "series.db");

                    builder.addCallback(new RoomDatabase.Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                                @Override
                                public void run() {
                                    initialCategories(context);
                                }
                            });
                        }
                    });

                    instance = (SeriesDatabase) builder.build();
                }
            }
        }

        return instance;
    }

    private static void initialCategories(final Context context){

        String[] names = context.getResources().getStringArray(R.array.initial_categories);

        for (String name : names) {

            Category category = new Category(name);

            instance.categoryDao().insert(category);
        }
    }
}
