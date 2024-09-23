package com.headless;

import android.content.Intent;
import android.os.Bundle;

import com.facebook.react.HeadlessJsTaskService;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.jstasks.HeadlessJsTaskConfig;

public class AlarmTaskService extends HeadlessJsTaskService {
    @Override
    protected HeadlessJsTaskConfig getTaskConfig(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            return new HeadlessJsTaskConfig(
                    "AlarmTask", // The task name to call in JavaScript
                    Arguments.fromBundle(extras),
                    5000, // Timeout for task to finish
                    true  // Set to true if the task needs to continue even when the app is terminated
            );
        }
        return null;

    }

}
