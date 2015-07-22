package net.majorkernelpanic;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import net.majorkernelpanic.spydroid.CommandSender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientServer extends Service {

    private ServerSocket serverSocket;
    private CommandSender sendor;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sendor = new CommandSender();
        sendor.native_init();
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
                try {
                    Socket client = serverSocket.accept();
                    InputStreamReader inputStream = new InputStreamReader(client.getInputStream());
                    BufferedReader in = new BufferedReader(inputStream);
                    String str = in.readLine();
                    Log.i(Constants.TAG, "S : Received :" + str);
                    byte[] bytes = getCmdBytes(str);
                    sendor.native_update(bytes);

                    OutputStream outputStream = client.getOutputStream();
                    PrintStream stream = new PrintStream(outputStream);
                    stream.print("success");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private byte[] getCmdBytes(String str) {
        byte[] bytes = new byte[4];
        if (str != null) {
            for (int index = 0; index <= 3; index++) {
                String cmd;
                if (index == 3) cmd = str;
                else
                    cmd = str.substring(0, str.indexOf(","));
                int currentCmd = Integer.parseInt(cmd);
                bytes[index] = (byte) currentCmd;
                str = str.substring(str.indexOf(",") + 1);
            }
        }
        return bytes;
    }
}