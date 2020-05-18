package com.wellee.annotation;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wellee.annotation.annotations.InjectView;
import com.wellee.annotation.annotations.ItemView;
import com.wellee.annotation.recyclerView.CustomRecyclerView;

import java.util.List;

/**
 * @author : liwei
 * 创建日期 : 2019/12/20 13:46
 * 邮   箱 : liwei@worken.cn
 * 功能描述 :
 */
public class RvAdapter extends CustomRecyclerView.BaseAdapter<String, RvAdapter.RvViewHolder> {

    @ItemView(R.layout.item_rv)
    View itemView;

    @ItemView(R.layout.item_rv2)
    View itemView2;

    public RvAdapter(Context context, List<String> data) {
        super(context, data);
    }

    @Override
    public void bindRecyclerView(CustomRecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }

    @Override
    protected RvViewHolder onCreateViewHolderConvert(ViewGroup parent, int viewType) {
        // View itemView = View.inflate(mContext, R.layout.item_rv, null);
        if (viewType == 1) {
            return new RvViewHolder(itemView);
        } else {
            return new RvViewHolder(itemView2);
        }
    }

    @Override
    protected void onBindViewHolderConvert(@NonNull RvViewHolder holder, int position) {
        holder.textView.setText(mData.get(position) + ", viewType = " + getItemViewType(position));
    }

    class RvViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.tv)
        TextView textView;

        public RvViewHolder(@NonNull View itemView) {
            super(itemView);
//            textView = itemView.findViewById(R.id.tv);
        }
    }
}
