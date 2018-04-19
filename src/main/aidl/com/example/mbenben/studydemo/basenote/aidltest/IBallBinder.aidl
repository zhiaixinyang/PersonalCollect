// IBallScreenShot.aidl
package com.example.mbenben.studydemo.basenote.aidltest;

import com.example.mbenben.studydemo.basenote.aidltest.IBallCallBack;

interface IBallBinder {
    void registerCallback(String pkg, IBallCallBack cb);
    void unregisterCallback(IBallCallBack cb);
}
