package CommandsOnServer;


import Answer.Answer;
import Answer.AnswerStatus;
import Control.CollectionOperator;
import Control.Sender;

/**
 * Команда "add". Добавляет элемент в коллекцию, узнав у пользователя все нужные данные
 */
public class Clear implements Executable{

    private final CollectionOperator collectionOperator;
    private final Sender sender;


    public Clear(CollectionOperator collectionOperator, Sender sender) {
        this.collectionOperator = collectionOperator;
        this.sender = sender;
    }



    /**
     * Описание команды
     * @return Строка - описание
     */
    public static String description() {
        return "clear : очистить коллекцию";
    }

    /**
     * Выполнение команды
     * @param argument - аргумент команды
     * @return Статус выполнения команды
     */
    @Override
    public void execute(Object argument, String currentUser) {
        collectionOperator.clearCollection(currentUser);
        sender.send(new Answer("Коллекция очищена!", AnswerStatus.OK));
    }
}
