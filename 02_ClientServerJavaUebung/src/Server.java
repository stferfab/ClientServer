
import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;


public class Server
{


    public static void main(String[] args) throws IOException {
       //Variablen werden initialisiert
        CalcAnswer msgAns = new CalcAnswer();
        CalcMsg msgReq = new CalcMsg();
        int x=0;
        int y=0;
        double erg=0;
        char operation;
        ServerSocket listener = new ServerSocket(9090); //server wird gestartet port 9090
        System.out.println("Listening..");
        try {
            while (true) {
                Socket socket = listener.accept(); //client wird akzeptiert
                System.out.println("Connected");
                try
                {
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true); //Kommunikationskanal wird hergestellt
                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    msgReq.setMessage(input.readLine());//Server bekommt String von Client und löst in in Message-Klasse auf
                    x=msgReq.getX();
                    y=msgReq.getY();
                    operation=msgReq.getZeichen();


                    if(msgReq.getUsername().equals("Fabian"))//Benutzerauthentifizierung
                    {
                        switch (operation) {// Rechenoperation wird ausgewählt
                            case '+': {
                                erg = x + y;
                                msgAns.setMessage(String.valueOf(erg));
                                out.println(msgAns.getMessage());//Server gibt Ergebnis zurück
                                break;
                            }
                            case '-': {
                                erg = x - y;
                                msgAns.setMessage(String.valueOf(erg));
                                out.println(msgAns.getMessage());
                                break;
                            }
                            case '*': {
                                erg = x * y;
                                msgAns.setMessage(String.valueOf(erg));
                                out.println(msgAns.getMessage());
                                break;
                            }
                            case '/': {
                               try{ erg = x / y;}
                               catch (ArithmeticException ex)
                               {
                                   JOptionPane.showMessageDialog(null,
                                           "Division durch 0 ist nicht definiert!",
                                           "Error-Client",
                                           JOptionPane.ERROR_MESSAGE);
                                   System.exit(0);
                               }
                                msgAns.setMessage(String.valueOf(erg));
                                out.println(msgAns.getMessage());
                                break;
                            }
                        }
                    }
                    else{
                        msgAns.setMessage("ACCESS NOT GRANTED");
                        out.println(msgAns.getMessage());
                        out.flush();
                    }

                    //Ressourcen werden Freigegeben
                    out.close();
                    input.close();
                }catch (SocketException ex)
                {
                    JOptionPane.showMessageDialog(null,
                            "Verbindungsfehler!)",
                            "Error-Client",
                            JOptionPane.ERROR_MESSAGE);
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

