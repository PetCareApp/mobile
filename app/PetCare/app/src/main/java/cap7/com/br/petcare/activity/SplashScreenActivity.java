package cap7.com.br.petcare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import cap7.com.br.petcare.R;

public class SplashScreenActivity extends AppCompatActivity
        implements Runnable{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(this, 2000);
    }

    public void run(){
        startActivity(new Intent(this, ProprietarioActivity.class));
        finish();
    }
}
