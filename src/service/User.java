package service;

public class User {
    private int id;
    private String name;
    private String mail;
    private String password;
    private Boolean voted;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getVoted() {
        return voted;
    }

    public void setVoted(Boolean voted) {
        this.voted = voted;
    }
}
