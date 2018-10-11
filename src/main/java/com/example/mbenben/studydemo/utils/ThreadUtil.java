package com.example.mbenben.studydemo.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Process;

/**
 * Helper methods to deal with threading related tasks.
 */
public class ThreadUtil {

    /**
     * Run the supplied Runnable on the main thread. The method will block
     * only if the current thread is the main thread.
     */
    public static void runOnUiThread(Runnable r) {
        if (runningOnUiThread()) {
            r.run();
        } else {
            LazyHolder.sUiThreadHandler.post(r);
        }
    }

    public static void removeTaskOnUiThread(Runnable r) {
        LazyHolder.sUiThreadHandler.removeCallbacks(r);
    }

    /**
     * Post the supplied Runnable to run on the main thread. The method will
     * not block, even if called on the UI thread.
     *
     */
    public static void postOnUiThread(Runnable r) {
        LazyHolder.sUiThreadHandler.post(r);
    }

    /**
     * Post the supplied Runnable to run on the main thread after the specified
     * amount of time elapses. The method will not block, even if called on the
     * UI thread.
     */
    public static void postOnUiThreadDelayed(Runnable r, long delayMillis) {
        LazyHolder.sUiThreadHandler.postDelayed(r, delayMillis);
    }

    /**
     * Asserts that the current thread is running on the main thread.
     */
    public static void assertOnUiThread() {
        assert runningOnUiThread();
    }

    /**
     * @return true iff the current thread is the main (UI) thread.
     */
    public static boolean runningOnUiThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    /**
     * Set thread priority to audio.
     */
    public static void setThreadPriorityAudio(int tid) {
        Process.setThreadPriority(tid, Process.THREAD_PRIORITY_AUDIO);
    }

    private static class LazyHolder {
        private static Handler sUiThreadHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * Ensure you are operating on the main thread, if not, an exception
     * will be thrown.
     */
    public static void mainThreadEnforce(String checkpoint) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalStateException(checkpoint + " accessed from non-main thread"
                    + Looper.myLooper());
        }
    }

    /**
     * Ensure you aren't operating on the main thread, if not, an exception
     * will be thrown.
     */
    public static void nonMainThreadEnforce(String checkpoint) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException(checkpoint + " accessed from main thread"
                    + Looper.myLooper());
        }
    }
}
