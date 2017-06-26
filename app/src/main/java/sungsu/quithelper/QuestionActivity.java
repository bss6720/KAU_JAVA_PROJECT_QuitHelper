package sungsu.quithelper;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class QuestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(checkQuestionsAnswered()) {
            startActivity(new Intent(this, MainActivity.class));
        }
        setContentView(R.layout.activity_question);
    }
    public void saveInformation(View view) {
        String name, date, age, average;
        name = ((EditText) findViewById(R.id.user_name)).getText().toString();
        age = ((EditText) findViewById(R.id.user_age)).getText().toString();
        date = (((EditText) findViewById(R.id.user_smoked))).getText().toString();
        average = ((EditText) findViewById(R.id.user_average_smoke)).getText().toString();
        Date nalzza = new Date();
        if(name.equals("")) {
            Toast.makeText(getApplicationContext(),"이름을 입력해 주세요.",Toast.LENGTH_LONG).show();
        } else if(age.equals("")) {
            Toast.makeText(getApplicationContext(),"나이를 입력해 주세요.",Toast.LENGTH_LONG).show();
        } else if(date.equals("")) {
            Toast.makeText(getApplicationContext(),"날짜를 입력해 주세요.",Toast.LENGTH_LONG).show();
        } else if(average.equals("")) {
            Toast.makeText(getApplicationContext(),"평균 흡연횟수를 입력해 주세요.",Toast.LENGTH_LONG).show();
        } else if(!nalzza.setDate(date)) {
            Toast.makeText(getApplicationContext(),"yyyymmdd 형식이 아니거나 잘못된 날짜입니다.",Toast.LENGTH_LONG).show();
        } else{
            Toast.makeText(getApplicationContext(),"데이터 생성중...",Toast.LENGTH_LONG).show();
            SharedPreferences pref = getSharedPreferences("UserData", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("userName",name);
            editor.putInt("userAge",Integer.parseInt(age));
            editor.putString("smokeDate",date);
            editor.putInt("smokeAverage",Integer.parseInt(average));
            editor.putBoolean("hasAnswered", true);
            editor.apply();
            DBHandler dbHandler=new DBHandler(this,null,null,1);
            dbHandler.add(pref.getString("smokeDate",getDate()),pref.getInt("smokeAverage",0));
            dbHandler.update(pref.getInt("smokeAverage",0));
            dbHandler.delete(getDate());
            dbHandler.add(getDate(),0);
            dbHandler.close();
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    //앱 처음 실행시 물어보는 질문들을 답했는지 확인
    public boolean checkQuestionsAnswered() {
        SharedPreferences pref = getSharedPreferences("UserData", Activity.MODE_PRIVATE); //default mode 0
        boolean hasAnswered = pref.getBoolean("hasAnswered",false); //false여야됨
        return hasAnswered;
    }

    //오늘의 날짜를 형식에 맞게 리턴
    public String getDate() {
        Date date = new Date();
        return date.toString();
    }
}
