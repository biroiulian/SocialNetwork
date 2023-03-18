package socialnetwork.socialnetwork;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import socialnetwork.socialnetwork.Domain.InvalidFriendshipFormDataException;
import socialnetwork.socialnetwork.Service.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;
import java.util.Vector;

public class HomeSceneController {
    private Service serv;
    public Button LogOutButton;
    public Label UsernameLabel;
    public Circle statusCircle;
    public ListView<String> FriendListView;
    public ListView<String> FriendReqListView;
    public TextField UsernameFriendReqTextField;
    public Label FriendRequestSentLabel;
    public Button DeleteSentFriendReqButton;
    public ListView<String> SentFriendReqListView;
    //chat part
    public Button SendMessageButton;
    public Button OpenConvButton;
    public Label FriendConvLabel;
    public ListView<String> ChatListView;
    public TextField MessageTextField;
    //chat part
    public Label ScoreLabel;
    public Circle Circle1;
    public Circle Circle2;
    public Circle Circle3;
    public Circle Circle4;
    public Circle Circle5;
    public Circle Circle6;
    public Circle Circle7;
    public Circle Circle8;
    public Circle Circle9;
    public Random random;
    private Vector<Circle> circles;

    public HomeSceneController(){
        System.out.println("Initialized Controller...");
    }

