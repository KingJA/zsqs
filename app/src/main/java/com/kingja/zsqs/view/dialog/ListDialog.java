package com.kingja.zsqs.view.dialog;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.kingja.zsqs.R;
import com.kingja.zsqs.view.StringTextView;

import java.util.List;

import butterknife.BindView;

/**
 * Description:TODO
 * Create Time:2019/4/20 0020 下午 2:24
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ListDialog<T> extends BaseDialog {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lv)
    ListView lv;
    private boolean selectable;
    private List<T> list;
    private String title;
    private IConvertor convertor;
    private OnItemClickListener onItemClickListener;
    private int maxItmeCount = 5;

    public ListDialog(Builder<T> builder) {
        super(builder.context, R.style.RoundAlertDialog);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        this.title = builder.title;
        this.selectable = builder.selectable;
        this.list = builder.list;
        this.convertor = builder.convertor;
        this.onItemClickListener = builder.onItemClickListener;
    }


    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        tvTitle.setText(title);
        Adapter adapter = new Adapter();
        lv.setAdapter(adapter);
        View listItem = adapter.getView(0, null, lv);
        listItem.measure(0, 0);
        int measuredHeight = listItem.getMeasuredHeight();
        if (list.size() > maxItmeCount) {
            ViewGroup.LayoutParams layoutParams = lv.getLayoutParams();
            layoutParams.height = measuredHeight * maxItmeCount;
            lv.setLayoutParams(layoutParams);
        }
        lv.setOnItemClickListener((parent, view, position, id) -> {
            T t = (T) parent.getItemAtPosition(position);
            this.dismiss();
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(t);
                adapter.selectItem(position);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_list;
    }

    class Adapter extends BaseAdapter {
        private int selectedPosition = -1;

        public Adapter() {
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.item_list_tv, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if (selectable) {
                viewHolder.tv.setTextColor(selectedPosition == position ? ContextCompat.getColor(getContext(), R.color
                        .f_3) : ContextCompat.getColor(getContext(), R.color.f_9));
            }
            viewHolder.tv.setText(convertor.getDisplayName(list.get(position)));
            viewHolder.iv_selected.setVisibility(selectedPosition == position ? View.VISIBLE : View.GONE);
            return convertView;
        }

        public void selectItem(int selectedPosition) {
            this.selectedPosition = selectedPosition;
        }

        public class ViewHolder {
            public final View root;
            StringTextView tv;
            ImageView iv_selected;

            public ViewHolder(View root) {
                this.root = root;
                tv = root.findViewById(R.id.tv);
                iv_selected = root.findViewById(R.id.iv_selected);
            }
        }
    }

    public interface IConvertor<T> {
        String getDisplayName(T t);
    }

    public interface OnItemClickListener<T> {
        void onItemClick(T t);
    }


    public static class Builder<E> {
        private Context context;
        private boolean selectable;
        private IConvertor<E> convertor;
        private OnItemClickListener<E> onItemClickListener;
        private List<E> list;
        private String DEFAULT_TITLE = "请选择";
        private String title = DEFAULT_TITLE;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setList(List<E> list) {
            this.list = list;
            return this;
        }

        public Builder setSelectable(boolean selectable) {
            this.selectable = selectable;
            return this;
        }

        public Builder setConvertor(IConvertor<E> convertor) {
            this.convertor = convertor;
            return this;
        }

        public Builder setOnItemClickListener(OnItemClickListener<E> onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
            return this;
        }

        public ListDialog build() {
            return new ListDialog(this);
        }
    }
}
