package com.gln.codenum1.chapter4;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gln.codenum1.R;
import com.gln.codenum1.chapter13.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guolina on 2017/6/1.
 */
public class NewsTitleListFragment extends Fragment {

    private View mRoot;
    private RecyclerView mRecyclerView;

    private List<News> mNewsList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_news_title_list, container);
        mRecyclerView = (RecyclerView) mRoot.findViewById(R.id.news_title_list);
        LogUtils.d("NewsTitleListFragment", "onCreateView");
        return mRoot;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mNewsList == null || mNewsList.size() == 0) {
            loadData();
        }

        if (mRecyclerView.getAdapter() == null) {
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setAdapter(new MyAdapter());
        }

        LogUtils.d("NewsTitleListFragment", "onResume");
    }

    private void loadData() {
        mNewsList = new ArrayList<>();
        for (int i=0;i<10;i++) {
            News news = new News.Builder()
                    .title("Title" + i)
                    .content("This is title" + i + ", content is " + i)
                    .build();
            mNewsList.add(news);
        }
    }

    class MyAdapter extends RecyclerView.Adapter<VH> {

        @Override
        public int getItemCount() {
            return mNewsList.size();
        }

        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_title, parent, false);
            final VH holder = new VH(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    News news = mNewsList.get(holder.getAdapterPosition());
                    if (((NewsActivity) getActivity()).isTwoPane()) {
                        NewsContentFragment fragment =
                                (NewsContentFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_news_content);
                        fragment.refresh(news.title(), news.content());
                    } else {
                        Intent intent = new Intent(getContext(), NewsContentActivity.class);
                        intent.putExtra("title", news.title());
                        intent.putExtra("content", news.content());
                        startActivity(intent);
                    }
                }
            });
            LogUtils.d("Myadapter", "onCreateViewHolder");
            return holder;
        }

        @Override
        public void onBindViewHolder(VH holder, int position) {
            holder.titleText.setText(mNewsList.get(position).title());
        }
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView titleText;

        public VH(View itemView) {
            super(itemView);
            titleText = (TextView) itemView.findViewById(R.id.text_item_title);
        }
    }
}
