package states;


import appObjects.User;
import appObjects.tasks.Event;
import appObjects.tasks.Task;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class EventState extends TaskState {


    public EventState(User user, Event event) {
        super(user, event);
    }
}
