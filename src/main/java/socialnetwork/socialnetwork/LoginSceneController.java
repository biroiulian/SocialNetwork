package socialnetwork.socialnetwork;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import socialnetwork.socialnetwork.Domain.User;
import socialnetwork.socialnetwork.Service.Service;
import socialnetwork.socialnetwork.Domain.FriendshipValidator;
import socialnetwork.socialnetwork.Domain.UserValidator;
import socialnetwork.socialnetwork.Repository.FriendshipDbRepository;
import socialnetwork.socialnetwork.Repository.UsersDbRepository;

import java.io.IOException;

public class LoginSceneController {
    public Button LoginButton;
    public Button RegisterButton;
    public TextField UsernameTextField;
    public PasswordField PwPasswordField;
    public Label WelcomeLabel;
    public Label LoginStatusLabel;
    private Service serv;


    public LoginSceneController(){
        try {
            this.initializeComponents();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Initialized Controller...");
    }

    private void initializeComponents() throws IOException {

    }
    public void onLoginButtonClicked() throws IOException {
        System.out.println("Login Clicked");
        User u = this.serv.getUser(UsernameTextField.getText(), PwPasswordField.getText());
        if(u==null) {
            LoginStatusLabel.setText("The Username-Password combination does not exist");
            return;
        }
        this.serv.setCurrentUser(u);
        //proceed to changing the scene
        FXMLLoader fxmlLoaderLogin = new FXMLLoader(Application.class.getResource("HomeScene.fxml"));
        Parent root = fxmlLoaderLogin.load();
        HomeSceneController homeSceneCont = fxmlLoaderLogin.getController();
        homeSceneCont.setService(serv);
        homeSceneCont.initializeComponents();
        Scene HomeScene = new Scene(root, 640, 560);
        Stage Window = (Stage)LoginButton.getScene().getWindow();
        Window.setScene(HomeScene);
        //scene changed
        System.out.println("Scene changed to Main");
    }


    public void onRegisterButtonClicked() throws IOException {
        System.out.println("Register Clicked");
        //
        FXMLLoader fxmlLoaderRegister = new FXMLLoader(Application.class.getResource("RegisterScene.fxml"));
        Parent root = fxmlLoaderRegister.load();
        RegisterSceneController regSceneCont = fxmlLoaderRegister.getController();
        regSceneCont.setService(serv);
        Scene RegisterScene = new Scene(root, 640, 560);
        Stage Window = (Stage)RegisterButton.getScene().getWindow();
        Window.setScene(RegisterScene);
        //
        System.out.println("Scene changed to Registration");
    }

    public void setService(Service service) {
        this.serv=service;
    }
}