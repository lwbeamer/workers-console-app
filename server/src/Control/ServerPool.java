package Control;

import java.net.DatagramSocket;
import java.util.concurrent.ForkJoinPool;

public class ServerPool extends Thread{
    private DatagramSocket socket;
    private CommandManager commandManager;
    private ForkJoinPool forkJoinPool;


    public ServerPool(DatagramSocket socket, CommandManager commandManager){
        this.socket = socket;
        this.commandManager = commandManager;
        this.forkJoinPool = new ForkJoinPool();
    }

    @Override
    public void run(){
        Receiver receiver = new Receiver(socket,commandManager);
        forkJoinPool.execute(receiver);
    }

}
