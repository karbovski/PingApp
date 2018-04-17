package no.karbowski.pingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.PrintStream;

public class MainActivity extends AppCompatActivity {

    EditText hostnameEditText;
    TextView latencyTextView;
    String hostname;
    PrintStream output;

    public void pingOnClick(View view) {
        try {
            hostname = hostnameEditText.getText().toString();
            double latency = Ping.ping(hostname);
            String latencyString = "Latency: " + latency + " ms";
            latencyTextView.setText(latencyString);
            output.println(hostname + " " + latency + "ms");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hostnameEditText = findViewById(R.id.hostNameEditText);

        latencyTextView = findViewById(R.id.latencyTextView);

        try {
            output = new PrintStream(openFileOutput("log.txt",MODE_APPEND | MODE_PRIVATE));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    public void showLogClick(View view){
        Intent intent = new Intent(this, LogActivity.class);
        startActivity(intent);
    }
}
