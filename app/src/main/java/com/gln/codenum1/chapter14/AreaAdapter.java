package com.gln.codenum1.chapter14;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by guolina on 2017/6/6.
 */
public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.VH> {

    private SparseArray<String> mDatas;
    private Context mContext;

    private MyListener mListener;

    public void setData(SparseArray<String> datas) {
        if (datas != null && datas.size() > 0) {
            mDatas = datas;
            notifyDataSetChanged();
        }
    }

    public void setListener(MyListener listener) {
        this.mListener = listener;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        TextView textView = new TextView(mContext);
        textView.setTextColor(mContext.getResources().getColor(android.R.color.black));
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setPadding(10, 5, 10, 5);
        final VH holder = new VH(textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onClickItem(v, holder.getAdapterPosition());
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.textView.setText(mDatas.get(mDatas.keyAt(position)));
    }

    static class VH extends RecyclerView.ViewHolder {

        TextView textView;

        public VH(View view) {
            super(view);
            textView = (TextView) view;
        }
    }

    interface MyListener {
        void onClickItem(View v, int index);
    }
}
