package il.cshaifasweng.OCSFMediatorExample.entities;

import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="Userss")
public class User implements Serializable {
    private static final long serialVersionUID = -8224097662914849956L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int Id_;
    String userName;
    String password;
    String firstName;
    String lastName;
    int permission;
    boolean isConnected;

    public User(){}

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public User(String userName, String password, String firstName, String lastName, int permission) {
        this.userName = userName;
        this.password = hashPassword(password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.permission = permission;
        this.isConnected = false;
    }

    public int getId_() {
        return Id_;
    }

    public void setId_(int id_) {
        Id_ = id_;
    }

    public String getUserName() {
        return userName;
    }

    public boolean getConnected()
    {
        return this.isConnected;
    }

    public boolean checkPassword(String plainPassword) {
        if (BCrypt.checkpw(plainPassword, this.password))
            return true;

        return false;
    }

    public void setPassword(String password) {
        this.password = hashPassword(password);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getPermission() {
        return permission;
    }

    private String hashPassword(String plainTextPassword){
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }
}