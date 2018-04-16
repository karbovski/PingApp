package no.karbowski.pingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    public void pingOnClick(View view) {

        EditText hostnameEditText = findViewById(R.id.hostNameEditText);

        TextView latencyTextView = findViewById(R.id.latencyTextView);

        String hostname = hostnameEditText.getText().toString();

        try {

            double latency = Ping.ping(hostname);
            String latencyString = "Latency: " + latency + " ms";
            latencyTextView.setText(latencyString);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
