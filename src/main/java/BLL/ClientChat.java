package BLL;

import SERCURITY.KhoaAES;
import SERCURITY.KhoaRSA;

import java.io.*;
import java.net.Socket;

public class ClientChat {
    Socket socket = null;
    int port = 50000;
    String host = "127.0.0.1";
    KhoaAES khoaAES = null;
    BufferedReader reader = null;
    BufferedWriter writer = null;

    public ClientChat(){
        try {
            socket = new Socket(host, port);
            System.out.println("Client connected");
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            khoaAES = new KhoaAES();
            KhoaRSA khoaRSA = new KhoaRSA();
            khoaRSA.initFromStrings();
            String key = khoaAES.getStringKey();
            String keyMaHoa = khoaRSA.encrypt(key);
            writer.write(keyMaHoa + "\n");
            writer.flush();
        }
        catch (IOException e){
            System.out.println("Error -> ClientChat -> ClientChat -> "+e.getMessage());
            System.exit(0);
        }
    }

    public void CloseServerChat(){
        try {
            sent("!@#byeserverchat!@#");
            System.out.println("Client socket closed");
            reader.close();
            writer.close();
            socket.close();
        }
        catch (IOException e) {
            System.out.println("Error -> ClientChat -> CloseChat -> "+e.getMessage());
            //e.printStackTrace();
        }
        System.exit(0);
    }


    //Sent
    public void sent(String line){
        try {
            String lineMaHoa = khoaAES.encrypt(line);
            writer.write(lineMaHoa + "\n");
            writer.flush();
        }
        catch (IOException e) {
            System.out.println("Error -> ClientChat -> sent -> "+e.getMessage());
            //e.printStackTrace();
            //JFrameErrorMess jFrameErrorMess = new JFrameErrorMess();
            //jFrameErrorMess.mess("Server", "Không tìm thấy socket liên kết server nữa");
            System.exit(0);
        }
    }

    //receive
    public String receive(){
        try{
            String lineMaHoa = reader.readLine();
            String line="";
            if(lineMaHoa!=null) line = khoaAES.decrypt(lineMaHoa);
            //System.out.println(line);
            return line;
        }
        catch (IOException e) {
            System.out.println("Error -> ClientChat -> receive -> "+e.getMessage());
            //e.printStackTrace();
            //System.out.println("Lỗi");
            return "false";
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
