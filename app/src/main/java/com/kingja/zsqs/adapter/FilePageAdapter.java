package com.kingja.zsqs.adapter;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingja.supershapeview.view.SuperShapeLinearLayout;
import com.kingja.zsqs.R;
import com.kingja.zsqs.constant.Constants;
import com.kingja.zsqs.i.IFile;
import com.kingja.zsqs.loader.image.ImageLoader;
import com.kingja.zsqs.net.entiy.FileItem;
import com.kingja.zsqs.utils.NoDoubleClickListener;
import com.kingja.zsqs.utils.ToastUtil;
import com.kingja.zsqs.view.dialog.PhotoPriviewFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:TODO
 * Create Time:2018/8/21 0021 下午 4:12
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class FilePageAdapter extends PagerAdapter {
    private List<View> fileViews = new ArrayList<>();

    public FilePageAdapter(Activity context, List<FileItem> fileList) {
        for (int i = 0; i < fileList.size(); i++) {
            IFile file = fileList.get(i);
            View fileView = null;
            int finalI = i;
            switch (file.getType()) {
                case Constants.FILE_TYPE.IMG:
                    fileView = View.inflate(context, R.layout.item_vp_image, null);
                    ImageView iv_img = fileView.findViewById(R.id.iv_img);
                    TextView tv_fileName = fileView.findViewById(R.id.tv_fileName);
                    tv_fileName.setText(file.getFileName());
                    ImageLoader.getInstance().loadImage(context, file.getImgUrl(), iv_img);

                    break;
                case Constants.FILE_TYPE.PDF:
                    fileView = View.inflate(context, R.layout.item_vp_pdf, null);
                    break;
            }
            assert fileView != null;
            fileView.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    PhotoPriviewFragment.newInstance(fileList, finalI).show((FragmentActivity) context);
                }
            });

            fileViews.add(fileView);
        }
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public int getCount() {
        return fileViews.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View bannerView = fileViews.get(position);
        ViewParent parent = bannerView.getParent();
        if (parent != null) {
            ((ViewPager) bannerView.getParent()).removeView(bannerView);
            if (((ViewPager) parent).getChildCount() < fileViews.size()) {
                container.addView(bannerView);
            }
        } else {
            container.addView(bannerView);
        }
        return bannerView;
    }
}
