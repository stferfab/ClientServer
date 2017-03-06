
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.*;



public class Server_MultiThread {


    public static void main(String args[]) throws Exception {
        System.out.println("Listening");

        ServerSocket serverSocket = new ServerSocket(9090);
        ExecutorService pool = Executors.newFixedThreadPool(5);// ExecutorService wird mit poolSize 5 eerstellt
        ServerMsg serverMsg = new ServerMsg();
        try {

            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    System.out.println("Connected");

                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    serverMsg.setMessage(input.readLine()); //Auswahl wird vom Client übergeben

                    if (Integer.parseInt(serverMsg.getMessage()) == 1) { //hier werden je nach Auswahl des Clients die Threads Sequentiell oder Paralell ausgeführt.
                        Handler handler = new Handler(socket, true); //parallel
                        pool.execute(handler);

                    } else {
                        Handler handler = new Handler(socket, false);//sequentiell
                        pool.execute(handler);


                    }
                } catch (SocketException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    pool.shutdown();
                }catch (RejectedExecutionException ex)
                {
                    ex.printStackTrace();
                }

            }
        } finally {
            serverSocket.close();
            //Sauberer Shutdown wird ausgeführt
            pool.shutdown();
            try {
                // Wartet 3 sek. damit existierende Tasks fertig arbeiten können
                if (!pool.awaitTermination(3, TimeUnit.SECONDS)) {
                    pool.shutdownNow(); // Bricht derzeit ausgeführte Tasks ab
                }
            } catch (InterruptedException ie) {
                pool.shutdownNow();// Wird nochmal abgebrochen falls aktueller Task abgebrochen wird
                Thread.currentThread().interrupt(); // Erhaltet den Status des Interrupts
            }

        }


    }
}
