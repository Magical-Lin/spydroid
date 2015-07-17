package net.majorkernelpanic;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

import java.lang.reflect.Method;

public class WifiUtil {
    ConnectivityManager mConnectivityManager;
    Context mcontext;
    WifiManager wifiManager;
    private static boolean WifiApMode = false;

    public WifiUtil(Context paramContext) {
        this.mcontext = paramContext;
        this.wifiManager = ((WifiManager) this.mcontext.getSystemService(Context.WIFI_SERVICE));
        this.mConnectivityManager = ((ConnectivityManager) this.mcontext.getSystemService(Context.CONNECTIVITY_SERVICE));
    }

    public boolean setWifiApEnabled(boolean paramBoolean) {
        WifiApMode = paramBoolean;
        if (paramBoolean) {
            this.wifiManager.setWifiEnabled(false);
            try {
                WifiConfiguration localWifiConfiguration2 = new WifiConfiguration();
                localWifiConfiguration2.SSID = "Rk3288_AP";
                Class localClass2 = this.wifiManager.getClass();
                Class[] arrayOfClass2 = new Class[2];
                arrayOfClass2[0] = WifiConfiguration.class;
                arrayOfClass2[1] = Boolean.TYPE;
                Method localMethod2 = localClass2.getMethod("setWifiApEnabled", arrayOfClass2);
                WifiManager localWifiManager2 = this.wifiManager;
                Object[] arrayOfObject2 = new Object[2];
                arrayOfObject2[0] = localWifiConfiguration2;
                arrayOfObject2[1] = Boolean.valueOf(paramBoolean);
                boolean bool3 = ((Boolean) localMethod2.invoke(localWifiManager2, arrayOfObject2)).booleanValue();
                return bool3;
            } catch (Exception localException2) {
                return false;
            }
        }
        try {
            WifiConfiguration localWifiConfiguration1 = new WifiConfiguration();
            localWifiConfiguration1.SSID = "Rk3288_AP";
            localWifiConfiguration1.preSharedKey = "Rk3288_AP";
            Class localClass1 = this.wifiManager.getClass();
            Class[] arrayOfClass1 = new Class[2];
            arrayOfClass1[0] = WifiConfiguration.class;
            arrayOfClass1[1] = Boolean.TYPE;
            Method localMethod1 = localClass1.getMethod("setWifiApEnabled", arrayOfClass1);
            WifiManager localWifiManager1 = this.wifiManager;
            Object[] arrayOfObject1 = new Object[2];
            arrayOfObject1[0] = localWifiConfiguration1;
            arrayOfObject1[1] = Boolean.valueOf(paramBoolean);
            boolean bool1 = ((Boolean) localMethod1.invoke(localWifiManager1, arrayOfObject1)).booleanValue();
            boolean bool2 = this.wifiManager.setWifiEnabled(true);
            return bool2;
        } catch (Exception localException1) {
        }
        return false;
    }
}


