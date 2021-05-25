package Control;

import Answer.AnswerStatus;
import Answer.Request;
import Exceptions.UnauthorizedAcces;
import Exceptions.WrongArgumentException;
import Exceptions.WrongFieldException;
import WorkerData.*;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.HashMap;

public class CommandManager {

    private final HashMap<String, Convertible<Boolean,String>> commandsMap = new HashMap<>();
    private final Sender sender;
    private final Receiver receiver;
    private final UserDataReceiver userDataReceiver;
    private String currentUser = null;


    public CommandManager(Sender sender, Receiver receiver, UserDataReceiver userDataReceiver) {

        this.sender = sender;

        this.receiver = receiver;

        this.userDataReceiver = userDataReceiver;

        commandsMap.put("help",this::help);

        commandsMap.put("add",this::add);

        commandsMap.put("add_if_max",this::addIfMax);

        commandsMap.put("add_if_min",this::addIfMin);

        commandsMap.put("clear",this::clear);

        commandsMap.put("exit",this::exit);

        commandsMap.put("execute_script",this::executeScript);

        commandsMap.put("filter_by_status",this::filterByStatusCommand);

        commandsMap.put("info",this::info);

        commandsMap.put("print_field_descending_status",this::printFieldDescendingStatus);

        commandsMap.put("remove_all_by_person",this::removeAllByPerson);

        commandsMap.put("remove_by_id",this::removeById);

        commandsMap.put("remove_head",this::removeHead);

        commandsMap.put("save",this::save);

        commandsMap.put("show",this::show);

        commandsMap.put("update",this::update);

        commandsMap.put("sign_up",this::signUp);

        commandsMap.put("sign_in",this::signIn);
    }

    /**
     *
     * Возвращает мапу с командами
     * @return мапу (ключ - команда, значение - ссылка на метод)
     */
    public HashMap<String, Convertible<Boolean, String>> getCommandsMap() {
        return commandsMap;
    }

