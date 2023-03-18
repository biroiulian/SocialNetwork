package socialnetwork.socialnetwork.Repository;

import socialnetwork.socialnetwork.Domain.AuxFriendship;
import socialnetwork.socialnetwork.Domain.Friendship;
import socialnetwork.socialnetwork.Domain.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Set;
import java.util.Vector;

public class FriendshipDbRepository {

    private String url;
    private String username;
    private String password;
    private Vector<Friendship> friendshipList;

    public FriendshipDbRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.friendshipList= new Vector<Friendship>();
        loadFromDb();
        System.out.println("Initialized FriendsDbRepo...");
    }

    private void loadFromDb(){
        Vector<Friendship> friendships = new Vector<>();
        Vector<AuxFriendship> fr = new Vector<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        try (Connection connection = DriverManager.getConnection(url, username, password);
                 PreparedStatement statement = connection.prepareStatement("SELECT * from friendships");
                 ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                    Long id1 = resultSet.getLong("idUSER1");
                    Long id2 = resultSet.getLong("idUSER2");
                    LocalDateTime dateTime = LocalDateTime.parse(resultSet.getString("datetime"), formatter);
                    String status = resultSet.getString("status");
                    fr.add(new AuxFriendship(id1, id2, dateTime, status));
                }

        } catch (SQLException e) {
                e.printStackTrace();
            }
            int index =fr.size();
            index--;
            while(index>=0) {
                User u1=null, u2=null;
                //creating first User object
                try (Connection connection2 = DriverManager.getConnection(url, username, password);
                     PreparedStatement stmt = connection2.prepareStatement("SELECT id, username, password, age FROM users WHERE id=?")) {
                    stmt.setLong(1, fr.get(index).id1);
                    ResultSet result = stmt.executeQuery();
                    if(result.next()) {
                        String usrname = result.getString("username");
                        String passwd = result.getString("password");
                        int age = result.getInt("age");
                        u1 = new User(usrname, passwd, age);
                        u1.setId(fr.get(index).id1);
                    }
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }

                //creating second User object
                try (Connection connection3 = DriverManager.getConnection(url, username, password);
                     PreparedStatement stmt2 = connection3.prepareStatement("SELECT id, username, password, age FROM users WHERE id=?")) {
                    stmt2.setLong(1, fr.get(index).id2);
                    ResultSet result2 = stmt2.executeQuery();
                    if(result2.next()) {
                        String usrname2 = result2.getString("username");
                        String passwd2 = result2.getString("password");
                        int age2 = result2.getInt("age");
                        u2 = new User(usrname2, passwd2, age2);
                        u2.setId(fr.get(index).id2);
                    }
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
                friendships.add(new Friendship(u1, u2, fr.get(index).dt, fr.get(index).status));
                index--;
            }
            this.friendshipList=friendships;
    }

    public void delete(Long id1, Long id2) {
        String deleteSQL = "DELETE FROM friendships WHERE \"idUSER1\" = ? and \"idUSER2\" = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(deleteSQL)) {
            statement.setLong(1, id1);
            statement.setLong(2, id2);
            statement.executeUpdate();
            this.loadFromDb();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(Friendship friendship) {
        String addSQL = "INSERT INTO friendships(\"idUSER1\", \"idUSER2\", datetime, status) VALUES(?,?,?,?)";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(addSQL)) {
            statement.setLong(1, friendship.getId1());
            statement.setLong(2, friendship.getId2());
            statement.setString(3, friendship.getDateAccepted().format(formatter));
            statement.setString(4, friendship.getStatus());
            statement.executeUpdate();
            this.loadFromDb();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Vector<Friendship> getFriendshipList() {
        return this.friendshipList;
    }

    public Vector<Long> getFriendsIdList(Long id) {
        Vector<Long> userFriendsId = new Vector<>();
        this.friendshipList.forEach(e -> {
            if(Objects.equals(e.getStatus(), "accepted") && Objects.equals(e.getId1(), id)){
                userFriendsId.add(e.getId2());
            }
            else if(Objects.equals(e.getStatus(), "accepted") && Objects.equals(e.getId2(), id)){
                userFriendsId.add(e.getId1());
            }
        });
        return userFriendsId;
    }

    public Vector<Friendship> getFriendReqOfUser(Long id){
        Vector<Friendship> frList=new Vector<>();
        friendshipList.forEach(e->
        {
            if(Objects.equals(e.getId2(), id) && Objects.equals(e.getStatus(), "pending"))
                frList.add(e);
        });
        return frList;
    }
    public Vector<Friendship> getSentFriendReqOfUser(Long id){
        Vector<Friendship> frList=new Vector<>();
        friendshipList.forEach(e->
        {
            if(Objects.equals(e.getId1(), id) && Objects.equals(e.getStatus(), "pending"))
                frList.add(e);
        });
        return frList;
    }
    public void acceptFriendRequest(Long u1, Long u2){
        String updateSQL = "UPDATE friendships set status = \'accepted\' where \"idUSER1\"=? and \"idUSER2\"=?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(updateSQL)) {
            statement.setLong(1, u1);
            statement.setLong(2, u2);
            statement.executeUpdate();
            this.loadFromDb();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
