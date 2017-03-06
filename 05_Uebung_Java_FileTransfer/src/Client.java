
import javax.swing.*;
import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client
{

    public static void main(String[] args) throws IOException {

        ClientMsg clientMsg= new ClientMsg();
        String row="";
        Socket s=null;
        byte [] fileByteArray;
        InputStream inputStream;
        FileOutputStream fileOutputStream;
        BufferedOutputStream bufferedOutputStream;
        Integer bytesRead;
        Integer currentByte=0;

        boolean ipCorrect=false;
        try {
            s = new Socket("127.0.0.1", 9090);
            ipCorrect=true;
        } catch (UnknownHostException ex) {
            JOptionPane.showMessageDialog(null,
                    "Falsche IP-Adresse",
                    "Error-Client",
                    JOptionPane.ERROR_MESSAGE);

        }catch ( ConnectException ex)
        {
            JOptionPane.showMessageDialog(null,
                    "Die Verbindung kann nicht aufgebaut werden (Ist der Server gestartet?)",
                    "Error-Client",
                    JOptionPane.ERROR_MESSAGE);

        }


        BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);

       String path = JOptionPane.showInputDialog(
                "Enter file path (C:\\Users\\Fabian\\...):\n");
         path = "C:\\Users\\Fabian\\IdeaProjects\\Uebung_5_Java_FileTransfer\\src\\Files\\transfer.txt";
        try {

            clientMsg.setMessage(path+" "+1);// Der Dateipfad wird der Message-Klasse übergeben
            out.println(clientMsg.getMessage());// Dateipfad wird verschikt
            out.flush();

            clientMsg.setMessage(input.readLine());
            System.out.println("Complete: "+clientMsg.getMessage()+"\n");
            System.out.println("GetText: "+clientMsg.getText()+"\n");
            System.out.println("GEt Filesize: "+clientMsg.getFileSize()+"\n");
            fileByteArray = new byte[clientMsg.getFileSize()+1000];
            inputStream= s.getInputStream();
            fileOutputStream= new FileOutputStream("C:\\Users\\Fabian\\IdeaProjects\\Uebung_5_Java_FileTransfer\\src\\FilesClient\\file.txt");
            bufferedOutputStream= new BufferedOutputStream(fileOutputStream);
            bytesRead= inputStream.read(fileByteArray,0,fileByteArray.length);
            currentByte= bytesRead;

            do {
                bytesRead = inputStream.read(fileByteArray, currentByte, (fileByteArray.length-currentByte));
                System.out.println(currentByte);
                if(bytesRead >= 0) currentByte += bytesRead;

            } while(bytesRead > -1);

            bufferedOutputStream.write(fileByteArray,0,currentByte); // Heruntergeladene Bytes werden in File reingeschrieben
            bufferedOutputStream.flush();




            //ressourcen werden Freigegeben
            out.close();
            input.close();
            inputStream.close();
            bufferedOutputStream.close();
            fileOutputStream.close();

        }catch (FileNotFoundException ex)
        {
            JOptionPane.showMessageDialog(null,
                    "Das System kann den angegebenen Pfad nicht finden",
                    "Error-Client",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        catch (ArrayIndexOutOfBoundsException ex)
        {
            JOptionPane.showMessageDialog(null,
                    "Message ist ungültig",
                    "Error-Client",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            System.exit(0);
        }
        catch (NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(null,
                    "Der Vorgang wurde abgebrochen",
                    "Error-Client",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            System.exit(0);
        }
        catch ( IOException ex) {
            ex.printStackTrace();
        }
        catch (NullPointerException ex)
        {
            ex.printStackTrace();
        }
        finally {

            s.close();
        }


        System.exit(0);
    }
}



