package com.human.sqlite_hanmirae;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.human.sqlite_hanmirae.DatabaseTables.StudentTable;

import java.util.ArrayList;
import java.util.List;
import com.human.sqlite_hanmirae.DatabaseTables.StudentTable;

public class MainActivity extends AppCompatActivity {

    //현재 클래스에서 사용할 멤버변수 생성
    private DatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mSqLiteDatabase; //sql템플릿(insert,select..)이 여기 포함
    private RecyclerAdapter mRecyclerAdapter;
    private List mItemList = new ArrayList<StudentVO>(); //아예 객체 생성. select쿼리결과 저장 객체.

    //입력,수정,삭제 EditText 변수 선언
    private EditText mEditTextGrade;
    private EditText mEditTextNumber;
    private EditText mEditTextName;

    //어댑터에서 선택한 값 확인 변수
    private int currentCursorId = -1;

    //버튼 button 변수 선언
    private Button mButtonInsert;
    private Button mButtonUpdate;
    private Button mButtonDelete;

    //메인액티비티가 실행되면, 자동으로 실행되는 매소드 onCreate.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //부모 클래스 초기화
        setContentView(R.layout.activity_main); //화면 렌더링 됨.

        //신규 데이터베이스 객체 생성(=메모리에 올리기, 실행 가능하게 만들기)
        //=데이터베이스헬퍼클래스의 생성자 메서드 실행
        mDatabaseHelper = new DatabaseHelper(this,"school.db",null,1);

        //데이터베이스 파일 만들기
        //아래 싱글톤으로 생성한 메서드 / 인스턴스는 1번만 실행됨.
        mSqLiteDatabase = mDatabaseHelper.getWritableDatabase();

        //mSQLiteDatabase 객체를 이용해서 더미데이터 insert test
        //자바의 HashMap형식과 비슷한 안드로이그 데이터형 ContentsValues형
/*
        ContentValues contentValues = new ContentValues();
        contentValues.put(StudentTable.GRADE,2);
        contentValues.put(StudentTable.NUMBER,2017130999);
        contentValues.put(StudentTable.NAME,"아무개2");
        mSqLiteDatabase.insert(StudentTable.TABLE_NAME,null, contentValues);
*/

        /*mItemList에 select쿼리 결과값이 set 되어 있어야 함.*/
        //멤버변수로 객체 초기화 하기
        bindObject();

        //List쿼리 실행 리사이클러 어댑터 바인딩
        bindList(); //->공간 마련

        //List반영(화면출력: 입력,수정,삭제시 화면 리프레스가 필요하고, 구현하는 메서드)
        updateList(); //->데이터가 여기서 바인딩 되서 RecyclerAdapter가 화면에 재생됨.

        //update버튼 클릭 이벤트
        btnUpdate();

