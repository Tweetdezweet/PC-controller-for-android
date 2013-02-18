package be.zweetinc.PcController;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: KoenG
 * Date: 15/02/13
 * Time: 7:51
 * To change this template use File | Settings | File Templates.
 */
public class ConnectionHandler extends Handler {
    public static final String MESSAGE_DATA = "be.zweetinc.PcController.Message_Data";
    public static final int CONNECTION = 0;
    public static final int CONNECT = 0;
    public static final int DISCONNECT = -1;

    private Socket client;
    private PrintWriter printwriter;
    private Message message;

    public ConnectionHandler(Looper looper){
        super(looper);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);    //To change body of overridden methods use File | Settings | File Templates.
        message = msg;
        if(message.what != CONNECTION){
            sendMessageToServer();
        } else {
            handleConnection();
        }
    }
    private void handleConnection(){
        if(message.arg1 == CONNECT) {
            makeConnection();
        } else if(message.arg1 == DISCONNECT) {
            closeConnection();
        }
    }

    private void sendMessageToServer(){
        String msg = message.getData().getString(MESSAGE_DATA);
        printwriter.println(msg);
    }

    protected void quit(){
        getLooper().quit();
    }

    private void makeConnection(){
        try {
            client = new Socket("192.168.0.119", 4444);
            Log.d("ConnectionInfo", client.toString());
            printwriter = new PrintWriter(client.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private void closeConnection(){
        printwriter.close();
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        quit();
    }
}
