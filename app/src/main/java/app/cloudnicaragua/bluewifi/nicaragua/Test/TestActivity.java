package app.cloudnicaragua.bluewifi.nicaragua.Test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import app.cloudnicaragua.bluewifi.nicaragua.Helper.ConnectionSSID;
import app.cloudnicaragua.bluewifi.nicaragua.R;

public class TestActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView txtDetail;
    private Button btnConnect, btnForget;
    private ConnectionSSID helperSSID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        this.initialize();
        this.assigneeClick();
    }

    private void initialize()
    {
        this.txtDetail = findViewById(R.id.txtDetail);
        this.btnConnect = findViewById(R.id.btnConnect);
        this.btnForget = findViewById(R.id.btnForget);
        this.helperSSID = new ConnectionSSID(this);
        this.txtDetail.setText(ConnectionSSID.getConnectedSSID(this));
    }

    private void assigneeClick()
    {

        this.btnConnect.setOnClickListener(this);
        this.btnForget.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnConnect:
                this.connectSSID();
                break;

            case R.id.btnForget:
                this.forgetSSID();
                break;

        }
    }

    private void connectSSID()
    {
        this.helperSSID.setNetworkSSID("red_privada");
        this.helperSSID.setNetworkPass("passdelared");
        this.helperSSID.tryHiddenConnection();

        this.txtDetail.setText(ConnectionSSID.getConnectedSSID(this));
    }

    private void forgetSSID()
    {
        this.helperSSID.disconnectCurrentNetwork();

        this.txtDetail.setText(ConnectionSSID.getConnectedSSID(this));
    }

}
