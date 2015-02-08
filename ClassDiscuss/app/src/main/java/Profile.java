/**
 * Created by nhu on 15-02-07.
 */
public class Profile {
    private String name;
    private String email;


    private Profile(String Name, String Email) {
        this.name = Name;
        this.email = Email;
    }

    public String getUserName() {
        return this.name;
    }

    public String getUserEmail() {
        return this.email;
    }

    public void setUserName(String newName) {
        this.name = newName;
    }

    public void setUserEmail(String Email) {
        this.email = Email;
    }


    public String getUserNameFromEmail(String email) {
        return this.email;
    }

}