package com.company.regular.simpleapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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


		GetFileQuery query = RetrofitBuilder.getFileQuery();
		query.getFile().enqueue(new Callback<ImageEntryModel>() {
			@Override
			public void onResponse(Call<ImageEntryModel> call, Response<ImageEntryModel> response) {
				Log.d("debugkey", response.body().getImageEntry().get(0).getImageUrl());
				Log.d("debugkey", "response code: " + response.code());
				initRecyclerView(response.body());
				if (response.body() != null) {
					mData = response.body();
					Util.saveCurrencyInCache(MainActivity.this, response.body());
					initRecyclerView(response.body());
				}
			}


			@Override
			public void onFailure(Call<ImageEntryModel> call, Throwable t) {
				Util.getDataFromCache(MainActivity.this);
				Log.d("debugkey", "fucked up");
				t.printStackTrace();
			}
		});
	}


	private void initRecyclerView(ImageEntryModel imageEntryModel) {
		mRecyclerView.setLayoutManager(new GridLayoutManager(this, NUMBER_OF_COLUMNS));
		RecyclerViewAdapter adapter = new RecyclerViewAdapter(imageEntryModel, this);
		mRecyclerView.setAdapter(adapter);
	}


	@Override
	public void onItemClicked(int position) {
		if (mData != null){
			mData.getImageEntry().get(position);
		}
	}
}
