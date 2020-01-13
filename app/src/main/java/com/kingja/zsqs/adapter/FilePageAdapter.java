package com.kingja.zsqs.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingja.zsqs.R;
import com.kingja.zsqs.constant.Constants;
import com.kingja.zsqs.constant.Status;
import com.kingja.zsqs.loader.image.ImageLoader;
import com.kingja.zsqs.net.entiy.FileInfo;
import com.kingja.zsqs.net.entiy.FileItem;
import com.kingja.zsqs.utils.NoDoubleClickListener;

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
        for (FileItem file : fileList) {
            View fileView = View.inflate(context, R.layout.item_vp_file, null);
            ImageView iv_pdf = fileView.findViewById(R.id.iv_pdf);
            ImageView iv_img = fileView.findViewById(R.id.iv_img);
            TextView tv_fileName = fileView.findViewById(R.id.tv_fileName);
            tv_fileName.setText(file.getFileName());
            switch (file.getType()) {
                case Constants.FILE_TYPE.IMG:
                    iv_pdf.setVisibility(View.GONE);
                    ImageLoader.getInstance().loadImage(context, file.getFileUrl(),iv_img);
                    fileView.setOnClickListener(new NoDoubleClickListener() {
                        @Override
                        public void onNoDoubleClick(View v) {
                        }
                    });

                    break;
                case Constants.FILE_TYPE.PDF:
                    iv_img.setImageDrawable(null);
                    iv_pdf.setVisibility(View.VISIBLE);
                    fileView.setOnClickListener(new NoDoubleClickListener() {
                        @Override
                        public void onNoDoubleClick(View v) {
                        }
                    });
                    break;
            }
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
