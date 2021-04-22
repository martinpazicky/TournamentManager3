package tm.model;

import java.io.Serializable;

public class Participant implements Serializable {
    private String nickName;
    private String firstName;
    private String lastName;
    private int age;


    public Participant(){
    }

    public Participant(String nickName) {
        this.nickName = nickName;
    }

    public String toString(){
        return getNickName();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
