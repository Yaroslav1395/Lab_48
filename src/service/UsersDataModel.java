package service;

import java.util.List;

public class UsersDataModel {
    private List<User> users;


    public UsersDataModel() {

    }
    public UsersDataModel(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
