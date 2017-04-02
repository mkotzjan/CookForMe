package de.cookforme.cookforme;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;

public class MainActivity extends AppCompatActivity {

    private TextView loginText;
    //private TextView userText;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginText = (TextView)findViewById(R.id.textView);
        //userText = (TextView)findViewById(R.id.textView2);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Wait while loading...");
        progressDialog.setCancelable(false); // disable dismiss by tapping outside of the dialog
    }

    public void login(View v) {
        progressDialog.show();
        new Connection().execute("http://cookforme.ddns.net:3000/","login?email=user1@email.com&password=test");
    }

    public void user(View v) {
        Toast.makeText(this, "Clicked on User", Toast.LENGTH_LONG).show();
        //userText.setText("Response:");
    }

    private class Connection extends AsyncTask<String, Integer, String> {
        URL aURL;

        @Override
        protected String doInBackground(String... params) {
            byte[] response = new byte[1024];
            int numRead;
            ByteArrayOutputStream responseString = new ByteArrayOutputStream();
            try {
                aURL = new URL(params[0]);
                final HttpURLConnection aHttpURLConnection = (HttpURLConnection) aURL.openConnection();

                InputStream aInputStream = aHttpURLConnection.getInputStream();
                BufferedInputStream aBufferedInputStream = new BufferedInputStream(aInputStream);

                try {
                    while ((numRead = aBufferedInputStream.read(response)) != -1) {
                        responseString.write(response, 0, numRead);
                    }
                } finally {
                    aBufferedInputStream.close();
                }
            } catch (IOException e) {
                Log.d("", e.toString());
            }
            return new String(responseString.toByteArray());
        }

        protected void onPostExecute(String result) {
            loginText.setText(result);
            progressDialog.dismiss();
        }
    }
}
