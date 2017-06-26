package sungsu.quithelper;

import android.widget.TextView;

import java.util.ArrayList;
public class Smoker extends Person {
    ArrayList<Smoke> smokeHistory;
    boolean quitSmoking;
    DBHandler dbHandler;
    TextView state;
    TextView info;
    public Smoker(String name, int age, MainActivity main) {
        super(name,age);
        quitSmoking=false;
        dbHandler = new DBHandler(main, null, null, 1);
        state = (TextView) main.findViewById(R.id.userstate);
        info = (TextView) main.findViewById(R.id.information);
        dbHandler.update(0);
        smokeHistory = dbHandler.getHistory();
        updateUserState();
    }

    //흡연하기
    public void smokes() {
        dbHandler.update(0);
        String todayDate =getDate();
        dbHandler.incrementCount(todayDate);
        updateUserState();
    }
    //smokeHistory 업데이트하기, 만약 년도가 바뀌면 age를 바꿔준다.
    public void setHistory() {
        Date latestDate = smokeHistory.get(smokeHistory.size()-1).getDate();
        smokeHistory= dbHandler.getHistory();
        Date currentDate = smokeHistory.get(smokeHistory.size()-1).getDate();
        if(latestDate.getYear()!=currentDate.getYear()) {
            yearOlder();
        }
    }

    public void updateUserState() {
        setHistory();
        int numSmoke = smokeHistory.get(smokeHistory.size()-1).getCount();
        if(numSmoke==0) {
            //금연중인 일수를 찾음.
            quitSmoking=true;
            int smokeFree = 0;
            for(int i=smokeHistory.size()-1;i>=0;i--) {
                int d = smokeHistory.get(i).getCount();
                if(d == 0) {
                    smokeFree++;
                } else {
                    break;
                }
            }
            state.setText(getName()+"님은 오늘 "+numSmoke+"번 흡연하셨습니다.\n오늘로 금연 "+smokeFree+"일째 입니다.");
        } else {
            quitSmoking=true;
            state.setText(getName()+"님은 오늘 "+numSmoke+"번 흡연하셨습니다.\n오늘 흡연에 사용한 돈: "+(numSmoke*225)+"원");
        }
    }

    public String getDate() {
        Date date = new Date();
        return date.toString();
    }

    public void getDailyHistory(int dailyPoints[]) {

        for(int i = 0;i < 7 ;i++) {
            Smoke smoke = smokeHistory.get(smokeHistory.size()-i-1);
            dailyPoints[6-i] = smoke.getCount();
        }
    }

    public void getMonthlyHistory(int monthlyPoints[]) {
        Date endDate = smokeHistory.get(smokeHistory.size()-1).getDate();
        int i = smokeHistory.size()-1;
        int j = 6;
        while(i >= 0 && j >= 0) {
            if(smokeHistory.get(i).getDate().getMonth() == endDate.getMonth()) {
                monthlyPoints[j]+=smokeHistory.get(i).getCount();
                i--;
            } else {
                j--;
                endDate = smokeHistory.get(i).getDate();
            }
        }
    }

    public void getYearlyHistory(int yearlyPoints[]) {
        Date endDate = smokeHistory.get(smokeHistory.size()-1).getDate();
        int i = smokeHistory.size()-1;
        int j = 6;
        while(i >= 0 && j >= 0) {
            if(smokeHistory.get(i).getDate().getYear() == endDate.getYear()) {
                yearlyPoints[j]+=smokeHistory.get(i).getCount();
                i--;
            } else {
                j--;
                endDate = smokeHistory.get(i).getDate();
            }
        }
    }
}
