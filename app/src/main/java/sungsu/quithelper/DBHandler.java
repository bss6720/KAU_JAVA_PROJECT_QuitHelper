package sungsu.quithelper;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;

public class DBHandler extends SQLiteOpenHelper {

    //DB의 파일 이름과 열의 데이터타입
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "smokeDB.db";
    private static final String TABLE_SMOKE = "SmokeHistory";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_COUNTER = "counter";

    // DBHandler 생성자로 관리할 DB 이름과 버전 정보를 받음
    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    // DB를 새로 생성할 때 호출되는 함수
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 새로운 테이블 생성
        // 테이블의 이름과 열의 이름과 데이터 타입들을 정해준다.
        String tableName = "CREATE TABLE " + TABLE_SMOKE + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_DATE + " TEXT, " + COLUMN_COUNTER + " INTEGER" + ")";
        db.execSQL(tableName);
    }

    // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // DB에 입력한 값으로 행 추가
    public void add(String date, int count) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO SmokeHistory VALUES(null, " + date + ", " + count + ")");
        db.close();
    }

    // 현재 날짜와 DB의 마지막 날짜를 비교하여 빈 날짜를 새로 생성한다.
    public void update(String date, int count) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_SMOKE,null);
        cursor.moveToLast();
        int latestDate = Integer.parseInt(cursor.getString(1));
        int currentDate = Integer.parseInt(date);
        if(latestDate < currentDate) {
            Calendar latest = Calendar.getInstance();
            latest.set(latestDate/10000,(latestDate%10000)/100-1,1);
            while(latestDate<currentDate) {
                latestDate++;
                if(latestDate%100>=latest.getActualMaximum(Calendar.DATE)+1) {
                    latestDate+=100;
                    latestDate=latestDate/100*100+1;
                    if((latestDate%10000)/100>=13) {
                        latestDate+=10000;
                        latestDate = (latestDate/10000)*10000+101;
                    }
                    latest.set(latestDate/10000,(latestDate%10000)/100-1,1);
                }
                add(Integer.toString(latestDate),count);
            }
        }
        cursor.close();
        db.close();
    }

    //받은 날짜의 count를 1더해준다.
    public void incrementCount(String date) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE "+TABLE_SMOKE+" SET counter = counter + 1 WHERE date = "+date);
        db.close();
    }

    public void delete(String date) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM SmokeHistory WHERE date='" + date + "';");
        db.close();
    }

    //db에 있는 모든 데이터를 list형식으로 반환
    public List<Smoke> getHistory() {
        SQLiteDatabase db = getReadableDatabase();

        List<Smoke> smokeList = new ArrayList<Smoke>();

        // DB에 있는 데이터를 Cursor를 사용하여 테이블에 있는 데이터를 List에 넣어서 반환
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_SMOKE, null);
        while (cursor.moveToNext()) {
            smokeList.add(new Smoke(cursor.getInt(0), cursor.getString(1), cursor.getInt(2)));
        }
        cursor.close();
        return smokeList;
    }

    //특정한 날짜의 데이터를 리턴
    public Smoke findData(String date) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from SmokeHistory where date="+date,null);
        if(cursor!=null) {
            Smoke data = new Smoke(cursor.getInt(0),cursor.getString(1),cursor.getInt(2));
            cursor.close();
            return data;
        } else {
            cursor.close();
            return null;
        }

    }
}


