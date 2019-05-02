package com.example.binemecio.conectify;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.binemecio.conectify.Helper.ConnectionSSID;
import com.example.binemecio.conectify.Helper.ConnectionServer;
import com.example.binemecio.conectify.Helper.DesignHelper;
import com.example.binemecio.conectify.Pojo.EnterPrise;
import com.github.ybq.android.spinkit.SpinKitView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SSIDConnectionActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textLoading;
    private SpinKitView spinKitView;
    private List<ConnectionSSID> connectionList = new ArrayList<>();
    ConnectionServer connectionServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_layout);
        this.connectionServer = new ConnectionServer(this);
        this.initialice();
        startConnectionServer();


        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }
       // this.registerTheCurrentNetwork();
    }

    private void initialice()
    {
        textLoading = findViewById(R.id.textLoading);
        spinKitView = findViewById(R.id.spin_kit);
    }


    private void startConnectionServer()
    {
        textLoading.setText("Recupenrando Información");
        connectionServer.getEnterpriseDataFromServer(data -> {
            if (data.isEmpty()){
                Toast.makeText(this,"Failed retrieve data from server", Toast.LENGTH_LONG).show();
                DesignHelper.showSimpleDialog(this,"Error", "Tenemos problemas al conectarnos a la red WIFI por favor intente nuevamente", "Intentar de nuevo", () -> {
                    this.startConnectionServer();
                } );
                return;
            }


            for (EnterPrise item : data)
            {
                System.out.println("SSID 2: "+ item.getSsid2_empresa());
                System.out.println("Password: "+ item.getPassword());
            }

            final Handler handler = new Handler();
            handler.postDelayed(() -> {
                SSIDConnectionActivity.this.textLoading.setText("Conectandose a la red WIFI");
                SSIDConnectionActivity.this.connectToSSID(data.get(0).getSsid2_empresa(), data.get(0).getPassword());
            }, 3000);

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
            isConnected = connection.tryReconnect();

            if (isConnected)
            {
                Toast.makeText(this,"Conected to "+ ssid, Toast.LENGTH_LONG).show();
                this.connectionList.add(connection);
                this.startActivity(new Intent(this,CustomerRecord.class));
                this.finish();
            }
            else
            {
                Toast.makeText(this,"Failed when connect to "+ ssid, Toast.LENGTH_LONG).show();
                DesignHelper.showSimpleDialog(this,"Error", "Tenemos problemas al conectarnos a la red WIFI por favor intente nuevamente", "Intentar de nuevo", () -> {
                    this.startConnectionServer();
                } );
            }
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnConnect:
                break;
        }
    }
}
