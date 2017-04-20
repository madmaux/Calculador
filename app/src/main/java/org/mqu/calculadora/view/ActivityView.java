package org.mqu.calculadora.view;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

public class ActivityView {
    private WeakReference<Activity> activityWeakReference;

    public ActivityView(Activity activity) {
        this.activityWeakReference = new WeakReference<>(activity);
    }

    @Nullable
    public Activity getActivity() {
        return this.activityWeakReference.get();
    }

    @Nullable
    public Context getContext() {
        return this.getActivity();
    }

    @Nullable
    public FragmentManager getFragmentManager() {
        Activity activity = getActivity();
        return (activity != null) ? activity.getFragmentManager() : null;
    }
}
