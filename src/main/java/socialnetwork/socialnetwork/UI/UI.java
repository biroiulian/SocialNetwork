package socialnetwork.socialnetwork.UI;

import socialnetwork.socialnetwork.Domain.Friendship;
import socialnetwork.socialnetwork.Service.Service;
import socialnetwork.socialnetwork.Domain.User;

import java.time.LocalDateTime;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import java.util.Vector;

public class UI {
    private final Scanner sc = new Scanner(System.in);
    private Service service;

    public UI(Service serv) {
        this.service=serv;
    }

    /**
     * Prints a User calling the .toString() method
     * @param u Object of type User
     */
    private void printUser(User u){
        System.out.print(u.toString());
    }

    /**
     * Prints a list of vector
     * @param user_list a Vector of Objects of type User
     */
    private void printUserList(Vector<User> user_list){
        for(User user : user_list){
            this.printUser(user);
        }
    }

    private void printFriendshipList(Vector<Friendship> fr_list){
        for(Friendship f : fr_list){
            System.out.println(f);
        }
    }
    /**
     * Menu for adding a Friendship
     */

    private void addAFriendshipMenu() {
        this.printUserList(this.service.getUserList());
        System.out.println("Carui user ii adaugi prieten? (username) ");
        String u1 = sc.nextLine();
        System.out.println("Ce prieten ii adaugi? (username) ");
        String u2 = sc.nextLine();
        LocalDateTime localDT = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        // Formatting LocalDateTime to string
        //String dateTimeString = localDT.format(formatter);
        try {
            this.service.addFriendship(u1, u2, localDT);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Menu for adding Users
     */
    private void addUserMenu(){
        System.out.println("Username: ");
        String un = sc.nextLine();
        System.out.println("Password: ");
        String pw = sc.nextLine();
        System.out.println("Age: ");
        String a = sc.nextLine();
        try{
            this.service.addUser(un, pw, a);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Menu for deleting User
     */
    private void deleteUserMenu(){
        System.out.println(service.getUserList().size());
        this.printUserList(service.getUserList());
        System.out.println("Ce user vrei sa stergi? (username) ");
        Long un = Long.valueOf(sc.nextLine());
        this.service.deleteUser(un);
    }
    /**
     * Menu for deleting Friendship
     */
    private void deleteFriendshipMenu(){
        this.printFriendshipList(service.getFriendshipList());
        System.out.println("Ce prietenie vrei sa stergi? (username)");
        String un1 = sc.nextLine();
        System.out.println("(al doilea username) ");
        String un2 = sc.nextLine();
        this.service.deleteFriendship(un1, un2);
    }

    /**
     * Meniul principal
     */
    private void show_menu(){
        System.out.println("1.Adauga user");
        System.out.println("2.Sterge user");
        System.out.println("3.Adauga prietenie");
        System.out.println("4.Sterge prietenie");
        System.out.println("5. Iesi");
    }
    /**
     * Loop-ul propriu-zis al aplicatiei
     */
    public void start(){
        int menuChoice;
        while(true) {
            this.show_menu();
            try {
                menuChoice = Integer.parseInt(sc.nextLine());
            }

            catch(Exception e) {
                System.out.println("Introdu doar numarul corespunzator alegerii");
                menuChoice=-1;
            }

            if(menuChoice==1){
                this.addUserMenu();
            }
            if(menuChoice==2){
                this.deleteUserMenu();
            }
            if(menuChoice==3){
                this.addAFriendshipMenu();
            }
            if(menuChoice==4) {
                this.deleteFriendshipMenu();
            }
            if(menuChoice==5){
                return;
            }
        }
    }
}

