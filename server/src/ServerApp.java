import CommandsOnServer.*;
import Control.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.DatagramSocket;

public class ServerApp {
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {

        Database database = new Database();

        DatagramSocket socket = new DatagramSocket(1337);

        Sender sender = new Sender(socket);
        CollectionOperator collectionOperator = new CollectionOperator(database);


        CommandManager commandManager = new CommandManager(
                new Help(sender),
                new Add(collectionOperator,sender),
                new AddIfMax(collectionOperator, sender),
                new AddIfMin(collectionOperator, sender),
                new Clear(collectionOperator, sender),
                new Exit(collectionOperator),
                new FilterByStatus(collectionOperator,sender),
                new Info(collectionOperator, sender),
                new PrintFieldDescendingStatus(collectionOperator,sender),
                new RemoveAllByPerson(collectionOperator,sender),
                new RemoveById(collectionOperator,sender),
                new RemoveHead(collectionOperator,sender),
                new Save(collectionOperator),
                new Show(collectionOperator, sender),
                new Update(collectionOperator, sender),
                new SignUp(database,sender),
                new SignIn(database,sender));

        new ServerConsole(commandManager,"serverConsoleThread").start();

        new ServerPool(socket,commandManager).start();
    }
}
