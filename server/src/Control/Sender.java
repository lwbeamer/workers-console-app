package Control;

import Answer.Answer;

import java.io.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Logger;


public class Sender{

    public static final Logger log = Logger.getLogger("logger");
    private Answer answer = null;
    private final DatagramSocket socket;
    static InetAddress address;
    static int port;

    public Sender(DatagramSocket socket) {
        this.socket = socket;
    }

    public void send(Answer answer){
        try  {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

            objectOutputStream.writeObject(answer);
            objectOutputStream.flush();

            byte[] bytes = byteArrayOutputStream.toByteArray();

            Integer length = bytes.length;

            byteArrayOutputStream.flush();

            DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length, address, port);
            socket.send(datagramPacket);

            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

            objectOutputStream.writeObject(length);
            objectOutputStream.flush();

            bytes = byteArrayOutputStream.toByteArray();

            datagramPacket = new DatagramPacket(bytes, bytes.length, address, port);

            socket.send(datagramPacket);

            log.info("Отправлен ответ на " + datagramPacket.getAddress() + ":" + datagramPacket.getPort()+"\nExecution Status: "+ answer.getAnswerStatus().toString());

        } catch (IOException e) {
            log.severe("Не удалось отправить ответ!");
        }
    }

    public static void setAddress(InetAddress addressToSend) {
        address = addressToSend;
    }

    public static void setPort(int portToSend) {
        port = portToSend;
    }

    public static InetAddress getAddress() {
        return address;
    }

}
