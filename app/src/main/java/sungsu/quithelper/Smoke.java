package sungsu.quithelper;


public class Smoke {
    private int id;
    private String date;
    private int count;
    public Smoke(int id, String date, int count) {
        this.id = id;
        this.date = date;
        this.count = count;
    }

    public void setID(int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return id + ": " +date.substring(0,4)+"년 " + date.substring(4,6) + "월 "+date.substring(6,8) + "일 " + count+"회";
    }
}
