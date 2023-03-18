package socialnetwork.socialnetwork;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import socialnetwork.socialnetwork.Domain.FriendshipValidator;
import socialnetwork.socialnetwork.Domain.UserValidator;
import socialnetwork.socialnetwork.Repository.ConversationDbRepository;
import socialnetwork.socialnetwork.Repository.FriendshipDbRepository;
import socialnetwork.socialnetwork.Repository.UsersDbRepository;
import socialnetwork.socialnetwork.Service.Service;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        UsersDbRepository usersRepo = new UsersDbRepository("jdbc:postgresql://localhost:5432/social","postgres", "Biro88888888");
        FriendshipDbRepository friendshipRepo = new FriendshipDbRepository("jdbc:postgresql://localhost:5432/social","postgres", "Biro88888888");
        ConversationDbRepository convRepo = new ConversationDbRepository("jdbc:postgresql://localhost:5432/social","postgres", "Biro88888888");
        UserValidator uVal= new UserValidator();
        FriendshipValidator fVal = new FriendshipValidator();
        Service service = new Service(usersRepo, friendshipRepo, convRepo, uVal, fVal);
        FXMLLoader fxmlLoaderLogin = new FXMLLoader(Application.class.getResource("LoginScene.fxml"));
        Parent root = fxmlLoaderLogin.load();
        LoginSceneController loginSceneCont=fxmlLoaderLogin.getController();
        loginSceneCont.setService(service);
        Scene LoginScene = new Scene(root, 640, 450);
        stage.setTitle("Live Up!");
        stage.setScene(LoginScene);
        stage.setMinHeight(570);
        stage.setMinWidth(640);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}