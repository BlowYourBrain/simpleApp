package com.company.regular.simpleapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.company.regular.simpleapp.R;
import com.company.regular.simpleapp.adapter.RecyclerViewAdapter;
import com.company.regular.simpleapp.retrofit.GetFileQuery;
import com.company.regular.simpleapp.retrofit.ImageEntryModel;
import com.company.regular.simpleapp.retrofit.RetrofitBuilder;
import com.company.regular.simpleapp.utils.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.OnRecyclerViewItemClicked {
	private final byte NUMBER_OF_COLUMNS = 2;
	private RecyclerView mRecyclerView;
	private ImageEntryModel mData;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mRecyclerView = findViewById(R.id.recyclerView);
	}


	@Override
	protected void onStart() {
		super.onStart();
		fillData();
	}


	/**
	 * Метод, который запускает процесс инициализации данных
	 */
	private void fillData() {
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
					Log.d("debugkey", "totally fucked up");
				}
			}
		});
	}


	private void initRecyclerView(ImageEntryModel imageEntryModel) {
		mRecyclerView.setLayoutManager(new GridLayoutManager(this, NUMBER_OF_COLUMNS));
		RecyclerViewAdapter adapter = new RecyclerViewAdapter(imageEntryModel, this);
		mRecyclerView.setAdapter(adapter);
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
