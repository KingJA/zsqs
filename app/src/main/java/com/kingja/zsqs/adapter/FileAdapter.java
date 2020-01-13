package com.kingja.zsqs.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kingja.zsqs.R;
import com.kingja.zsqs.constant.Constants;
import com.kingja.zsqs.loader.image.ImageLoader;
import com.kingja.zsqs.net.entiy.FileItem;

import java.util.List;

/**
 * Description:TODO
 * Create Time:2018/9/17 0017 下午 4:55
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class FileAdapter extends BaseLvAdapter<FileItem> {


    public FileAdapter(Context context, List<FileItem> list) {
        super(context, list);
    }

    @Override
    public View simpleGetView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View
                    .inflate(context, R.layout.item_file, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        FileItem item = (FileItem) getItem(position);
        switch (item.getType()) {
            case Constants.FILE_TYPE.IMG:
                viewHolder.iv_pdf.setVisibility(View.GONE);
                ImageLoader.getInstance().loadImage(context,item.getFileUrl(),viewHolder.iv_img);
                break;
            case Constants.FILE_TYPE.PDF:
                viewHolder.iv_img.setImageDrawable(null);
                viewHolder.iv_pdf.setVisibility(View.VISIBLE);
                break;
        }
        viewHolder.tv_fileName.setText(item.getFileName());
        return convertView;
    }


    public class ViewHolder {
        public final View root;
        public ImageView iv_img;
        public ImageView iv_pdf;
        public TextView tv_fileName;

        public ViewHolder(View root) {
            this.root = root;
            iv_img = root.findViewById(R.id.iv_img);
            iv_pdf = root.findViewById(R.id.iv_pdf);
            tv_fileName = root.findViewById(R.id.tv_fileName);
        }
    }
}
