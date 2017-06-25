package sungsu.quithelper;


import java.util.*;

public class Smoke {
    private int id;
    private Date date;
    private int count;
    public Smoke(int id, String date, int count) {
        this.id = id;
        this.date = new Date(date);
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public int getCount() {
        return count;
    }

    /*
    @Override
    public String toString() {
        return id + ": " +date.substring(0,4)+"년 " + date.substring(4,6) + "월 "+date.substring(6,8) + "일 " + count+"회";
    }*/
}
