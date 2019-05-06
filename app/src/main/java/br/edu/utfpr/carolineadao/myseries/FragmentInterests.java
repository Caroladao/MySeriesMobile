package br.edu.utfpr.carolineadao.myseries;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FragmentInterests extends Fragment {
    View v;
    private RecyclerView seriesRecyclerView;
    private List<Serie> lstSeries;
    FloatingActionButton floatBtn;
    private RecyclerViewAdapter recyclerViewAdapter;

    public FragmentInterests() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.interests_fragment,container,false);

        floatBtn = v.findViewById(R.id.btn_add);

        seriesRecyclerView = (RecyclerView) v.findViewById(R.id.interests_recyclerview);
        recyclerViewAdapter = new RecyclerViewAdapter(getContext(),lstSeries);
        seriesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        seriesRecyclerView.setAdapter(recyclerViewAdapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lstSeries = ((SeriesActivity) getActivity()).getLstAll();

        Category category = new Category("Terror",20);

        if(((SeriesActivity) getActivity()).getLstAll().isEmpty()) {

            ((SeriesActivity) getActivity()).setLstAll(new Serie("The Vampire Diaries", 8, category, "Interesses"));
            ((SeriesActivity) getActivity()).setLstAll(new Serie("The Walking Dead", 9, category, "Interesses"));
            ((SeriesActivity) getActivity()).setLstAll(new Serie("Supergirl", 4, category, "Interesses"));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
