package com.example.mbenben.studydemo.basenote.ballwidget.widget.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.annotation.NonNull;

import com.example.mbenben.studydemo.App;
import com.example.mbenben.studydemo.utils.DateUtils;


/**
 * 仅插件内部调用,应用成请勿调用，调用也无法保证生效<p>
 *
 * 因为SP内部缓存原因，导致跨进的的读写并不安全
 * @author MDove on 2018/4/19.
 */
public class WidgetBallSp implements SpKeys {

    private static SharedPreferences sPrefs;
    private static final long HOUR_IN_MILLIS = DateUtils.HOUR_IN_MILLIS;
    private static Context mAppContext;

    private static SharedPreferences initSharedPreferences() {
        if (sPrefs == null) {
            mAppContext = App.getAllContext();
            sPrefs = mAppContext.getSharedPreferences("widget_service", Context.MODE_PRIVATE);
        }
        return sPrefs;
    }

    public static void registerSharedPreferencesChange(@NonNull SharedPreferences.OnSharedPreferenceChangeListener listener) {
        SharedPreferences sp = initSharedPreferences();
        sp.registerOnSharedPreferenceChangeListener(listener);
    }

    public static void unregisterOnSharedPreferenceChangeListener(@NonNull SharedPreferences.OnSharedPreferenceChangeListener listener) {
        SharedPreferences sp = initSharedPreferences();
        sp.unregisterOnSharedPreferenceChangeListener(listener);
    }

    /**
     * 注意仅限在 {@link com.example.mbenben.studydemo.basenote.ballwidget.service.BallWidgetService}中使用
     *
     * @return
     */
    public static void setAutoRefreshInterval(long millis) {
        SharedPreferences sp = initSharedPreferences();
        sp.edit().putLong(PREF_KEY_AUTO_REFRESH_INTERVAL, millis).apply();
    }

    /**
     * 注意仅限在 {@link com.example.mbenben.studydemo.basenote.ballwidget.service.BallWidgetService}中使用
     *
     * @return
     */
    public static long getAutoRefreshInterval() {
        SharedPreferences sp = initSharedPreferences();
        return sp.getLong(PREF_KEY_AUTO_REFRESH_INTERVAL, HOUR_IN_MILLIS);
    }

    /**
     * 注意仅限在 {@link com.example.mbenben.studydemo.basenote.ballwidget.service.BallWidgetService}中使用
     *
     * @return
     */
    public static void setBallEnable(boolean enable) {
        SharedPreferences sp = initSharedPreferences();
        sp.edit().putBoolean(PREF_KEY_BALL_ENABLE, enable).apply();
//        Settings.System.putInt(mAppContext.getContentResolver(), "ballEnable", enable ? 1 : 0);
    }

    public static boolean isBallEnable() {
        SharedPreferences sp = initSharedPreferences();
        return sp.getBoolean(PREF_KEY_BALL_ENABLE, true);
    }

    public static void setShown(boolean shown) {
        try {
            initSharedPreferences();
            if (shown) {
                Settings.System.putString(mAppContext.getContentResolver(), KEY_CONTROLLER_PKG, mAppContext.getPackageName());
            }
            Settings.System.putInt(mAppContext.getContentResolver(), KEY_IS_SHOWN, shown ? 1 : 0);
        } catch (Exception e) {//一些三星手机不允许不同签名的app修改系统配置
            e.printStackTrace();
        }
    }

    public static String getCallerPkg() {
        return Settings.System.getString(mAppContext.getContentResolver(), KEY_CONTROLLER_PKG);
    }

    public static boolean isShown() {
        initSharedPreferences();
        int value = Settings.System.getInt(mAppContext.getContentResolver(), KEY_IS_SHOWN, 0);
        return value == 1;
    }

    /**
     * 注意仅限在 {@link com.example.mbenben.studydemo.basenote.ballwidget.service.BallWidgetService}中使用
     *
     * @return
     */
    public static void saveWeatherWidgetPosition(int x, int y) {
        SharedPreferences sp = initSharedPreferences();
        sp.edit().putInt(KEY_BALL_POSITION_X, x)
                .putInt(KEY_BALL_POSITION_Y, y)
                .apply();
    }

    /**
     * 注意仅限在 {@link com.example.mbenben.studydemo.basenote.ballwidget.service.BallWidgetService}中使用
     *
     * @return
     */
    public static int getPositionX() {
        SharedPreferences sp = initSharedPreferences();
        return sp.getInt(KEY_BALL_POSITION_X, -1);
    }

    /**
     * 注意仅限在 {@link com.example.mbenben.studydemo.basenote.ballwidget.service.BallWidgetService}中使用
     *
     * @return
     */
    public static int getPositionY() {
        SharedPreferences sp = initSharedPreferences();
        return sp.getInt(KEY_BALL_POSITION_Y, -1);
    }

    public static boolean isTemperatureUnitInC() {
        SharedPreferences sp = initSharedPreferences();
        return sp.getBoolean(KEY_TEMP_UNIT_C, true);
    }

    public static void setTemperatureUnitInC(boolean value) {
        SharedPreferences sp = initSharedPreferences();
        sp.edit().putBoolean(KEY_TEMP_UNIT_C, value).apply();
    }

    public static void saveWeatherWidgetPosition4Landscape(int x, int y) {
        SharedPreferences sp = initSharedPreferences();
        sp.edit().putInt(PREF_KEY_BALL_POSITION_X_LANDSCAPE, x)
                .putInt(PREF_KEY_BALL_POSITION_Y_LANDSCAPE, y)
                .apply();
    }

    public static int getLandscapePositionX() {
        SharedPreferences sp = initSharedPreferences();
        return sp.getInt(PREF_KEY_BALL_POSITION_X_LANDSCAPE, -1);
    }

    public static int getLandscapePositionY() {
        SharedPreferences sp = initSharedPreferences();
        return sp.getInt(PREF_KEY_BALL_POSITION_Y_LANDSCAPE, -1);
    }

    public static void setWeatherDataTimestamp() {
        SharedPreferences sp = initSharedPreferences();
        sp.edit().putLong(PREF_KEY_WEATHER_DATA_TIMESTAMP, System.currentTimeMillis()).apply();
    }

    public static boolean isWeatherDataExpired() {
        SharedPreferences sp = initSharedPreferences();
        long timestamp = sp.getLong(PREF_KEY_WEATHER_DATA_TIMESTAMP, 0);
        return !DateUtils.isInDay(timestamp);
    }
}
