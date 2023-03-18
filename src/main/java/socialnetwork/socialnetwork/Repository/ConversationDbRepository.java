package socialnetwork.socialnetwork.Repository;

import socialnetwork.socialnetwork.Domain.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Vector;

public class ConversationDbRepository {

    private String url;
    private String username;
    private String password;
    private Long user_id;
    private Vector<Conversation> conversationsList;

    public ConversationDbRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.user_id=Integer.toUnsignedLong(0);
        this.conversationsList= new Vector<Conversation>();
        loadFromDb();
        System.out.println("Initialized ConvDbRepo...");

}
    public void reloadConvsForUser(Long id){
        System.out.println("Incerc sa fac reload acum pt: "+ id);
        this.user_id=id;
        loadFromDb();
    }
    private void loadFromDb() {
        if(user_id==0)return;
        this.conversationsList=new Vector<Conversation>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT sender_id, u1.username as un1, u1.password as pw1, u1.age as a1, receiver_id,\n" +
                     "u2.username as un2, u2.password as pw2, u2.age as a2, date, text\n" +
                     "FROM messages\n" +
                     "INNER JOIN users u1\n" +
                     "ON (sender_id = u1.id and sender_id=?) or (sender_id = u1.id and receiver_id=?)\n" +
                     "INNER JOIN users u2\n" +
                     "ON receiver_id = u2.id")){
            statement.setLong(1, user_id);
            statement.setLong(2, user_id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Long id1 = resultSet.getLong("sender_id");
                Long id2 = resultSet.getLong("receiver_id");
                LocalDateTime dateTime = LocalDateTime.parse(resultSet.getString("date"), formatter);
                String text = resultSet.getString("text");
                System.out.println("added "+id1+" "+text);
                if(!this.addMessageToConversation(new Message(text, dateTime, resultSet.getString("un1"), resultSet.getString("un2")))){
                    this.create_conv(
                            new User(id1,
                                    resultSet.getString("un1"),
                                    resultSet.getString("pw1"),
                                    resultSet.getInt("a1")),
                            new User(id2,
                                    resultSet.getString("un2"),
                                    resultSet.getString("pw2"),
                                    resultSet.getInt("a2")));
                    this.addMessageToConversation(new Message(text, dateTime, resultSet.getString("un1"), resultSet.getString("un2")));
                };
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void create_conv(User u1, User u2){
        this.conversationsList.add(new Conversation(new Vector<Message>(), u1, u2));
        System.out.println("created a new conv for actual user");
    }
    private boolean addMessageToConversation(Message m){
        //cauta daca exista o conversatie intre id urile mesajului respectiv, si daca nu exista
        //o creeaza si adauga mesajul la ea
        int found = 0;
        System.out.println("CAUT O CONV INTRE USERUL " + m.getSender() + " si " + m.getReceiver());
        for(Conversation c: conversationsList){
            System.out.println("    PARCURG CONV INTRE: " + m.getSender() + " si " + m.getReceiver());
            if((Objects.equals(c.getUser1().getUsername(), m.getSender()) &&
                    (Objects.equals(c.getUser2().getUsername(), m.getReceiver())) )
                    ||
                (Objects.equals(c.getUser2().getUsername(), m.getSender()) &&
                    (Objects.equals(c.getUser1().getUsername(), m.getReceiver()))))
            {
                found=1;
                c.addMessage(m);
            }
        }
        return found != 0;
    }
    public Vector<Message> getConversationOf(Long id){
        System.out.println(conversationsList.size()+" number of convs for this user");
        for(Conversation c: conversationsList){
            //System.out.println(c.getUser1().getId()+" comp cu "+c.getUser2().getId());
            if((c.getUser1().getId()==id && c.getUser2().getId()==user_id) ||
                    (c.getUser2().getId()==id && c.getUser1().getId()==user_id)){
                return c.getMessages();
            }
        }
        System.out.println("Nu am gasit in repo conversatia dintre userul actual si "+id);
        return null;
    }
    public void add(String text, String dt, Long sender, Long receiver){
        String addSQL = "INSERT INTO public.messages(sender_id, receiver_id, text, date) VALUES (?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(addSQL)) {
            statement.setLong(1, sender);
            statement.setLong(2, receiver);
            statement.setString(3, text);
            statement.setString(4, dt);
            statement.executeUpdate();
            reloadConvsForUser(sender);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

