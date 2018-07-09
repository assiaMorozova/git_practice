package main;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Dimension2D;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import contract.aspirant.ContractAsp;
import contract.eng.ContractEng;
import contract.law.ContractLaw;
import contract.med.ContractMED;
import contract.ru.ContractRUS;
import contract.ua.ContractUA;
import net.miginfocom.swing.MigLayout;
import server.RMIServerConnection;
import utilities.Info;
 
public class TestFrame extends JFrame {
 
    static int i = 0;
    
    JMenuBar mb = new JMenuBar();
    public static float rateUsd =27.16f;
 
    public TestFrame() {
 
        super("Контракты");
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createMenu();
        
        Font font = new Font("Verdana", Font.PLAIN, 20);
        
        final JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(font);
 
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());
 
        
    
	
       
        
        
        content.add(tabbedPane, BorderLayout.CENTER);
 
        try {
			tabbedPane.addTab("Украинцы ",( new ContractUA()).createTab());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
        try {
			tabbedPane.addTab("Иностранцы ",( new ContractRUS()).createTab());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
        
        try {
			tabbedPane.addTab("Медики англ",( new ContractMED()).createTab());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
        
        
        try {
			tabbedPane.addTab("Англ. форма ",( new ContractEng()).createTab());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
        
        
		try {
			tabbedPane.addTab("Аспиранты ",( new ContractAsp()).createTab());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
        getContentPane().add(content);
        
        Dimension sSize = Toolkit.getDefaultToolkit ().getScreenSize ();
        setSize(new Dimension(400, 400));
        pack();
       
        setVisible(true);
        RMIServerConnection.instantiateServer();
    }
 
    public void createMenu(){
    	
         setJMenuBar(mb);
         JMenu file = new JMenu("Файл");
         JMenuItem mitem = new JMenuItem("Соединить с сервером");
         JMenuItem mitemRate    = new JMenuItem("Задать курс");
         JMenu rate = new JMenu("Курс $: "+rateUsd);
         file.add(mitem);
         file.add(mitemRate);
         mb.add(file);
         mb.add(rate);
         this.setJMenuBar(mb);
         mitem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				   RMIServerConnection.instantiateServer();
				
			}
		});
         
         mitemRate.addActionListener(new ActionListener() {
 			
 			@Override
 			public void actionPerformed(ActionEvent e) {
 				 
 				final JDialog jd = new JDialog(TestFrame.this);
 				jd.setLayout(new MigLayout());
 				JPanel infoPanel = new JPanel();

 		        JLabel info = new JLabel("Задайте курс валюты.");
 		        info.setHorizontalTextPosition(SwingConstants.CENTER);
 		       
 		       jd.getContentPane().add(infoPanel);

 		        JPanel coursePane = new JPanel();
 		        JLabel jlCourse = new JLabel("Курс $ :");
 		        jlCourse.setHorizontalTextPosition(SwingConstants.CENTER);
 		        coursePane.add(jlCourse);
 		        final JTextField jtfRate = new JTextField(5);
 		    
 		        coursePane.add(jtfRate);
 		        jd.getContentPane().add(coursePane);
 		       JButton jbDo;
 		        JPanel btnPane = new JPanel();
 		        btnPane.add(jbDo = new JButton("OK"));
 		      
 		       jd.getContentPane().add(btnPane);
 		       
 		      
 		        jbDo.addActionListener(new ActionListener() {

 		                public void actionPerformed (ActionEvent ae){
 		                try {
 		                   
 		                   
 		                    mb.getMenu(1).setText("Курс $: "+jtfRate.getText());
 		                    rateUsd = Float.parseFloat(jtfRate.getText());
 		                    jd.dispose();
 		                } catch (Exception e) {
 		                    throw new RuntimeException(e);
 		                }
 		            }


 		        });
 		        
 		       jd.setModal(true);
		        jd.pack();
		       jd.setVisible(true);
 		        
 		       
 		       
 				
 			}
 		});
    }
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame.setDefaultLookAndFeelDecorated(true);
                readConfig();
                new TestFrame();
            }
        });
    }
    
    private static void readConfig()
    {
    	InputStream input = null;
    	BufferedReader bf =null;
    	try {

    		input = new FileInputStream("config.ini");
    		
    		bf= new BufferedReader(new InputStreamReader(input));
    		String str;
    		if((str=bf.readLine())!=null)
    		{
    			Info.WORD_PATH=str;
    			
    		}
    		if((str=bf.readLine())!=null)
    		{
    			if(!str.equals(""))
    				Info.PATERN_PATH=str;
    			
    		}
    		if((str=bf.readLine())!=null)
    		{
    			if(!str.equals(""))
    				Info.OUTPUT_FILE_PATH=str;
    			
    		} 
    		
    		  
    		
    	} catch (IOException ex) {
    		ex.printStackTrace();
    	} finally {
    		if (input != null) {
    			try {
    				
    				input.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    		if (bf != null) {
    			try {
    				
    				bf.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    	}
    	
    }
}