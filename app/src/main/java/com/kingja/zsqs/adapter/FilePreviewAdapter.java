package com.kingja.zsqs.adapter;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kingja.pdfsir.NewRemotePDFViewPager;
import com.kingja.pdfsir.remote.DownloadFile;
import com.kingja.supershapeview.view.SuperShapeLinearLayout;
import com.kingja.zsqs.R;
import com.kingja.zsqs.constant.Constants;
import com.kingja.zsqs.i.IFile;
import com.kingja.zsqs.loader.image.ImageLoader;
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
public class FilePreviewAdapter extends PagerAdapter {
    private List<View> fileViews = new ArrayList<>();

    public FilePreviewAdapter(Activity context, List<IFile> fileList) {
        for (int i = 0; i < fileList.size(); i++) {
            IFile file = fileList.get(i);
            View fileView = null;
            int finalI = i;
            switch (file.getType()) {
                case Constants.FILE_TYPE.IMG:
                    fileView = View.inflate(context, R.layout.item_preview_image, null);
                    ImageView iv_img = fileView.findViewById(R.id.iv_img);
                    TextView tv_fileName = fileView.findViewById(R.id.tv_fileName);
                    tv_fileName.setText(file.getFileName());
                    ImageLoader.getInstance().loadImage(context, file.getImgUrl(), iv_img);
                    fileView.setOnClickListener(new NoDoubleClickListener() {
                        @Override
                        public void onNoDoubleClick(View v) {
                            PhotoPriviewFragment.newInstance(fileList, finalI).show((FragmentActivity) context);
                        }
                    });

                    break;
                case Constants.FILE_TYPE.PDF:
                    fileView = View.inflate(context, R.layout.item_preview_pdf, null);
                    NewRemotePDFViewPager remotePDFViewPager = fileView.findViewById(R.id.pdfView);
                    LinearLayout llPdf = fileView.findViewById(R.id.ll_pdf);
                    TextView pdfFileName = fileView.findViewById(R.id.tv_fileName);
                    pdfFileName.setText(file.getFileName());
                    SuperShapeLinearLayout ssll_download = fileView.findViewById(R.id.ssll_download);
                    ssll_download.setOnClickListener(new NoDoubleClickListener() {
                        @Override
                        public void onNoDoubleClick(View v) {
                            Log.e("FilePreviewAdapter", "PDF URL: "+Constants.BASE_URL + file.getOpenUrl() );
                            remotePDFViewPager.setUrl(Constants.BASE_FILE_URL + file.getOpenUrl());
                            remotePDFViewPager.setDownloadFileListener(new DownloadFile.Listener() {
                                @Override
                                public void onSuccess(String url, String destinationPath, int totalPage) {
//                                    Pdf.this.totalPage = totalPage;
                                    llPdf.setVisibility(View.GONE);
                                    ssll_download.setVisibility(View.GONE);
//                                    tvPage.setVisibility(View.VISIBLE);
//                                    tvPage.setText(String.format("%d/%d", 1, totalPage));
                                }

                                @Override
                                public void onFailure(Exception e) {
                                    ToastUtil.showText("PDF下载失败");
                                    Log.e("FilePreviewAdapter", "失败原因: "+e.toString() );
                                }

                                @Override
                                public void onProgressUpdate(int progress, int total) {
//                                    tv_tip.setText(String.format("%d/%d", progress, total));
                                }
                            });
                            remotePDFViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                                @Override
                                public void onPageSelected(int position) {
//                                    tvPage.setText(String.format("%d/%d", position + 1, totalPage));
                                }
                            });
                            remotePDFViewPager.start();
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
        View bannerView = fileViews.get(position % fileViews.size());
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
