package states;


import Interfaces.IState;
import appObjects.User;

public abstract class UserState implements IState {

    private User user;

    public UserState(User user) {

        this.setUser(user);

    }

    protected User getUser() {
        return user;
    }

    protected void setUser(User user) {
        this.user = user;
    }

    @Override
    public abstract void draw();
}
