package CommandsOnServer;


/**
 * Команда "execute_script". Класс отвечает за запуск скрипта. Смена режима происходит в классе Console.
 */
public class ExecuteScript implements Executable{


    /**
     * Описание команды
     * @return Строка - описание
     */
    public static String description() {
        return "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.";
    }

    /**
     * Выполнение команды
     * @param argument - аргумент команды
     * @return Статус выполнения команды
     */
    @Override
    public void execute(Object argument, String currentUser) {

    }
}
