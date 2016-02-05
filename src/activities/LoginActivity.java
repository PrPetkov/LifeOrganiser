package activities;


import appObjects.User;

public class LoginActivity extends Activity{

    @Override
    public void onCreate() {
        //TODO add the buttons
        //TODO read the user input for username and password
        String username = null;
        String password = null;

        if (User.isValidUser(username, password)){
            //TODO download user data from database
            new HomeActivity();
        }else {
            this.onCreate();
        }
    }
}
