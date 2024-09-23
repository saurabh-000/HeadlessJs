package com.headless;

import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class RNModule extends ReactContextBaseJavaModule {
    RNModule(ReactApplicationContext context) {
        super(context);
    }
    @NonNull
    @Override
    public String getName() {
        return "RNModule";
    }

    @ReactMethod
    public void startService() {
        Log.d("RNModule", "Service started");
    }
}
