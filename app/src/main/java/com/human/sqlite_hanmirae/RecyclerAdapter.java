package com.human.sqlite_hanmirae;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/*
RecyclerAdapter클래스는 recyclerView콤포넌트와 SQLiteDB를 바이딩 시키는 기능.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {

    //android에서 제공되는 OnItemClickListener는 매개변수 4개 이기 때문에 2개 추가
    public interface OnItemClickListener { //중첩클래스 내에서 사용.
        void onItemClick(View v, int position); //매개변수 2개인 메서드 명세
    }

    //멤버변수 선언
    public OnItemClickListener mOnItemClickListener;
    private List mList;

    //인터페이스 온클릭리스너에 대한 셋메서드 생성(나중에 메인액티비티에서 사용예정)
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    //===============================================================================================
    //ItemViewHolder클래스가 호출 되면 자동으로 아래 onCreateViewHolder메서드 실행됨.
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //레이아웃 xml을 자바단에서 화면을 로딩할 때 inflater메서드 사용 = startActivity(화면 띄울 때)
        //inflate()매개변수 xml 디자인이 필수. 리사이클러뷰 내에 들어가는 아이템 디자인.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new ItemViewHolder(view);
    }

    /*
    온바인드홀더에서 mList값을 사용하려면, mList값을 생성.
    생성자 메서드: 메인액티비티java에서 호출하면서 매개변수 리스트 쿼리값을 보냄.
     */
    public RecyclerAdapter(List itemList) {
        mList = itemList; //현재 클래스에서 최초로 실행.
    }

    /*
    뷰홀더와 position(pos)를 이용해서 xml디자인 textview item에 기존값 출력하기
    개발자가 호출하는 것이 아니고, 안드로이드의 레이아웃 관리자프로그램이 자동으로 호출.
     */
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        //개발자가 구현
        StudentVO studentVO = (StudentVO) mList.get(position); //1개 레코드 저장
        //리사이클러뷰의 recyclerview_item.xml디자인에 데이터를 출력함.
        holder.itemGrade.setText(Integer.toString(studentVO.getmGrade()));
        holder.itemNumber.setText(Integer.toString(studentVO.getmNumber()));
        holder.itemName.setText(studentVO.getmName());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    //===============================================================================================
    //중첩클래스 생성(역할: 리사이클러뷰 컴포넌트에 데이터를 홀딩시키는 클래스)
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        //멤버변수 선언(클래스 전역 변수)
        private TextView itemGrade; //(홀더 내에 객체 중) 학년 아이템
        private TextView itemNumber; //(홀더 내에 객체 중) 학번 아이템
        private TextView itemName; //(홀더 내에 객체 중) 학생이름 아이템
        
        public ItemViewHolder(@NonNull View itemView) { //new 생성자 메서드
            super(itemView); //메모리에 ViewHolder클래스를 로딩하게 됨.
            //위 선언한 변수 객체로 만듦.(메모리 로딩 실행가능하게 변경)
            itemGrade = itemView.findViewById(R.id.item_grade); //xml의 콤포넌트를 객체화 시킴.
            itemNumber = itemView.findViewById(R.id.item_number);
            itemName = itemView.findViewById(R.id.item_name);

            //new키워드로 생성(객체가 만들어질)될 때 아이템 클릭 이벤트 생성.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int pos = getAdapterPosition(); //리사이클러뷰 클릭한 리스트 위치
                    System.out.println("디버그 itemView.setOnClickListener : " + pos);
                    if(pos != RecyclerView.NO_POSITION) { //즉, 커서로 클릭했을 때.
                        //여기는, 커서로 선택한 학생이 학생이 없을 때 실행
                        if(mOnItemClickListener != null) {
                            mOnItemClickListener.onItemClick(v,pos);
                        }
                    }
                }
            });
            //작동 확인 후 나중에 람다식(자바버전8이후부터 지원가능)으로 변경예정.
        }

    }
}
