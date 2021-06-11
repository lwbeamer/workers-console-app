package CommandsOnServer;


import Answer.Answer;
import Answer.AnswerStatus;
import Control.CollectionOperator;
import Control.Database;
import Control.Sender;
import Exceptions.EmptyCollectionException;
import Exceptions.PermissonDeniedException;
import WorkerData.Worker;

/**
 * Команда "print_field_descending_status". Сортирует коллекцию в порядке убывания (по выбранному полю в compareTo) и выводит значения полей Status всех элементов
 */
public class PrintFieldDescendingStatus implements Executable{



    private final CollectionOperator collectionOperator;
    private final Sender sender;
    private final Database database;


    public PrintFieldDescendingStatus(CollectionOperator collectionOperator, Sender sender, Database database) {
        this.collectionOperator = collectionOperator;
        this.sender = sender;
        this.database = database;
    }


    /**
     * Описание команды
     * @return Строка - описание
     */
    public static String description(){
        return "print_field_descending_status : вывести значения поля status всех элементов в порядке убывания";
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
            if (collectionOperator.collectionSize() == 0) throw new EmptyCollectionException();
            collectionOperator.sortReverseCollection();
            StringBuilder stringBuilder = new StringBuilder();
            for (Worker worker: collectionOperator.getWorkersCollection()){
                stringBuilder.append(worker.getStatus()).append("\n");
            }
            collectionOperator.sortCollection();
            sender.send(new Answer(stringBuilder.toString(), AnswerStatus.OK));
        }  catch (EmptyCollectionException e) {
            sender.send(new Answer("Коллекция пуста!", AnswerStatus.ERROR));
        } catch (PermissonDeniedException e){
            sender.send(new Answer("У вас нет прав для выполнения данной операции!",AnswerStatus.ERROR));
        }
    }
}
