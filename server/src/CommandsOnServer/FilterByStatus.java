package CommandsOnServer;


import Answer.Answer;
import Answer.AnswerStatus;
import Control.CollectionOperator;
import Control.Database;
import Control.Sender;
import Exceptions.EmptyCollectionException;
import Exceptions.PermissonDeniedException;
import WorkerData.Status;

/**
 * Команда "filter_by_status". Выводит все элементы, у которых status равен введённому с помощью аргумента значению.
 */
public class FilterByStatus implements Executable{


    private final CollectionOperator collectionOperator;
    private final Sender sender;
    private final Database database;


    public FilterByStatus(CollectionOperator collectionOperator, Sender sender, Database database) {
        this.collectionOperator = collectionOperator;
        this.sender = sender;
        this.database = database;
    }

    /**
     * Описание команды
     * @return Строка - описание
     */
    public static String description() {
        return "filter_by_status status : вывести элементы, значение поля status которых равно заданному";
    }

    /**
     * Выполнение команды
     * @param argument - аргумент команды
     * @return Статус выполнения команды
     */
    @Override
    public void execute(Object argument, String currentUser, String currentPassword) {
        try {
            if (!database.checkUser(currentUser,currentPassword)) throw new PermissonDeniedException();
            String strArg = (String) argument;
            if (collectionOperator.collectionSize() == 0) throw new EmptyCollectionException();
            Status status = Status.valueOf(strArg.toUpperCase());
            String filteredInfo = collectionOperator.statusFilteredInfo(status);
            if (!filteredInfo.isEmpty()) {
                sender.send(new Answer(filteredInfo,AnswerStatus.OK));
            } else sender.send(new Answer("В коллекции нет рабочих с выбранным статусом!",AnswerStatus.OK));

        } catch (EmptyCollectionException e) {
            sender.send(new Answer("Коллекция пуста!",AnswerStatus.ERROR));
        } catch (IllegalArgumentException e) {

            String info = "Статуса нет в списке!\n" +
                    "Список возможных статусов - " + Status.getValues() + "\n";
            sender.send(new Answer(info,AnswerStatus.ERROR));
        } catch (PermissonDeniedException e){
            sender.send(new Answer("У вас нет прав для выполнения данной операции!",AnswerStatus.ERROR));
        }
    }
}
