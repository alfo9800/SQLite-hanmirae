package com.human.sqlite_hanmirae;

import android.provider.BaseColumns;

/*
DatabaseTables클래스는 물리테이블과 DAO클래스와 데이터 연동 시 필요.
알려진 용어로는 Contract(계약서)라고 함.
 */
/*
아래 클래스는 '중첩클래스'
: 데이터형클래스를 여러개 생성할 때 필요한 클래스 구조(관리향상)
 */
public class DatabaseTables {

    //학생테이블용 필드값 클래스로 지정
    public static class StudentTable implements BaseColumns {
        public static final String TABLE_NAME = "student.db";
        public static final String GRADE = "grade";
        public static final String NUMBER = "number";
        public static final String NAME = "name";
    }
}
