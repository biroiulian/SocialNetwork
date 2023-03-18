package socialnetwork.socialnetwork;

import socialnetwork.socialnetwork.Domain.FriendshipValidator;
import socialnetwork.socialnetwork.Domain.UserValidator;
import socialnetwork.socialnetwork.Repository.*;
import socialnetwork.socialnetwork.Service.Service;
import socialnetwork.socialnetwork.UI.UI;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        UsersDbRepository usersRepo = new UsersDbRepository("jdbc:postgresql://localhost:5432/social","postgres", "Biro88888888");
        FriendshipDbRepository friendshipRepo = new FriendshipDbRepository("jdbc:postgresql://localhost:5432/social","postgres", "Biro88888888");
        ConversationDbRepository convRepo = new ConversationDbRepository("jdbc:postgresql://localhost:5432/social","postgres", "Biro88888888");
        UserValidator uVal= new UserValidator();
        FriendshipValidator fVal = new FriendshipValidator();
        Service service = new Service(usersRepo, friendshipRepo, convRepo, uVal, fVal);
        UI ui=new UI(service);
        ui.start();
    }
}