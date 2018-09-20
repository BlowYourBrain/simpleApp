package com.company.regular.simpleapp.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Объект, в котором хранится список картинок с url
 * */
public class ImageEntryModel {
	@Expose
	@SerializedName("images")
	private List<Image> imageEntry;


	public List<Image> getImageEntry() {
		return imageEntry;
	}


	public class Image {
		@Expose
		@SerializedName("image_url")
		private String imageUrl;


		public String getImageUrl() {
			return imageUrl;
		}
	}
}
