package states;


import appObjects.User;
import appObjects.tasks.Task;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class RecieveMoneyState extends TaskState {


    public RecieveMoneyState(User user, Task recieveMoneyTask) {
        super(user, recieveMoneyTask);
    }

    @Override
    public void draw() {
        throw new NotImplementedException();
    }

}
