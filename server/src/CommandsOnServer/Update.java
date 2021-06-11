package CommandsOnServer;



import Answer.Answer;
import Answer.AnswerStatus;
import Control.CollectionOperator;
import Control.Database;
import Control.Sender;
import Exceptions.EmptyCollectionException;
import Exceptions.PermissonDeniedException;
import Exceptions.WorkerNotFoundException;
import WorkerData.*;


/**
 * Команда "update". Обновляет элемент в коллекции по ID, узнав у пользователя все нужные данные
 */
public class Update implements Executable{

    private final CollectionOperator collectionOperator;
    private final Sender sender;
    private final Database database;

    public Update(CollectionOperator collectionOperator, Sender sender, Database database) {
        this.collectionOperator = collectionOperator;
        this.sender = sender;
        this.database = database;
    }

    /**
     * Описание команды
     * @return Строка - описание
     */
    public static String description(){
        return "update id {element} : обновить значение элемента коллекции, id которого равен заданному";
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

            long id = Long.parseLong((String) argument);

            if (collectionOperator.collectionSize() == 0) throw new EmptyCollectionException();

            Worker oldWorker = collectionOperator.getById(id);

            if (oldWorker == null) throw new WorkerNotFoundException();


            sender.send(new Answer(oldWorker, AnswerStatus.OK));

        }  catch (EmptyCollectionException e) {
            sender.send(new Answer("Коллекция пуста!",AnswerStatus.ERROR));
        }  catch (WorkerNotFoundException e) {
            sender.send(new Answer("Рабочего с таким ID в коллекции нет!",AnswerStatus.ERROR));
        } catch (PermissonDeniedException e){
            sender.send(new Answer("У вас нет прав для выполнения данной операции!",AnswerStatus.ERROR));
        }
    }
}

