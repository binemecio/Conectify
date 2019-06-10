package com.example.binemecio.conectify;

import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.binemecio.conectify.GlobalVar.GlobalVar;
import com.example.binemecio.conectify.Helper.ConnectionSSID;
import com.example.binemecio.conectify.Helper.ConnectionServer;
import com.example.binemecio.conectify.Helper.DesignHelper;
import com.example.binemecio.conectify.Helper.HelperAd;
import com.example.binemecio.conectify.Helper.Helpers;
import com.example.binemecio.conectify.Pojo.ClientRecord;
import com.example.binemecio.conectify.Pojo.EnterPrise;
import com.example.binemecio.conectify.Singletoon.StorageSingleton;

public class CustomerRecord extends AppCompatActivity implements View.OnClickListener, EditText.OnFocusChangeListener {

    private EditText editTextName,editTextLastName, editTextPhone, editTextEmail;
    private TextView btnSendRecord;
    private ClientRecord record = new ClientRecord();
    private String SOInfo = "", deviceInfo = "";
    private ConnectionServer connection;
    private boolean isStarted = false, isValidName, isValidLastName, isValidPhone, isValidEmail;
    private Helpers helper = new Helpers();
    private EnterPrise enterPrise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_record);
        this.setTitle("Registro");
        this.initialize();
        //this.getLoopTime();
        this.assigneEventClick();
        this.loadData();
    }

    private void initialize()
    {
        this.record = new ClientRecord();
        this.helperAd = new HelperAd(this);
        this.connection = new ConnectionServer(this, GlobalVar.serverUrl);
        // get device info
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        String versionRelease = Build.VERSION.RELEASE;

        this.deviceInfo = manufacturer + " " + model;
        this.SOInfo = "Android " + versionRelease;
        this.record.setDispositivo_cliente(this.deviceInfo);
        this.record.setSistema_operativo_cliente(this.SOInfo);


        editTextName = findViewById(R.id.name);
        editTextLastName = findViewById(R.id.lastName);
        editTextPhone = findViewById(R.id.phone);
        editTextEmail = findViewById(R.id.email);
        btnSendRecord = findViewById(R.id.btnSend);
        this.enterPrise = StorageSingleton.getInstance().getEnterPrise();
        this.record.setId_configuracion_empresa(this.enterPrise.getId_configuracion_empresa());

        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            if(StorageSingleton.getInstance().getSsid2() == "")
            {
                DesignHelper.showSimpleDialog(this,"Atencion!","No logramos conectarnos a la red wifi esperada, desea seguir utilizando la aplicación?", "Continuar", () -> {

                });
            }
        }, 2000);


    }


    private void getLoopTime(){

        new Thread(() -> {
            this.connection.getLoopTime(this.enterPrise.getId_ciclo_display_anuncios(),(result, value) -> {

                CustomerRecord.this.runOnUiThread(() -> {
                    StorageSingleton.getInstance().setLoopTime(value == Long.valueOf(0) ? Long.valueOf(10000) : value);
                });
            });
        }).start();
    }

    private void assigneEventClick()
    {
        btnSendRecord.setOnClickListener(this);

        this.editTextName.setOnFocusChangeListener(this);
        this.editTextLastName.setOnFocusChangeListener(this);
        this.editTextPhone.setOnFocusChangeListener(this);
        this.editTextEmail.setOnFocusChangeListener(this);
    }

    private void loadData()
    {
        this.editTextName.setText(record.getNombres_cliente());
        this.editTextLastName.setText(record.getApellidos_cliente());
        this.editTextPhone.setText(record.getNumero_celular());
        this.editTextEmail.setText(record.getCorreo_electronico());

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSend)
        {
            this.btnSendRecord.setFocusableInTouchMode(true);
            this.btnSendRecord.setFocusable(true);
            this.btnSendRecord.requestFocus();
            this.sendData(this.record);
        }
    }

    private void sendData(ClientRecord record)
    {


        if (isValidName && isValidLastName && isValidPhone && isValidEmail)
        {

            this.minimize();this.connection.sendRecordDataToServer(record, (result, message) -> {

            });
            this.startActivityForResult(new Intent(this,DashBoardActivity.class),0);
        }
        else
        {
            String messageField = "";
            if (!isValidName)
                messageField += "Verifique el campo nombres\n";
            if (!isValidLastName)
                messageField += "Verifique el campo apellidos\n";
            if (!isValidPhone)
                messageField += "Verifique el campo telefono\n";
            if (!isValidEmail)
                messageField += "Verifique el campo correo\n";
            DesignHelper.showSimpleDialog(CustomerRecord.this, "", messageField, () -> {});
        }
    }

    public void minimize() {
       this.moveTaskToBack(true);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        this.focusChange(v,hasFocus);
    }

    private void focusChange(View view, Boolean canFocus)
    {
        if (canFocus)
        {
            if (view == this.editTextName)
            {
                this.editTextName.setText(record.getNombres_cliente());
            }
            else if (view == this.editTextLastName)
            {
                this.editTextLastName.setText(record.getApellidos_cliente());
            }
            else if (view == this.editTextPhone)
            {
                this.editTextPhone.setText(record.getNumero_celular());
            }
            else if (view == this.editTextEmail)
            {
                this.editTextEmail.setText(record.getCorreo_electronico());
            }
        }
        else
        {
            if (view == this.editTextName)
            {
                this.record.setNombres_cliente(this.editTextName.getText().toString());
                isValidName = !this.helper.isNullOrWhiteSpace(helper.getString( this.record.getNombres_cliente()) );
            }
            else if (view == this.editTextLastName)
            {
                this.record.setApellidos_cliente(helper.getString(this.editTextLastName.getText().toString()));
                isValidLastName = !this.helper.isNullOrWhiteSpace(this.record.getApellidos_cliente());
            }
            else if (view == this.editTextPhone)
            {
                this.record.setNumero_celular(helper.getString(this.editTextPhone.getText().toString().trim()));
                isValidPhone = this.helper.isValidPhone(this.record.getNumero_celular());
            }
            else if (view == this.editTextEmail)
            {
                this.record.setCorreo_electronico(helper.getString(this.editTextEmail.getText().toString().trim()));
                isValidEmail = this.helper.isValidEmail(helper.getString(this.record.getCorreo_electronico()));
            }
        }
    }
    HelperAd helperAd;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.minimize();
        if (resultCode == 0)
        {
            Helpers helper = new Helpers();
            String ssid = helper.getString(StorageSingleton.getInstance().getSsid());
            String ssid2 = helper.getString(StorageSingleton.getInstance().getSsid2());
            ConnectionSSID connectionSSID = new ConnectionSSID(this);
            connectionSSID.setNetworkSSID(ssid);
            connectionSSID.disconnectNetwork(ssid2);
            connectionSSID.tryReconnect();
            this.finish();
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onDestroy() {
        Helpers helper = new Helpers();
        String ssid = helper.getString(StorageSingleton.getInstance().getSsid());
        String ssid2 = helper.getString(StorageSingleton.getInstance().getSsid2());
        ConnectionSSID connectionSSID = new ConnectionSSID(this);
        connectionSSID.setNetworkSSID(ssid);
        connectionSSID.disconnectNetwork(ssid2);
        connectionSSID.tryReconnect();
        super.onDestroy();
    }
}
