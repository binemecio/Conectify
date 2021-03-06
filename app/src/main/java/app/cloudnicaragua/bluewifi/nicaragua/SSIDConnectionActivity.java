package app.cloudnicaragua.bluewifi.nicaragua;

import android.content.Intent;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import app.cloudnicaragua.bluewifi.nicaragua.Helper.ConnectionSSID;
import app.cloudnicaragua.bluewifi.nicaragua.Helper.ConnectionServer;
import app.cloudnicaragua.bluewifi.nicaragua.Helper.DesignHelper;
import app.cloudnicaragua.bluewifi.nicaragua.Helper.Helpers;

import app.cloudnicaragua.bluewifi.nicaragua.R;

import app.cloudnicaragua.bluewifi.nicaragua.Singletoon.StorageSingleton;
import com.github.ybq.android.spinkit.SpinKitView;

import java.util.ArrayList;
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
        this.removeNetwork();
        this.connectionServer = new ConnectionServer(this);
        this.initialice();

        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }
    }

    private void initialice()
    {
        textLoading = findViewById(R.id.textLoading);
        spinKitView = findViewById(R.id.spin_kit);
        this.ssid = ConnectionSSID.getConnectedSSID(this);
        this.actualSSID = new ConnectionSSID(this);
        StorageSingleton.getInstance().setSsid(this.ssid);
        this.validateServices();
    }

    private void validateServices()
    {
//        boolean canStartConnection = false;
        if(!ConnectionSSID.isWifiOpen(this))
        {
            DesignHelper.showSimpleDialog(this,"Error", "Por favor habilite la red WIFI", "Habilitar red WIFI", () -> {
                ConnectionSSID.setEnabledWifi(this);
                this.validateServices();
            } );
            return;
        }

        if (helper.isNullOrWhiteSpace(ssid))
        {
            DesignHelper.showSimpleDialog(this,"Error", "Es necesario que se conecte a una red WIFI para usar esta aplicación", "Abrir configuración", () -> {
                SSIDConnectionActivity.this.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                this.validateServices();
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
    }

    private void startConnectionServer()
    {
        textLoading.setText("Recupenrando Información");
//        this.ssid = "KODEIN_WIFI";
        connectionServer.getEnterpriseDataFromServer(this.ssid.replace("\"","") ,data -> {
            if (data == null){
                Toast.makeText(this,"Failed retrieve data from server", Toast.LENGTH_LONG).show();
                DesignHelper.showSimpleDialog(this,"Error", "Tenemos problemas al conectarnos a la red WIFI por favor intente nuevamente", "Intentar de nuevo", () -> {

                    SSIDConnectionActivity.this.actualSSID.tryReconnect();
                    this.validateServices();
                } );
                return;
            }

            StorageSingleton.getInstance().setEnterPrise(data);
            StorageSingleton.getInstance().setSsid2(data.getSsid2_empresa());
            StorageSingleton.getInstance().setLoopTime(data.getTiempo_ciclo_lanzamiento());
            final Handler handler = new Handler();
            handler.postDelayed(() -> {
                SSIDConnectionActivity.this.textLoading.setText("Conectandose a la red WIFI");
            }, 1000);
            SSIDConnectionActivity.this.connectToSSID(data.getSsid2_empresa(), data.getPassword());

        });
    }

    private void removeNetwork()
    {
        String ssid = StorageSingleton.getInstance().getSsid2();
        if (helper.isNullOrWhiteSpace(ssid))
            return;
        ConnectionSSID connectionSSID = new ConnectionSSID(this, ssid, "");
        connectionSSID.disconnectCurrentNetwork();
        connectionSSID.tryReconnect();
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
//        else
//        {
//            if(!ConnectionSSID.isWifiOpen(this))
//            {
//
//            }
//            else if(ConnectionSSID.getConnectedSSID(this).isEmpty())
//            {
//
//            }
//
//        }
    }

    private void startActivityAd()
    {
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            this.startActivity(new Intent(this,CustomerRecord.class));
        }, 3000);
    }

    private void connectToSSID(String ssid, String password)
    {
        StorageSingleton.getInstance().setSsid2(ssid);
        ConnectionSSID connection = new ConnectionSSID(this, ssid, password);

        boolean isConnected = connection.tryHiddenConnection();

        if(isConnected)
        {
            StorageSingleton.getInstance().setNetId(connection.getNetWorkID());
            Toast.makeText(this,"Conectado a "+ ssid, Toast.LENGTH_LONG).show();
            this.startActivityAd();
//            this.startActivity(new Intent(this,CustomerRecord.class));
            this.connectionList.add(connection);
            this.connectionList.add(connection);
        }
        else
        {
            isConnected = connection.tryReconnect(this.ssid);

            if (isConnected)
            {
                Toast.makeText(this,"Conectado a "+ ssid, Toast.LENGTH_LONG).show();
                this.connectionList.add(connection);
                StorageSingleton.getInstance().setNetId(connection.getNetWorkID());
//                this.startActivityForResult(new Intent(this,CustomerRecord.class), 0);
                this.startActivityAd();
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
