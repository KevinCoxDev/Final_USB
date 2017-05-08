import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;


public class Powershell {

    private ArrayList volumes = new ArrayList();
    private String line;

    public static void main(String [] args) throws IOException {
        Powershell p = new Powershell();
        System.out.print((p.volumeList()));
        p.format();
    }

    public String volumeList() throws IOException {
        String st = System.getProperty("user.dir");
        st = new StringBuffer(st).insert(2, "\\").toString();
        String string=("powershell.exe"+ "\\"+" \""+st+"\\"+"scripts\\"+"disk_info.ps1\"");
        Process ps = Runtime.getRuntime().exec(string);
        ps.getOutputStream().close();
        BufferedReader stdout = new BufferedReader(new InputStreamReader(ps.getInputStream()));
        while ((line = stdout.readLine()) != null) {
            volumes.add(line);
        }
        stdout.close();
        Object[] volArray = (volumes.toArray());
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(volArray);
        for(int i = 0; i<volArray.length; i++) {
            try (Writer writer = new FileWriter("scripts/Output.json")) {
                gson.toJson(volArray[i], writer);
                FormatThread thread = new FormatThread();
                thread.run();
            }
        }
        return json;
    }

    public void format() throws IOException {
        String dir = System.getProperty("user.dir");
        String st = new String(dir);
        st = new StringBuffer(st).insert(2, "\\").toString();
        String string=("powershell.exe"+ "\\"+" \""+st+"\\"+"scripts\\"+"format.ps1\"");
        Process ps = Runtime.getRuntime().exec(string);
        ps.getOutputStream().close();
    }

}
