package com.company.regular.simpleapp.activity;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.company.regular.simpleapp.R;
import com.company.regular.simpleapp.adapter.RecyclerViewAdapter;
import com.company.regular.simpleapp.retrofit.GetFileQuery;
import com.company.regular.simpleapp.retrofit.ImageEntryModel;
import com.company.regular.simpleapp.retrofit.RetrofitBuilder;
import com.company.regular.simpleapp.utils.InternetConnection;
import com.company.regular.simpleapp.utils.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements
		RecyclerViewAdapter.OnRecyclerViewItemClicked, InternetConnection.ConnectivityListener {
	private final byte NUMBER_OF_COLUMNS = 2;
	private RecyclerView mRecyclerView;
	private TextView mNoDataTextView;
	private ImageEntryModel mData;
	private InternetConnection mBroadcastReceiver;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mRecyclerView = findViewById(R.id.recycler_view);
		mNoDataTextView = findViewById(R.id.no_data);

		mBroadcastReceiver = new InternetConnection(this);
		IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(mBroadcastReceiver, intentFilter);

		shouldShowError(false);
	}


	@Override
	protected void onStart() {
		super.onStart();
		fillData();
	}


	@Override
	protected void onDestroy() {
		unregisterReceiver(mBroadcastReceiver);
		super.onDestroy();
	}


	@Override
	public void isNetworkOnline(boolean isOnline) {
		if (isOnline) {
			fillData();
		}
	}


	/**
	 * Метод, который запускает процесс инициализации данных
	 */
	private void fillData() {
		if (mRecyclerView.getAdapter() == null) {
			GetFileQuery query = RetrofitBuilder.getFileQuery();
			query.getFile().enqueue(new Callback<ImageEntryModel>() {
				@Override
				public void onResponse(Call<ImageEntryModel> call, Response<ImageEntryModel> response) {
					initRecyclerView(response.body());
					if (response.body() != null) {
						mData = response.body();
						Util.saveDataInCache(MainActivity.this, response.body());
						initRecyclerView(response.body());
					}
				}


				@Override
				public void onFailure(Call<ImageEntryModel> call, Throwable t) {
					t.printStackTrace();
					mData = Util.getDataFromCache(MainActivity.this);
					if (mData != null) {
						initRecyclerView(mData);
					} else {
						shouldShowError(true);
					}
				}
			});
		}
	}


	private void initRecyclerView(ImageEntryModel imageEntryModel) {
		shouldShowError(false);
		mRecyclerView.setLayoutManager(new GridLayoutManager(this, NUMBER_OF_COLUMNS));
		RecyclerViewAdapter adapter = new RecyclerViewAdapter(imageEntryModel, this);
		mRecyclerView.setAdapter(adapter);
	}


	private void shouldShowError(boolean shouldShow) {
		if (shouldShow) {
			mNoDataTextView.setVisibility(View.VISIBLE);
			mRecyclerView.setVisibility(View.GONE);
		} else {
			mNoDataTextView.setVisibility(View.GONE);
			mRecyclerView.setVisibility(View.VISIBLE);
		}
	}


	/**
	 * Обработка клика по КАРТИНКЕ в адаптере (именно по картинке, а не по ViewHolder)
	 */
	@Override
	public void onItemClicked(int position) {
		if (mData != null) {
			FullSizeImageActivity.startActivity(this, mData.getImageEntry().get(position).getImageUrl());
		}
	}
}
