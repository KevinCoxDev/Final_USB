import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;


public class Powershell {

    private ArrayList volumes = new ArrayList();
    private String line;

    public static void main(String [] args) throws IOException {
        Powershell p = new Powershell();
        p.threadCaller();
    }



    public ArrayList volumeList() throws IOException {
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
        System.out.print(Arrays.toString(new ArrayList[]{volumes}));

        return volumes;
    }

    public void threadCaller() throws IOException{
        ArrayList array = volumeList();
        FormatThread thread = new FormatThread();
        for(int i =0 ; i < array.size(); i++){
            thread.run((String) array.get(i));
        }

    }
}