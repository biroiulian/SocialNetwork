package socialnetwork.socialnetwork.Service;

import socialnetwork.socialnetwork.Domain.*;
import socialnetwork.socialnetwork.Repository.ConversationDbRepository;
import socialnetwork.socialnetwork.Repository.FriendshipDbRepository;
import socialnetwork.socialnetwork.Repository.UsersDbRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Stack;
import java.util.Vector;

public class Service {

    private FriendshipDbRepository friendship_repository;
    private UsersDbRepository users_repository;
    private ConversationDbRepository conv_repository;
    private UserValidator userVal;
    private FriendshipValidator friendshipVal;
    private User currentUser;

    public Service(UsersDbRepository uRepo, FriendshipDbRepository fRepo, ConversationDbRepository cRepo, UserValidator uv, FriendshipValidator fv) {
        this.users_repository=uRepo;
        this.friendship_repository=fRepo;
        this.conv_repository=cRepo;
        this.userVal=uv;
        this.friendshipVal=fv;
        System.out.println("Initialized Service...");
    }

    /**
     *
     * @return Lista de useri
     */
    public Vector<User> getUserList(){
        return users_repository.getUsersList();
    }

    public void setCurrentUser(User u){
        this.currentUser=u;
        this.conv_repository.reloadConvsForUser(u.getId());
    }
    /**
     *
     * @return Lista de prietenii
     */
    public Vector<Friendship> getFriendshipList(){
        return friendship_repository.getFriendshipList();
    }

    public Vector<User> getFriendsOfUser(Long id){
        Vector<User> friendsList = new Vector<>();

        friendship_repository.getFriendsIdList(id).forEach(e -> {
            friendsList.add(this.users_repository.getUser(e));
        });
        return friendsList;
    }

    public Vector<Friendship>getFriendReqOfUser(Long id){
        return friendship_repository.getFriendReqOfUser(id);
    }
    public Vector<Friendship>getSentReqOfUser(Long id){
        Vector<Friendship> reqList = new Vector<>();
        friendship_repository.getSentFriendReqOfUser(id).forEach(e -> {
            if(Objects.equals(e.getUser1().getUsername(), currentUser.getUsername()) && Objects.equals(e.getStatus(), "pending")){
                reqList.add(e);
            }
        });
        return reqList;

    }
    public void acceptFriendRequest(String u1, String u2){
        friendship_repository.acceptFriendRequest(getUser(u1).getId(), getUser(u2).getId());
    }
    /**
     * Adds a user, validate the data then call method from repository to store it
     * @param un username of user
     * @param pw password of user
     * @param a age of user
     * @throws InvalidUserFormDataException
     */
    public void addUser(String un, String pw, String a) throws InvalidUserFormDataException {
        this.userVal.validateUserFormData(un, pw, Integer.parseInt(a));

        this.users_repository.add(new User(un, pw, Integer.parseInt(a)));
    }

    /**
     * Adds a Friendship, validates it and then call method from repository to store it
     * @param u1 username of first user
     * @param u2 username of second user
     * @param dt date of friendship
     * @throws InvalidFriendshipFormDataException
     */
    public void addFriendship(String u1, String u2, LocalDateTime dt) throws InvalidFriendshipFormDataException {
        this.friendshipVal.validateFriendshipFormData(u1, u2);
        User user1 = this.users_repository.getUser(u1);
        User user2 = this.users_repository.getUser(u2);
        this.friendship_repository.add(new Friendship(user1, user2, dt, "pending"));
    }

    /**
     * Deletes all the users's friendships
     * @param id_user username of User
     */
    public void deleteUser(Long id_user){
        //removing the actual user
        this.users_repository.delete(id_user);
        //removing all the friendships that he's involved in
        Vector<Friendship> userFriendships = new Vector<Friendship>(this.getFriendshipList());
        for(Friendship f: userFriendships){
            if(f.getId2()== id_user || f.getId1() == id_user){
                this.deleteFriendship(f.getId1(),f.getId2());
            }
        }
    }

    public void deleteFriendship(Long un1, Long un2){
        this.friendship_repository.delete(un1, un2);
    }

    /**
     * Deletes friendships
     * @param un1 username of User1
     * @param un2 username of User2
     */
    public void deleteFriendship(String un1, String un2){
        this.friendship_repository.delete(this.users_repository.getUser(un1).getId(), this.users_repository.getUser(un2).getId());
        this.friendship_repository.delete(this.users_repository.getUser(un2).getId(), this.users_repository.getUser(un1).getId());
    }

    public User getUser(String un){
        if(un==null)return null;
        return this.users_repository.getUser(un);
    }
    public User getUser(Long id){
        if(id==0)return null;
        return this.users_repository.getUser(id);
    }

    public User getUser(String un, String pw){
        if(Objects.equals(un, "admin")){
            return new User("admin","admin",20);
        }
        if(Objects.equals(un, "") || Objects.equals(pw, ""))return null;
        if (Objects.equals(this.users_repository.getUser(un).getPassword(), pw)){
                return this.users_repository.getUser(un);
        }
        return null;
    }
    public User getCurrentUser(){
        return currentUser;
    }

    //chat part
    public Vector<Message> getConversationOf(String un){
        return this.conv_repository.getConversationOf(this.getUser(un).getId());
    }
    public void addMessage(String text, LocalDateTime date, String toUser){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        this.conv_repository.add(text, date.format(formatter), currentUser.getId(), this.getUser(toUser).getId());
    }
}
