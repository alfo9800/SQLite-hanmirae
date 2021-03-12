package com.human.sqlite_hanmirae;
/*
StudentVO 클래스는 xml과 DB와 MainActivuty.Java와 데이터를 get/set하기 위해서
 */
public class StudentVO {
    //VO클래스의 멤버변수
    private int mId; //Cursor id(=레코드 아이디)
    private int mGrade; //학년
    private int mNumber; //학번
    private String mName; //이름

    //get . set
    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public int getmGrade() {
        return mGrade;
    }

    public void setmGrade(int mGrade) {
        this.mGrade = mGrade;
    }

    public int getmNumber() {
        return mNumber;
    }

    public void setmNumber(int mNumber) {
        this.mNumber = mNumber;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }
}
