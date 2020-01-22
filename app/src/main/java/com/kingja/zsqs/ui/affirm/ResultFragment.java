package com.kingja.zsqs.ui.affirm;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kingja.supershapeview.view.SuperShapeTextView;
import com.kingja.zsqs.CommonAdapter;
import com.kingja.zsqs.R;
import com.kingja.zsqs.adapter.FilePageAdapter;
import com.kingja.zsqs.adapter.ViewHolder;
import com.kingja.zsqs.base.BaseTitleFragment;
import com.kingja.zsqs.base.DaggerBaseCompnent;
import com.kingja.zsqs.constant.Constants;
import com.kingja.zsqs.injector.component.AppComponent;
import com.kingja.zsqs.net.entiy.FileItem;
import com.kingja.zsqs.net.entiy.KV;
import com.kingja.zsqs.net.entiy.ModelImage;
import com.kingja.zsqs.net.entiy.ModelTao;
import com.kingja.zsqs.net.entiy.ResultInfo;
import com.kingja.zsqs.net.entiy.Tao;
import com.kingja.zsqs.utils.AppUtil;
import com.kingja.zsqs.utils.GsonUtil;
import com.kingja.zsqs.utils.ToastUtil;
import com.kingja.zsqs.view.FixedGridView;
import com.kingja.zsqs.view.FixedListView;
import com.kingja.zsqs.view.dialog.DialogAllFileFragment;
import com.kingja.zsqs.view.dialog.PhotoPriviewFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Description:TODO
 * Create Time:2020/1/7 0007 下午 2:09
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ResultFragment extends BaseTitleFragment implements ResultContract.View {

    @Inject
    ResultPresenter resultPresenter;
    @BindView(R.id.rootView)
    LinearLayout rootView;
    Unbinder unbinder;
    private int queryType;

    public static ResultFragment newInstance(int fileType) {
        ResultFragment fragment = new ResultFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.Extra.RESULT_TYPE, fileType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initVariable() {
        if (getArguments() != null) {
            queryType = getArguments().getInt(Constants.Extra.RESULT_TYPE);
        }
    }

    @Override
    protected void initComponent(AppComponent appComponent) {
        DaggerBaseCompnent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
        resultPresenter.attachView(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void initNet() {
        resultPresenter.getResultInfo("e6c00411-4fe9-40b8-bfeb-7b4b0c50a19a", "e08f8072-d87b-41a1-9e73-19d52eb80406",
                queryType);
    }

    @Override
    protected String getTitle() {
        return "认定结果";
    }

    @Override
    protected int getContentId() {
        return R.layout.fra_result;
    }

    @Override
    public void onGetResultInfoSuccess(ResultInfo resultInfo) {
        setTitle(resultInfo.getTitle());
        List<ResultInfo.DatasBean> models = resultInfo.getDatas();
        for (int i = 0; i < models.size(); i++) {
            ResultInfo.DatasBean model = models.get(i);
            String value = model.getValue();
            switch (model.getType()) {
                case Constants.MODEL_TYPE.KVS:
                    initKvView(value);
                    break;
                case Constants.MODEL_TYPE.IMAGES:
                    initImageView(value);
                    break;
                case Constants.MODEL_TYPE.TAOS:
                    initTaoView(value);
                    break;
            }
            addDivider(models, i, model);
        }

    }

    private void addDivider(List<ResultInfo.DatasBean> models, int i, ResultInfo.DatasBean model) {
        if (i != models.size() - 1 && model.getType() != Constants.MODEL_TYPE.IMAGES) {
            View dividleView = new View(getActivity());
            dividleView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.c_divider));
            LinearLayout.LayoutParams dvLP =
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, AppUtil.dp2px(2));
            dvLP.setMargins(0, AppUtil.dp2px(40), 0, 0);
            rootView.addView(dividleView, dvLP);
        }
    }

    private void initKvView(String value) {
        List<KV> kvList = new Gson().fromJson(value, new TypeToken<List<KV>>() {
        }.getType());
        FixedGridView fgv_kv = (FixedGridView) View.inflate(getActivity(), R.layout.model_kv, null);
        fgv_kv.setAdapter(new CommonAdapter<KV>(getActivity(), kvList, R.layout.item_kv) {
            @Override
            public void convert(ViewHolder helper, KV item) {
                helper.setText(R.id.tv_key, item.getKey());
                helper.setText(R.id.tv_value, item.getValue());
                helper.setVisibility(R.id.tv_colon, !TextUtils.isEmpty(item.getKey()));
            }
        });

        rootView.addView(fgv_kv);
    }

    private void initImageView(String value) {
        ModelImage modelImage = GsonUtil.json2obj(value, ModelImage.class);
        List<FileItem> fileList = modelImage.getValue();
        View modelImageView = View.inflate(getActivity(), R.layout.model_images, null);
        rootView.addView(modelImageView);
        TextView tv_title = modelImageView.findViewById(R.id.tv_title);
        ViewPager vp_files = modelImageView.findViewById(R.id.vp_files);
        SuperShapeTextView sstv_showAll = modelImageView.findViewById(R.id.sstv_showAll);
        SuperShapeTextView ssll_index = modelImageView.findViewById(R.id.sstv_index);
        TextView tv_empty = modelImageView.findViewById(R.id.tv_empty);
        tv_title.setText(modelImage.getKey());
        if (fileList != null && fileList.size() > 0) {
            FilePageAdapter filePageAdapter = new FilePageAdapter(getActivity(), fileList);
            vp_files.setAdapter(filePageAdapter);
            ssll_index.setText(String.format("1/%d",fileList.size()));
            vp_files.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
                @Override
                public void onPageSelected(int position) {
                    ssll_index.setText(String.format("1/%d",position+1));
                }
            });
            sstv_showAll.setOnClickListener(v -> DialogAllFileFragment.newInstance(fileList).show(getActivity()));


        } else {
            tv_empty.setVisibility(View.VISIBLE);
            sstv_showAll.setVisibility(View.GONE);
            ssll_index.setVisibility(View.GONE);
        }


    }

    private void initTaoView(String value) {
        ModelTao modelTao = GsonUtil.json2obj(value, ModelTao.class);
        final List<Tao> taoList = modelTao.getValue();
        View modelTaoView = View.inflate(getActivity(), R.layout.model_tao, null);
        rootView.addView(modelTaoView);
        FixedListView flv_tao = modelTaoView.findViewById(R.id.flv_tao);
        if (taoList != null && taoList.size() > 0) {
            flv_tao.setAdapter(new CommonAdapter<Tao>(getActivity(), taoList, R.layout.item_tao) {
                @Override
                public void convert(ViewHolder helper, Tao item) {
                    helper.setText(R.id.tv_patternName, item.getPatternName());
                    helper.setText(R.id.tv_area, item.getArea() + "㎡");
                    helper.setText(R.id.tv_remark, item.getRemark());
                    helper.setOnClickListen(R.id.sstv_appoint, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ToastUtil.showText(item.getPatternName());
                        }
                    });
                }
            });
        } else {

        }


    }

}
