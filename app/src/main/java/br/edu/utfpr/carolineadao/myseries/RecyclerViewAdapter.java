package br.edu.utfpr.carolineadao.myseries;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.edu.utfpr.carolineadao.myseries.models.Serie;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    Context mContext;
    List<Serie> mData;
    View v;

    public RecyclerViewAdapter(Context mContext, List<Serie> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        v = LayoutInflater.from(mContext).inflate(R.layout.item_serie,viewGroup,false);
        MyViewHolder vHolder = new MyViewHolder(v);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.serie_name.setText(mData.get(position).getName());
        holder.serie_seasons.setText(mData.get(position).getSeasons()+" "+ mContext.getString(R.string.seasons));

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Toast.makeText(mContext,"clicou na posição "+position,Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView serie_name;
        private TextView serie_seasons;
        private TextView serie_category;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            serie_name = (TextView) itemView.findViewById(R.id.name_serie);
            serie_seasons = (TextView) itemView.findViewById(R.id.season_serie);
            serie_category = (TextView) itemView.findViewById(R.id.category_serie);
        }
    }
}
