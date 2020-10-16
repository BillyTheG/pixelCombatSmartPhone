package com.example.pixelcombat.utils;

import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.pixelcombat.manager.GameButtonManager;

/**
 * A simple double-click listener
 * Usage:
 * // Scenario 1: Setting double click listener for myView
 * myView.setOnClickListener(new DoubleClickListener() {
 *
 * @Override public void onDoubleClick() {
 * // double-click code that is executed if the user double-taps
 * // within a span of 200ms (default).
 * }
 * });
 * <p>
 * // Scenario 2: Setting double click listener for myView, specifying a custom double-click span time
 * myView.setOnClickListener(new DoubleClickListener(500) {
 * @Override public void onDoubleClick() {
 * // double-click code that is executed if the user double-taps
 * // within a span of 500ms (default).
 * }
 * });
 * @author Srikanth Venkatesh
 * @version 1.0
 * @since 2014-09-15
 */
public abstract class DoubleClickListener implements OnClickListener {

    protected final GameButtonManager buttonManager;
    private long doubleClickQualificationSpanInMillis;
    private long timestampLastClick;

    public DoubleClickListener(GameButtonManager buttonManager, long doubleClickQualificationSpanInMillis) {
        this.buttonManager = buttonManager;
        this.doubleClickQualificationSpanInMillis = doubleClickQualificationSpanInMillis;
        timestampLastClick = 0;
    }

    @Override
    public void onClick(View v) {
        if ((SystemClock.elapsedRealtime() - timestampLastClick) < doubleClickQualificationSpanInMillis) {
            onDoubleClick(v);
            return;
        }
        timestampLastClick = SystemClock.elapsedRealtime();
        onSingleClick(v);
    }

    public abstract void onSingleClick(View v);

    public abstract void onDoubleClick(View v);

}