//Imports are listed in full to show what's being used
//could just import javax.swing.* and java.awt.* etc..

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Arrays;

public class display {

    static JButton drive1;
    static JButton drive2;
    static JButton drive3;
    static JButton drive4;
    static JButton drive5;

    private JButton button1;
    JPanel comboPanel;
    JFrame guiFrame;
    JComboBox<String> list1;
    static JProgressBar progressBar;
    static JPanel panel;
    private JButton logs;
    private JButton Exit;
	private JPanel panel_1;
	private JPanel listPanel;
	static JPanel panel_2;
	private JButton browse;
	private JFileChooser fileChooser;
	private File selectedFile;;


    @SuppressWarnings("unchecked")
	public display()
    {
    	Script script = new Script();
    	
        guiFrame = new JFrame();
        guiFrame.getContentPane().setBackground(SystemColor.textHighlight);
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setTitle("Multi-USB reformatter");
        guiFrame.setSize(505,300);
        guiFrame.setLocationRelativeTo(null);
        guiFrame.setVisible(true);
        guiFrame.getContentPane().setLayout(null);
        
        
        JLabel comboLbl = new JLabel("Please select the NAS model you wish to use this USB for:");

        Border border = new LineBorder(Color.BLACK,3);
        
        
        //Options for the JComboBox
        Object[] models =  script.generateDeviceList(script.make_map(script.parse_file()));
        String[] models_1 = Arrays.asList(models).toArray(new String[models.length]);
        
        list1 = new JComboBox(models_1);
        list1.addActionListener(this::actionPerformed);
        JLabel lblWarning = new JLabel("WARNING!");
        lblWarning.setHorizontalAlignment(SwingConstants.CENTER);
        lblWarning.setBounds(0, 11, 455, 14);
        JLabel lblWarning2 = new JLabel("This is a process that can wipe disks.");
        lblWarning2.setHorizontalAlignment(SwingConstants.CENTER);
        lblWarning2.setBounds(0, 44, 455, 14);
        JLabel lblWarning3 = new JLabel("Please ensure only disks you wish to wipe are connected");
        lblWarning3.setHorizontalAlignment(SwingConstants.CENTER);
        lblWarning3.setBounds(0, 59, 455, 14);
        
        
        
        panel_1 = new JPanel();
        panel_1.setBackground(SystemColor.textHighlight);
        panel_1.setBounds(17, 52, 455, 114);
        panel_1.setLayout(null);
        panel_1.add(lblWarning);
        panel_1.add(lblWarning2);
        panel_1.add(lblWarning3);
        panel_1.setVisible(true);
        
        guiFrame.getContentPane().add(panel_1);
        
        browse = new JButton("Browse For ZIP containing files");
        browse.setBounds(100, 80, 255, 23);
        browse.addActionListener(this::actionPerformed);
        panel_1.add(browse);

        //The first JPanel contains a JLabel and JCombobox
        comboPanel = new JPanel();
        guiFrame.getContentPane().add(comboPanel);
        comboPanel.setBounds(0, 0, 505, 50);
        comboPanel.add(comboLbl);
        comboPanel.add(list1);
        comboPanel.setVisible(false);
        comboPanel.setBackground(Color.white);
        
        button1 = new JButton( "Format");
        button1.setVisible(true);
        button1.addActionListener(this::actionPerformed);
        
        panel = new JPanel();
        guiFrame.getContentPane().add(panel);
        panel.setBounds(0, 217, 505, 50);
        panel.setLayout(new GridLayout(1, 3));
        panel.setVisible(false);
        panel.add(button1);
        
        progressBar = new JProgressBar();
        progressBar.setBounds(0, 0, 419, 35);
        progressBar.setVisible(true);
        progressBar.setMaximum(400);
        progressBar.setBackground(Color.white);
        progressBar.setStringPainted(true);
        
        panel_2 = new JPanel();
        panel_2.setBounds(32, 177, 419, 35);
        panel_2.setLayout(null);
        panel_2.setVisible(false);
        panel_2.add(progressBar);
        
        
         
        logs = new JButton("Get Logs");
        Exit = new JButton("Exit");

        
        listPanel = new JPanel();
        listPanel.setBorder(new TitledBorder(null, "Drives in green are completed. Drives with errors are in red", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        listPanel.setBounds(40, 30, 405, 120);
        listPanel.setBackground(SystemColor.textHighlight);
        listPanel.setLayout(null);
        listPanel.setVisible(false);


        // Buttons to display drive writing progress
        
        
        drive2 = new JButton("No Drive");
        drive2.setBounds(5, 16, 75, 100);
        listPanel.add(drive2);
        drive2.setVisible(true);
        drive2.setBackground(Color.lightGray);
        drive2.setBorder(border);

        drive3 = new JButton("No Drive");
        drive3.setBounds(85, 16, 75, 100);
        listPanel.add(drive3);
        drive3.setVisible(true);
        drive3.setBackground(Color.lightGray);
        drive3.setBorder(border);

        drive4 = new JButton("No Drive");
        drive4.setBounds(165, 16, 75, 100);
        listPanel.add(drive4);
        drive4.setVisible(true);
        drive4.setBackground(Color.lightGray);
        drive4.setBorder(border);

        drive5 = new JButton("No Drive");
        drive5.setBounds(245, 16, 75, 100);
        listPanel.add(drive5);
        drive5.setVisible(true);
        drive5.setBackground(Color.lightGray);
        drive5.setBorder(border);

        drive1 = new JButton("No Drive");
        drive1.setBounds(325, 16, 75, 100);
        listPanel.add(drive1);
        drive1.setVisible(true);
        drive1.setBackground(Color.lightGray);
        drive1.setBorder(border);
                
        
        
        guiFrame.getContentPane().add(panel_2);
        
        guiFrame.getContentPane().add(listPanel);
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        fileChooser = new JFileChooser();



    }

    public void actionPerformed(ActionEvent e)
    {
    	
    	Script script = new Script();
    	if(browse == e.getSource()) {
    		comboPanel.setVisible(true);
    		browse.setVisible(false);
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            int result = fileChooser.showOpenDialog(comboPanel);
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
            }
        }
    	
        if(e.getSource() == button1) {
            listPanel.setVisible(true);
            comboPanel.setVisible(false);
            panel_1.setVisible(false);
            panel_2.setVisible(true);
            button1.setText("Start Again");
            panel.add(logs);
            panel.add(Exit);
            JOptionPane.showMessageDialog(null, "WARNING!\nPlease do not disconnect any device.\nProcess will start when you press OK.\nYou will be notified once the process ends. \n", "alert", JOptionPane.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
            Thread thread = new Thread(new ShellScriptThread());
            thread.start();

        }

        if(list1 == e.getSource()) {
        	panel.setVisible(true);
            String str = (String) list1.getSelectedItem();
            System.out.println(str);
            script.unzip(selectedFile.getAbsolutePath(), script.getKeyFromValue(script.make_map(script.parse_file()), str));
            
        }
        
        
    }
}

