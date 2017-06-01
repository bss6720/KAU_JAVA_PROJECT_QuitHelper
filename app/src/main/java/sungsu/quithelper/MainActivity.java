package sungsu.quithelper;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences pref = getSharedPreferences("UserData", Activity.MODE_PRIVATE);
        if(!checkQuestionsAnswered()) {
            //질문 activity 실행.


            //DB 생성
            //***여기까지 5/31 수요일 4시
        }
        setContentView(R.layout.activity_main);
        //기본화면 코딩

    }

    //앱 처음 실행시 물어보는 질문들을 답했는지 확인
    public boolean checkQuestionsAnswered() {
        SharedPreferences pref = getSharedPreferences("UserData", Activity.MODE_PRIVATE); //default mode 0
        boolean hasAnswered = pref.getBoolean("hasAnswered",false); //false여야됨
        return hasAnswered;
    }

    public void incrementCounter (View view) {
        DBHandler dbHandler = new DBHandler(this, null, null, 1);

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyMMdd");
        String todayDate =simpleDateFormat.format(date);


    }

}
