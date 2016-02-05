package states;


import appObjects.User;
import appObjects.tasks.PayTask;
import appObjects.tasks.Task;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class PayStane extends TaskState {

    public PayStane(User user, PayTask payTask) {
        super(user, payTask);
    }
}
