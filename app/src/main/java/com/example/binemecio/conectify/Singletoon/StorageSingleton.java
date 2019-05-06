package com.example.binemecio.conectify.Singletoon;

import com.example.binemecio.conectify.Pojo.ClientRecord;
import com.example.binemecio.conectify.Pojo.EnterPrise;

/**
 * Created by binemecio on 3/5/2019.
 */

public class StorageSingleton {

    private static StorageSingleton instance;

    public static StorageSingleton getInstance()
    {
        if(instance == null)
            instance = new StorageSingleton();

        return instance;
    }

    private String ssid = "";
    private ClientRecord clientRecord;
    private EnterPrise enterPrise;
    public Long loopTime = Long.valueOf(10000);
    public int indexAd = 0;

    public int getIndexAd() {
        return indexAd;
    }

    public void setIndexAd(int indexAd) {
        this.indexAd = indexAd;
    }

    public Long getLoopTime() {
        return loopTime;
    }

    public void setLoopTime(Long loopTime) {
        this.loopTime = loopTime;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public ClientRecord getClientRecord() {
        return clientRecord;
    }

    public void setClientRecord(ClientRecord clientRecord) {
        this.clientRecord = clientRecord;
    }

    public EnterPrise getEnterPrise() {
        return enterPrise;
    }

    public void setEnterPrise(EnterPrise enterPrise) {
        this.enterPrise = enterPrise;
    }
}
