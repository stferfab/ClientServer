import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


import java.util.*;


public class Handler extends Thread {
    private final Socket socket;
    private boolean multiThread;
    PrintWriter out;
    ServerMsg serverMsg= new ServerMsg();


    public Handler (Socket socket, boolean multiThread)
    {
        this.socket=socket;
        this.multiThread=multiThread;
    }


    @Override
    public void run() {
       MyThread thread1= new MyThread();//Time Thread wird erstellt
       MyThreadRandom thread2= new MyThreadRandom();//Random Thread wird erstellt
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
       if(multiThread)
        {
            System.out.println("Paralell!");
            thread1.start();
            thread2.start();
        }else{
            System.out.println("Sequentiell!");
            thread1.run();
            thread2.run();
        }

        try {
            thread1.join();
            thread2.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        serverMsg.setMessage(thread1.getAnswer()+" / "+thread2.getRandomInt());//Daten werden Client übergeben
        out.println(serverMsg.getMessage());
        out.flush();


        out.close();
        try
        {
            socket.close();
        }catch (IOException ex)
        {
            ex.printStackTrace();
        }


    }
}
