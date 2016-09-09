package com.skyinno.ytx;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.yuntongxun.as.R;
import com.yuntongxun.as.common.CCPAppManager;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends Activity implements AdapterView.OnItemClickListener {

    private ListView mLv;
    private ArrayList<String> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        mLv = (ListView)findViewById(R.id.lv);
        mLv.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_expandable_list_item_1,getData()));

        mLv.setOnItemClickListener(this);
    }

    private List<String> getData() {
       mList = new ArrayList<>();
        for(int i = 0 ; i < 10 ;i++){
            mList.add("1375001939"+i);
        }
        return mList;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String phone = mList.get(position);
        CCPAppManager.startChattingAction(this,phone,phone.substring(phone.length()-1),"http://img4.imgtn.bdimg.com/it/u=3643762419,2437132997&fm=15&gp=0.jpg");
    }
}
