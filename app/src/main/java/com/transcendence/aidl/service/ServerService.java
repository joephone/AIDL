package com.transcendence.aidl.service;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.transcendence.aidl.bean.Person;
import com.transcendence.aidl.log.LogUtils;
import com.transcendence.aidl.server.BinderStub;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Joephone C
 * @CreateTime 2024年12月16日 00:00:00
 * @Desc
 */
public class ServerService extends Service {
    private final String TAG = ServerService.class.getSimpleName();

    private List<Person> mPersonList = new ArrayList<>();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mStub;
    }

    private IBinder mStub = new BinderStub() {
        @Override
        public void addPerson(Person person) {
            if(person == null) {
                LogUtils.e(" obj null");
                person = new Person();
            }
            mPersonList.add(person);
        }

        @Override
        public List<Person> getPersonList() {
            return mPersonList;
        }
    };
}
