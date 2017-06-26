package sungsu.quithelper;

import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
public class Smoker extends Person {
    ArrayList<Smoke> smokeHistory;
    boolean quitSmoking;
    DBHandler dbHandler;
    TextView state;
    TextView info;
    LinearLayout layoutMain;
    String[] healthChange;
    public Smoker(String name, int age, MainActivity main) {
        super(name,age);
        quitSmoking=false;
        dbHandler = new DBHandler(main, null, null, 1);
        state = (TextView) main.findViewById(R.id.userstate);
        info = (TextView) main.findViewById(R.id.information);
        layoutMain = (LinearLayout) main.findViewById(R.id.mainlayout);
        dbHandler.update(0);
        smokeHistory = dbHandler.getHistory();
        healthChange=new String[8];
        healthChange[0]="금연 후 24시간: 몸에 쌓여있던 일산화탄소가 몸 밖으로 전부 배출";
        healthChange[1]="금연 후 1주 후: 가래의 점도와 색이 정상적으로 돌아오며, 흡연에 대한 욕구가 생기기 시작";
        healthChange[2]="금연 후 1개월: 피부개선 및 폐기능 30% 향상";
        healthChange[3]="금연 후 3개월: 피로와 호흡곤란 증상이 완화되며, 호흡기 질환에 대한 면역력이 강화, 폐 정상기능";
        healthChange[4]="금연 후 1년: 모든 세포가 정상화되며, 심장병 발병률이 절반으로 떨어집니다.";
        healthChange[5]="금연 후 5년: 자궁경부암, 뇌졸증, 중풍등의 발병률이 비흡연자와 비슷한 수준으로 떨어집니다. 또한 신체 체중도 증가합니다.";
        healthChange[6]="금연 후 10년: 폐암 사망률이 흡연자의 절반 수준이 되며, 구강암, 후두암, 식도암, 방광암, 신장암, 췌장암의 발생 위험도 감소";
        healthChange[7]="금연 후 15년: 심방병이 걸릴 위험과 사망위험이 비흡연자와 비슷한 수준이 됩니다.";
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
            layoutMain.setBackgroundColor(Color.rgb(255,255,255));
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

            Date date = new Date(smokeFree/365,smokeFree%365/30,smokeFree%30);
            state.setText(getName()+"님은 오늘 "+numSmoke+"번 흡연하셨습니다.\n오늘로 금연 "+smokeFree+"일째 입니다.");
            if(smokeFree/365>=15) {
                info.setText(healthChange[7]);
            } else if(smokeFree/365>=10) {
                info.setText(healthChange[6]);
            } else if(smokeFree/365>=5) {
                info.setText(healthChange[5]);
            } else if(smokeFree/365>=1) {
                info.setText(healthChange[4]);
            } else if(smokeFree%365/30>=3) {
                info.setText(healthChange[3]);
            } else if(smokeFree%365/30>=1) {
                info.setText(healthChange[2]);
            } else if(smokeFree%30>=7) {
                info.setText(healthChange[1]);
            } else{
                info.setText(healthChange[0]);
            }
        } else {
            int col = 255/(int)(Math.pow(numSmoke,0.5));
            col = Color.rgb(col,col,col);
            layoutMain.setBackgroundColor(col);
            if(col<=Color.LTGRAY) {
                state.setTextColor(Color.WHITE);
                info.setTextColor(Color.WHITE);
            }
            quitSmoking=true;
            state.setText(getName()+"님은 오늘 "+numSmoke+"번 흡연하셨습니다.\n오늘 흡연에 사용한 돈: "+(numSmoke*225)+"원");
            info.setText("흡연자가 흡연 전 상태로 돌아가는데는 약 15년의 시간이 걸립니다.");
        }
    }

    public String getDate() {
        Date date = new Date();
        return date.toString();
    }

    public boolean isQuitSmoking() {
        return quitSmoking;
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
