package com.example.mercedinho;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDateTime;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        final Button buttonSend = findViewById(R.id.button_sendMessage);
        final EditText messagesBox = findViewById(R.id.messagesBox);
        messagesBox.setEnabled(false);
        final EditText inputText = findViewById(R.id.inputTestText);
        MainActivity.ConnectedThread.updateActivity(this);


        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputText.getText().toString().isEmpty()) {
                    Snackbar.make(findViewById(R.id.testLayout), "Input text camp should contain text.", Snackbar.LENGTH_LONG).show();
                } else {
                    try {
                        messagesBox.append(LocalDateTime.now().toString() + ": " + inputText.getText().toString() + "\n");
                        MainActivity.connectedThread.write(inputText.getText().toString().getBytes());
                        inputText.setText("");
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                }
            }
        });
    }
}