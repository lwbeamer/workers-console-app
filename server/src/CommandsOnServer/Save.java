package CommandsOnServer;


import Control.CollectionOperator;
import Control.ServerConsole;
import Exceptions.WrongArgumentException;

/**
 * Команда "save". Сохраняет текущую коллекцию в файл.
 */
public class Save implements Executable{

    private final CollectionOperator collectionOperator;


    public Save(CollectionOperator collectionOperator) {
        this.collectionOperator = collectionOperator;
    }

    /**
     * Описание команды
     * @return Строка - описание
     */
    public static String description(){
        return "save : сохранить коллекцию в файл";
    }


    /**
     * Выполнение команды
     * @param argument - аргумент команды
     * @return Статус выполнения команды
     */
    @Override
    public void execute(Object argument, String currentUser) {
        try {
            String strArg = (String) argument;
            if (!strArg.isEmpty()) throw new WrongArgumentException();
            collectionOperator.saveCollection();
            ServerConsole.println("Коллекция сохранена!");
        } catch (WrongArgumentException e) {
            ServerConsole.printError("Для этой комманды не нужен аргумент, попробуйте ещё раз");
        }
    }
}
