package com.mythsun.ncm;

import android.net.InterfaceConfiguration;
import android.net.LinkAddress;
import android.os.Parcel;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.UnknownHostException;

public class Server {
    private static final String TAG = "Server";

    public Server() {
    }

    private void setNcm() {
        InterfaceConfiguration interfaceConfiguration = null;
        Class<?> networkManager = null;
        try {
            networkManager = Class.forName("com.android.server.NetworkManagementService");
            Method method = networkManager.getMethod("getInterfaceConfig", String.class);
            interfaceConfiguration = (InterfaceConfiguration) method.invoke(networkManager, "ncm");
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getByName("");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
// TODO: 2021/4/20  
        interfaceConfiguration.setLinkAddress(new LinkAddress(new InterfaceAddress(inetAddress,null,null);
        interfaceConfiguration.setInterfaceUp();
        try {
            networkManager = Class.forName("com.android.server.NetworkManagementService");
            Method method = networkManager.getMethod("setInterfaceConfig", String.class, InterfaceConfiguration.class);
            method.invoke(networkManager, "ncm",interfaceConfiguration);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
