import java.io.Serializable;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.Date;

/**
 * Created by otk_prog on 29.07.2016.
 */
public class Message implements Serializable {
    public String name;
    public InetAddress ipAdres;
    public Date dateMessage;
    public String state;
    public String textMessage;

    public Message(String name, InetAddress ipAdres, Date dateMessage, String state, String textMessage) {
        this.name = name;
        this.ipAdres = ipAdres;
        this.dateMessage = dateMessage;
        this.state = state;
        this.textMessage = textMessage;
    }

    public Message() {

    }

    @Override
    public String toString() {
        return "Message{" +
                "name='" + name + '\'' +
                ", ipAdres=" + ipAdres +
                ", dateMessage=" + dateMessage +
                ", state='" + state + '\'' +
                ", textMessage='" + textMessage + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InetAddress getIpAdres() {
        return ipAdres;
    }

    public void setIpAdres(InetAddress ipAdres) {
        this.ipAdres = ipAdres;
    }

    public Date getDateMessage() {
        return dateMessage;
    }

    public void setDateMessage(Date dateMessage) {
        this.dateMessage = dateMessage;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }
}
