package be.zweetinc.PcController;

import android.app.Activity;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: KoenG
 * Date: 14/02/13
 * Time: 16:19
 * To change this template use File | Settings | File Templates.
 */
public class VlcRemoteActivity extends Activity {

    private Button fullScreenButton;
    private Button powerButton;
    private Button aspectRatioButton;
    private Button playPauseButton;
    private Button stopButton;
    private Button nextButton;
    private Button previousButton;
    private Button volumeUpButton;
    private Button volumeDownButton;
    private Button fastForwardButton;
    private Button rewindButton;
    private Button muteButton;

    private Message message;


    private HandlerThread connectionHandlerThread;
    private ConnectionHandler connectionHandler;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setupButtons();
        startConnectionThread();
        tellThreadToMakeConnection();
//        sendButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String textToSend = messageText.getText().toString();
//
//                Bundle data = new Bundle();
//                data.putString(ConnectionHandler.MESSAGE_DATA, messageText.getText().toString());
////                TODO: change arg1 and arg2 parameter when feature is not limited to logging only
//                message = Message.obtain(connectionHandler, MessageCode.CLASS_VLC, MessageCode.VLC_VOLUME_UP, 0, data);
//                connectionHandler.sendMessage(message);
//            }
//        });
//
//        killServerButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                message = Message.obtain(connectionHandler, MessageCode.CLASS_SERVICE, 0, 0);
//                connectionHandler.sendMessage(message);
//            }
//        });
    }

    private void setupButtons(){
        fullScreenButton = (Button) findViewById(R.id.fullScreenButton);
        powerButton = (Button) findViewById(R.id.powerButton);
        aspectRatioButton = (Button) findViewById(R.id.aspectRatioButton);
        playPauseButton = (Button) findViewById(R.id.playPauseButton);
        stopButton = (Button) findViewById(R.id.stopButton);
        nextButton = (Button) findViewById(R.id.nextButton);
        previousButton = (Button) findViewById(R.id.previousButton);
        volumeUpButton = (Button) findViewById(R.id.volumeUpButton);
        volumeDownButton = (Button) findViewById(R.id.volumeDownButton);
        fastForwardButton = (Button) findViewById(R.id.fastForwardButton);
        rewindButton = (Button) findViewById(R.id.rewindButton);
        muteButton = (Button) findViewById(R.id.muteButton);

        setOnClickListenersForButtons();
    }

    private void setOnClickListenersForButtons(){
        fullScreenButton.setOnClickListener(new FullScreenListener());
        powerButton.setOnClickListener(new PowerListener());
        aspectRatioButton.setOnClickListener(new AspectRatioListener());
        playPauseButton.setOnClickListener(new PlayPauseListener());
        stopButton.setOnClickListener(new stopListener());
        nextButton.setOnClickListener(new NextListener());
        previousButton.setOnClickListener(new PreviousListener());
        volumeUpButton.setOnClickListener(new VolumeUpListener());
        volumeDownButton.setOnClickListener(new VolumeDownListener());
        fastForwardButton.setOnClickListener(new FastForwardListener());
        rewindButton.setOnClickListener(new RewindListener());
        muteButton.setOnClickListener(new MuteListener());
    }

    private void startConnectionThread(){
        connectionHandlerThread = new HandlerThread("ConnectionThread");
        connectionHandlerThread.start();
        connectionHandler = new ConnectionHandler(connectionHandlerThread.getLooper());
    }

    private void tellThreadToMakeConnection(){
        message = Message.obtain(connectionHandler);
        message.what = ConnectionHandler.CONNECTION; // EventClass CONNECTION
        message.arg1 = ConnectionHandler.CONNECT; // EventAction CONNECT

        connectionHandler.sendMessage(message);
    }

    @Override
    protected void onPause() {
        super.onPause();
        message = Message.obtain(connectionHandler, ConnectionHandler.CONNECTION);
        message.arg1 = ConnectionHandler.DISCONNECT;
        connectionHandler.sendMessage(message);
    }

    private class FullScreenListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String msg = prepareMessage("vlc", "fullScreen");
            sendActionToRemote(msg);
        }
    }

    private class PowerListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String msg = prepareMessage("vlc", "power");
            sendActionToRemote(msg);
        }
    }

    private class AspectRatioListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String msg = prepareMessage("vlc", "aspectRatio");
            sendActionToRemote(msg);
        }
    }

    private class PlayPauseListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String msg = prepareMessage("vlc", "play_pause");
            sendActionToRemote(msg);
        }
    }

    private class NextListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String msg = prepareMessage("vlc", "next");
            sendActionToRemote(msg);
        }
    }

    private class PreviousListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String msg = prepareMessage("vlc", "previous");
            sendActionToRemote(msg);
        }
    }

    private class VolumeUpListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String msg = prepareMessage("vlc", "volumeUp");
            sendActionToRemote(msg);
        }
    }

    private class VolumeDownListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String msg = prepareMessage("vlc", "volumeDown");
            sendActionToRemote(msg);
        }
    }

    private class FastForwardListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String msg = prepareMessage("vlc", "fastForward");
            sendActionToRemote(msg);
        }
    }

    private class RewindListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String msg = prepareMessage("vlc", "rewind");
            sendActionToRemote(msg);
        }
    }

    private class MuteListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String msg = prepareMessage("vlc", "mute");
            sendActionToRemote(msg);
        }
    }

    private String prepareMessage(String eventDestination, String eventAction){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("eventDestination", eventDestination);
            jsonObject.put("eventAction", eventAction);
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return jsonObject.toString();
    }

    private class stopListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String msg = prepareMessage("vlc", "stop");
            sendActionToRemote(msg);
        }
    }

    private void sendActionToRemote(String msg){
        Bundle bundle = new Bundle();
        bundle.putString(ConnectionHandler.MESSAGE_DATA, msg);
        message = Message.obtain(connectionHandler, 1);
        message.setData(bundle);
        connectionHandler.sendMessage(message);
    }
}