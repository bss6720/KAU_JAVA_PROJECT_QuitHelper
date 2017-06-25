package sungsu.quithelper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RecordGraph extends AppCompatActivity {
    int [][]points;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_graph);
        points = new int[7][3];
    }
}
