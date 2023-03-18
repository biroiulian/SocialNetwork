package socialnetwork.socialnetwork.Domain;

public class User {
    private String username;
    private String password;
    private int age;
    private String description;
    private Long Id;


    public User(String username, String password, int age) {
        this.username = username;
        this.password = password;
        this.age = age;
    }
    public User(Long id, String username, String password, int age) {
        this.Id=id;
        this.username = username;
        this.password = password;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return Id +"   "+ username + ", " + password + ", " + age+",\n";
    }

    public void setId(Long id) {
        this.Id=id;
    }
    public Long getId() {return Id;
    }
}
