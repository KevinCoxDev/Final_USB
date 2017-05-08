import javax.swing.*;

import org.apache.commons.io.FileUtils;

import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static java.awt.Color.*;

import com.google.common.base.Charsets;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.hash.Hashing;

import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.core.ZipFile;


public class Script {


    static int progress;
    List volumes;


    public static void main(String[] args) throws IOException {
        //Script script = new Script();
        //script.vol_list(script.volumeList());

        //display app = new display();


    }

    public static void scriptCall() throws IOException {
        String dir = System.getProperty("user.dir");
        String st = new String(dir);
        st = new StringBuffer(st).insert(2, "\\").toString();
        String string=("powershell.exe"+ "\\"+" \""+st+"\\"+"scripts\\"+"test2.ps1\"");
        Process ps = Runtime.getRuntime().exec(string);
        ps.getOutputStream().close();
        String line;
        BufferedReader stdout = new BufferedReader(new InputStreamReader(ps.getInputStream()));
        progress = 0;
        while ((line = stdout.readLine()) != null) {
            System.out.println(line);
            String check = line;

            progress++;
            display.progressBar.setValue(progress);

            if(line.contains("Dest =")){
                String segments[] = line.split(" ");
                String document = segments[segments.length - 1];
                System.out.println(document);
                progressWindow(document,true);
            }
            else if(line.contains("Dest -")){
                String segments[] = line.split(" ");
                String document = segments[segments.length - 1];
                System.out.println(document);
                progressWindow(document, false);
            }

            String error = line;
            if(error.contains("ERROR") || error.contains("ERROR") || error.contains("The parameter is incorrect")){
                System.out.println("ERROR: UNABLE TO FIND THE DESTINATION /n Please ensure no disks are disconnected during the process.Reconnect the disk and try again");
                errorCheck();
            }
            else if(error.equals("PROCESS HAS COMPLETED,THANK YOU")){

            }
        }
        stdout.close();

    }

    public ArrayList volumes() throws IOException {
        String dir = System.getProperty("user.dir");
        String st = new String(dir);
        st = new StringBuffer(st).insert(2, "\\").toString();
        String string=("powershell.exe"+ "\\"+" \""+st+"\\"+"scripts\\"+"disk_info.ps1\"");
        Process ps = Runtime.getRuntime().exec(string);
        ps.getOutputStream().close();
        String line;
        BufferedReader stdout = new BufferedReader(new InputStreamReader(ps.getInputStream()));
        progress = 0;
        while ((line = stdout.readLine()) != null) {
            System.out.println(line);
            volumes = new ArrayList();
            volumes.add(line);
            //System.out.println("Disks: " + volumeList.toString());
        }
        stdout.close();

        return (ArrayList) volumes;
    }

    public void vol_list(ArrayList vols){
        System.out.println("Disks: " + volumes.toString());

    }

    public static void  errorCheck(){
        JOptionPane.showMessageDialog(null, "There has been an issue with one of your drives \nThis error message may reoccur on the same drive \n Process will attempt to continue on other drives", "alert", JOptionPane.WARNING_MESSAGE);
    }

    public static void progressWindow(String input, Boolean check){
        Color color = gray;
        if(check){
            color = green;
        }
        else if(!check){
            color = red;
        }

        if(input.contains("Q")){
            display.drive1.setText("Q:");
            display.drive1.setBackground(color);
        }
        else if(input.contains("R")){
            display.drive2.setText("R:");
            display.drive2.setBackground(color);
        }
        else if(input.contains("S")){
            display.drive3.setText("S:");
            display.drive3.setBackground(color);
        }
        else if(input.contains("T")){
            display.drive4.setText("T:");
            display.drive4.setBackground(color);
        }
        else if(input.contains("U")){
            display.drive5.setText("U:");
            display.drive5.setBackground(color);
        }

    }

	private File tmp;

    public String[] parse_file() {
        List<String> list = null;
        try {
            list = Files.readAllLines(Paths.get("settings.ini"), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] array = list.toArray(new String[list.size()]);

        return array;
    }

    public BiMap make_map(String[] array){
        BiMap<String,String> map = HashBiMap.create();
        for(int i=0; i < array.length; i++){
            String[] split = array[i].split(":");
            String key = split[0];
            String[] values = split[1].split(",");
            for(int d=0; d<values.length;d++){
            	String value = values[d];
            	map.put(key,value);
            }	
        }
        return map;
    }

    public String select_file(BiMap map, String input){
        String key = getKeyFromValue(map,input);
        return key;
    }

    public Object[] generateDeviceList(BiMap map) {
        
    	map.values();
    	Object[] device_array = map.values().toArray(new Object[map.values().size()]);
    	Arrays.toString(device_array);
    	
        return device_array;
    }

    public String getKeyFromValue(BiMap map, String input) {
    	System.out.println(map.values());
    	String key = (String) map.inverse().get(input);
    	System.out.println("Key:"+key);
    	return key;
        
    }
    
    public void unzip(String source,String name){
        
        String dest = "uncompressed_zip";

        try {
             ZipFile zipFile = new ZipFile(source);
             zipFile.extractAll(dest);
             pull_files(listDir(dest,name));
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }
    
    private File listDir(String source, String name)
    {
    	File root = new File(source);
    	File[] files = root.listFiles();
    	File target = null;
    	
    	
    	for(int i=0;i<files.length;i++){
    		if(files[i].isDirectory()){
    			listDir(files[i].toString(),name);
    			if(files[i].toString().contains(name)){
    				target= files[i];
    				System.out.println("target found: " + target);
    			}
    			
    		}
    	}


    	if(target != null){
    		
    		tmp = target;
    		System.out.print(tmp);
    	}
    	
    	System.out.println("TEST: " +  target);
    	return tmp;
		
		
    	
    }
    
    private void pull_files(File source){
    	File dest = new File("resources");
    	try {
    		System.out.println("Source " + source);
    		System.out.println("Dest " + dest);
    	    FileUtils.copyDirectory(source, dest);
    	} catch (IOException e) {
    	    e.printStackTrace();
    	}
    }
    
    private void hash_check(){
    	CharSequence myString = "622628";
		Hashing.md5().hashString(myString, Charsets.UTF_8); 
    }
    
    


}