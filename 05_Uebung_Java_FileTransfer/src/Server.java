
import javax.swing.*;
import java.io.*;
import java.net.*;


public class Server
{


    public static void main(String[] args) throws IOException {
        ClientMsg clientMsg = new ClientMsg(); //Variablen & Objects werden initialisiert
        ServerMsg serverMsg = new ServerMsg();
        OutputStream outServer;
        File file;
        FileInputStream fileInputStream;
        BufferedInputStream bufferedInputStream;
        byte [] fileByteArray;


        ServerSocket listener = new ServerSocket(9090);//Dem ServerSocket wird der Port 9090 zugewiesen
        try {
            while (true) {
                System.out.println("Waiting for connection...");
                Socket socket = listener.accept(); //Server akzeptiert die Verbindung zum Client

                try
                {
                    System.out.println("Accepted connection : " + socket);

                    outServer = socket.getOutputStream(); //Die Verbindngskanäle werden initialisiert
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    serverMsg.setMessage(input.readLine()); //server bekommt Dateipfad von client

                    file = new File(serverMsg.getText());
                    System.out.println("Complete: "+serverMsg.getMessage()+"\n");
                    System.out.println("GetText: "+serverMsg.getText()+"\n");
                    System.out.println("GEt Filesize: "+serverMsg.getFileSize()+"\n");
                    fileInputStream = new FileInputStream(file);
                    bufferedInputStream = new BufferedInputStream(fileInputStream);
                    fileByteArray=new byte[(int)file.length()];//array mit der Länge des Files wird generiert

                    serverMsg.setMessage((int)file.length());//länge der Datei wird dem Client übergeben
                    System.out.println(serverMsg.getMessage());
                    out.write(serverMsg.getMessage());

                    bufferedInputStream.read(fileByteArray,0,fileByteArray.length);
                    outServer.write(fileByteArray,0,fileByteArray.length);
                    outServer.flush();




                    bufferedInputStream.close();//ressourcen werden Freigegeben
                    outServer.close();
                    fileInputStream.close();
                    input.close();

                }catch (SocketException ex)
                {
                    JOptionPane.showMessageDialog(null,
                            "Verbindungsfehler!)",
                            "Error-Client",
                            JOptionPane.ERROR_MESSAGE);
                } catch (MalformedURLException ex) {
                    ex.printStackTrace();
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                } catch (UnsupportedEncodingException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (ArrayIndexOutOfBoundsException ex)
                {
                    ex.printStackTrace();
                }
                finally
                {

                    socket.close();

                }

            }
        } finally {
            listener.close();
        }
    }

}

