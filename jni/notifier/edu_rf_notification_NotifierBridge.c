#include "edu_rf_notification_NotifierBridge.h"

JNIEXPORT void JNICALL Java_edu_rf_notification_NotifierBridge_init(JNIEnv * env, jobject jo) {
    //todo check for error
    notify_init ("icon-summary-body");
}

JNIEXPORT void JNICALL Java_edu_rf_notification_NotifierBridge_destroy(JNIEnv * env, jobject jo) {
    notify_uninit();
}

void closed_handler (NotifyNotification* notification, gpointer data)
{
    g_print ("closed_handler() called");
    return;
}

JNIEXPORT void JNICALL Java_edu_rf_notification_NotifierBridge_displayMessage(JNIEnv * env, jobject jo, jstring title, jstring content){
    NotifyNotification* notification;
    gboolean success;
    GError* error = NULL;
    const char *nativeTitle = (*env)->GetStringUTFChars(env, title, 0);
    const char *nativeContent = (*env)->GetStringUTFChars(env, content, 0);


    /* try the icon-summary-body case */
    notification = notify_notification_new (nativeTitle, nativeContent, NULL);
    success = notify_notification_show (notification, &error);
    if (!success)
        g_print ("That did not work ... \"%s\".\n", error->message);
    g_signal_connect (G_OBJECT (notification), "closed", G_CALLBACK (closed_handler), NULL);

    (*env)->ReleaseStringUTFChars(env, title, nativeTitle);
    (*env)->ReleaseStringUTFChars(env, content, nativeContent);
}