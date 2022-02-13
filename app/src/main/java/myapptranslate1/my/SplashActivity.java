package myapptranslate1.my;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TextView tvAppName = findViewById(R.id.tv_app_name);


        Animation animation = AnimationUtils.loadAnimation(this , R.anim.show_up);
        tvAppName.startAnimation(animation);

        if (!Check_Wifi()){
            show_dialogWifi();
        }else{
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    gotoMainActivity();
                }
            }, 2500);
        }
    }

    private Boolean Check_Wifi(){
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    private void showWifi(){
        Toast.makeText(this , "Pls , connect Wifi for running app" , Toast.LENGTH_SHORT).show();
    }

    private void gotoMainActivity(){
        Intent intent = new Intent(this , MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void show_dialogWifi(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        View mview = getLayoutInflater().inflate(R.layout.layout_dialog_wifi, null);

        Button btn_wifi = mview.findViewById(R.id.btn_dialog_have_wifi);
        Button btn_stop_app = mview.findViewById(R.id.btn_dialog_stop_app);

        alert.setView(mview);
        AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);

        btn_wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Check_Wifi()){
                    showWifi();
                }else{
                    gotoMainActivity();
                }
            }
        });

        btn_stop_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
        alertDialog.show();
    }
}
