import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

/**
 * Created by kcox on 08/05/2017.
 */
public class FormatThread {

    private Thread t;
    private String line;

    FormatThread() {
        System.out.println("Creating ");
    }

    public void run(String size) throws IOException {

        try (Writer writer = new FileWriter("scripts/Output.json")){
            Gson gson = new GsonBuilder().create();
            String json = gson.toJson(size);
            System.out.print(json);
            gson.toJson(json, writer);

        } catch (IOException e) {
            e.printStackTrace();
        }
        script();

    }

    public void script() throws IOException {
        String st = System.getProperty("user.dir");
        st = new StringBuffer(st).insert(2, "\\").toString();
        String string=("powershell.exe"+ "\\"+" \""+st+"\\"+"scripts\\"+"format.ps1\"");
        Process ps = Runtime.getRuntime().exec(string);
        ps.getOutputStream().close();
        BufferedReader stdout = new BufferedReader(new InputStreamReader(ps.getInputStream()));
        while ((line = stdout.readLine()) != null) {
            System.out.println(line);
        }
    }
}
