package com.example.nullname.passwordmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {

    Button btnGenPass, btnStoragePass, btnReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGenPass = findViewById(R.id.btnGenPass);
        btnGenPass.setOnClickListener(this);

        btnStoragePass = findViewById(R.id.btnStoragePass);
        btnStoragePass.setOnClickListener(this);

        btnReference = findViewById(R.id.btnReference);
        btnReference.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnGenPass:
                Intent intent_one = new Intent(this, GenPass.class);
                startActivity(intent_one);
                break;
            case R.id.btnStoragePass:
                Intent intent_twoo = new Intent(this, StoragePass.class);
                startActivity(intent_twoo);
                break;
            case R.id.btnReference:
                break;
        }
    }
}
