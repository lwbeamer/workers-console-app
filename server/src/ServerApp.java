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
                new Add(collectionOperator,sender,database),
                new AddIfMax(collectionOperator, sender,database),
                new AddIfMin(collectionOperator, sender,database),
                new Clear(collectionOperator, sender,database),
                new Exit(collectionOperator),
                new FilterByStatus(collectionOperator,sender,database),
                new Info(collectionOperator, sender,database),
                new PrintFieldDescendingStatus(collectionOperator,sender,database),
                new RemoveAllByPerson(collectionOperator,sender,database),
                new RemoveById(collectionOperator,sender,database),
                new RemoveHead(collectionOperator,sender,database),
                new Save(collectionOperator),
                new Show(collectionOperator, sender,database),
                new Update(collectionOperator, sender,database),
                new SignUp(database,sender),
                new SignIn(database,sender));

        new ServerConsole(commandManager,"serverConsoleThread").start();

        new ServerPool(socket,commandManager).start();
    }
}
