package com.example.mbenben.studydemo.basenote.contentprovider;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.base.CommonAdapter;
import com.example.mbenben.studydemo.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2017/2/4.
 */

public class ContentProviderActivity extends AppCompatActivity {
    @BindView(R.id.rlv_main) RecyclerView rlvMain;

    private CommonAdapter<Contacts> adapter;
    private List<Contacts> data=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contentprovider);
        ButterKnife.bind(this);

        adapter=new CommonAdapter<Contacts>(this,R.layout.item_contentprovider,data) {
            @Override
            public void convert(ViewHolder holder, Contacts contacts) {
                holder.setText(R.id.tv_name,contacts.getName());
                holder.setText(R.id.tv_phone,contacts.getPhone());
            }
        };
        rlvMain.setLayoutManager(new LinearLayoutManager(this));
        rlvMain.setAdapter(adapter);
    }

    public void contentProvider(View view){
        ContentResolver cr=getContentResolver();
        Cursor cursor=cr.query(ContactsContract.Contacts.CONTENT_URI,
                new String[]{ContactsContract.Contacts.DISPLAY_NAME,ContactsContract.Contacts._ID},
                null,null,null);
        if (cursor!=null) {
            while (cursor.moveToNext()) {
                Contacts contacts = new Contacts();
                int id = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                Cursor phoneC = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        new String[]{ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER},
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + id, null, null);
                if (phoneC != null) {
                    while (phoneC.moveToNext()) {
                        contacts.setPhone(
                                phoneC.getInt(
                                        phoneC.getColumnIndex(
                                                ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER)) + "");
                    }
                    phoneC.close();
                } else {
                    contacts.setPhone("无手机号");
                }

                contacts.setName(cursor
                        .getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
                data.add(contacts);
            }
            cursor.close();
        }

        adapter.addData(data);
    }

    public void insertContentProvider(View view){
//        ContentResolver cr=getContentResolver();
//        ContentValues contentValues=new ContentValues();
//
//        Uri uri = cr.insert(ContactsContract.RawContacts.CONTENT_URI, contentValues);
//        //获取插入的id
//        long parseId = ContentUris.parseId(uri);
//        //插入姓名
//        contentValues.clear();
//        //字段id
//        contentValues.put(ContactsContract.CommonDataKinds.StructuredName.RAW_CONTACT_ID,parseId);
//        //字段：名字
//        contentValues.put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,"齐天大圣");
//        cr.insert(ContactsContract.Data.CONTENT_URI,contentValues);
//        //插入电话内容
//        contentValues.clear();
//        //依然是字段id
//        contentValues.put(ContactsContract.CommonDataKinds.Phone.RAW_CONTACT_ID,parseId);
//        //字段手机号
//        contentValues.put(ContactsContract.CommonDataKinds.Phone.NUMBER,"1008611");
//        cr.insert(ContactsContract.Data.CONTENT_URI,contentValues);
    }
}
