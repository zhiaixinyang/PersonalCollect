// IBindService.aidl
package com.example.mbenben.studydemo.basenote.aidltest;

// Declare any non-default types here with import statements
import com.example.mbenben.studydemo.basenote.aidltest.AidlData;
interface IBindService {
    void sendData(in AidlData data);
}
