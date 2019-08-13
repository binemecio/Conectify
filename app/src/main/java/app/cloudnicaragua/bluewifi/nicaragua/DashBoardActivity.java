package app.cloudnicaragua.bluewifi.nicaragua;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import app.cloudnicaragua.bluewifi.nicaragua.Helper.HelperAd;

import app.cloudnicaragua.bluewifi.nicaragua.R;

public class DashBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        this.runOnUiThread(() -> {

            this.minimize();
            HelperAd helperAd = new HelperAd(DashBoardActivity.this);
            helperAd.startEngine();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 0)
        {
            this.finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(1);
    }


    public void minimize() {
        this.moveTaskToBack(true);
    }
}
