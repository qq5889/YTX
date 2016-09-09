package com.yuntongxun.as.ui.chatting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yuntongxun.as.R;
import com.yuntongxun.as.common.utils.ToastUtil;
import com.yuntongxun.as.storage.IMessageSqlManager;
import com.yuntongxun.as.ui.ECSuperActivity;
import com.yuntongxun.ecsdk.ECChatManager;
import com.yuntongxun.ecsdk.ECDevice;
import com.yuntongxun.ecsdk.ECError;
import com.yuntongxun.ecsdk.ECMessage;
import com.yuntongxun.ecsdk.ECReadMessageMember;
import com.yuntongxun.ecsdk.SdkErrorCode;

import java.util.ArrayList;

/**
 * Created by luhuashan on 16/6/18.
 */
public class ECMessageFeedUI extends ECSuperActivity implements View.OnClickListener{
    /**
     * The sub Activity implement, set the Ui Layout
     *
     * @return
     */

    ArrayList<ECReadMessageMember> arrayList;
    @Override
    protected int getLayoutId() {
        return R.layout.message_feed;
    }

//    private  ECMessage message;
    private  ListView lv;

    public static ECMessage message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTopBarView().setTopBarToStatus(1, R.drawable.topbar_back_bt,
                -1, null,
                null,
                getString(R.string.app_read_unread), null, this);
        arrayList =new ArrayList<ECReadMessageMember>();
        ECDevice.getECChatManager().queryMessageReadStatus(message,listener);

          lv =(ListView) findViewById(R.id.feed_list);
    }

    private  final ECChatManager.OnQueryMessageReadStatusListener listener =new ECChatManager.OnQueryMessageReadStatusListener() {
        @Override
        public void onQueryMessageReadStatusResult(ECError error, ArrayList<ECReadMessageMember> readArr, ArrayList<ECReadMessageMember> unReadArr) {


            if(error.errorCode== SdkErrorCode.REQUEST_SUCCESS){

                filtList(readArr,unReadArr);

            }else {
                ToastUtil.showMessage("查询失败"+error.errorCode);
            }
        }
    };

    private void filtList(ArrayList<ECReadMessageMember> readArr, ArrayList<ECReadMessageMember> unReadArr) {

        if(readArr!=null&&readArr.size()>0){
            arrayList.addAll(readArr);
            IMessageSqlManager.updateMsgReadCount(message.getMsgId(),readArr.size());
        }
        if(unReadArr!=null&&unReadArr.size()>0){
            arrayList.addAll(unReadArr);
        }
        lv.setAdapter(new FeedBackAdapter());
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_left) {
            hideSoftKeyboard();
            finish();

        }
    }


    private  class FeedBackAdapter extends BaseAdapter{

        /**
         * How many items are in the data set represented by this Adapter.
         *
         * @return Count of items.
         */
        @Override
        public int getCount() {
            return arrayList.size();
        }

        /**
         * Get the data item associated with the specified position in the data set.
         *
         * @param position Position of the item whose data we want within the adapter's
         *                 data set.
         * @return The data at the specified position.
         */
        @Override
        public Object getItem(int position) {
            return arrayList.get(position);
        }

        /**
         * Get the row id associated with the specified position in the list.
         *
         * @param position The position of the item within the adapter's data set whose row id we want.
         * @return The id of the item at the specified position.
         */
        @Override
        public long getItemId(int position) {
            return position;
        }

        /**
         * Get a View that displays the data at the specified position in the data set. You can either
         * create a View manually or inflate it from an XML layout file. When the View is inflated, the
         * parent View (GridView, ListView...) will apply default layout parameters unless you use
         * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
         * to specify a root view and to prevent attachment to the root.
         *
         * @param position    The position of the item within the adapter's data set of the item whose view
         *                    we want.
         * @param convertView The old view to reuse, if possible. Note: You should check that this view
         *                    is non-null and of an appropriate type before using. If it is not possible to convert
         *                    this view to display the correct data, this method can create a new view.
         *                    Heterogeneous lists can specify their number of view types, so that this View is
         *                    always of the right type (see {@link #getViewTypeCount()} and
         *                    {@link #getItemViewType(int)}).
         * @param parent      The parent that this view will eventually be attached to
         * @return A View corresponding to the data at the specified position.
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

               View v = View.inflate(ECMessageFeedUI.this,R.layout.message_feedback_item,null);
                   TextView accountTv= (TextView) v.findViewById(R.id.messageAccount);
                   TextView stateTv= (TextView) v.findViewById(R.id.message_state);
                    ECReadMessageMember item =(ECReadMessageMember) getItem(position);

            accountTv.setText(item.getAccount());
            boolean isRead =true;
            if(item.getTimestamp()==null||item.getTimestamp()==""){
                isRead =false;
            }
            stateTv.setText(isRead?"已读":"未读");
            return v;
        }
    }
}
