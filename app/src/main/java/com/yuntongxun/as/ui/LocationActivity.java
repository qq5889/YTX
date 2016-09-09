package com.yuntongxun.as.ui;

import android.content.Intent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.yuntongxun.as.R;
import com.yuntongxun.as.ui.chatting.ChattingActivity;

/**
 * 
 * @author luhuashan 定位界面
 *
 */
public class LocationActivity extends ECSuperActivity implements OnClickListener {


	boolean isFirstLoc = true;
	private View viewCache;
	private TextView tvResult;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		getTopBarView().setTopBarToStatus(1, R.drawable.topbar_back_bt, "发送",
				getString(R.string.app_panel_location), this);

		isFirstLoc=true;

		viewCache = LayoutInflater.from(this)
				.inflate(R.layout.pop_layout, null);
		tvResult = (TextView) viewCache.findViewById(R.id.location_tips);

	}



	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.activity_location;
	}

	@Override
	public void onClick(View v) {

		int i = v.getId();
		if (i == R.id.btn_left) {
			hideSoftKeyboard();
			finish();

		} else if (i == R.id.text_right) {
			Intent intent = new Intent();
			LocationInfo locationInfo = new LocationInfo();
			//locationInfo.setLat(lat);
			// locationInfo.setLon(lon);
			//locationInfo.setAddress(address);
			//intent.putExtra("location", locationInfo);
			setResult(ChattingActivity.RESULT_OK, intent);

			finish();


		} else {
		}
		
	}

}
