package com.example.mylibrary.managers;

import android.content.Context;

import com.example.mylibrary.PinActivity;
import com.example.mylibrary.PinCompatActivity;
import com.example.mylibrary.PinFragmentActivity;

public class LockManager<T extends AppLockActivity> {

    private static LockManager mInstance;
    private static AppLock mAppLocker;

    public static LockManager getInstance() {
        synchronized (LockManager.class) {
            if (mInstance == null) {
                mInstance = new LockManager<>();
            }
        }
        return mInstance;
    }

    public void enableAppLock(Context context, Class<T> activityClass) {
        if (mAppLocker != null) {
            mAppLocker.disable();
        }
        mAppLocker = AppLockImpl.getInstance(context, activityClass);
        mAppLocker.enable();
    }

    public boolean isAppLockEnabled() {
        return (mAppLocker != null && (PinActivity.hasListeners() ||
                PinFragmentActivity.hasListeners() || PinCompatActivity.hasListeners()));
    }

    public void disableAppLock() {
        if (mAppLocker != null) {
            mAppLocker.disable();
        }
        mAppLocker = null;
    }

    public void setAppLock(AppLock appLocker) {
        if (mAppLocker != null) {
            mAppLocker.disable();
        }
        mAppLocker = appLocker;
    }

    public AppLock getAppLock() {
        return mAppLocker;
    }
}
