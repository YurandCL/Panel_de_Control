package com.lobosistemas.gestiondeventas;

import android.graphics.Color;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Estadisticas extends AppCompatActivity {

    BarChart mChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);

        mChart = findViewById(R.id.barChart);

        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);
        mChart.setMaxVisibleValueCount(50);
        mChart.setPinchZoom(false);
        mChart.setDrawGridBackground(true);

        ArrayList<BarEntry> brtEntry = new ArrayList<>();
        brtEntry.add(new BarEntry(1,40f));
        brtEntry.add(new BarEntry(2,44f));
        brtEntry.add(new BarEntry(3,51f));
        brtEntry.add(new BarEntry(4,30f));

        BarDataSet barDataSet = new BarDataSet(brtEntry, "Enero");

        barDataSet.setColors(ColorTemplate.rgb("#00417d"), ColorTemplate.rgb("#faa519"), ColorTemplate.rgb("#73BE46"));
        BarData data = new BarData(barDataSet);
        data.setBarWidth(0.9f);
        mChart.setData(data);

        /*String[] anios = new String[]{"2016", "2017", "2018", "2019"};
        XAxis xaxis = mChart.getXAxis();
        xaxis.setValueFormatter(new anios(anios));*/
    }

    /*public class anios implements IAxisValueFormatter{

        private String[] mvalues;
        public anios(String[] values){
            this.mvalues = values;
        }

        @Override
        public String getFormattedValue (float value, AxisBase axis){
            return mvalues[(int)value];
        }
    }*/
}
