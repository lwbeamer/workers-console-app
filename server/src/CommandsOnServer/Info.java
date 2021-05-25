package CommandsOnServer;


import Answer.Answer;
import Answer.AnswerStatus;
import Control.CollectionOperator;
import Control.Sender;


import java.time.LocalDateTime;

/**
 * Команда "info". Выводит информацию о коллекции.
 */
public class Info implements Executable{


    private final CollectionOperator collectionOperator;
    private final Sender sender;


    public Info(CollectionOperator collectionOperator, Sender sender) {
        this.collectionOperator = collectionOperator;
        this.sender = sender;
    }

    /**
     * Описание команды
     * @return Строка - описание
     */
    public static String description(){
        return "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
    }

    /**
     * Выполнение команды
     * @param argument - аргумент команды
     * @return Статус выполнения команды
     */
    @Override
    public void execute(Object argument, String currentUser) {
            LocalDateTime lastInitTime = collectionOperator.getLastInitialisationTime();
            String lastInitTimeString;

            if (lastInitTime == null)
                lastInitTimeString = "Инициализации ещё не было";
            else lastInitTimeString = lastInitTime.toLocalDate().toString() + " " + lastInitTime.toLocalTime().toString();

        String info = " Тип коллекции: " + collectionOperator.collectionType() + "\n" +
                " Количество элементов: " + collectionOperator.collectionSize() + "\n" +
                " Дата последней инициализации: " + lastInitTimeString + "\n";
        sender.send(new Answer(info, AnswerStatus.OK));

    }
}
