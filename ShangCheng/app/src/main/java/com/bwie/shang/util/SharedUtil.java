package com.bwie.shang.util;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 为避免多次生成工具类对象，将其设计成单例模式
 */

public class SharedUtil {
	// 配置文件名字
	private final String CONFIG = "config";

	private SharedUtil() {
	}

	public static SharedUtil sharedUtil;

	public static SharedUtil getInstances() {
		if (sharedUtil == null) {
			sharedUtil = new SharedUtil();
		}
		return sharedUtil;
	}

	/**
	 * 保存数据的方法
	 *
	 * @param context
	 */
	public void saveDatad(Context context, String key, Object object) {
		SharedPreferences sp = context.getSharedPreferences(CONFIG,
				Context.MODE_PRIVATE);
		Editor edit = sp.edit();
		if (object instanceof String) {
			edit.putString(key, (String) object);

		} else if (object instanceof Boolean) {
			edit.putBoolean(key, (Boolean) object);

		} else if (object instanceof Float) {
			edit.putFloat(key, (Float) object);

		} else if (object instanceof Long) {
			edit.putLong(key, (Long) object);

		} else if (object instanceof Integer) {
			edit.putInt(key, (Integer) object);

		}

		edit.commit();

	}

	/**
	 * 根据key得到值
	 *
	 * @param context
	 *            上下文
	 * @param keyStr
	 *            键
	 * @param defValue
	 *            默认值
	 * @return
	 */
	public Object getValueByKey(Context context, String keyStr, Object defValue) {
		SharedPreferences sp = context.getSharedPreferences(CONFIG,
				context.MODE_PRIVATE);
		if (defValue instanceof String) {
			return sp.getString(keyStr, (String) defValue);
		} else if (defValue instanceof Integer) {
			return sp.getInt(keyStr, (Integer) defValue);
		} else if (defValue instanceof Long) {
			return sp.getLong(keyStr, (Long) defValue);
		} else if (defValue instanceof Float) {
			return sp.getFloat(keyStr, (Float) defValue);
		} else if (defValue instanceof Boolean) {
			return sp.getBoolean(keyStr, (Boolean) defValue);
		}
		return null;
	}

	// 清除所有数据
	public void clearAllData(Context context) {
		SharedPreferences sp = context.getSharedPreferences(CONFIG,
				context.MODE_PRIVATE);
		Editor edit = sp.edit();
		edit.clear();
		edit.commit();
	}

	// 查询某个key是否存在
	public boolean isExist(Context context, String keyStr) {
		SharedPreferences sp = context.getSharedPreferences(CONFIG,
				context.MODE_PRIVATE);
		return sp.contains(keyStr);

	}

}
