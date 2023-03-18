package socialnetwork.socialnetwork.Domain;

import java.util.Stack;
import java.util.Vector;

public class Conversation {
    Vector<Message> messages;
    User user1;
    User user2;

    public Conversation(Vector<Message> messages, User user1, User user2) {
        this.messages = messages;
        this.user1 = user1;
        this.user2 = user2;
    }

    public Vector<Message> getMessages() {
        return messages;
    }

    public void setMessages(Vector<Message> messages) {
        this.messages = messages;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }
    public void addMessage(Message m){
        messages.add(m);
    }
}
