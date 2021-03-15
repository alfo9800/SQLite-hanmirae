package com.human.sqlite_hanmirae;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import com.human.sqlite_hanmirae.DatabaseTables.StudentTable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //현재 클래스에서 사용할 멤버변수 생성
    private DatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mSqLiteDatabase; //sql템플릿(insert,select..)이 여기 포함
    private RecyclerAdapter mRecyclerAdapter;
    private List mItemList = new ArrayList<StudentVO>(); //아예 객체 생성. select쿼리결과 저장 객체.

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
        /*
        ContentValues contentValues = new ContentValues();
        contentValues.put(StudentTable.GRADE,3);
        contentValues.put(StudentTable.NUMBER,201713099);
        contentValues.put(StudentTable.NAME,"아무개");
        mSqLiteDatabase.insert(StudentTable.TABLE_NAME,null, contentValues);
        */

        //mItemList에 select쿼리 결과값이 set 되어 있어야 함.

        //List쿼리 실행 리사이클러 어댑터 바인딩
        bindList(); //->공간 마련

        //List반영(화면출력)
        updateList(); //->데이터가 여기서 바인딩 되서 RecyclerAdapter가 화면에 재생됨.
    }

    private void updateList() {
        mItemList.clear();
        mItemList.addAll(getAllData());
        //안드로이드 전용 클래스이고, 메서드임.
        mRecyclerAdapter.notifyDataSetChanged(); //어댑터에 실제 값이 들어가면서 화면에 뿌려짐.
    }

    //============select쿼리 결과를 return함.============
    private List getAllData() {
        List tableList = new ArrayList<>(); //studentTable내용이 담길 예정.
        //쿼리작업
        return tableList;
    }

    //List실행 리사이클러 어댑터 바인딩
    private void bindList() {
        //객체생성
        mRecyclerAdapter = new RecyclerAdapter(mItemList);
    }
}