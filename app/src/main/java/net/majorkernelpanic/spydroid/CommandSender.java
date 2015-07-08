package net.majorkernelpanic.spydroid;

public class CommandSender {

    static {
        System.loadLibrary("neo_pwm");
    }

    public native int native_init();

    public native int native_deinit();

    public native int native_update(byte[] bytes);
}