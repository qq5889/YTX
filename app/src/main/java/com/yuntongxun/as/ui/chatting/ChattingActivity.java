package com.yuntongxun.as.ui.chatting;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.KeyEvent;

import com.yuntongxun.as.R;
import com.yuntongxun.as.common.CCPAppManager;
import com.yuntongxun.as.common.utils.LogUtil;
import com.yuntongxun.as.ui.ECFragmentActivity;
import com.yuntongxun.as.ui.SDKCoreHelper;
import com.yuntongxun.as.ui.chatting.view.AppPanelControl;
import com.yuntongxun.as.ui.chatting.view.CCPChattingFooter2;

/**
 * @author 容联•云通讯 聊天页面
 * @date 2014-12-9
 * @version 4.0
 */
public class ChattingActivity extends ECFragmentActivity {

	private static final String TAG = "ECSDK_Demo.ChattingActivity";
	public ChattingFragment mChattingFragment;
	private MyReceiver myReceiver;

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		LogUtil.d(TAG, "chatting ui dispatch key event :" + event);
		if (mChattingFragment != null
				&& mChattingFragment.onKeyDown(event.getKeyCode(), event)) {
			return true;
		}

		return super.dispatchKeyEvent(event);
	}

	@Override
	protected void onResume() {
		super.onResume();
		IntentFilter filter1 = new IntentFilter();

		filter1.addAction("com.yuntongxun.ecdemo.removemember");
		filter1.addAction(SDKCoreHelper.ACTION_KICK_OFF);

		registerReceiver(myReceiver, filter1);
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		unregisterReceiver(myReceiver);
	}

	public class MyReceiver extends BroadcastReceiver
	{
		// 可用Intent的getAction()区分接收到的不同广播
		@Override
		public void onReceive(Context arg0, Intent intent)
		{
			if(intent==null){
				return;
			}
			if (intent.getAction().equals(SDKCoreHelper.ACTION_KICK_OFF)
					|| intent.getAction().equals(
							"com.yuntongxun.ecdemo.removemember"))
				finish();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		LogUtil.d(TAG, "onCreate");
		super.onCreate(null);
		getWindow().setFormat(PixelFormat.TRANSPARENT);

		myReceiver = new MyReceiver();
		String recipients = getIntent().getStringExtra(
				ChattingFragment.RECIPIENTS);
		if (recipients == null) {
			finish();
			LogUtil.e(TAG, "recipients is null !!");
			return;
		}
		setContentView(R.layout.chattingui_activity_container);
		mChattingFragment = new ChattingFragment();
		Bundle bundle = getIntent().getExtras();
		mChattingFragment.setArguments(bundle);
		getSupportFragmentManager().beginTransaction()
				.add(R.id.ccp_root_view, mChattingFragment).commit();
		onActivityCreate();

		if (isChatToSelf(recipients) || isPeerChat(recipients)) {
			AppPanelControl.setShowVoipCall(false);
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		LogUtil.d(TAG, "chatting ui on key down, " + keyCode + ", " + event);
		
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0&&CCPChattingFooter2.isRecodering) {
			// do nothing.
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
	
	
	

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		LogUtil.d(TAG, "chatting ui on key up");
		return super.onKeyUp(keyCode, event);
	}

	public boolean isPeerChat() {
		if (mChattingFragment != null) {
			return mChattingFragment.isPeerChat();
		}
		return false;
	}

	public boolean isChatToSelf(String sessionId) {

		String userId = CCPAppManager.getUserId();
		return sessionId != null
				&& (sessionId.equalsIgnoreCase(userId) ? true : false);
	}

	public boolean isPeerChat(String sessionId) {

		return sessionId.toLowerCase().startsWith("g");
	}

}