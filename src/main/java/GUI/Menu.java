package GUI;

import BLL.CheckInput;
import BLL.ClientChat;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Menu extends JFrame{
    private JTextField textField1;
    private JButton sendJButton;
    private JPanel JPanel1;
    ClientChat clientchat = null;

    public Menu(){
        clientchat = new ClientChat();
        setContentPane(JPanel1);
        setSize(700,150);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                clientchat.CloseServerChat();
            }
        });
        setResizable(false);
        setVisible(true);

        sendJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrameMess jFrameMess = new JFrameMess();
                String name = textField1.getText();
                CheckInput checkInput = new CheckInput();
                if(name.equalsIgnoreCase("") || name.length()==0 || !checkInput.checkTextField(name)){
                    jFrameMess.mess("Nhập tên", "Hãy nhập tên !!!");
                }
                else{
                    clientchat.sent(name);
                    String boolname = clientchat.receive();
                    if(boolname.equalsIgnoreCase("!@#Success!@#")){
                        System.out.println("success");
                        GUIPlease guiPlease = new GUIPlease(name, clientchat);
                        setVisible(false);
                    }
                    else{
                        jFrameMess.mess("Nhập tên", "Hãy nhập lại tên (tên đã tồn tại)!!!");
                    }
                }
            }
        });

    }

    public static void main(String[] args){
        Menu menu = new Menu();
    }

}