    /**
     * Запускает выполнение команды
     * @param argument аргумент команды
     * @return статус выполнения команды
     */
    public boolean help(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongArgumentException();

            Request request = new Request("help");
            sender.sendCommand(request);
            receiver.receive();

            return true;
        } catch (WrongArgumentException e){
            Outputer.printError("Для этой комманды не нужен аргумент, попробуйте ещё раз");
        } catch (IOException e){
            Outputer.printError("Непредвиденная ошибка");
        }
        return false;
    }

    /**
     * Запускает выполнение команды
     * @param argument аргумент команды
     * @return статус выполнения команды
     */
    public boolean add(String argument) {

        try {
            if (!argument.isEmpty()) throw new WrongArgumentException();

            if (currentUser == null) throw new UnauthorizedAcces();


            Request request = new Request("add", new Worker(0, userDataReceiver.askName(), userDataReceiver.askCoordinates(), ZonedDateTime.now(), userDataReceiver.askSalary(), userDataReceiver.askPosition(), userDataReceiver.askStatus(), userDataReceiver.askPerson()));
            request.setCurrentUser(currentUser);
            sender.sendCommand(request);
            receiver.receive();

            return true;
        } catch (WrongArgumentException e){
            Outputer.printError("Для этой комманды не нужен аргумент, попробуйте ещё раз");
        } catch (IOException e){
            Outputer.printError("Непредвиденная ошибка");
        } catch (UnauthorizedAcces e){
            Outputer.printError("Вы должны авторизироваться для выполнения данной команды");
        }
        return false;
    }

    /**
     * Запускает выполнение команды
     * @param argument аргумент команды
     * @return статус выполнения команды
     */
    public boolean addIfMax(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongArgumentException();

            if (currentUser == null) throw new UnauthorizedAcces();

            Request request = new Request("add_if_max", new Worker(0, userDataReceiver.askName(), userDataReceiver.askCoordinates(), ZonedDateTime.now(), userDataReceiver.askSalary(), userDataReceiver.askPosition(), userDataReceiver.askStatus(), userDataReceiver.askPerson()));
            request.setCurrentUser(currentUser);
            sender.sendCommand(request);
            receiver.receive();

            return true;
        } catch (WrongArgumentException e){
            Outputer.printError("Для этой комманды не нужен аргумент, попробуйте ещё раз");
        } catch (IOException e){
            Outputer.printError("Непредвиденная ошибка");
        } catch (UnauthorizedAcces e){
            Outputer.printError("Вы должны авторизироваться для выполнения данной команды");
        }
        return false;
    }

    /**
     * Запускает выполнение команды
     * @param argument аргумент команды
     * @return статус выполнения команды
     */
    public boolean addIfMin(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongArgumentException();

            if (currentUser == null) throw new UnauthorizedAcces();

            Request request = new Request("add_if_min", new Worker(0, userDataReceiver.askName(), userDataReceiver.askCoordinates(), ZonedDateTime.now(), userDataReceiver.askSalary(), userDataReceiver.askPosition(), userDataReceiver.askStatus(), userDataReceiver.askPerson()));
            request.setCurrentUser(currentUser);
            sender.sendCommand(request);
            receiver.receive();

            return true;
        } catch (WrongArgumentException e){
            Outputer.printError("Для этой комманды не нужен аргумент, попробуйте ещё раз");
        } catch (IOException e){
            Outputer.printError("Непредвиденная ошибка");
        } catch (UnauthorizedAcces e){
            Outputer.printError("Вы должны авторизироваться для выполнения данной команды");
        }
        return false;
    }

    /**
     * Запускает выполнение команды
     * @param argument аргумент команды
     * @return статус выполнения команды
     */
    public boolean clear(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongArgumentException();

            if (currentUser == null) throw new UnauthorizedAcces();

            Request request = new Request("clear");
            request.setCurrentUser(currentUser);
            sender.sendCommand(request);
            receiver.receive();

            return true;
        } catch (WrongArgumentException e){
            Outputer.printError("Для этой комманды не нужен аргумент, попробуйте ещё раз");
        } catch (IOException e){
            Outputer.printError("Непредвиденная ошибка");
        } catch (UnauthorizedAcces e){
            Outputer.printError("Вы должны авторизироваться для выполнения данной команды");
        }
        return false;
    }

    /**
     * Запускает выполнение команды
     * @param argument аргумент команды
     * @return статус выполнения команды
     */
    public boolean executeScript(String argument) {

        try {
            argument = argument.trim();
            if (argument.isEmpty()) throw new WrongArgumentException();
            Outputer.println("Выполняю скрипт '" + argument + "'...");
            return true;
        } catch (WrongArgumentException e) {
            Outputer.printError("Укажите файл со скриптом в качестве аргумента.");
        }
        return false;
    }

    public boolean exit(String argument) {
        return true;
    }

    /**
     * Запускает выполнение команды
     * @param argument аргумент команды
     * @return статус выполнения команды
     */
    public boolean info(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongArgumentException();

            Request request = new Request("info");
            sender.sendCommand(request);
            receiver.receive();

            return true;
        } catch (WrongArgumentException e){
            Outputer.printError("Для этой комманды не нужен аргумент, попробуйте ещё раз");
        } catch (IOException e){
            Outputer.printError("Непредвиденная ошибка");
        }
        return false;
    }

    /**
     * Запускает выполнение команды
     * @param argument аргумент команды
     * @return статус выполнения команды
     */
    public boolean printFieldDescendingStatus(String argument) {

        try {

            if (!argument.isEmpty()) throw new WrongArgumentException();


            Request request = new Request("print_field_descending_status");
            sender.sendCommand(request);
            receiver.receive();

            return true;
        } catch (WrongArgumentException e){
            Outputer.printError("Для этой комманды не нужен аргумент, попробуйте ещё раз");
        } catch (IOException e){
            Outputer.printError("Непредвиденная ошибка");
        }
        return false;
    }

    /**
     * Запускает выполнение команды
     * @param argument аргумент команды
     * @return статус выполнения команды
     */
    public boolean removeAllByPerson(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongArgumentException();

            if (currentUser == null) throw new UnauthorizedAcces();

            Request request = new Request("remove_all_by_person", userDataReceiver.askPerson());
            request.setCurrentUser(currentUser);
            sender.sendCommand(request);
            receiver.receive();

            return true;
        } catch (WrongArgumentException e){
            Outputer.printError("Для этой комманды не нужен аргумент, попробуйте ещё раз");
        } catch (IOException e){
            Outputer.printError("Непредвиденная ошибка");
        } catch (UnauthorizedAcces e){
            Outputer.printError("Вы должны авторизироваться для выполнения данной команды");
        }
        return false;
    }

    /**
     * Запускает выполнение команды
     * @param argument аргумент команды
     * @return статус выполнения команды
     */
    public boolean removeById(String argument) {
        try {
            argument = argument.trim();
            if (argument.isEmpty()) throw new WrongArgumentException();

            if (currentUser == null) throw new UnauthorizedAcces();

            Request request = new Request("remove_by_id", argument);
            request.setCurrentUser(currentUser);
            sender.sendCommand(request);
            receiver.receive();

            return true;
        } catch (WrongArgumentException e){
            Outputer.printError("Введите ID!");
        } catch (IOException e){
            Outputer.printError("Непредвиденная ошибка");
        } catch (UnauthorizedAcces e){
            Outputer.printError("Вы должны авторизироваться для выполнения данной команды");
        }
        return false;
    }

    /**
     * Запускает выполнение команды
     * @param argument аргумент команды
     * @return статус выполнения команды
     */
    public boolean removeHead(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongArgumentException();

            if (currentUser == null) throw new UnauthorizedAcces();

            Request request = new Request("remove_head");
            request.setCurrentUser(currentUser);
            sender.sendCommand(request);
            receiver.receive();

            return true;
        } catch (WrongArgumentException e){
            Outputer.printError("Для этой комманды не нужен аргумент, попробуйте ещё раз");
        } catch (IOException e){
            Outputer.printError("Непредвиденная ошибка");
        } catch (UnauthorizedAcces e){
            Outputer.printError("Вы должны авторизироваться для выполнения данной команды");
        }
        return false;
    }

    /**
     * Запускает выполнение команды
     * @param argument аргумент команды
     * @return статус выполнения команды
     */
    public boolean save(String argument) {
        return true;
    }

    /**
     * Запускает выполнение команды
     * @param argument аргумент команды
     * @return статус выполнения команды
     */
    public boolean show(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongArgumentException();

            Request request = new Request("show");
            sender.sendCommand(request);
            receiver.receive();

            return true;
        } catch (WrongArgumentException e){
            Outputer.printError("Для этой комманды не нужен аргумент, попробуйте ещё раз");
        } catch (IOException e){
            Outputer.printError("Непредвиденная ошибка");
        }
        return false;
    }

    /**
     * Запускает выполнение команды
     * @param argument аргумент команды
     * @return статус выполнения команды
     */
    public boolean update(String argument) {
        try {
            argument = argument.trim();
            if (argument.isEmpty()) throw new WrongArgumentException();
            long id = Long.parseLong(argument);

            if (currentUser == null) throw new UnauthorizedAcces();

            Request request = new Request("update", argument);
            request.setCurrentUser(currentUser);
            sender.sendCommand(request);
            receiver.receive();

            if (receiver.getAnswer().getAnswerStatus().equals(AnswerStatus.ERROR)) return false;

            Worker oldWorker = (Worker) receiver.getAnswer().getObjectAnswer();


            String name = oldWorker.getName();
            Coordinates coordinates = oldWorker.getCoordinates();
            ZonedDateTime creationDate = oldWorker.getCreationDate();
            double salary = oldWorker.getSalary();
            Position position = oldWorker.getPosition();
            Status status = oldWorker.getStatus();
            Person person = oldWorker.getPerson();



            if (userDataReceiver.askQuestion("Хотите изменить имя рабочего?")) name = userDataReceiver.askName();
            if (userDataReceiver.askQuestion("Хотите изменить координаты рабочего?")) coordinates = userDataReceiver.askCoordinates();
            if (userDataReceiver.askQuestion("Хотите изменить зарплату рабочего?")) salary = userDataReceiver.askSalary();
            if (userDataReceiver.askQuestion("Хотите изменить должность рабочего?")) position = userDataReceiver.askPosition();
            if (userDataReceiver.askQuestion("Хотите изменить статус рабочего?")) status = userDataReceiver.askStatus();
            if (userDataReceiver.askQuestion("Хотите изменить личные данные рабочего?")) person = userDataReceiver.askPerson();



            request = new Request("add",new Worker(id,name,coordinates,creationDate,salary,position,status,person));
            request.setCurrentUser(currentUser);
            sender.sendCommand(request);
            receiver.receive();

            return true;
        } catch (WrongArgumentException e){
            Outputer.printError("Укажите ID в качестве аргумента!");
        } catch (IOException e){
            Outputer.printError("Непредвиденная ошибка");
        } catch (NumberFormatException e) {
            Outputer.printError("ID должен быть представлен числом!");
        } catch (UnauthorizedAcces e){
            Outputer.printError("Вы должны авторизироваться для выполнения данной команды");
        }
        return false;
    }

    /**
     *
     * Запускает выполнение команды
     * @param argument аргумент команды
     * @return статус выполнения команды
     */
    public boolean filterByStatusCommand(String argument) {
        argument = argument.trim();
        try {

            if (argument.isEmpty()) throw new WrongArgumentException();

            Request request = new Request("filter_by_status",argument);
            sender.sendCommand(request);
            receiver.receive();

            return true;
        } catch (WrongArgumentException e){
            Outputer.printError("Введите статус!");
        } catch (IOException e){
            Outputer.printError("Непредвиденная ошибка");
        }
        return false;
    }


    public boolean signUp(String argument) {
        argument = argument.trim();

        try {

            if (argument.isEmpty()) throw new WrongArgumentException();
            String[] args = argument.split(" ",2);
            if (args.length == 1) throw new WrongArgumentException();
            if (args[1].contains(" ")) throw new WrongFieldException();

            Request request = new Request("sign_up",argument);
            sender.sendCommand(request);
            receiver.receive();

            return true;
        } catch (WrongArgumentException e){
            Outputer.printError("Введите логин и пароль через пробел!");
        } catch (IOException e){
            Outputer.printError("Непредвиденная ошибка");
        } catch (WrongFieldException e){
            Outputer.printError("Пароль не должен содержать пробелов!");
        }
        return false;
    }

    public boolean signIn(String argument){
        argument = argument.trim();

        try {

            if (argument.isEmpty()) throw new WrongArgumentException();
            String[] args = argument.split(" ",2);
            if (args.length == 1) throw new WrongArgumentException();
            if (args[1].contains(" ")) throw new WrongFieldException();


            Request request = new Request("sign_in",argument);
            sender.sendCommand(request);
            receiver.receive();

            currentUser = args[0];

            return true;
        } catch (WrongArgumentException e){
            Outputer.printError("Введите логин и пароль через пробел!");
        } catch (IOException e){
            Outputer.printError("Непредвиденная ошибка");
        } catch (WrongFieldException e){
            Outputer.printError("Пароль не должен содержать пробелов!");
        }
        return false;
    }


}
