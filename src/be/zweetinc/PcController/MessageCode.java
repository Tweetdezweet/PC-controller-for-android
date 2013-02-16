package be.zweetinc.PcController;

public abstract class MessageCode {
    public static final int CLASS_SERVICE = -1;
    public static final int CLASS_CONNECTION = 0;
    public static final int CLASS_TEXT_MESSAGE = 1;
    public static final int CLASS_PROGRAM = 2;
    public static final int CLASS_VLC = 3;

    public static final int CONNECTION_CONNECT = 1;
    public static final int CONNECTION_DISCONNECT = -1;


    public static final int VLC_VOLUME_DOWN = 0;
    public static final int VLC_VOLUME_UP = 1;
}