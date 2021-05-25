package Answer;

import java.io.Serializable;

public class Request implements Serializable {

    private final String command;
    private Object arg;
    private String currentUser;

    public Request(String command) {
        this.command = command;
    }

    public Request(String command, Object arg) {
        this.command = command;
        this.arg = arg;
    }

    public String getCommand() {
        return command;
    }

    public Object getArg() {
        return arg;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public String getCurrentUser() {
        return currentUser;
    }
}
