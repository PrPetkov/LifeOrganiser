package Models.user;

//import com.example.lifeorganiser.src.Models.dataBase.DBAdapter;

import Models.Interfaces.IDBManager;

public class UserManager {

    private User user;
    private static UserManager manager;
    private IDBManager dbManager;

    private UserManager() {
//        this.dbAdapter = new DBAdapter();
    }

    public static UserManager getInstance(){
        if (UserManager.manager == null){
            UserManager.manager = new UserManager();
        }

        return UserManager.manager;
    }

    public void logUser(){
        //TODO
    }
}
