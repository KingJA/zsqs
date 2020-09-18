package com.kingja.zsqs;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import com.kingja.zsqs.adapter.IDataInjector;
import com.kingja.zsqs.adapter.ILvSetData;
import com.kingja.zsqs.adapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:TODO
 * Create Time:2018/7/27 0027 上午 10:11
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public abstract class CommonAdapter<T> extends BaseAdapter implements IDataInjector<T>, ILvSetData<T> {
    protected final String TAG = getClass().getSimpleName();
    protected LayoutInflater mInflater;
    protected Context mContext;
    protected boolean mEditable;
    protected List<T> mDatas;
    protected final int mItemLayoutId;


    public CommonAdapter(Context context, List<T> datas, int itemLayoutId) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mDatas = (datas == null ? new ArrayList<T>() : datas);
        this.mItemLayoutId = itemLayoutId;
    }

    public CommonAdapter(Context context, List<T> datas) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mDatas = datas;
        this.mItemLayoutId = getItemLayoutId();
    }

    public List<T> getData() {
        return mDatas;
    }


    protected int getItemLayoutId() {
        return 0;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder = getViewHolder(position, convertView,
                parent);
        convert(viewHolder, getItem(position));
        return viewHolder.getConvertView();

    }

    public abstract void convert(ViewHolder helper, T item);

    private ViewHolder getViewHolder(int position, View convertView,
                                     ViewGroup parent) {
        return ViewHolder.get(mContext, convertView, parent, mItemLayoutId,
                position);
    }
    @Override
    public void reset() {
        this.mDatas.clear();
        notifyDataSetChanged();
    }

    @Override
    public void setData(List<T> list) {
        this.mDatas = (list == null ? new ArrayList<T>() : list);
        notifyDataSetChanged();
    }
    @Override
    public void setData(List<T> list, boolean editable) {
        this.mDatas = (list == null ? new ArrayList<T>() : list);
        notifyDataSetChanged();
    }
    @Override
    public void addData(List<T> list) {
        this.mDatas.addAll(list == null ? new ArrayList<T>() : list);
        Log.e(TAG, "addData: "+list.size() );
        notifyDataSetChanged();
    }

    public void addData(T data) {
        if (data == null) {
            return;
        }
        this.mDatas.add(0, data);
        notifyDataSetChanged();
    }



}