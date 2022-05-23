package GUI;

import javax.swing.*;

public class JFrameMess {
    public JFrame frame = null;

    public JFrameMess(){
        frame = new JFrame("JOptionPane showMessageDialog example");
    }

    public void messerror(String t){
        JOptionPane.showMessageDialog(frame, t,"Error Title", JOptionPane.ERROR_MESSAGE);
    }

    public void mess(String title, String note){
        JOptionPane.showMessageDialog(frame, note, title, JOptionPane.ERROR_MESSAGE);
    }
}
