package Models;


import Interfaces.IDatabase;

public class Database implements IDatabase {

    private User user;

    public Database(User user) {
        this.user = user;
    }

    public void downloadUserData(){
        //TODO;
    }

    public void uploadUserData(){
        //TODO
    }
}
