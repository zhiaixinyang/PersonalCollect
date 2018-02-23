package com.example.mbenben.studydemo.basenote.aidltest;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MDove on 2018/2/23.
 */

public class AidlData implements Parcelable {
    public String mData;

    public AidlData(String data) {
        mData = data;
    }

    protected AidlData(Parcel in) {
        mData = in.readString();
    }

    public static final Creator<AidlData> CREATOR = new Creator<AidlData>() {
        @Override
        public AidlData createFromParcel(Parcel in) {
            return new AidlData(in);
        }

        @Override
        public AidlData[] newArray(int size) {
            return new AidlData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mData);
    }
}
