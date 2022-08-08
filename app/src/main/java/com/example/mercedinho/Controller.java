package com.example.mercedinho;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

public class Controller extends AppCompatActivity {

    private byte currentGear = 0;
    private byte pwmValue = 0;

    private Timer timer = new Timer();
    private TimerTask timerTask;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);

        TextView commandsBox = findViewById(R.id.commandsBox);
        commandsBox.setEnabled(false);
        TextView currentGearTextBox = findViewById(R.id.text_currentGear);

        ImageView throttle = findViewById(R.id.icon_throttle);
        ImageView brake = findViewById(R.id.icon_brake);

        Button gearUp = findViewById(R.id.button_upshift);
        Button gearDown = findViewById(R.id.button_downshift);
        ToggleButton toggleCommandsBoxVisibility = findViewById(R.id.toggleCommandBoxVisibility);

        // TODO: TEST THIS FUNCTION:
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    String command = "throttle, " + pwmValue + ", " + currentGear;
                    MainActivity.connectedThread.write(command.getBytes());
                } catch (Exception e) { }
            }
        };

        gearUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (currentGear < (byte)8) {
                        currentGear++;
                        if (currentGear == 0) {
                            currentGearTextBox.setText("N");
                        } else {
                            currentGearTextBox.setText(String.valueOf(currentGear));
                        }
                    }
                } catch (Exception e) { }
            }
        });

        gearDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (currentGear > (byte)-1) {
                        currentGear--;
                        if (currentGear == 0) {
                            currentGearTextBox.setText("N");
                        } else {
                            currentGearTextBox.setText(String.valueOf(currentGear));
                        }
                    }
                } catch (Exception e) { }
            }
        });

        toggleCommandsBoxVisibility.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    commandsBox.setVisibility(View.VISIBLE);
                } else {
                    commandsBox.setVisibility(View.GONE);
                }
            }
        });

        throttle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                try {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        commandsBox.append("throttle, " + pwmValue + ", " + currentGear + "\n");
                        pwmValue++;
                        timer.schedule(timerTask, 0, 50);
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        timer.cancel();
                    }
                } catch (Exception e) { }
                return false;
            }
        });

        brake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commandsBox.append("brake\n");
            }
        });
    }
}