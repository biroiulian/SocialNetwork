package socialnetwork.socialnetwork.Repository;

import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;
import java.util.Vector;

import socialnetwork.socialnetwork.Domain.Friendship;
import socialnetwork.socialnetwork.Domain.User;

public class UsersRepository implements FileRepository{

    private Vector<User> usersList;
    private String usersFilename;

    private void loadFromFile(){
        try {
            String line;
            String[] arrUserData;
            // Create f1 object of the file to read data
            File f1 = new File(this.usersFilename);
            Scanner dataReader = new Scanner(f1);
            while (dataReader.hasNextLine()) {
                //reading the data
                line=dataReader.nextLine();
                //split by ,
                arrUserData=line.split(",");
                this.usersList.add(new User(arrUserData[0], arrUserData[1], Integer.parseInt(arrUserData[2])));
            }
            //closing the stream
            dataReader.close();
        } catch (FileNotFoundException exception) {
            System.out.println("Unexcpected error occurred!");
            exception.printStackTrace();
        }
    }

    private void writeToFile() {
        try {
            FileWriter fwrite = new FileWriter(this.usersFilename);
            for(User u:usersList){
                fwrite.write(u.toString());
            }
            fwrite.close();

        } catch (IOException e) {
            System.out.println("Unexpected error occurred");
            e.printStackTrace();
        }
    }

    /**
     *
     * @param uFile Name of the file which stores data
     */
    public UsersRepository(String uFile) {
        this.usersFilename=uFile;
        this.usersList=new Vector<User>();
        this.loadFromFile();
    }

    /**
     *
     * @return Vector of objects of type users
     */
    public Vector<User> getUsersList(){
        return usersList;
    }

    /**
     * Adds user file and memory
     * @param u Object of type user
     */
    public void add(User u){
        this.usersList.add(u);
        this.writeToFile();
    }

    /**
     * Deletes user from file and memory
     * @param username name of User
     */
    public void delete(String username){
        for(User u:usersList){
            if(Objects.equals(u.getUsername(), username)){
                this.usersList.remove(u);
                break;
            }
        }
        this.writeToFile();
    }
}
