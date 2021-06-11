package Control;


import Answer.Request;
import CommandsOnServer.Executable;
import WorkerData.Worker;

public class CommandManager {

    private final Executable helpCommand;
    private final Executable addCommand;
    private final Executable addIfMaxCommand;
    private final Executable addIfMinCommand;
    private final Executable clearCommand;
    private final Executable exitCommand;
    private final Executable filterByStatusCommand;
    private final Executable infoCommand;
    private final Executable printFieldDescendingStatusCommand;
    private final Executable removeAllByPersonCommand;
    private final Executable removeByIdCommand;
    private final Executable removeHeadCommand;
    private final Executable saveCommand;
    private final Executable showCommand;
    private final Executable updateCommand;
    private final Executable signUpCommand;
    private final Executable signInCommand;

    public CommandManager(Executable helpCommand, Executable addCommand, Executable addIfMaxCommand, Executable addIfMinCommand,
                          Executable clearCommand, Executable exitCommand, Executable filterByStatusCommand,
                          Executable infoCommand, Executable printFieldDescendingStatusCommand, Executable removeAllByPersonCommand,
                          Executable removeByIdCommand, Executable removeHeadCommand, Executable saveCommand, Executable showCommand, Executable updateCommand,
                          Executable signUpCommand, Executable signInCommand) {
        this.helpCommand = helpCommand;
        this.addCommand = addCommand;
        this.addIfMaxCommand = addIfMaxCommand;
        this.addIfMinCommand = addIfMinCommand;
        this.clearCommand = clearCommand;
        this.exitCommand = exitCommand;
        this.filterByStatusCommand = filterByStatusCommand;
        this.infoCommand = infoCommand;
        this.printFieldDescendingStatusCommand = printFieldDescendingStatusCommand;
        this.removeAllByPersonCommand = removeAllByPersonCommand;
        this.removeByIdCommand = removeByIdCommand;
        this.removeHeadCommand = removeHeadCommand;
        this.saveCommand = saveCommand;
        this.showCommand = showCommand;
        this.updateCommand = updateCommand;
        this.signUpCommand = signUpCommand;
        this.signInCommand = signInCommand;
    }

    private String userCommand;
    private Object argument;
    private String currentUser;
    private String currentPassword;

    public void executeCommand(Request request){
        userCommand = request.getCommand();
        argument = request.getArg();
        currentUser = request.getCurrentUser();
        currentPassword = request.getCurrentPassword();

        switch (userCommand){
            case ("help"):
                help();
                break;
            case ("show"):
                show(currentUser, currentPassword);
                break;
            case ("info"):
                info(currentUser, currentPassword);
                break;
            case ("add"):
                add((Worker) argument, currentUser, currentPassword);
                break;
            case ("update"):
                update(argument, currentUser, currentPassword);
                break;
            case ("add_if_max"):
                addIfMax((Worker) argument, currentUser, currentPassword);
                break;
            case ("add_if_min"):
                addIfMin((Worker) argument, currentUser, currentPassword);
                break;
            case ("clear"):
                clear(currentUser, currentPassword);
                break;
            case ("filter_by_status"):
                filterByStatus(argument, currentUser, currentPassword);
                break;
            case ("print_field_descending_status"):
                printFieldDescendingStatus(currentUser, currentPassword);
                break;
            case ("remove_all_by_person"):
                removeAllByPerson(argument,currentUser, currentPassword);
                break;
            case ("remove_by_id"):
                removeById(argument,currentUser, currentPassword);
                break;
            case ("remove_head"):
                removeHead(currentUser, currentPassword);
                break;
            case ("exit"):
                exit();
                break;
            case ("sign_up"):
                signUp(argument);
                break;
            case ("sign_in"):
                signIn(argument);
                break;
        }

    }

    public void help(){
        helpCommand.execute("","","");
    }

    public void show(String currentUser, String currentPassword){
        showCommand.execute("",currentUser, currentPassword);
    }

    public void info(String currentUser, String currentPassword){
        infoCommand.execute("",currentUser,currentPassword);
    }

    public void add(Worker argument, String currentUser, String currentPassword){
        addCommand.execute(argument,currentUser,currentPassword);
    }

    public void addIfMax(Worker argument, String currentUser, String currentPassword){
        addIfMaxCommand.execute(argument,currentUser,currentPassword);
    }

    public void addIfMin(Worker argument, String currentUser, String currentPassword){
        addIfMinCommand.execute(argument,currentUser,currentPassword);
    }

    public void update(Object argument, String currentUser, String currentPassword){
        updateCommand.execute(argument,currentUser,currentPassword);
    }

    public void clear(String currentUser, String currentPassword){
        clearCommand.execute("",currentUser,currentPassword);
    }

    public void filterByStatus(Object argument, String currentUser, String currentPassword){
        filterByStatusCommand.execute(argument,"",currentPassword);
    }

    public void printFieldDescendingStatus(String currentUser, String currentPassword){
        printFieldDescendingStatusCommand.execute("","",currentPassword);
    }

    public void removeAllByPerson(Object argument,String currentUser, String currentPassword){
        removeAllByPersonCommand.execute(argument,currentUser,currentPassword);
    }

    public void removeById(Object argument, String currentUser, String currentPassword){
        removeByIdCommand.execute(argument,currentUser,currentPassword);
    }

    public void removeHead(String currentUser, String currentPassword){
        removeHeadCommand.execute("",currentUser,currentPassword);
    }

    public void save(){
        saveCommand.execute("","",currentPassword);
    }

    public void exit(){
        exitCommand.execute("","",currentPassword);
    }

    public void signUp(Object argument){signUpCommand.execute(argument,"",currentPassword);}

    public void signIn(Object argument){signInCommand.execute(argument,"",currentPassword);}

}
