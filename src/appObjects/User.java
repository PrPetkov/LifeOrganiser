package appObjects;


import appObjects.accounts.Account;
import appObjects.tasks.TODOTask;
import appObjects.tasks.Task;

import java.util.List;

public class User {

    private String name;
    private String pass;
    private List<Task> tasks;
    private List<Account> accounts;
    private List<TODOTask> todos;

}
