package com.example.myapplication.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.View;

import androidx.annotation.StyleRes;
import androidx.core.content.ContextCompat;

import com.example.myapplication.R;
import com.example.myapplication.models.CheckItem;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;


public final class BarChartSetup {
    private static final float font_size = 15f;
    private static final int anim_duration = 800;


    public static BarChart getBarChart(final View view){
        BarChart barChart = view.findViewById(R.id.chart);
        barChart.getAxisLeft().setStartAtZero(true);
        barChart.getAxisRight().setStartAtZero(true);
        barChart.setScaleEnabled(false);
        barChart.setFitBars(true);
        barChart.setAutoScaleMinMaxEnabled(false);
        barChart.setScaleEnabled(false);
        barChart.getXAxis().setEnabled(false);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getDescription().setEnabled(false);
        barChart.animateY(anim_duration);
        barChart.setNoDataText(view.getResources().getString(R.string.start_planning_chart));
        barChart.setNoDataTextColor(view.getContext().getColor(R.color.white));
        barChart.getLegend().setTextColor(Color.WHITE);
        barChart.getLegend().setTextSize(font_size);
        barChart.getAxisLeft().setTextColor(Color.WHITE);

        return barChart;
    }

    public static BarData getDataSets(Context ctx, List<CheckItem> list ){

        ArrayList <BarEntry> progress = new ArrayList<>();
        ArrayList <BarEntry> plans = new ArrayList<>();

        progress.add( new BarEntry(1, countChecked(list)));
        plans.add( new BarEntry   (2, list.size()));

        BarDataSet barDataSetProgress = getBarDataSet(ctx,progress,
                                        ctx.getString(R.string.chart_label_1),
                                        R.color.completed_tasks);
        BarDataSet barDataSetPlans    = getBarDataSet(ctx,plans,
                                        ctx.getString(R.string.chart_label_2),
                                        R.color.plans);
        BarData barData = new BarData();
        barData.addDataSet(barDataSetProgress);
        barData.addDataSet(barDataSetPlans);
        barData.setHighlightEnabled(false);
        barData.setValueTextSize(font_size);
        return barData;
    }

    private static BarDataSet getBarDataSet(Context ctx,ArrayList<BarEntry> entries, String label, int color){
        BarDataSet barDataSet = new BarDataSet(entries,label);
        barDataSet.setColor(ctx.getColor(color));
        barDataSet.setValueTextColor(ctx.getColor(color));
        barDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return "" + ( int ) value;
            }
        });
        return barDataSet;
    }

    private static long countChecked(List<CheckItem> list){
        long count  = 0;
        for (CheckItem item : list)
            count += (item.getComplete()) ? 1: 0;
        return count;
    }
}
