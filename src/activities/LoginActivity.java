package activities;


import Controlers.LoginControler;
import Models.User;

public class LoginActivity extends Activity{

    LoginControler controler = new LoginControler();

    @Override
    public void onCreate() {

        //TODO add the buttons
        //TODO read the user input for username and password
        String username = null;
        String password = null;

        if (controler.logIn(username, password)){
            new HomeActivity();
        }else {
            this.onCreate();
        }
    }
}
