package sungsu.quithelper;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class RecordGraph extends AppCompatActivity {
    int [][]points;
    GraphView graph;
    int[][] dates;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_graph);
        points = new int[3][7];
        MainActivity.smoker.getDailyHistory(points[0]);
        MainActivity.smoker.getMonthlyHistory(points[1]);
        MainActivity.smoker.getYearlyHistory(points[2]);
        graph = (GraphView) findViewById(R.id.graph);
        graph.getViewport().setMaxX(7);
        graph.getViewport().setXAxisBoundsManual(true);
        setGraph(0);
    }

    public void setGraph(int r) {
        graph.removeAllSeries();
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(0, points[r][0]),
                new DataPoint(1, points[r][1]),
                new DataPoint(2, points[r][2]),
                new DataPoint(3, points[r][3]),
                new DataPoint(4, points[r][4]),
                new DataPoint(5, points[r][5]),
                new DataPoint(6, points[r][6])

        });
        graph.addSeries(series);
        series.setSpacing(50);
    }

    public void setDaily(View view) {
        setGraph(0);
    }

    public void setMonthly(View view) {
        setGraph(1);
    }

    public void setYearly(View view) {
        setGraph(2);
    }
}
