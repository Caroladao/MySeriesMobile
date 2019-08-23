package br.edu.utfpr.carolineadao.myseries;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.edu.utfpr.carolineadao.myseries.models.Category;

public class itemCategory extends ArrayAdapter<Category> {

    Context mContext;

    itemCategory(Context context, List<Category> category){
        super(context, R.layout.item_category, category);
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View itemView = layoutInflater.inflate(R.layout.item_category, parent, false);

        Category category = getItem(position);

        TextView category_name = (TextView) itemView.findViewById(R.id.name_category);

        category_name.setText(category.getName());

        return itemView;
    }
}
