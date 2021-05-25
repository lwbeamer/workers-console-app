import Control.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;

public class ClientApp {
    public static void main(String[] args) throws IOException{


        SocketAddress address = new InetSocketAddress("localhost",1337);

        DatagramChannel channel = DatagramChannel.open();

        Receiver receiver = new Receiver(channel);

        Sender sender = new Sender(channel,address);

        Scanner scanner = new Scanner(System.in);

        UserDataReceiver userDataReceiver = new UserDataReceiver(scanner);

        CommandManager commandManager = new CommandManager(sender,receiver,userDataReceiver);

        CommandReader commandReader = new CommandReader(scanner,commandManager, userDataReceiver);

        commandReader.readCommand();

        sender.close();
    }
}
