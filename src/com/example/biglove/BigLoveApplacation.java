package com.example.biglove;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;

import com.example.biglove.bean.UserBean;
import com.example.biglove2.R;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class BigLoveApplacation extends Application {
	public boolean tag = false;

	@Override
	public void onCreate() {
		super.onCreate();
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.cacheInMemory(true).cacheOnDisc(true)
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.bitmapConfig(Bitmap.Config.RGB_565)// 防止内存溢出的，图片太多就这这个。还有其他设置
				// 如Bitmap.Config.ARGB_8888
				.showImageOnLoading(R.drawable.default_picture) // 默认图片
				.showImageForEmptyUri(R.drawable.default_picture) // url爲空會显示该图片，自己放在drawable里面的
				.showImageOnFail(R.drawable.default_picture)// 加载失败显示的图片
				// .displayer(new RoundedBitmapDisplayer(5)) // 圆角，不需要请删除
				.build();

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this)
				.memoryCacheExtraOptions(480, 800)
				// 缓存在内存的图片的宽和高度
				.discCacheExtraOptions(480, 800, CompressFormat.PNG, 70, null)
				// CompressFormat.PNG类型，70质量（0-100）
				.memoryCache(new WeakMemoryCache()).threadPoolSize(5)
				.threadPriority(Thread.MIN_PRIORITY + 3)
				.denyCacheImageMultipleSizesInMemory()
				.memoryCacheSize(5 * 1024 * 1024)
				// 缓存到内存的最大数据
				.discCacheSize(5 * 1024 * 1024)
				// 缓存到文件的最大数据
				.discCacheFileCount(1000)
				// 文件数量
				.defaultDisplayImageOptions(options)
				.tasksProcessingOrder(QueueProcessingType.LIFO). // 上面的options对象，一些属性配置
				build();
		ImageLoader.getInstance().init(config); // 初始化
	}

	private static UserBean infoBean;

	/**
	 * 设置用户信息的Bean
	 * 
	 * @param bean
	 */
	public static void setPersonInfoBean(UserBean bean) {
		infoBean = bean;
	}

	/**
	 * 获取用户信息的Bean
	 * 
	 * @return
	 */
	public static UserBean getPersonInfoBean() {
		return infoBean;
	}
}
