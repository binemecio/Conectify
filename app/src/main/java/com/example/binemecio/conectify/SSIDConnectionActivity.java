package com.example.binemecio.conectify;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.binemecio.conectify.Helper.ConnectionSSID;

import java.util.ArrayList;
import java.util.List;

public class SSIDConnectionActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView currentNetwork, networkFromServer;
    private Button connectButton;

    private List<ConnectionSSID> connectionList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ssidconnection);
        this.initialice();
        this.assigneEventClick();
        this.registerTheCurrentNetwork();
    }

    private void initialice()
    {
        this.currentNetwork = findViewById(R.id.currentNetwork);
        this.networkFromServer = findViewById(R.id.networkFromServer);
        this.connectButton = findViewById(R.id.btnConnect);
    }

    private void assigneEventClick()
    {
        this.connectButton.setOnClickListener(this);
    }

    private void registerTheCurrentNetwork()
    {
        ConnectionSSID connection = new ConnectionSSID(this);
        String currentSSID = ConnectionSSID.getConnectedSSID(this);
        System.out.println("current SSID: "+currentSSID);
        if (!currentSSID.isEmpty())
        {
            connection.setNetworkSSID(currentSSID);
            // print current network
            this.currentNetwork.setText(currentSSID);
            this.connectionList.add(connection);
        }
        else
        {
            if(!ConnectionSSID.isWifiOpen(this))
            {
                this.currentNetwork.setText("Error the wifi is not on");
            }
            else if(ConnectionSSID.getConnectedSSID(this).isEmpty())
            {
                this.currentNetwork.setText("Error can not be connect");
            }

        }
    }

    private void retrieveDataFromServer()
    {
        this.connectToSSID();
    }

    private void connectToSSID()
    {
        ConnectionSSID connection = new ConnectionSSID(this,"test12345","abcd1234");

        boolean isConnected = connection.tryConnection();
        System.out.println("isconnected to test12345: "+isConnected);

        if(isConnected)
        {
            this.networkFromServer.setText(connection.getNetworkSSID() + "   is connected: "+connection.isConnected());
            this.connectionList.add(connection);
        }
        else
        {
            isConnected = connection.tryReconnect();

            if (isConnected)
            {
                this.networkFromServer.setText(connection.getNetworkSSID() + "   is connected: "+connection.isConnected());
                this.connectionList.add(connection);
            }
            else
            {
                this.networkFromServer.setText("Error can not be connect");
            }
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnConnect:
                retrieveDataFromServer();
                break;
        }
    }
}
