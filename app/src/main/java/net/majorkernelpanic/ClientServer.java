package net.majorkernelpanic;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientServer extends Service {

    private ServerSocket serverSocket;
    private String str;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ServerTask task = new ServerTask();
        task.execute();
        Log.i("SERVICE", "service start!");
    }

    private class ServerTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                serverSocket = new ServerSocket(5038);
            } catch (IOException e) {
                e.printStackTrace();
            }

            while (true) {
                Log.i("lixiaolu", "S : Connecting ...");
                try {
                    Socket client = serverSocket.accept();
                    InputStreamReader inputStream = new InputStreamReader(client.getInputStream());
                    BufferedReader in = new BufferedReader(inputStream);

                    str = in.readLine();
                    Byte[] bytes = new Byte[7];
                    Log.i("lixiaolu", "S : Received :" + str);
                    if (str != null) {
                        for (int index = 0; index <= 6; index++) {
                            String cmd;
                            if (index == 6) cmd = str;
                            else
                                cmd = str.substring(0, str.indexOf(","));
                            int currentCmd = Integer.parseInt(cmd);
                            bytes[index] = (byte) currentCmd;
                            str = str.substring(str.indexOf(",") + 1);
                        }
                        update(bytes);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private String intToIp(int i) {
        return (i & 0xFF) + "." +
                ((i >> 8) & 0xFF) + "." +
                ((i >> 16) & 0xFF) + "." +
                (i >> 24 & 0xFF);
    }
}