package com.example.binemecio.conectify;

import android.content.Intent;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.binemecio.conectify.Helper.ConnectionSSID;
import com.example.binemecio.conectify.Helper.ConnectionServer;
import com.example.binemecio.conectify.Helper.DesignHelper;
import com.example.binemecio.conectify.Helper.Helpers;
import com.example.binemecio.conectify.Pojo.EnterPrise;
import com.example.binemecio.conectify.Singletoon.StorageSingleton;
import com.github.ybq.android.spinkit.SpinKitView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SSIDConnectionActivity extends AppCompatActivity {

    private TextView textLoading;
    private SpinKitView spinKitView;
    private List<ConnectionSSID> connectionList = new ArrayList<>();
    private ConnectionServer connectionServer;
    private String ssid = "";
    private Helpers helper = new Helpers();
    private Boolean isConnected = false;
    private ConnectionSSID actualSSID, hiddeSSID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_layout);
        this.connectionServer = new ConnectionServer(this);
        this.initialice();


        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }
       // this.registerTheCurrentNetwork();
    }

    private void initialice()
    {
        textLoading = findViewById(R.id.textLoading);
        spinKitView = findViewById(R.id.spin_kit);
        this.ssid = ConnectionSSID.getConnectedSSID(this);
        this.actualSSID = new ConnectionSSID(this);
        StorageSingleton.getInstance().setSsid(this.actualSSID.getNetworkSSID());
        this.validateServices();
    }

    private void validateServices()
    {
//        boolean canStartConnection = false;
        if(!ConnectionSSID.isWifiOpen(this))
        {
            DesignHelper.showSimpleDialog(this,"Error", "Por favor habilite la red WIFI", "Habilitar red WIFI", () -> {
                ConnectionSSID.setEnabledWifi(this);
            } );
            return;
        }

        if (helper.isNullOrWhiteSpace(ssid))
        {
            DesignHelper.showSimpleDialog(this,"Error", "Es necesario que se conecte a una red WIFI para usar esta aplicación", "Abrir configuración", () -> {
                SSIDConnectionActivity.this.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            } );
            return;
        }

        this.actualSSID.setNetworkSSID(this.ssid);
        this.actualSSID.setNetworkPass("");

        this.startConnectionServer();
    }

    @Override
    protected void onResume() {

        super.onResume();
//        if (!this.isConnected) {
//            this.validateServices();
//        }

    }

    private void startConnectionServer()
    {
        textLoading.setText("Recupenrando Información");
        this.ssid = "red_publica_1";
        connectionServer.getEnterpriseDataFromServer(this.ssid ,data -> {
            if (data == null){
                Toast.makeText(this,"Failed retrieve data from server", Toast.LENGTH_LONG).show();
                DesignHelper.showSimpleDialog(this,"Error", "Tenemos problemas al conectarnos a la red WIFI por favor intente nuevamente", "Intentar de nuevo", () -> {

                    SSIDConnectionActivity.this.actualSSID.tryReconnect();
                    this.validateServices();
                } );
                return;
            }

//            for (EnterPrise item : data)
//            {
//                System.out.println("SSID 2: "+ item.getSsid2_empresa());
//                System.out.println("Password: "+ item.getPassword());
//            }
            StorageSingleton.getInstance().setEnterPrise(data);
            final Handler handler = new Handler();
            handler.postDelayed(() -> {
                SSIDConnectionActivity.this.textLoading.setText("Conectandose a la red WIFI");
                SSIDConnectionActivity.this.connectToSSID(data.getSsid2_empresa(), data.getPassword());
            }, 1000);

            //Arrays.asList(data).indexOf( )
        });
    }

    private void registerTheCurrentNetwork(String ssid, String password)
    {
        ConnectionSSID connection = new ConnectionSSID(this,ssid, password);
        String currentSSID = ConnectionSSID.getConnectedSSID(this);
        System.out.println("current SSID: "+currentSSID);
        if (!currentSSID.isEmpty())
        {
            connection.setNetworkSSID(currentSSID);
            // print current network
            this.connectionList.add(connection);
        }
        else
        {
            if(!ConnectionSSID.isWifiOpen(this))
            {

            }
            else if(ConnectionSSID.getConnectedSSID(this).isEmpty())
            {

            }

        }
    }



    private void connectToSSID(String ssid, String password)
    {
        ConnectionSSID connection = new ConnectionSSID(this,ssid,password);

        boolean isConnected = connection.tryHiddenConnection();


        if(isConnected)
        {
            Toast.makeText(this,"Conected to "+ ssid, Toast.LENGTH_LONG).show();
            this.startActivity(new Intent(this,CustomerRecord.class));
            this.connectionList.add(connection);
            this.connectionList.add(connection);
        }
        else
        {
            isConnected = connection.tryReconnect(this.ssid);

            if (isConnected)
            {
                Toast.makeText(this,"Conected to "+ ssid, Toast.LENGTH_LONG).show();
                this.connectionList.add(connection);
                this.startActivityForResult(new Intent(this,CustomerRecord.class), 0);
            }
            else
            {
                Toast.makeText(this,"Failed when connect to "+ ssid, Toast.LENGTH_LONG).show();
                DesignHelper.showSimpleDialog(this,"Error", "Tenemos problemas al conectarnos a la red WIFI por favor intente nuevamente", "Intentar de nuevo", () -> {
                    this.validateServices();
                } );
            }
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.finish();
        super.onActivityResult(requestCode, resultCode, data);
    }
}
