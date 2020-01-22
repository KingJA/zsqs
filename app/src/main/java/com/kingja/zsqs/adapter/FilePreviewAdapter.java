package com.kingja.zsqs.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;
import com.kingja.zsqs.R;
import com.kingja.zsqs.constant.Constants;
import com.kingja.zsqs.i.IFile;
import com.kingja.zsqs.loader.image.ImageLoader;
import com.kingja.zsqs.net.entiy.BannerItem;
import com.kingja.zsqs.utils.NoDoubleClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:TODO
 * Create Time:2018/8/21 0021 下午 4:12
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class FilePreviewAdapter extends PagerAdapter {
    private List<View> imageViewList = new ArrayList<>();

    public FilePreviewAdapter(Activity context, List<IFile> bannerImageList) {
        for (IFile file : bannerImageList) {
            View fileView = View.inflate(context, R.layout.item_vp_ifile, null);
            View iv_pdf = fileView.findViewById(R.id.iv_pdf);
            PhotoView iv_img = fileView.findViewById(R.id.iv_img);
            switch (file.getType()) {
                case Constants.FILE_TYPE.IMG:
                    iv_pdf.setVisibility(View.GONE);
                    iv_img.setVisibility(View.VISIBLE);
                    ImageLoader.getInstance().loadImage(context, file.getImgUrl(),iv_img);
                    fileView.setOnClickListener(new NoDoubleClickListener() {
                        @Override
                        public void onNoDoubleClick(View v) {
                        }
                    });

                    break;
                case Constants.FILE_TYPE.PDF:
                    iv_img.setVisibility(View.GONE);
                    iv_img.setImageDrawable(null);
                    iv_pdf.setVisibility(View.VISIBLE);
                    fileView.setOnClickListener(new NoDoubleClickListener() {
                        @Override
                        public void onNoDoubleClick(View v) {
                        }
                    });
                    break;
            }
            imageViewList.add(fileView);

        }
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View bannerView = imageViewList.get(position % imageViewList.size());
        ViewParent parent = bannerView.getParent();
        if (parent != null) {
            ((ViewPager) bannerView.getParent()).removeView(bannerView);
            if (((ViewPager) parent).getChildCount() < imageViewList.size()) {
                container.addView(bannerView);
            }
        } else {
            container.addView(bannerView);
        }
        return bannerView;
    }
}
