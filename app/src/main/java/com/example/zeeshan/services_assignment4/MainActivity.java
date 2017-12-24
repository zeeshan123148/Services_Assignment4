package com.example.zeeshan.services_assignment4;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {


    Button btnStartService, btnStopService;
    EditText etSetLimit;
    TextView textView;
    int limit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnStartService = (Button) findViewById(R.id.btnS);
        btnStopService = (Button) findViewById(R.id.btnStop);
        etSetLimit = (EditText) findViewById(R.id.etSetLimit);
        textView = (TextView) findViewById(R.id.tvShow);


        limit = Integer.parseInt(etSetLimit.getText().toString());

        btnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getApplicationContext(), MyService.class);
                intent.putExtra("limit", etSetLimit.getText().toString());
                startService(intent);
            }
        });

        btnStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyService.class);
                stopService(intent);
            }
        });

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void progress(MyService.ProgressEvent event) {
        textView.setText(event.msg + "%");
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}


