package no.karbowski.pingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class LogActivity extends AppCompatActivity {

    LinearLayout layout;
    Scanner scanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        layout = findViewById(R.id.logLayout);
        readLogData();
    }

    private void readLogData(){

        try {
            scanner = new Scanner(openFileInput("log.txt"));

            while (scanner.hasNextLine()){
                TextView newLogLine = new TextView(this);
                newLogLine.setText(scanner.nextLine());
                layout.addView(newLogLine);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    public void clearLogOnClick(View view){
        try {
            layout.removeAllViews();
            PrintStream output = new PrintStream(openFileOutput("log.txt", MODE_PRIVATE));
            output.print("");
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
