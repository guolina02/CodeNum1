package com.gln.codenum1.chapter12;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gln.codenum1.R;

import java.util.List;

/**
 * Created by guolina on 2017/6/5.
 */
public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.VH> {

    private List<Fruit> mFruitList;
    private Context mContext;

    public FruitAdapter(List<Fruit> list) {
        this.mFruitList = list;
    }

    @Override
    public int getItemCount() {
        return mFruitList.size();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_fruit, parent, false);
        final VH holder = new VH(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Fruit item = mFruitList.get(position);
                Intent intent = new Intent(mContext, FruitDetailActivity.class);
                intent.putExtra(FruitDetailActivity.FRUIT_NAME, item.getName());
                intent.putExtra(FruitDetailActivity.FRUIT_IMAGE, item.getImageId());
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        Fruit fruit = mFruitList.get(position);
        holder.textView.setText(fruit.getName());
        Glide.with(mContext).load(fruit.getImageId()).into(holder.imageView);
    }

    static class VH extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView imageView;
        TextView textView;

        public VH(View view) {
            super(view);
            cardView = (CardView) view;
            imageView = (ImageView) view.findViewById(R.id.image_item_fruit);
            textView = (TextView) view.findViewById(R.id.text_item_fruit);
        }
    }
}
