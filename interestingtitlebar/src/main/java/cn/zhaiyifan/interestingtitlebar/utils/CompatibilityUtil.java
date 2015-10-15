package cn.zhaiyifan.interestingtitlebar.utils;

import android.os.Build;

import java.lang.reflect.Method;

/**
 * Compatibility for special ROM
 * <p/>
 * Created by tigerliang on 2015/10/12.
 */
public class CompatibilityUtil {

    private static final String TAG = "CompatibilityUtil";

    /**
     * Check whether is running Flyme
     *
     * @return
     */
    public static boolean isFlyme() {
        try {
            // Invoke Build.hasSmartBar()
            final Method method = Build.class.getMethod("hasSmartBar");
            return method != null;
        } catch (final Exception e) {
            return false;
        }
    }

    /**
     * Check whether is MIUI
     *
     * @return
     */
    public static boolean isMIUI() {
        String miuiVer = PropertyUtils.getQuickly("ro.miui.ui.version.name", null);
        return miuiVer != null && miuiVer.contains("V");
    }

    /**
     * Get MIUI version
     *
     * @return the MIUI version or -1 if failed to get this value.
     */
    public static int getMIUIVersion() {
        int result = -1;
        String miuiVer = PropertyUtils.getQuickly("ro.miui.ui.version.name", null);
        if (miuiVer != null && miuiVer.contains("V")) {
            int versionStart = miuiVer.indexOf("V") + 1;
            if (versionStart < miuiVer.length()) {
                String version = miuiVer.substring(versionStart);
                try {
                    result = Integer.parseInt(version);
                } catch (NumberFormatException e) {
                    //omit
                }
            }
        }
        return result;
    }

}
