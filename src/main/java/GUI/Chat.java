package GUI;

import BLL.CheckInput;
import BLL.ClientChat;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

public class Chat extends JFrame{
    private JTable table1;
    private JPanel JPanel1;
    private JTextField textField1;
    private JButton button1;
    DefaultTableModel defaultTableModel = null;
    boolean bool = true;

    public Chat(String namesent, String namereceive, ClientChat clientchat){
        setContentPane(JPanel1);
        setSize(900, 500);
        //setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                bool = false;
                clientchat.sent("!@#closeghepdoi!@#");
            }
        });
        setResizable(false);
        setVisible(true);

        Vector header = new Vector();
        header.add(namereceive);
        header.add(namesent+" (là bạn)");
        defaultTableModel = new DefaultTableModel(header,0){
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };

        table1.setModel(defaultTableModel);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textField1.getText();
                CheckInput checkInput = new CheckInput();
                if(checkInput.checkTextField(text) && !text.equalsIgnoreCase("")){
                    clientchat.sent(text);
                    updatetablePhai(text);
                    textField1.setText("");
                }
                else{
                    JFrameMess jFrameMess = new JFrameMess();
                    jFrameMess.mess("Chat","Có chứa thông tin không hợp lệ");
                }
            }
        });

        receivemail(namesent, namereceive, clientchat);
    }

    public void receivemail(String namesent, String namereceive, ClientChat clientchat){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String textbool = namereceive+": !@#closeghepdoi!@#";
                while(bool) {
                    if(clientchat.getSocket().isConnected()==false) System.exit(0);
                    String line = clientchat.receive();

                    if(line.equalsIgnoreCase(textbool)) {
                        clientchat.sent("!@#closeghepdoi!@#");
                        //bool= false;
                        break;
                    }
                    updatetableTrai(line);
                }
                GUIPlease guiPlease = new GUIPlease(namesent, clientchat);
                setVisible(false);
            }
        }).start();
    }

    public void updatetableTrai(String line){
        Vector row = new Vector();
        row.add(line);
        row.add("");
        defaultTableModel.addRow(row);
        table1.setModel(defaultTableModel);
    }

    public void updatetablePhai(String line){
        Vector row = new Vector();
        row.add("");
        row.add(line);
        defaultTableModel.addRow(row);
        table1.setModel(defaultTableModel);
    }

    public void closechat(String user, ClientChat clientchat){
        System.out.println("vao");
        clientchat.sent("!@#closeghepdoi!@#");
        GUIPlease guiPlease = new GUIPlease(user, clientchat);
        setVisible(false);
    }



}
