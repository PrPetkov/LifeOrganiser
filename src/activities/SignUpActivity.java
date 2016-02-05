package activities;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends Activity{

    @Override
    public void onCreate() {
        //TODO add buttons
        String email = null;
        String username = null;
        String password = null;
        String repeatPassword = null;

        if(this.isCorrectInput(email, username, password, repeatPassword)){
            new HomeActivity();
        }
    }

    private boolean isCorrectInput(String email, String username, String password, String repeatPassword) {
        return this.isCorrectEmail(email) && this.checkForUsername(username) && this.repeatPasswordMatches(password, repeatPassword);
    }

    private boolean isCorrectEmail(String email){
        Pattern pattern = Pattern.compile("/^(([a-zA-Z]|[0-9])|([-]|[_]|[.]))+[@](([a-zA-Z0-9])|([-])){2,63}[.](([a-zA-Z0-9]){2,63})+$/");
        Matcher matcher = pattern.matcher(email);
        //TODO throw error in case of mistake
        return matcher.find();
    }

    private boolean checkForUsername(String username){
        //TODO check database for repetition
        return true;
    }

    private boolean repeatPasswordMatches(String password, String repeatPassword){
        return password.equals(repeatPassword);
    }
}
