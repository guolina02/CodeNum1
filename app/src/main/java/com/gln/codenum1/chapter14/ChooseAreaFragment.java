package com.gln.codenum1.chapter14;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gln.codenum1.R;
import com.gln.codenum1.chapter14.db.City;
import com.gln.codenum1.chapter14.db.County;
import com.gln.codenum1.chapter14.db.Province;
import com.gln.codenum1.chapter14.utils.DataHandler;
import com.gln.codenum1.chapter14.utils.HttpUtils;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by guolina on 2017/6/6.
 */
public class ChooseAreaFragment extends Fragment {

    public static final int LEVEL_PROVICE = 1;
    public static final int LEVEL_CITY = 2;
    public static final int LEVEL_COUNTY = 3;

    private static final int MSG_RESPONSE = 101;
    private static final int MSG_ERROR = 102;

    private View mRoot;

    private TextView mTextTitle;
    private Button mBtnBack;
    private RecyclerView mRecyclerView;
    private ProgressDialog mProgressDialog;

    private AreaAdapter mAdapter;
    private SparseArray<String> mDataList = new SparseArray();

    private List<Province> mListProvince;
    private List<City> mListCity;
    private List<County> mListCounty;

    private Province mSelectedProvince;
    private City mSelectedCity;
    private County mSelectedCounty;

    private int mCurrentLevel = LEVEL_PROVICE;

    private WeatherActivity mActivity;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.choose_area, container, false);

        mTextTitle = (TextView) mRoot.findViewById(R.id.text_current_area);
        mBtnBack = (Button) mRoot.findViewById(R.id.btn_area_back);
        mRecyclerView = (RecyclerView) mRoot.findViewById(R.id.recycler_areas);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new AreaAdapter();
        mAdapter.setListener(new AreaAdapter.MyListener() {
            @Override
            public void onClickItem(View v, int index) {
                if (mCurrentLevel == LEVEL_PROVICE) {
                    mSelectedProvince = mListProvince.get(index);
                    queryCities();
                } else if (mCurrentLevel == LEVEL_CITY) {
                    mSelectedCity = mListCity.get(index);
                    queryCounties();
                } else {
                    mSelectedCounty = mListCounty.get(index);
                    mActivity.searchWeather(mSelectedCounty.getWeatherId());
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setTitle("Loading");
        mProgressDialog.setCanceledOnTouchOutside(false);

        return mRoot;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mActivity = (WeatherActivity) getContext();

        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentLevel == LEVEL_COUNTY) {
                    queryCities();
                } else if (mCurrentLevel == LEVEL_CITY) {
                    queryProvinces();
                }
            }
        });

        queryProvinces();
    }

    /**
     * query provinces, first query db, if no data then query internet
     */
    private void queryProvinces() {
        mTextTitle.setText("中国");
        mBtnBack.setVisibility(View.GONE);

        mListProvince = DataSupport.findAll(Province.class);
        if (mListProvince != null && mListProvince.size() > 0) {
            mDataList.clear();
            for (Province p: mListProvince) {
                mDataList.put(p.getId(), p.getProvinceName());
            }
            mAdapter.setData(mDataList);
            mCurrentLevel = LEVEL_PROVICE;
        } else {
            String address = "http://guolin.tech/api/china";
            queryFromServer(address, "province");
        }
    }

    /**
     * query cities, first query db, then internet
     */
    private void queryCities() {
        mTextTitle.setText(mSelectedProvince.getProvinceName());
        mBtnBack.setVisibility(View.VISIBLE);

        mListCity = DataSupport.where("provinceId=?", String.valueOf(mSelectedProvince.getId())).find(City.class);
        if (mListCity != null && mListCity.size() > 0) {
            mDataList.clear();
            for (City p: mListCity) {
                mDataList.put(p.getId(), p.getCityName());
            }
            mAdapter.setData(mDataList);
            mCurrentLevel = LEVEL_CITY;
        } else {
            String address = "http://guolin.tech/api/china/" + mSelectedProvince.getProvinceCode();
            queryFromServer(address, "city");
        }
    }

    private void queryCounties() {
        mTextTitle.setText(mSelectedCity.getCityName() + " " + mSelectedProvince.getProvinceName());
        mBtnBack.setVisibility(View.VISIBLE);

        mListCounty = DataSupport.where("cityId=?", String.valueOf(mSelectedCity.getId())).find(County.class);
        if (mListCounty != null && mListCounty.size() > 0) {
            mDataList.clear();
            for (County p: mListCounty) {
                mDataList.put(p.getId(), p.getCountyName());
            }
            mAdapter.setData(mDataList);
            mCurrentLevel = LEVEL_COUNTY;
        } else {
            String address = "http://guolin.tech/api/china/" + mSelectedProvince.getProvinceCode() + "/" + mSelectedCity.getCityCode();
            queryFromServer(address, "county");
        }
    }

    private void queryFromServer(String address, final String type) {
        mProgressDialog.show();
        HttpUtils.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mProgressDialog.dismiss();
                        Toast.makeText(getContext(), "Load data failed!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                boolean result = false;
                if ("province".equals(type)) {
                    result = DataHandler.handleProvinceData(responseText);
                } else if ("city".equals(type)) {
                    result = DataHandler.handleCityData(responseText, mSelectedProvince.getId());
                } else if ("county".equals(type)) {
                    result = DataHandler.handleCountyData(responseText, mSelectedCity.getId());
                }
                if (result) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mProgressDialog.dismiss();
                            if ("province".equals(type)) {
                                queryProvinces();
                            } else if ("city".equals(type)) {
                                queryCities();
                            } else if ("county".equals(type)) {
                                queryCounties();
                            }
                        }
                    });
                }
            }
        });
    }
}
