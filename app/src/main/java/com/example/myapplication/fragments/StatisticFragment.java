package com.example.myapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.utils.BarChartSetup;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;

public class StatisticFragment extends Fragment {
    private BarChart barChart;
    private BarData barData;

    public StatisticFragment(){
        this.barData = new BarData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.statistic_fragment,container,false);
        this.barChart = BarChartSetup.getBarChart(view);
        this.barData.setValueTextSize(15f);
        barChart.setData(this.barData);
        barChart.invalidate();
        return view;
    }
    public  void updateChart( BarData barData){
        this.barData = barData;
    }

}
