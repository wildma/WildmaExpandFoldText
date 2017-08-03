package com.wildma.wildmaexpandfoldtext;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import java.util.List;

/**
 * Author     wildma
 * DATE       2017/8/3
 * Des	      ${展开折叠文本适配器}
 */
public class ExpandFoldTextAdapter extends RecyclerView.Adapter<ExpandFoldTextAdapter.MyViewHolder> {
    private Activity mContent;

    private final int MAX_LINE_COUNT = 3;//最大显示行数

    private final int STATE_UNKNOW = -1;//未知状态

    private final int STATE_NOT_OVERFLOW = 1;//文本行数小于最大可显示行数

    private final int STATE_COLLAPSED = 2;//折叠状态

    private final int STATE_EXPANDED = 3;//展开状态

    /**
     * 注意：保存文本状态集合的key一定要是唯一的，如果用position。
     * 如果使用position作为key，则删除、增加条目的时候会出现显示错乱
     */
    private SparseArray<Integer> mTextStateList;//保存文本状态集合

    List<ExpandFoldTextBean> mList;

    public ExpandFoldTextAdapter(List<ExpandFoldTextBean> list, Activity context) {
        mContent = context;
        this.mList = list;
        mTextStateList = new SparseArray<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mContent.getLayoutInflater().inflate(R.layout.item_expand_fold_text, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        int state = mTextStateList.get(mList.get(position).getId(), STATE_UNKNOW);
        //第一次初始化，未知状态
        if (state == STATE_UNKNOW) {
            holder.content.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    //这个回掉会调用多次，获取完行数后记得注销监听
                    holder.content.getViewTreeObserver().removeOnPreDrawListener(this);
                    //holder.content.getViewTreeObserver().addOnPreDrawListener(null);
                    //如果内容显示的行数大于最大显示行数
                    if (holder.content.getLineCount() > MAX_LINE_COUNT) {
                        holder.content.setMaxLines(MAX_LINE_COUNT);//设置最大显示行数
                        holder.expandOrFold.setVisibility(View.VISIBLE);//显示“全文”
                        holder.expandOrFold.setText("全文");
                        mTextStateList.put(mList.get(position).getId(), STATE_COLLAPSED);//保存状态
                    } else {
                        holder.expandOrFold.setVisibility(View.GONE);
                        mTextStateList.put(mList.get(position).getId(), STATE_NOT_OVERFLOW);
                    }
                    return true;
                }
            });

            holder.content.setMaxLines(Integer.MAX_VALUE);//设置文本的最大行数，为整数的最大数值
            holder.content.setText(mList.get(position).getContent());
        } else {
            //如果之前已经初始化过了，则使用保存的状态。
            switch (state) {
                case STATE_NOT_OVERFLOW:
                    holder.expandOrFold.setVisibility(View.GONE);
                    break;
                case STATE_COLLAPSED:
                    holder.content.setMaxLines(MAX_LINE_COUNT);
                    holder.expandOrFold.setVisibility(View.VISIBLE);
                    holder.expandOrFold.setText("全文");
                    break;
                case STATE_EXPANDED:
                    holder.content.setMaxLines(Integer.MAX_VALUE);
                    holder.expandOrFold.setVisibility(View.VISIBLE);
                    holder.expandOrFold.setText("收起");
                    break;
            }
            holder.content.setText(mList.get(position).getContent());
        }

        //全文和收起的点击事件
        holder.expandOrFold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int state = mTextStateList.get(mList.get(position).getId(), STATE_UNKNOW);
                if (state == STATE_COLLAPSED) {
                    holder.content.setMaxLines(Integer.MAX_VALUE);
                    holder.expandOrFold.setText("收起");
                    mTextStateList.put(mList.get(position).getId(), STATE_EXPANDED);
                } else if (state == STATE_EXPANDED) {
                    holder.content.setMaxLines(MAX_LINE_COUNT);
                    holder.expandOrFold.setText("全文");
                    mTextStateList.put(mList.get(position).getId(), STATE_COLLAPSED);
                }
            }
        });

        //删除点击事件
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mList.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nickname;
        public TextView content;
        public TextView delete;
        public TextView expandOrFold;

        public MyViewHolder(View itemView) {
            super(itemView);
            nickname = (TextView) itemView.findViewById(R.id.tv_nickname);
            content = (TextView) itemView.findViewById(R.id.tv_content);
            delete = (TextView) itemView.findViewById(R.id.tv_delete);
            expandOrFold = (TextView) itemView.findViewById(R.id.tv_expand_or_fold);
        }
    }
}
