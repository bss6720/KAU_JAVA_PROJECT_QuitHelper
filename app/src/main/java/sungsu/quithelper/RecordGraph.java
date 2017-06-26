package sungsu.quithelper;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

public class RecordGraph extends AppCompatActivity {
    int [][]points;
    GraphView graph;
    String[] xLabel;
    TextView xText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_graph);
        points = new int[3][7];
        xLabel = new String[3];
        xLabel[0] = " 6일전    5일전    4일전    3일전    2일전    1일전    오늘";
        xLabel[1] = "   6달전   5달전   4달전    3달전    2달전    1달전   이번달";
        xLabel[2] = "       6년전   5년전   4년전   3년전   2년전   1년전  이번해";
        MainActivity.smoker.getDailyHistory(points[0]);
        MainActivity.smoker.getMonthlyHistory(points[1]);
        MainActivity.smoker.getYearlyHistory(points[2]);
        graph = (GraphView) findViewById(R.id.graph);
        xText = (TextView) findViewById(R.id.timelabel);
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
        xText.setText(xLabel[r]);
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