        //delete버튼 클릭 이벤트
        btnDelete();
    }

    //================================================================================================================================

    //delete버튼 클릭 이벤트
    private void btnDelete() {
        mButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentCursorId == -1) {
                    Toast.makeText(getApplicationContext(), "선택된 값이 없습니다.", Toast.LENGTH_SHORT).show();
                    return; //선택된 값이 없으면 삭제 이벤트 종료
                }
                //삭제 쿼리 호출
                deleteData(currentCursorId);

                //EditText객체의 값 비우기
                mEditTextGrade.setText("");
                mEditTextNumber.setText("");
                mEditTextName.setText("");
                currentCursorId = -1; //현재 테이블 커서아이디가 지워졌으니, 초기화 시킴.

                //리사이클러 뷰 화면 리프레쉬
                updateList();
            }
        });
    }

    private void deleteData(int currentCursorId) {
        //SQLiteDatabass 템플릿 delete메서드 실행
        mSqLiteDatabase.delete(StudentTable.TABLE_NAME,StudentTable._ID+ "=" +currentCursorId,null);
    }

    //================================================================================================================================

    //Update버튼 클릭 이벤트
    private void btnUpdate() {
        mButtonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentCursorId == -1) {
                    Toast.makeText(getApplicationContext(),"선택된 값이 없습니다.", Toast.LENGTH_SHORT).show();
                    return; //클릭 이벤트 진행 중지
                }
                //DB갱신
                final int grade = Integer.parseInt(mEditTextGrade.getText().toString());
                final int number = Integer.parseInt(mEditTextNumber.getText().toString());
                final String name = mEditTextName.getText().toString();
                //쿼리 메서드 호출
                updateData(currentCursorId,grade,number,name);
                //화면 리프레쉬 재생
                updateList();
            }
        });
    }

    //DB update버튼에 대한 쿼리
    private void updateData(int currentCursorId, int grade, int number, String name) {
        //쿼리 매개변수로 1개의 객테에 값을 담기 위해서 객체변수 생성
        ContentValues contentValues = new ContentValues();
        contentValues.put(StudentTable.GRADE, grade);
        contentValues.put(StudentTable.NUMBER, number);
        contentValues.put(StudentTable.NAME, name);

        //SQLiteDatabass에 있는 템플릿 메서드 중 update사용
        mSqLiteDatabase.update(StudentTable.TABLE_NAME,contentValues,StudentTable._ID+ "=" + currentCursorId, null);
    }

    //================================================================================================================================

    //멤버변수로 객체 초기화
    private void bindObject() {
        //EditText변수를 객체로 생성
        mEditTextGrade = findViewById(R.id.editTextGrade);
        mEditTextNumber = findViewById(R.id.editTextNumber);
        mEditTextName = findViewById(R.id.editTextName);

        //Button변수를 객체로 생성
        mButtonInsert = findViewById(R.id.btnInsert);
        mButtonUpdate = findViewById(R.id.btnUpdate);
        mButtonDelete = findViewById(R.id.btnDelete);
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
        //쿼리작업에서 사용될 필드면 바인딩
        String[] projection = {
                StudentTable._ID, //AutoIncrement 자동증가 PK
                StudentTable.GRADE,
                StudentTable.NUMBER,
                StudentTable.NAME
        };
        //쿼리 템플릿 메서드 사용 (Cursor:커서는 테이블의 레코드 위치)
        Cursor cursor = mSqLiteDatabase.query(StudentTable.TABLE_NAME, projection,null,null,null,null,"_id desc");
        //반복문조건은 커서테이블의 다음레코드가 있을 때 까지
        while (cursor.moveToNext()) { //studentTable에 있는 필드값을 하나씩 추출해 tableList 객체에 1레코드씩 저장
            int p_id = cursor.getInt(cursor.getColumnIndexOrThrow(StudentTable._ID));
            int p_grade = cursor.getInt(cursor.getColumnIndexOrThrow(StudentTable.GRADE));
            int p_number = cursor.getInt(cursor.getColumnIndexOrThrow(StudentTable.NUMBER));
            String p_name = cursor.getString(cursor.getColumnIndexOrThrow(StudentTable.NAME));
            //매개변수가 없는 클래스의 생성자 메서드는 자바가 자동으로 만들어줌.
            tableList.add(new StudentVO(p_id, p_grade, p_number, p_name));
        }
        return tableList;
    }

    //List실행 리사이클러 어댑터 바인딩
    private void bindList() {
        //객체생성
        mRecyclerAdapter = new RecyclerAdapter(mItemList);

        mRecyclerAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                StudentVO studentVO = (StudentVO) mItemList.get(position);

                //디버그
                currentCursorId = studentVO.getmId();
                Toast.makeText(getApplicationContext(),"현재 선택한 커서 레코드 Id는 "+ currentCursorId, Toast.LENGTH_SHORT).show();
                mEditTextGrade.setText(Integer.toString(studentVO.getmGrade()));
                mEditTextNumber.setText(Integer.toString(studentVO.getmNumber()));
                mEditTextName.setText(studentVO.getmName());
            }
        });
        //리사이클러뷰xml과 어댑터 바인딩(attach) No adapter attached
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true); //리시아클러뷰의 높이를 고정함.
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mRecyclerAdapter); //실제 attach(=바인딩)

    }
}