    public void initializeComponents() throws IOException {
        UsernameLabel.setText(serv.getCurrentUser().getUsername());
        statusCircle.setFill(Paint.valueOf("#09a444"));
        initializeFriendList();
        initializeFriendReqList();
        initializeSentFriendReqList();

        initializeCircles();
        Timeline threeSecondsWonder = new Timeline(
                new KeyFrame(Duration.seconds(6),
                        new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent event) {
                                if(checkCirclesSameColor()){
                                    int score = Integer.parseInt(ScoreLabel.getText());
                                    ScoreLabel.setText(Integer.toString(score+1));
                                }
                                randomizeCircles();
                            }
                        }));
        threeSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        threeSecondsWonder.play();

    }
    private void initializeCircles(){
        circles=new Vector<>();
        circles.add(Circle1);
        circles.add(Circle2);
        circles.add(Circle3);
        circles.add(Circle4);
        circles.add(Circle5);
        circles.add(Circle6);
        circles.add(Circle7);
        circles.add(Circle8);
        circles.add(Circle9);
        random=new Random();
    }

    private void initializeFriendReqList(){
        FriendReqListView.getItems().clear();
        FriendReqListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        serv.getFriendReqOfUser(serv.getCurrentUser().getId()).forEach(e->
        {
            FriendReqListView.getItems().add(e.getUser1().getUsername().toUpperCase());
        });
    }
    private void initializeFriendList(){
        FriendListView.getItems().clear();
        FriendListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        serv.getFriendsOfUser(serv.getCurrentUser().getId()).forEach(e-> {
            FriendListView.getItems().add(e.getUsername());
        });
    }
    private void initializeSentFriendReqList(){
        SentFriendReqListView.getItems().clear();
        SentFriendReqListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        serv.getSentReqOfUser(serv.getCurrentUser().getId()).forEach(e->
        {
            SentFriendReqListView.getItems().add(e.getUser2().getUsername());
        });
    }
    public void onLogOutButtonPressed() throws IOException {
        System.out.println("Log Out Clicked");
        FXMLLoader fxmlLoaderLogin = new FXMLLoader(Application.class.getResource("LoginScene.fxml"));
        Parent root = fxmlLoaderLogin.load();
        LoginSceneController logSceneCont = fxmlLoaderLogin.getController();
        logSceneCont.setService(serv);
        Scene LoginScene = new Scene(root, 640, 570);
        Stage Window = (Stage)LogOutButton.getScene().getWindow();
        Window.setScene(LoginScene);
        System.out.println("Scene changed to Login");
    }
    public void onSendFriendReqButtonPressed() throws IOException, InvalidFriendshipFormDataException {
        String un = UsernameFriendReqTextField.getText();
        serv.addFriendship(serv.getCurrentUser().getUsername(), un, LocalDateTime.now());
        UsernameFriendReqTextField.setText("");
        FriendRequestSentLabel.setText("Friend request sent");
        initializeSentFriendReqList();
    }
    public void onAcceptFriendReqButtonPressed(){
        String friendReqUser = FriendReqListView.getSelectionModel().getSelectedItem().toLowerCase();
        serv.acceptFriendRequest(friendReqUser, serv.getCurrentUser().getUsername());
        initializeFriendReqList();
        initializeFriendList();
    }
    public void onDeleteFriendButtonPressed(){
        String friendUser = FriendListView.getSelectionModel().getSelectedItem();
        serv.deleteFriendship(friendUser, serv.getCurrentUser().getUsername());
        initializeFriendList();
    }
    public void onDeleteFriendReqButtonPressed(){
        String friendReqUser = FriendReqListView.getSelectionModel().getSelectedItem().toLowerCase();
        serv.deleteFriendship(friendReqUser, serv.getCurrentUser().getUsername());
        initializeFriendReqList();
        initializeFriendList();
    }
    public void onStatusCirclePressed(){
        System.out.println("CirclePressed, color: " + statusCircle.getFill().toString());
        if(Objects.equals(statusCircle.getFill().toString(), "0x09a444ff")){
            statusCircle.setFill(Paint.valueOf("#e2ef2a"));
        }
        else if(Objects.equals(statusCircle.getFill().toString(), "0xe2ef2aff")){
            statusCircle.setFill(Paint.valueOf("#f20000"));
        }
        else
        {
            statusCircle.setFill(Paint.valueOf("#09a444"));
        }
    }

    public void setService(Service service) {
        this.serv=service;
    }

    public void randomizeCircles(){
        String green="0x09a444ff";
        String yellow="0xe2ef2aff";
        String red="0xf20000ff";
        circles.forEach(e->{
            if(random.nextInt(3)==0)e.setFill(Paint.valueOf(red));
            if(random.nextInt(3)==1)e.setFill(Paint.valueOf(green));
            if(random.nextInt(3)==2)e.setFill(Paint.valueOf(yellow));
        });

    }
    public boolean checkCirclesSameColor(){
        String color=circles.elementAt(0).getFill().toString();
        for(Circle c:circles){
            if(!Objects.equals(c.getFill().toString(), color)){
                return false;
            }
        }
        return true;
    }
    public void onGameCirclePressed(MouseEvent e){
        System.out.println("CirclePressed, color: " + statusCircle.getFill().toString());
        try {
            if (Objects.equals(((Circle)e.getSource()).getFill().toString(), "0x09a444ff")) {
                ((Circle)e.getTarget()).setFill(Paint.valueOf("#e2ef2a"));
            } else if (Objects.equals(((Circle)e.getTarget()).getFill().toString(), "0xe2ef2aff")) {
                ((Circle)e.getTarget()).setFill(Paint.valueOf("#f20000"));
            } else {
                ((Circle)e.getTarget()).setFill(Paint.valueOf("#09a444"));
            }
        }
        catch(Exception ec){
            ec.printStackTrace();
        }
    }
    public void onDeleteSentFriendReqButtonPressed() {
        String friendReqUser = SentFriendReqListView.getSelectionModel().getSelectedItem().toLowerCase();
        serv.deleteFriendship(friendReqUser, serv.getCurrentUser().getUsername());
        initializeSentFriendReqList();
    }

    //chat part
    private void reloadChat(){
        String friendUser=FriendConvLabel.getText();
        ChatListView.getItems().clear();
        try {
            System.out.println("CONVERSATIA CU " + friendUser + "ESTE DESCHISA");
            serv.getConversationOf(friendUser).forEach(e -> {
                ChatListView.getItems().add(
                        "[" + e.getDateSent().toString() + "] " + this.serv.getUser(e.getSender()).getUsername() + ": " + e.getText());
            });
        }
        catch (NullPointerException e){
            System.out.println("Nu sunt mesaje de afisat pentru conversatia asta");
        }
    }
    public void onSendMessageButtonPressed(){
        serv.addMessage(MessageTextField.getText(), LocalDateTime.now(), FriendConvLabel.getText());
        this.reloadChat();
    }
    public void onOpenConvButtonPressed(){
        //label change to name of user to chat
        String friendUser = FriendListView.getSelectionModel().getSelectedItem();
        this.FriendConvLabel.setText(friendUser);
        //messages loading
        ChatListView.getItems().clear();
        try {
            serv.getConversationOf(friendUser).forEach(e -> {
                ChatListView.getItems().add(
                        "[" + e.getDateSent() + "] " + this.serv.getUser(e.getSender()).getUsername() + ": " + e.getText());
            });
        }
        catch (NullPointerException e){
            System.out.println("Nu sunt mesaje de afisat pentru conversatia asta");
        }
        //messages loading

    }
}
