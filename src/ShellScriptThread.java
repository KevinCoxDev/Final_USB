import javax.swing.*;
import java.io.IOException;

/**
 * Created by kcox on 03/05/2017.
 */
public class ShellScriptThread implements Runnable {
    @Override
    public void run() {
        try {
        	display.panel.setVisible(false);
            Script.scriptCall();
            display.panel.setVisible(true);
            display.progressBar.setValue(400);
            run2();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run2(){
        JOptionPane.showMessageDialog(null, "Drives have been prepared.\nAny devices marked in red were not succesfully processed", "Completed", JOptionPane.PLAIN_MESSAGE);
        
    }
}
