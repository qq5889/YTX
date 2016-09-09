package com.yuntongxun.as.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.yuntongxun.as.R;
/**
 * 
 * @author luhuashan显示收到或者发送的位置地图
 *
 */
public class ShowBaiDuMapActivity extends ECSuperActivity implements
		OnClickListener {
	private View viewCache;
	private TextView tvResult;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getTopBarView().setTopBarToStatus(1, R.drawable.topbar_back_bt, -1,
				R.string.app_panel_location, this);

		viewCache = LayoutInflater.from(this)
				.inflate(R.layout.pop_layout, null);
		tvResult = (TextView) viewCache.findViewById(R.id.location_tips);

		Intent intent = getIntent();
		LocationInfo b = (LocationInfo) intent.getSerializableExtra("location");
		
		String address=b.getAddress();
		
		if(TextUtils.isEmpty(address)){
			finish();
		}

		
		
		tvResult.setText(b.getAddress());
	}


	@Override
	protected int getLayoutId() {
		return R.layout.baidu_map;
	}

	@Override
	public void onClick(View v) {

		int i = v.getId();
		if (i == R.id.btn_left) {
			hideSoftKeyboard();
			finish();

		} else {
		}

	}

}
