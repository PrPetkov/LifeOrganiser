package Controlers;


import Models.User;

public class LoginControler {

    public boolean logIn(String usserName, String password){
        if (User.isValidUser(usserName, password)){
            User.getUser().downloadInfoFromDb();

            return true;
        }

        return false;
    }

}
