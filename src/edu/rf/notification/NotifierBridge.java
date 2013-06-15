package edu.rf.notification;

/**
 * Catalin Dumitru
 * Date: 6/15/13
 * Time: 10:35 PM
 */
public class NotifierBridge {
    static {
        System.loadLibrary("NotifierBridgeImpl");
    }

    public NotifierBridge() {
        init();
    }

    public native void init();

    public native void destroy();

    public native void displayMessage(String title, String content);

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        destroy();
    }
}
