package socialnetwork.socialnetwork;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import socialnetwork.socialnetwork.Service.Service;

import java.io.IOException;

public class RegisterSceneController {
    public Button BackToLoginButton;
    public PasswordField RegisterPwPasswordField;
    public TextField RegisterUsernameTextField;

    private Service serv;

    public RegisterSceneController(){
        try {
            this.initializeComponents();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Initialized Controller...");
    }

    private void initializeComponents() throws IOException {

    }

    public void onBackToLoginButtonClicked() throws IOException {
        System.out.println("Back to Login Clicked");
        FXMLLoader fxmlLoaderLogin = new FXMLLoader(Application.class.getResource("LoginScene.fxml"));
        Parent root = fxmlLoaderLogin.load();
        LoginSceneController logSceneCont = fxmlLoaderLogin.getController();
        logSceneCont.setService(serv);
        Scene LoginScene = new Scene(root, 640, 450);
        Stage Window = (Stage)BackToLoginButton.getScene().getWindow();
        Window.setScene(LoginScene);
        System.out.println("Scene changed to Login");
    }
    public void setService(Service service) {
        this.serv=service;
    }
}
