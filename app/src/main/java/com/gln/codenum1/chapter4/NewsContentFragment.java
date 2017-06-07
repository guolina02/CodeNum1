package com.gln.codenum1.chapter4;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gln.codenum1.R;
import com.gln.codenum1.chapter13.LogUtils;

/**
 * Created by guolina on 2017/6/1.
 */
public class NewsContentFragment extends Fragment {

    private View mRoot;

    private TextView mTextTitle;
    private TextView mTextContent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_news_content, container);
        mTextTitle = (TextView) mRoot.findViewById(R.id.text_news_title);
        mTextContent = (TextView) mRoot.findViewById(R.id.text_news_content);
        LogUtils.d("NewsContentFragment", "onCreateView");
        return mRoot;
    }

    public void refresh(String title, String content) {
        mTextTitle.setText(title);
        mTextContent.setText(content);
    }
}
