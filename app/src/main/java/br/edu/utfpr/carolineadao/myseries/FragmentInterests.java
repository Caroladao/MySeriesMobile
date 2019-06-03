package br.edu.utfpr.carolineadao.myseries;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.edu.utfpr.carolineadao.myseries.models.Serie;

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

        floatBtn = v.findViewById(R.id.btn_register);

        seriesRecyclerView = (RecyclerView) v.findViewById(R.id.interests_recyclerview);
        //recyclerViewAdapter = new RecyclerViewAdapter(getContext(),lstSeries);
        seriesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        seriesRecyclerView.setAdapter(recyclerViewAdapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lstSeries = new ArrayList<>();

        //lstSeries = ((SeriesActivity) getActivity()).getLstAll();

    }
}
