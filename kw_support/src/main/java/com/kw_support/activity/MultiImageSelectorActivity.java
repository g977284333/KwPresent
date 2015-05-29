package com.kw_support.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;

import com.kw_support.R;
import com.kw_support.base.BaseActivity;

import java.io.File;
import java.util.ArrayList;


/**
 * MultiImageSelectorActivity
 * Created by Nereo on 2015/4/7.
 */
public class MultiImageSelectorActivity extends BaseActivity implements MultiImageSelectorFragment.Callback {

    /**
     * The maximum number of selection
     */
    public static final String EXTRA_SELECT_COUNT = "max_select_count";

    /**
     * Image Select mode, the default multiple choice
     */
    public static final String EXTRA_SELECT_MODE = "select_count_mode";

    /**
     * Whether to display the camera option, the default is display
     */
    public static final String EXTRA_SHOW_CAMERA = "show_camera";

    /**
     * selection results
     */
    public static final String EXTRA_RESULT = "select_result";

    /**
     * the default selection list
     */
    public static final String EXTRA_DEFAULT_SELECTED_LIST = "default_list";

    /**
     * SingleSelection
     */
    public static final int MODE_SINGLE = 0;

    /**
     * MultiSelect
     */
    public static final int MODE_MULTI = 1;

    private ArrayList<String> mResultList = new ArrayList<String>();

    private Button mSubmitButton;

    private int mDefaultCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_image_selector);

        initData();
        initView();
    }

    private void initView() {

        findViewById(R.id.multi_image_selector_btn_back).setOnClickListener(mBtnBackListener);
        mSubmitButton = (Button) findViewById(R.id.multi_image_selector_btn_submit);

        if (mResultList == null || mResultList.size() <= 0) {
            setSubmitTextInSingleMode();
            mSubmitButton.setEnabled(false);
        } else {
            setSubmitTextInMultiMode();
            mSubmitButton.setEnabled(true);
        }

        mSubmitButton.setOnClickListener(mBtcCommitListener);
    }

    private void initData() {
        Intent intent = getIntent();
        mDefaultCount = intent.getIntExtra(EXTRA_SELECT_COUNT, 9);
        int mode = intent.getIntExtra(EXTRA_SELECT_MODE, MODE_MULTI);
        if (mode == MODE_MULTI && intent.hasExtra(EXTRA_DEFAULT_SELECTED_LIST)) {
            mResultList = intent.getStringArrayListExtra(EXTRA_DEFAULT_SELECTED_LIST);
        }
        boolean isShow = intent.getBooleanExtra(EXTRA_SHOW_CAMERA, true);


        Bundle bundle = new Bundle();
        bundle.putInt(MultiImageSelectorFragment.EXTRA_SELECT_COUNT, mDefaultCount);
        bundle.putInt(MultiImageSelectorFragment.EXTRA_SELECT_MODE, mode);
        bundle.putBoolean(MultiImageSelectorFragment.EXTRA_SHOW_CAMERA, isShow);
        bundle.putStringArrayList(MultiImageSelectorFragment.EXTRA_DEFAULT_SELECTED_LIST, mResultList);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.image_grid, Fragment.instantiate(this, MultiImageSelectorFragment.class.getName(), bundle))
                .commit();
    }

    @Override
    public void onSingleImageSelected(String path) {
        Intent data = new Intent();
        mResultList.add(path);
        data.putStringArrayListExtra(EXTRA_RESULT, mResultList);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void onImageSelected(String path) {
        if (!mResultList.contains(path)) {
            mResultList.add(path);
        }

        if (mResultList.size() > 0) {
            setSubmitTextInMultiMode();
            if (!mSubmitButton.isEnabled()) {
                mSubmitButton.setEnabled(true);
            }
        }
    }

    @Override
    public void onImageUnselected(String path) {
        if (mResultList.contains(path)) {
            mResultList.remove(path);
            setSubmitTextInMultiMode();
        } else {
            setSubmitTextInMultiMode();
        }

        if (mResultList.size() == 0) {
            setSubmitTextInSingleMode();
            mSubmitButton.setEnabled(false);
        }
    }

    private void setSubmitTextInSingleMode() {
        mSubmitButton.setText("完成");
    }

    private void setSubmitTextInMultiMode() {
        mSubmitButton.setText("完成(" + mResultList.size() + "/" + mDefaultCount + ")");
    }

    @Override
    public void onCameraShot(File imageFile) {
        if (imageFile != null) {
            Intent data = new Intent();
            mResultList.add(imageFile.getAbsolutePath());
            data.putStringArrayListExtra(EXTRA_RESULT, mResultList);
            setResult(RESULT_OK, data);
            finish();
        }
    }

    private View.OnClickListener mBtnBackListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setResult(RESULT_CANCELED);
            finish();
        }
    };

    private View.OnClickListener mBtcCommitListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mResultList != null && mResultList.size() > 0) {
                Intent data = new Intent();
                data.putStringArrayListExtra(EXTRA_RESULT, mResultList);
                setResult(RESULT_OK, data);
                finish();
            }
        }
    };


}
