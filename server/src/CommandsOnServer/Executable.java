package CommandsOnServer;

/**
 * Интерфейс для всех команд. Содержит один метод который отвечает за выполнение команды.
 */
public interface Executable {
    void execute(Object argument, String currentUser, String currentPassword);
}
