package com.yuntongxun.as.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;

import com.yuntongxun.as.R;
import com.yuntongxun.as.common.utils.DemoUtils;
import com.yuntongxun.as.common.utils.LogUtil;
import com.yuntongxun.as.common.utils.ToastUtil;
import com.yuntongxun.as.ui.ECSuperActivity;
import com.yuntongxun.as.ui.chatting.base.EmojiconEditText;
import com.yuntongxun.as.ui.group.CreateGroupActivity;

public class EditConfigureActivity extends ECSuperActivity implements View.OnClickListener{

    public static final String EXTRA_EDIT_TITLE = "edit_title";
    public static final String EXTRA_EDIT_HINT = "edit_hint";

    private EmojiconEditText mEdittext;
    public static boolean isTop=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isTop=true;
        String title = getIntent().getStringExtra("edit_title");
        getTopBarView().setTopBarToStatus(1, R.drawable.topbar_back_bt,
                R.drawable.btn_style_green, null,
                getString(R.string.dialog_ok_button),
                title, null, this);
        initView();
    }

    private void initView() {
        mEdittext = (EmojiconEditText) findViewById(R.id.content);
        InputFilter[] inputFilters = new InputFilter[1];
        inputFilters[0] = filter;
        mEdittext.setFilters(inputFilters);

        String defaultData = getIntent().getStringExtra("edit_default_data");
        if(!TextUtils.isEmpty(defaultData)) {
            mEdittext.setText(defaultData);
            mEdittext.setSelection(mEdittext.getText().length());
        } else if (getIntent().hasExtra(EXTRA_EDIT_HINT)) {
            mEdittext.setHint(getIntent().getStringExtra(EXTRA_EDIT_HINT));
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_configure;
    }
    
    @Override
    protected void onPause() {
    	// TODO Auto-generated method stub
    	super.onPause();
    	isTop=false;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_left) {
            hideSoftKeyboard();
            finish();

        } else if (i == R.id.text_right) {
            hideSoftKeyboard();
            Intent intent = new Intent();
            intent.putExtra("result_data", mEdittext.getText().toString().toString());
            setResult(RESULT_OK, intent);
            finish();

        } else {
        }
    }

    final InputFilter filter = new InputFilter () {

        private int limit = 128;
        @Override
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            LogUtil.i(LogUtil.getLogUtilsTag(CreateGroupActivity.class), source
                    + " start:" + start + " end:" + end + " " + dest
                    + " dstart:" + dstart + " dend:" + dend);
            float count = calculateCounts(dest);
            int overplus = limit - Math.round(count) - (dend - dstart);
            if(overplus <= 0) {
                if ((Float.compare(count, (float) (limit - 0.5D)) == 0)
                        && (source.length() > 0)
                        && (!(DemoUtils.characterChinese(source.charAt(0))))) {
                    return source.subSequence(0, 1);
                }
                ToastUtil.showMessage("超过最大限制");
                return "";
            }

            if( overplus >= (end - start)) {
                return null;
            }
            int tepmCont = overplus + start;
            if((Character.isHighSurrogate(source.charAt(tepmCont - 1))) && (--tepmCont == start)) {
                return "";
            }
            return source.subSequence(start, tepmCont);
        }

    };
    /**
     *
     * @param text
     * @return
     */
    public static float calculateCounts(CharSequence text) {

        float lengh = 0.0F;
        for(int i = 0; i < text.length() ; i++) {
            if(!DemoUtils.characterChinese(text.charAt(i))) {
                lengh += 1.0F;
            } else {
                lengh += 0.5F;
            }
        }

        return lengh;
    }

}
