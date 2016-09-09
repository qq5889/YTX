package com.skyinno.ytx;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.yuntongxun.as.R;
import com.yuntongxun.as.ui.GroupListFragment;

public class GroupListActivity extends FragmentActivity {

    private FragmentTransaction trans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_list);
        GroupListFragment mGroupListFragment = new GroupListFragment();
        mGroupListFragment.onGroupFragmentVisible(true);
        trans = getSupportFragmentManager().beginTransaction();
        trans.add(R.id.fl_container,mGroupListFragment,GroupListFragment.class.getName());
        trans.commit();
    }
}
