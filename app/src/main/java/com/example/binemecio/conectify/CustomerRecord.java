package com.example.binemecio.conectify;

import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.binemecio.conectify.GlobalVar.GlobalVar;
import com.example.binemecio.conectify.Helper.ConnectionServer;
import com.example.binemecio.conectify.Helper.DesignHelper;
import com.example.binemecio.conectify.Helper.HelperAd;
import com.example.binemecio.conectify.Helper.Helpers;
import com.example.binemecio.conectify.Pojo.ClientRecord;

public class CustomerRecord extends AppCompatActivity implements View.OnClickListener, EditText.OnFocusChangeListener {

    private EditText editTextName,editTextLastName, editTextPhone, editTextEmail;
    private TextView btnSendRecord;
    private ClientRecord record = new ClientRecord();
    private String SOInfo = "", deviceInfo = "";
    private ConnectionServer connection;
    private boolean isStarted = false, isValidName, isValidLastName, isValidPhone, isValidEmail;
    private Helpers helper = new Helpers();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_record);
        this.setTitle("Registro");
        this.initialize();
        this.assigneEventClick();
        this.loadData();
    }

    private void initialize()
    {
        this.helperAd = new HelperAd(this);
        this.connection = new ConnectionServer(this, GlobalVar.serverUrl);
        // get device info
        this.deviceInfo = Build.DEVICE + " " + Build.MODEL;
        this.SOInfo = Build.VERSION.BASE_OS;
        editTextName = findViewById(R.id.name);
        editTextLastName = findViewById(R.id.lastName);
        editTextPhone = findViewById(R.id.phone);
        editTextEmail = findViewById(R.id.email);
        btnSendRecord = findViewById(R.id.btnSend);
    }

    private void assigneEventClick()
    {
        btnSendRecord.setOnClickListener(this);
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
            this.btnSendRecord.setFocusable(true);
            this.btnSendRecord.requestFocus();
            this.sendData(this.record);
        }
    }

    private void sendData(ClientRecord record)
    {
        if (isValidName && isValidLastName && isValidPhone && isValidEmail)
        {
            new Thread(() -> {
                this.connection.sendRecordDataToServer(this.record,(result, message) -> {
                    CustomerRecord.this.runOnUiThread(() -> {
                        this.minimize();
                        this.isStarted = true;
                        this.helperAd.startEngine();
                    });
                });
            }).start();
        }
        else
        {
            String messageField = "";
            if (!isValidName)
                messageField = "Verifique el campo nombres\n";
            if (!isValidLastName)
                messageField = "Verifique el campo apellidos\n";
            if (!isValidPhone)
                messageField = "Verifique el campo telefono\n";
            if (!isValidEmail)
                messageField = "Verifique el campo correo\n";
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
                isValidName = this.helper.isValidName(this.record.getNombres_cliente(), (TextInputEditText)this.editTextName.getParent());
            }
            else if (view == this.editTextLastName)
            {
                this.record.setNombres_cliente(this.editTextLastName.getText().toString());
                isValidLastName = this.helper.isValidName(this.record.getNombres_cliente(), (TextInputEditText)this.editTextLastName.getParent());
            }
            else if (view == this.editTextPhone)
            {
                this.record.setNumero_celular(this.editTextPhone.getText().toString().trim());
                isValidPhone = this.helper.isValidName(this.record.getNombres_cliente(), (TextInputEditText)this.editTextPhone.getParent());
            }
            else if (view == this.editTextEmail)
            {
                this.record.setCorreo_electronico(this.editTextEmail.getText().toString().trim());
                isValidEmail = this.helper.isValidName(this.record.getNombres_cliente(), (TextInputEditText)this.editTextEmail.getParent());
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
        helperAd.startEngine();
        super.onActivityResult(requestCode, resultCode, data);

    }
}
