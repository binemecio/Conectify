package com.example.binemecio.conectify;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CustomerRecord extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_record);
        this.setTitle("Registro");


        TextView textView = findViewById(R.id.btn_send_record);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerRecord.this.closeApp();
            }
        });
    }

    private void closeApp()
    {
//        Intent intent = new Intent(getApplicationContext(), CustomerRecord.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.putExtra("EXIT", true);
//        startActivity(intent);
        this.finish();
    }

}
