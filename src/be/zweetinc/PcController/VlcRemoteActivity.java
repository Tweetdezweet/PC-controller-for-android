package be.zweetinc.PcController;

import android.app.Activity;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * Created with IntelliJ IDEA.
 * User: KoenG
 * Date: 14/02/13
 * Time: 16:19
 * To change this template use File | Settings | File Templates.
 */
public class VlcRemoteActivity extends Activity {

    private ImageButton volumeUpButton;
    private ImageButton volumeDownButton;
    private EditText messageText;
    private Message message;
    private Button sendButton;
    private Button killServerButton;

    private HandlerThread connectionHandlerThread;
    private ConnectionHandler connectionHandler;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        volumeDownButton = (ImageButton) findViewById(R.id.downButton);
        volumeUpButton = (ImageButton) findViewById(R.id.upButton);
        messageText = (EditText) findViewById(R.id.message);
        sendButton = (Button) findViewById(R.id.sendButton);
        killServerButton= (Button) findViewById(R.id.killServerButton);

        connectionHandlerThread = new HandlerThread("ConnectionThread");
        connectionHandlerThread.start();

        connectionHandler = new ConnectionHandler(connectionHandlerThread.getLooper());

        message = Message.obtain(connectionHandler);
        message.what = MessageCode.CLASS_CONNECTION; // EventClass CONNECTION
        message.arg1 = MessageCode.CONNECTION_CONNECT; // EventAction CONNECT

        connectionHandler.sendMessage(message);

        volumeUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message = Message.obtain(connectionHandler, MessageCode.CLASS_VLC, MessageCode.VLC_VOLUME_UP, 0);
                connectionHandler.sendMessage(message);
            }
        });

        volumeDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message = Message.obtain(connectionHandler, MessageCode.CLASS_VLC, MessageCode.VLC_VOLUME_DOWN, 0);
                connectionHandler.sendMessage(message);
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textToSend = messageText.getText().toString();

                Bundle data = new Bundle();
                data.putString(ConnectionHandler.MESSAGE_DATA, messageText.getText().toString());
//                TODO: change arg1 and arg2 parameter when feature is not limited to logging only
                message = Message.obtain(connectionHandler, MessageCode.CLASS_VLC, MessageCode.VLC_VOLUME_UP, 0, data);
                connectionHandler.sendMessage(message);
            }
        });

        killServerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message = Message.obtain(connectionHandler, MessageCode.CLASS_SERVICE, 0, 0);
                connectionHandler.sendMessage(message);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        message = Message.obtain(connectionHandler, MessageCode.CLASS_CONNECTION, MessageCode.CONNECTION_DISCONNECT, 0);
        connectionHandler.sendMessage(message);
//        connectionHandlerThread.quit();
    }

}