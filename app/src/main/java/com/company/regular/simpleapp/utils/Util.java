package com.company.regular.simpleapp.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.company.regular.simpleapp.retrofit.ImageEntryModel;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public final class Util {
	private static final String filename = "imageEntryModel";


	private Util() {
	}


	/**
	 * Получить ImageEntryModel, сохраненный в кэше. Если объекта нет, то вернёт null
	 *
	 * @return {@link ImageEntryModel}
	 */
	public static ImageEntryModel getDataFromCache(Context context) {
		byte[] buffer = null;
		try {
			FileInputStream inputStream = context.openFileInput(filename);
			buffer = new byte[inputStream.available()];
			inputStream.read(buffer, 0, buffer.length);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (buffer != null) {
			String json = new String(buffer);
			Gson gson = new Gson();
			return gson.fromJson(json, ImageEntryModel.class);
		}

		return null;
	}


	/**
	 * Сохранить объект ImageEntryModel в кэш
	 *
	 * @param context
	 * @param imageEntryModel - модель с данными.
	 */
	public static void saveDataInCache(@NonNull Context context, @NonNull ImageEntryModel imageEntryModel) {
		Gson gson = new Gson();
		String json = gson.toJson(imageEntryModel);
		deleteFileIfExists(context, filename);
		createFile(context, filename);
		writeInFile(context, filename, json);
	}


	//region манипуляции с файлом
	private static File createFile(Context context, String filename) {
		File file = null;
		try {
			file = File.createTempFile(filename, null, context.getCacheDir());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}


	private static void writeInFile(Context context, String filename, String data) {
		try {
			OutputStream outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
			outputStream.write(data.getBytes());
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private static void deleteFileIfExists(Context context, String filename) {
		File file = new File(context.getCacheDir(), filename);
		if (file.exists()) {
			file.delete();
		}
	}
	//endregion
}
