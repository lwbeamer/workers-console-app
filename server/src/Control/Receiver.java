package Control;

import Answer.Request;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.logging.Logger;


public class Receiver extends Thread{

    public static final Logger log = Logger.getLogger("logger");
    private final DatagramSocket socket;
    private final byte[] buf = new byte[16384];
    private final CommandManager commandManager;

    public Receiver(DatagramSocket socket, CommandManager commandManager) {
        this.socket = socket;
        this.commandManager = commandManager;
    }

    @Override
    public void run() {

        while (!Thread.currentThread().isInterrupted()) {
            try {
                DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length);

                socket.receive(datagramPacket);

                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buf);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

                Request request = (Request) objectInputStream.readObject();

                if (Sender.getAddress() != datagramPacket.getAddress()) {
                    Sender.setAddress(datagramPacket.getAddress());
                    Sender.setPort(datagramPacket.getPort());
                }


                log.info("Получен запрос от " + datagramPacket.getAddress() + ":" +
                        datagramPacket.getPort() + " - Command: " + request.getCommand() + "; Argument: " +request.getArg());


                commandManager.executeCommand(request);

            } catch (IOException | ClassNotFoundException e) {
                log.severe("Ошибка сериализации. Данные поверждены.");
            }
        }
    }
}
