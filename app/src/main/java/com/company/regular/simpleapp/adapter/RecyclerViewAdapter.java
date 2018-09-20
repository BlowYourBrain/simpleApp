package com.company.regular.simpleapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.company.regular.simpleapp.R;
import com.company.regular.simpleapp.retrofit.ImageEntryModel;
import com.squareup.picasso.Picasso;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

	/**
	 * Интерфейс по которому будем возвращать позицию в адаптере
	 */
	public interface OnRecyclerViewItemClicked {
		void onItemClicked(int position);
	}


	public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		public ImageView image;


		public ViewHolder(View v) {
			super(v);
			image = v.findViewById(R.id.image);
			image.setOnClickListener(this);
		}


		@Override
		public void onClick(View v) {
			mListener.onItemClicked(getAdapterPosition());
		}
	}


	private ImageEntryModel mData;
	private OnRecyclerViewItemClicked mListener;


	public RecyclerViewAdapter(ImageEntryModel imageEntryModel, OnRecyclerViewItemClicked listener) {
		mData = imageEntryModel;
		mListener = listener;
	}


	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
		return new ViewHolder(view);
	}


	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		final int IMAGE_SIZE = 200;

		Picasso.get()
				.load(mData.getImageEntry().get(position).getImageUrl())
				.resize(IMAGE_SIZE, IMAGE_SIZE)
				.placeholder(R.drawable.ic_baseline_photo_24px)
				.into(holder.image);
	}


	@Override
	public int getItemCount() {
		return mData.getImageEntry().size();
	}
}
