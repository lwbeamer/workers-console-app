package Control;


import Exceptions.ScriptRecursionException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CommandReader {

    private final Scanner inputScanner;
    private final CommandManager commandManager;
    private final UserDataReceiver userDataReceiver;

    private final List<String> scriptStack = new ArrayList<>();



    public CommandReader(Scanner inputScanner, CommandManager commandManager, UserDataReceiver userDataReceiver) {
        this.inputScanner = inputScanner;
        this.commandManager = commandManager;
        this.userDataReceiver = userDataReceiver;
    }


    public void readCommand() {
        int commandStatus;
        String[] inputCommand;
        Outputer.println("Клиент запущен!");

        do {
            inputCommand = (inputScanner.nextLine().trim() + " ").split(" ", 2);
            commandStatus = launchCommand(inputCommand);
        } while (commandStatus != 2);
    }

    public int scriptExecution(String argument) {
        String[] inputCommand;
        int commandStatus;

        argument = argument.trim();

        scriptStack.add(argument); //добавляем скрипт в список

        //читаем файл сканером, предрекая ошибки:
        try  {
            Scanner scriptScanner = new Scanner(new File(argument));
            if (!scriptScanner.hasNext()) throw new NoSuchElementException();

            Scanner tmpScanner = userDataReceiver.getInputScanner(); //Когда с файлом всё ок, передаём консольный сканер в сканер скрипта, чтобы работать с консколью из скрипта
            userDataReceiver.setInputScanner(scriptScanner);

            userDataReceiver.setScriptMode(); //устанавливаем скриптовый режим работы, от этого зависит вывод комманд

            do {
                inputCommand = (scriptScanner.nextLine().trim() + " ").split(" ", 2);
                inputCommand[1] = inputCommand[1].trim();
                while (scriptScanner.hasNextLine() && inputCommand[0].isEmpty()) { //цикл, для пропуска пустых строк
                    inputCommand = (scriptScanner.nextLine().trim() + " ").split(" ", 2);
                    inputCommand[1] = inputCommand[1].trim();
                }
                Outputer.println(String.join(" ", inputCommand));

                if (inputCommand[0].equals("execute_script")) { //если есть команда вызова скрипта, проверяется, не вызывается ли файл, который уже есть в стэке, если да, то происходит рекурсия (ошибка)
                    for (String script : scriptStack) {
                        if (inputCommand[1].equals(script)) throw new ScriptRecursionException();
                    }
                }
                commandStatus = launchCommand(inputCommand); //запуск команды
            } while (commandStatus == 0 && scriptScanner.hasNextLine()); //цикл работает до тех пор, пока всё идёт без ошибок (код 0), или пока не кончится скрипт.

            scriptStack.remove(scriptStack.size()-1); //удаляем завершённый скрипт

            userDataReceiver.setInputScanner(tmpScanner); //Отдаём сканер обратно
            userDataReceiver.setNormalMode(); //Возвращаем консольный режим

            if (commandStatus == 1) { //Если програма отработала с ошибкой
                Outputer.println("Что-то пошло не так. Проверьте на корректность введённых данных скрипт "+argument);
            }
            return commandStatus;

        }  catch (NoSuchElementException e) {
            Outputer.printError("Файл со скриптом пуст!");
        } catch (ScriptRecursionException e) {
            Outputer.printError("Скрипты не могут вызываться рекурсивно!");
        } catch (FileNotFoundException e) {
            Outputer.printError("Файл со скриптом не найден!");
        }
        return 1;
    }

    /**
     *
     * @param userCommand command + argument
     * @return command code
     */
    private int launchCommand(String[] userCommand) {

            if (!getCommandsMap().containsKey(userCommand[0])){
                Outputer.println("Команда " + userCommand[0] + " не найдена. Наберите 'help' для справки.");
                return 1;
            }

            if (userCommand[0].equals("exit")) {
                if (getCommandsMap().get(userCommand[0]).convert(userCommand[1])) return 2;
            }

            if (userCommand[0].equals("execute_script")){
                if (getCommandsMap().get(userCommand[0]).convert(userCommand[1])) return scriptExecution(userCommand[1]);
            }

            getCommandsMap().get(userCommand[0]).convert(userCommand[1]);

        return 0;
    }

    public HashMap<String, Convertible<Boolean,String>> getCommandsMap(){
        return commandManager.getCommandsMap();
    }
}
