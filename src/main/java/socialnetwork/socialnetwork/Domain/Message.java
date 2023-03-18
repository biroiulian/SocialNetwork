package socialnetwork.socialnetwork.Domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message {
    private String text;
    private LocalDateTime dateSent;
    private String sender;
    private String receiver;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    public Message(String text, LocalDateTime dateSent, String sender, String receiver) {
        this.text = text;
        this.dateSent = dateSent;
        this.sender = sender;
        this.receiver = receiver;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDateSent() {
        return dateSent.format(formatter);
    }

    public void setDateSent(LocalDateTime dateSent) {
        this.dateSent = dateSent;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
