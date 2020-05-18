package com.wellee.annotation.recyclerView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.wellee.annotation.InjectManager;

import java.util.List;

/**
 * @author : liwei
 * 创建日期 : 2019/12/20 17:03
 * 邮   箱 : liwei@worken.cn
 * 功能描述 :
 */
public class CustomRecyclerView extends RecyclerView {

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public CustomRecyclerView(@NonNull Context context) {
        super(context);
    }

    public CustomRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(@Nullable OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public void setOnItemClick(View v, int position) {
        getOnItemClickListener().onItemClick(v, position);
    }

    public final OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(View view, int position);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
    }

    public boolean setOnItemLongClick(View v, int position) {
        return getOnItemLongClickListener().onItemLongClick(v, position);
    }

    public final OnItemLongClickListener getOnItemLongClickListener() {
        return mOnItemLongClickListener;
    }


    public abstract static class BaseAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

        protected CustomRecyclerView mRecyclerView;
        protected Context mContext;
        protected List<T> mData;

        public BaseAdapter(Context context, List<T> data) {
            this.mContext = context;
            this.mData = data;
        }

        public abstract void bindRecyclerView(CustomRecyclerView recyclerView);

        @NonNull
        @Override
        public final VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            VH vh;
            InjectManager.injectRecyclerViewItems(mContext, this);
            vh = onCreateViewHolderConvert(parent, viewType);
            InjectManager.injectRecyclerViewHolder(vh);
            return vh;
        }

        @Override
        public final void onBindViewHolder(@NonNull VH holder, final int position) {
            View itemView = holder.itemView;
            if (mRecyclerView.getOnItemClickListener() != null) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mRecyclerView.setOnItemClick(v, position);
                    }
                });
            }
            if (mRecyclerView.getOnItemLongClickListener() != null) {
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        return mRecyclerView.setOnItemLongClick(v, position);
                    }
                });
            }
            onBindViewHolderConvert(holder, position);
        }

        @Override
        public int getItemCount() {
            return mData == null ? 0 : mData.size();
        }

        protected abstract VH onCreateViewHolderConvert(ViewGroup parent, int viewType);

        protected abstract void onBindViewHolderConvert(@NonNull VH holder, final int position);

    }

}
