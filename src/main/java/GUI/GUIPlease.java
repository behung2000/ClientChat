package GUI;

import BLL.ClientChat;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.StringTokenizer;

public class GUIPlease extends JFrame{
    private JPanel JPanel1;
    private javax.swing.JLabel JLabel;

    public GUIPlease(String user, ClientChat clientchat) {
        setContentPane(JPanel1);
        setSize(900, 150);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                clientchat.CloseServerChat();
            }
        });
        setResizable(false);
        String text = JLabel.getText();
        text = user+" -> "+text;
        JLabel.setText(text);
        setVisible(true);

        //ghepdoi(clientchat);

        clientchat.sent("!@#ghepdoi!@#");
        String name = clientchat.receive();
        //System.out.println(name);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ghepdoi(true, user, name, clientchat);
            }
        });
        thread.start();
        //String ghepdoi  = clientchat.receive();
    }

    public void ghepdoi(boolean dk, String user, String name, ClientChat clientchat){
        while(true) {
            if(name.equalsIgnoreCase("!@#hetghepdoi!@#")) dk=false;
            int i=0;
            while (dk) {
                int bool = JOptionPane.showConfirmDialog(this, "Có muốn chat với " + name, "Ghép đôi", JOptionPane.YES_NO_OPTION);
                //0 -> yes
                //1 -> no
                //System.out.println(bool);
                if (bool == 0) {
                    clientchat.sent("!@#yesghepdoi!@#");
                    openchat(user, name, clientchat);
                    i++;
                    break;
                } else clientchat.sent("!@#noghepdoi!@#");
                name = clientchat.receive();
                if(name.equalsIgnoreCase("!@#hetghepdoi!@#")) dk=false;
            }
            if(i>0) break;
            name = clientchat.receive();
            StringTokenizer stringTokenizer = new StringTokenizer(name, ":");
            if(stringTokenizer.countTokens()>1){
                clientchat.sent("!@#ghepdoi1Client!@#");
                String ten = stringTokenizer.nextToken();
                clientchat.sent(ten);
                openchat(user, ten, clientchat);
                break;
            }
        }
    }

    public void openchat(String namesent, String namereceive, ClientChat clientchat){
        Chat chat = new Chat(namesent, namereceive, clientchat);
        setVisible(false);
    }

}
