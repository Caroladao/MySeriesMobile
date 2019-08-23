package br.edu.utfpr.carolineadao.myseries;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.edu.utfpr.carolineadao.myseries.classDB.SeriesDatabase;
import br.edu.utfpr.carolineadao.myseries.models.Category;
import br.edu.utfpr.carolineadao.myseries.models.Serie;

public class itemSerie extends ArrayAdapter<Serie> {

    Context mContext;
    Category category;
    int ID;
    TextView serie_category;

    itemSerie(Context context, List<Serie> series){
        super(context, R.layout.item_serie, series);
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View itemView = layoutInflater.inflate(R.layout.item_serie, parent, false);

        Serie serie = getItem(position);

        TextView serie_name = (TextView) itemView.findViewById(R.id.name_serie);
        TextView serie_seasons = (TextView) itemView.findViewById(R.id.season_serie);
        TextView serie_status = (TextView) itemView.findViewById(R.id.status_serie);
        ImageView img_status = (ImageView) itemView.findViewById(R.id.img_status);
        serie_category = (TextView) itemView.findViewById(R.id.category_serie);

        ID = serie.getCategoryId();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                SeriesDatabase database = SeriesDatabase.getDatabase(mContext);

                category = database.categoryDao().queryForId(ID);

                serie_category.setText(mContext.getString(R.string.category)+": " + category.getName());
            }
        });


        if(serie.getStatus().equalsIgnoreCase(mContext.getString(R.string.interests))){
            img_status.setImageResource(R.drawable.star);
        }else if(serie.getStatus().equalsIgnoreCase(mContext.getString(R.string.watching))){
            img_status.setImageResource(R.drawable.play);
        }else{
            img_status.setImageResource(R.drawable.check);
        }
        serie_name.setText(serie.getName());
        serie_seasons.setText(serie.getSeasons()+" "+ mContext.getString(R.string.seasons));
        serie_status.setText(serie.getStatus());

        return itemView;
    }
}
