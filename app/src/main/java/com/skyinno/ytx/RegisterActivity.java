package com.skyinno.ytx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yuntongxun.as.R;
import com.yuntongxun.as.ui.SDKCoreHelper;
import com.yuntongxun.as.ui.group.BaseSearch;
import com.yuntongxun.as.ui.group.CreateGroupActivity;

public class RegisterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        SDKCoreHelper.login(this,"13750019397","Jackie","http://img4.imgtn.bdimg.com/it/u=1468480486,4254223798&fm=21&gp=0.jpg");

    }

    //通讯列表
    public void onIntoClick(View v){
        startActivity(new Intent(this,ConversationListActivity.class));
        //finish();
    }

    //联系人列表
    public void onContactClick(View v){
        startActivity(new Intent(this,ContactsActivity.class));
        //finish();
    }

    //群组列表
    public void onGroupClick(View v){
        startActivity(new Intent(RegisterActivity.this,GroupListActivity.class));
        //finish();
    }

    public void onCreateGroupClick(View v){
        // 创建群组
        startActivity(new Intent(this, CreateGroupActivity.class));
        //finish();
    }

    public void onSearchGroupClick(View v){
        // 群组搜索
        startActivity(new Intent(this,BaseSearch.class));
    }
}
