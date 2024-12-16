package com.transcendence.aidl.server;

import android.os.IBinder;
import android.os.IInterface;

import com.transcendence.aidl.bean.Person;

import java.util.List;

/**
 * @Author Joephone C
 * @CreateTime 2024年12月15日 21:57:17
 * @Desc
 */
public interface IPersonManager extends IInterface {

    void addPerson(Person person);

    List<Person> getPersonList();

    IBinder asBinder();
}
