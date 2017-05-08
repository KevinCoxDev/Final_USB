import java.io.IOException;

/**
 * Created by kcox on 08/05/2017.
 */
public class FormatThread {

    private Thread t;

    FormatThread() {
        System.out.println("Creating ");
    }

    public void run() throws IOException {
        String dir = System.getProperty("user.dir");
        String st = new String(dir);
        st = new StringBuffer(st).insert(2, "\\").toString();
        String string=("powershell.exe"+ "\\"+" \""+st+"\\"+"scripts\\"+"format.ps1\"");
        Process ps = Runtime.getRuntime().exec(string);
        ps.getOutputStream().close();
        System.out.println("WORKS");
    }
}
