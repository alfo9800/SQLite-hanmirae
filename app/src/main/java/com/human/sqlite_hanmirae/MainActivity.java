package com.human.sqlite_hanmirae;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import com.human.sqlite_hanmirae.DatabaseTables.StudentTable;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper mDatabaseHelper; //현재 클래스에서 사용할 멤버변수 생성
    private SQLiteDatabase mSqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //신규 데이터베이스 객체 생성(=메모리에 올리기, 실행 가능하게 만들기)
        //=데이터베이스헬퍼클래스의 생성자 메서드 실행
        mDatabaseHelper = new DatabaseHelper(this,"school.db",null,1);

        //데이터베이스 파일 만들기
        mSqLiteDatabase = mDatabaseHelper.getWritableDatabase();

        //mSQLiteDatabase 객체를 이용해서 더미데이터 insert test
        //자바의 HashMap형식과 비슷한 안드로이그 데이터형 ContentsValues형
        ContentValues contentValues = new ContentValues();
        contentValues.put(StudentTable.GRADE,1);
        contentValues.put(StudentTable.NUMBER,201713077);
        contentValues.put(StudentTable.NAME,"한미래");
        mSqLiteDatabase.insert(StudentTable.TABLE_NAME,null, contentValues);
    }
}