package socialnetwork.socialnetwork.Repository;

import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Scanner;
import java.util.Vector;

import socialnetwork.socialnetwork.Domain.Friendship;

public class FriendshipRepository implements FileRepository {

    private Vector<Friendship> friendshipList;
    private String friendshipsFilename;

    private void loadFromFile(){
        try {
            String line;
            String[] arrFriendshipData;
            // Create f1 object of the file to read data
            File f1 = new File(this.friendshipsFilename);
            Scanner dataReader = new Scanner(f1);
            while (dataReader.hasNextLine()) {
                //reading the data
                line=dataReader.nextLine();
                //split by ,
                arrFriendshipData=line.split(",");
                //conversie din string in LocalDateTime
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                LocalDateTime dateTime = LocalDateTime.parse(arrFriendshipData[2].replaceAll("\r", "").replaceAll("\n", ""), formatter);
                //conversie din string in LocalDateTime
                //this.friendshipList.add(new Friendship(Integer.parseInt(arrFriendshipData[0]), Integer.parseInt(arrFriendshipData[1]), dateTime));
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
            FileWriter fwrite = new FileWriter(this.friendshipsFilename);
            for(Friendship f:friendshipList){
                //separator is ","
                fwrite.write(f.toString());
            }
            fwrite.close();

        } catch (IOException e) {
            System.out.println("Unexpected error occurred");
            e.printStackTrace();
        }
    }

    /**
     * Constructor
     * @param fFile name of the file to store
     */
    public FriendshipRepository(String fFile) {
        this.friendshipsFilename=fFile;
        this.friendshipList= new Vector<Friendship>();
        this.loadFromFile();
    }

    /**
     *
     * @return Vector of objects of type friendship
     */
    public Vector<Friendship> getFriendshipList(){
        return friendshipList;
    }

    /**
     * adds friendship to the file and memory
     * @param f Object of type friendship
     */
    public void add(Friendship f){
        this.friendshipList.add(f);
        this.writeToFile();
    }

    /**
     * deletes friendship from file and memory
     * @param u1 username of first user
     * @param u2 username of second user
     */
    public void delete(String u1, String u2){
        for(Friendship f:friendshipList){
            if((Objects.equals(f.getId1(), u1) && Objects.equals(f.getId2(), u2)) || (Objects.equals(f.getId1(), u2) && Objects.equals(f.getId2(), u1))){
                this.friendshipList.remove(f);
                break;
            }
        }
        writeToFile();
    }
}
