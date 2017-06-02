package sungsu.quithelper;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView res;
    DBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHandler = new DBHandler(this, null, null, 1);
        res = (TextView) findViewById(R.id.history);
        SharedPreferences pref = getSharedPreferences("UserData", Activity.MODE_PRIVATE);
        if(!(checkQuestionsAnswered())) {
            //질문 activity

            //DB 생성
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("hasAnswered", true);
            editor.apply();
            dbHandler.add(getDate(),0);
        }
        dbHandler.update(getDate(),0);
        updateResult();
        //기본화면 코딩
    }

    //앱 처음 실행시 물어보는 질문들을 답했는지 확인
    public boolean checkQuestionsAnswered() {
        SharedPreferences pref = getSharedPreferences("UserData", Activity.MODE_PRIVATE); //default mode 0
        boolean hasAnswered = pref.getBoolean("hasAnswered",false); //false여야됨
        return hasAnswered;
    }

    public void incrementCounter (View view) {
        dbHandler.update(getDate(),0);
        String todayDate =getDate();
        dbHandler.incrementCount(todayDate);
        updateResult();
    }

    //오늘의 날짜를 형식에 맞게 리턴
    public String getDate() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyMMdd");
        return simpleDateFormat.format(date);
    }

    public void updateResult() {
        List<Smoke> smokeList = dbHandler.getHistory();
        String result = "";
        for(Smoke smoke : smokeList) {
            result = result + smoke + "\n";
        }
        res.setText(result);
    }
}
