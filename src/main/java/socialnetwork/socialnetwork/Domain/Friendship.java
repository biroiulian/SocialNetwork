package socialnetwork.socialnetwork.Domain;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Friendship {

    private User user1;
    private User user2;
    private LocalDateTime friendsFrom;
    private String status;

    public Friendship(User u1, User u2, LocalDateTime friendsFr, String status) {
        this.user1 = u1;
        this.user2 = u2;
        this.friendsFrom = friendsFr;
        this.status=status;
    }

    public Long getId1() {
        return user1.getId();
    }
    public User getUser1(){
        return user1;
    }
    public User getUser2(){
        return user2;
    }
    public Long getId2() {
        return user2.getId();
    }


    public LocalDateTime getDateAccepted() {
        return friendsFrom;
    }

    public void setDateAccepted(LocalDateTime fF) {
        this.friendsFrom = fF;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        // Formatting LocalDateTime to string
        String dateTimeString = friendsFrom.format(formatter);
        return  user1.getUsername()+" cu "+ user2.getUsername() + " din data de " + dateTimeString+ status +",\n";
    }
    public String getStatus() {
        return status;
    }
    public void acceptFriendship(){
        this.status="accepted";
    }
}
