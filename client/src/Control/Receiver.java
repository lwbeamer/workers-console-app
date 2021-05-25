package Control;

import Answer.Answer;

import Exceptions.CorruptedDataException;
import Exceptions.ServerIsNotAvailableException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.net.SocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.TimeUnit;

public class Receiver{

    private final DatagramChannel channel;
    private final ByteBuffer buffer;
    private final ByteBuffer bufferChecker;
    private Answer answer;
    private Integer check;

    public Receiver(DatagramChannel channel) {
        this.channel = channel;
        this.buffer = ByteBuffer.allocate(16384);
        this.bufferChecker = ByteBuffer.allocate(16384);
    }

    public void receive()  {

    try {

        channel.configureBlocking(false);

        ((Buffer)buffer).clear();

        TimeUnit.MILLISECONDS.sleep(1000);

        if (channel.receive(buffer)==null){
            Outputer.println("Сервер отвечает дольше чем обычно. Пытаюсь принять ответ...");
            TimeUnit.MILLISECONDS.sleep(3000);
            if (channel.receive(buffer)==null){
                throw new ServerIsNotAvailableException();
            }
        }

        TimeUnit.MILLISECONDS.sleep(300);

        channel.receive(bufferChecker);

        ((Buffer)bufferChecker).flip();

        ((Buffer)buffer).flip();

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bufferChecker.array());

        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

        check = (Integer) objectInputStream.readObject();

        if (buffer.limit() != check) throw new CorruptedDataException();

        byteArrayInputStream = new ByteArrayInputStream(buffer.array());

        objectInputStream = new ObjectInputStream(byteArrayInputStream);

        answer = (Answer) objectInputStream.readObject();

        answer.printStringAnswer();

        objectInputStream.close();
        byteArrayInputStream.close();

        ((Buffer)buffer).clear();

        } catch (InterruptedException e) {
            Outputer.printError("Ошибка прерывания!");
        } catch (ServerIsNotAvailableException e) {
            Outputer.printError("Сервер не отвечает!");
        } catch (IOException | ClassNotFoundException e) {
            Outputer.printError("Сериализуемый класс не найден или данные поверждены. Попробуйте ещё раз");
        } catch (CorruptedDataException e){
            Outputer.printError("Данные повреждены при транспортировке. Повторите попытку");
        }
    }

    public Answer getAnswer() {
        return answer;
    }
}
