package Control;

import Answer.Request;

import java.io.*;
import java.io.IOException;
import java.net.*;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Sender {
    private final DatagramChannel channel;
    private final SocketAddress address;

    private final ByteBuffer buf = ByteBuffer.allocate(8196);

    public Sender(DatagramChannel channel, SocketAddress address) {
        this.channel = channel;
        this.address = address;
    }

    public void sendCommand(Request request) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(request);
        buf.put(byteArrayOutputStream.toByteArray());
        objectOutputStream.flush();
        byteArrayOutputStream.flush();
        ((Buffer)buf).flip();
        channel.send(buf, address);
        objectOutputStream.close();
        byteArrayOutputStream.close();
        ((Buffer)buf).clear();
    }

    public  void close() throws IOException {
        channel.close();
    }

}
