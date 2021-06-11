package CommandsOnServer;


import Control.CollectionOperator;
import Control.ServerConsole;
import Exceptions.WrongArgumentException;

/**
 * Команда "exit". Завершает программу, сохранения коллекции не происходит.
 */
public class Exit implements Executable{


    private final CollectionOperator collectionOperator;


    public Exit(CollectionOperator collectionOperator) {
        this.collectionOperator = collectionOperator;
    }

    /**
     * Описание команды
     * @return Строка - описание
     */
    public static String description() {
        return "exit : завершить программу (без сохранения в файл)";
    }

    /**
     * Выполнение команды
     * @param argument - аргумент команды
     * @return Статус выполнения команды
     */
    @Override
    public void execute(Object argument, String currentUser, String currentPassword) {
        try {
            String strArg = (String) argument;
            if (!strArg.isEmpty()) throw new WrongArgumentException();
            System.exit(0);
        } catch (WrongArgumentException e) {
            ServerConsole.printError("Для этой комманды не нужен аргумент, попробуйте ещё раз");
        }
    }
}
