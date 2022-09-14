package service;

public class UserService {
    private UsersDataModel users = new FileService().readJsonUser("users.json");

    public UsersDataModel getUsersDataModel() {
        return users;
    }

    public boolean checkCookieId(String id){
        boolean isCookie = false;
        for (int i = 0; i < users.getUsers().size(); i++){
            if(users.getUsers().get(i).getId() == Integer.parseInt(id)){
                isCookie = true;
            }
        }
        return isCookie;
    }
}
