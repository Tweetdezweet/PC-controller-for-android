package be.zweetinc.PcController;

/**
 * Created with IntelliJ IDEA.
 * User: KoenG
 * Date: 15/02/13
 * Time: 12:45
 * To change this template use File | Settings | File Templates.
 */
public class ClientMessage {

    private int eventClass;
    private int eventAction;
    private int eventActionType;
    private String eventText;

    public int getEventClass() {
        return eventClass;
    }

    public void setEventClass(int eventClass) {
        this.eventClass = eventClass;
    }

    public int getEventAction() {
        return eventAction;
    }

    public void setEventAction(int eventAction) {
        this.eventAction = eventAction;
    }

    public int getEventActionType() {
        return eventActionType;
    }

    public void setEventActionType(int eventActionType) {
        this.eventActionType = eventActionType;
    }

    public String getEventText() {
        return eventText;
    }

    public void setEventText(String eventText) {
        this.eventText = eventText;
    }
}
