package com.example.mercedinho;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

public class TestActivity extends AppCompatActivity {

    Timer timer = new Timer();
    private static Handler responseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        final EditText inputText = findViewById(R.id.inputTestText);
        final EditText messagesBox = findViewById(R.id.messagesBox);
        messagesBox.setEnabled(false);

        responseHandler = new Handler (Looper.getMainLooper()) {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        String message = MainActivity.ConnectedThread.getResponse();
                        if (!message.isEmpty()) {
                            messagesBox.append(LocalDateTime.now() + ": " + message + "\n");
                        }
                        break;
                }
            }
        };

        // BUTTONS
        final Button buttonSend = findViewById(R.id.button_sendMessage);
        final Button buttonLedOn = findViewById(R.id.button_ledOn);
        final Button buttonLedOff = findViewById(R.id.button_ledOff);
        final Button buttonBack = findViewById(R.id.button_back);
        final Button buttonStartFromTest = findViewById(R.id.button_startFromTest);


        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (inputText.getText().toString().isEmpty()) {
                        Snackbar.make(findViewById(R.id.testLayout), "Input text camp should contain text.", Snackbar.LENGTH_LONG).show();
                    } else {
                        messagesBox.append(LocalDateTime.now().toString() + ": " + inputText.getText().toString() + "\n");
                        MainActivity.connectedThread.write(inputText.getText().toString().getBytes());
                        inputText.setText("");
                    }
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
        });

        buttonLedOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    messagesBox.append(LocalDateTime.now() + ": " + "<led-on>\n");
                    MainActivity.connectedThread.write("<led-on>".getBytes());
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
        });

        buttonLedOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    messagesBox.append(LocalDateTime.now() + ": " + "<led-off>\n");
                    MainActivity.connectedThread.write("<led-off>".getBytes());
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
        });

        buttonBack.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    finish();
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
        }));

        buttonStartFromTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent controllerIntent = new Intent(TestActivity.this, Controller.class);
                    startActivity(controllerIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static Handler getHandler() {
        return responseHandler;
    }
}