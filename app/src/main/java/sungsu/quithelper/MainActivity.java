package sungsu.quithelper;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    TextView res, todaySmoke;
    DBHandler dbHandler;
    static Smoker smoker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences pref = getSharedPreferences("UserData", Activity.MODE_PRIVATE);
        smoker = new Smoker(pref.getString("userName",null),pref.getInt("userAge",20), this);
        //dbHandler = new DBHandler(this, null, null, 1);
        //res = (TextView) findViewById(R.id.history);
        //dbHandler.update(0);
        //updateResult();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.record,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(this, RecordGraph.class));
        return super.onOptionsItemSelected(item);
    }
    //흡연하기 onclick
    public void incrementCounter (View view) {
        smoker.smokes();
        /*
        dbHandler.update(0);
        String todayDate =getDate();
        dbHandler.incrementCount(todayDate);
        updateResult();*/
    }

    //오늘의 날짜를 형식에 맞게 리턴
    public String getDate() {
        Date date = new Date();
        return date.toString();
    }

    /*
    public void updateResult() {
        List<Smoke> smokeList = dbHandler.getHistory();
        String result = "";
        for(Smoke smoke : smokeList) {
            result = result + smoke + "\n";
        }
        res.setText(result);
    }*/
}
