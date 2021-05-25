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

    public void executeCommand(Request request){
        userCommand = request.getCommand();
        argument = request.getArg();
        currentUser = request.getCurrentUser();

        switch (userCommand){
            case ("help"):
                help();
                break;
            case ("show"):
                show();
                break;
            case ("info"):
                info();
                break;
            case ("add"):
                add((Worker) argument, currentUser);
                break;
            case ("update"):
                update(argument, currentUser);
                break;
            case ("add_if_max"):
                addIfMax((Worker) argument, currentUser);
                break;
            case ("add_if_min"):
                addIfMin((Worker) argument, currentUser);
                break;
            case ("clear"):
                clear(currentUser);
                break;
            case ("filter_by_status"):
                filterByStatus(argument);
                break;
            case ("print_field_descending_status"):
                printFieldDescendingStatus();
                break;
            case ("remove_all_by_person"):
                removeAllByPerson(argument,currentUser);
                break;
            case ("remove_by_id"):
                removeById(argument,currentUser);
                break;
            case ("remove_head"):
                removeHead(currentUser);
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
        helpCommand.execute("","");
    }

    public void show(){
        showCommand.execute("","");
    }

    public void info(){
        infoCommand.execute("","");
    }

    public void add(Worker argument, String currentUser){
        addCommand.execute(argument,currentUser);
    }

    public void addIfMax(Worker argument, String currentUser){
        addIfMaxCommand.execute(argument,currentUser);
    }

    public void addIfMin(Worker argument, String currentUser){
        addIfMinCommand.execute(argument,currentUser);
    }

    public void update(Object argument, String currentUser){
        updateCommand.execute(argument,currentUser);
    }

    public void clear(String currentUser){
        clearCommand.execute("",currentUser);
    }

    public void filterByStatus(Object argument){
        filterByStatusCommand.execute(argument,"");
    }

    public void printFieldDescendingStatus(){
        printFieldDescendingStatusCommand.execute("","");
    }

    public void removeAllByPerson(Object argument,String currentUser){
        removeAllByPersonCommand.execute(argument,currentUser);
    }

    public void removeById(Object argument, String currentUser){
        removeByIdCommand.execute(argument,currentUser);
    }

    public void removeHead(String currentUser){
        removeHeadCommand.execute("",currentUser);
    }

    public void save(){
        saveCommand.execute("","");
    }

    public void exit(){
        exitCommand.execute("","");
    }

    public void signUp(Object argument){signUpCommand.execute(argument,"");}

    public void signIn(Object argument){signInCommand.execute(argument,"");}

}
