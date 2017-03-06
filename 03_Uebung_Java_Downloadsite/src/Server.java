
import javax.swing.*;
import java.io.*;
import java.net.*;


public class Server
{


    public static void main(String[] args) throws IOException {
        ClientMsg clientMsg = new ClientMsg(); //Variablen & Objects werden initialisiert
        ServerMsg serverMsg = new ServerMsg();
        URL webaddress;
        InputStream inputStream;
        BufferedReader bufferedReader;
        String row;
        StringBuilder text = new StringBuilder() ;
        ServerSocket listener = new ServerSocket(9090); //Dem ServerSocket wird der Port 9090 zugewiesen
        System.out.println("Listening..");
        try {
            while (true) {
                Socket socket = listener.accept(); //Server akzeptiert die Verbindung zum Client
                System.out.println("Connected");
                try
                {
                    PrintWriter outServer = new PrintWriter(socket.getOutputStream(), true);//Die Verbindngskanäle werden initialisiert
                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    clientMsg.setMessage(input.readLine()); //server bekommt URL von client

                    webaddress = new URL(clientMsg.getMessage());
                    inputStream= webaddress.openStream();
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                    while ((row = bufferedReader.readLine()) != null) {
                        text.append(row);
                    }
                    serverMsg.setMessage(text.toString());
                    outServer.println(serverMsg.getMessage());//Server übergibt Website an Client
                    outServer.flush();

                    inputStream.close();//Ressourcen werden Freigegeben
                    outServer.close();
                    bufferedReader.close();
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
                } finally
                {
                    socket.close();

                }

            }
        } finally {
            listener.close();
        }
    }

}

