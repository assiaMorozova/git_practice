package contract.aspirant;
//import com.sun.xml.internal.bind.v2.TODO;
//import com.sun.xml.internal.bind.v2.util.ByteArrayOutputStreamEx;
import net.miginfocom.swing.MigLayout;
import server.DBWorker;
import univer.oko.Message;
import univer.oko.student.Entrant;
import utilities.CDFormattedTextField;
import utilities.Countries;
import utilities.DateUtilities;
import utilities.Faculties;
import utilities.Firms;
import utilities.Info;
import utilities.NumberUtilities;
import word.WordOperation;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Section;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class ContractAsp  {

    private JPanel form;
    private JPanel contractPanel;

    
float years=0;
    /**
     * Labels
     */

    private JLabel labelContractNumber;
    private JLabel labelCountry;
    private JLabel labelFaculty;
    private JLabel labelSpeciality;
   
   
 
    private JTextField textPriceValue1;
    private JTextField textPriceValue2;
    private JLabel labelStart;
    private JLabel labelEnd;
 
   
    private JLabel labelPhone;
    private JLabel labelPrice;
    private JLabel labelPrice2;
    private JLabel labelForm;
    private JLabel labelYear;
    private JLabel labelClient;
    private JLabel labelClientFIO;
    private JLabel labelClientPass;
    private JLabel labelClientAdress;
    private JLabel labelClientCode;
    private JLabel labelLevel;
    private JLabel labelName;
    private JLabel labelPassNumber;
    private JLabel labelContractDate;
    /**
     * Comboboxes
     */

    private JComboBox boxCountry;
    private JComboBox boxFaculty;
    private JComboBox boxForma;
    private JComboBox boxLevel;

    /**
     * TextFields
     */


    CDFormattedTextField textContractDate;

    private JTextField textContractNumber;
    private CDFormattedTextField textStart;
    private CDFormattedTextField textEnd;
     private JTextField textSpeciality;

    private JTextField textPhone;
    private JTextField textYear;
    private JTextField textClientFIO;
    private JTextField textClientPass1;
    private JTextField textClientAdress1;
    private JTextField textClientCode;
    private JTextField textPass;
    private JTextField textName;


    /**
     * Buttons
     */

    private JButton buttonSave;
 
    private JButton buttonClearAll;
    private JButton buttonSaveEntrance;
    /**
     * Variables for DOC
     */

    private String country;
    private String contractNumber;
    
    private String faculty;
    private String speciality;

    private String volume;
    private String price1;
    private String start;
    private String end;

    private String phone;
    
    private String clientFIO;
    private String clientPass;
    private String clientAdress;
    private String clientCode;
    private String price2;
    private String forma;
    private String name;
    private String pass;
    public String OUTPUT_FILE = "new.doc";
    private String contractDate;
    /**
     * Variables for comboboxes
     */

 

    
   // private ArrayList<String> countries = Countries.listCountriesUa;
    private Prices prices=new Prices();
  

    DefaultComboBoxModel specModel = new DefaultComboBoxModel();
    DefaultComboBoxModel progModel = new DefaultComboBoxModel();

    DateFormat dateF = new SimpleDateFormat("yyyy");
    Date dateD = new Date();

    Calendar cal = Calendar.getInstance();
    String datedate;



    /**
     * Variables for POI
     */

    private String filepathAspUA ="";
    private String filepathAspForeign ="";
    private String filepathDocUA ="";
    private String filepathDocForeign ="";
    private POIFSFileSystem fs = null;


    /**
     * Конструктор
     * @throws IOException
     */

    private void filePathConfig()
    {
    	
    	
    		  
    	filepathAspUA = Info.PATERN_PATH+"/Pattern/contractUaAspirant.doc";
  		  filepathAspForeign = Info.PATERN_PATH+"/Pattern/contractForeignAspirant.doc";
  		  filepathDocUA = Info.PATERN_PATH+"/Pattern/contractUaDoctor.doc";
  		  filepathDocForeign = Info.PATERN_PATH+"/Pattern/contractForeignDoctor.doc";
    	
    }
    public ContractAsp() throws IOException{

    	//Read prop
    //	this.setTitle("Контракт АСПИРАНТЫ");
    	filePathConfig();
    	

    	try{

    	//	readFaculties();
    		readPrices();
            initComponents();
            
    	}catch(IOException ex)
    	{
    		JOptionPane.showMessageDialog(null, ex.getMessage());

    	}
      //  this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
   //     this.setMinimumSize(new Dimension(400, 400));
      //  this.setLocationRelativeTo(null);
       //this.setResizable(false);
    //    this.pack();
    //    this.setVisible(true);
    }

    /**
     * Наполнение окна
     * @throws IOException
     */

    private void initComponents() throws IOException{

        /**
         * Labels
         */

        cal.add(Calendar.YEAR, 1);
        Date nextYear = cal.getTime();

        datedate =DateUtilities.getCurrentPaymentYearName();



        labelContractNumber = new JLabel("№ контракта *");
        //labelDate = new JLabel("Дата контракта");
     
        labelName = new JLabel("ПІБ аспиранта");
        labelPassNumber = new JLabel("Паспорт аспиранта");
        labelCountry = new JLabel("Громадянин *");
        labelFaculty = new JLabel("Факультет");
        labelSpeciality = new JLabel("Спеціальність");
       
       
        //labelVolume = new JLabel("Объем нагрузки");
        labelPrice = new JLabel("Оплата за рік");
        labelPrice2 = new JLabel("Оплата за рік (словами)");
        labelForm = new JLabel("Форма");
     
        labelClient = new JLabel("Замовник:");
        labelClientFIO = new JLabel("ПІБ Замовника* ");
        labelClientPass = new JLabel("Паспорт замовника* ");
        labelClientAdress = new JLabel("Адреса замовника* ");
        labelClientCode = new JLabel("Ідент. код замовника *");
        labelStart = new JLabel("Термін з");
        labelEnd = new JLabel("Термін по");
        //labelSeries = new JLabel("Паспорт серия *");
        labelLevel = new JLabel("Уровень:");
        labelPhone = new JLabel("Телефон *");
        

        textPriceValue1 = new JTextField("",15);
        textPriceValue2 = new JTextField("", 35);
        
        textPass = new JTextField("", 50);
        textName = new JTextField("", 50);
        
        ////////////////////////////////////////init contract panel
        
        contractPanel = new JPanel();
        labelContractDate = new JLabel("Дата контракта");
        labelYear = new JLabel("Навчальний рік");
        
        textContractNumber = new JTextField("", 10);
        
        textContractDate =new CDFormattedTextField();
        textContractDate.setColumns(8);
        textContractDate.setText(DateUtilities.applicationFormat(DateUtilities.getCurrentDate()));
        
        textYear = new JTextField(datedate, 10);
        
        textContractNumber.setFont(NumberUtilities.font);
        textContractDate.setFont(NumberUtilities.font);
        textYear.setFont(NumberUtilities.font);
             
        labelContractDate.setFont(NumberUtilities.font);
        labelYear.setFont(NumberUtilities.font);
        
        contractPanel.add(textContractNumber);
        contractPanel.add(labelContractDate);
        contractPanel.add(textContractDate);
        contractPanel.add(labelYear);
        contractPanel.add(textYear);
       
        
        ////////////////////////////////////////////

        /**
         * Buttons
         */

        buttonSave = new JButton("Зберегти");
        buttonSaveEntrance = new JButton("Создать карту");
        buttonClearAll = new JButton("Стерти все");

        /**
         * Comboboxes
         */

        boxCountry = new JComboBox(Countries.listCountriesUa.toArray());
        AutoCompleteDecorator.decorate(boxCountry);
        boxCountry.setEditable(true);
        boxFaculty = new JComboBox(Faculties.faculties.toArray());
        boxFaculty.setSelectedItem(" ");
       textSpeciality = new JTextField("", 50);
       
        boxForma = new JComboBox(new String[]{"Денною", "Заочною"});
        boxLevel= new JComboBox(new String[]{"Доктор філософії", "Доктор наук"});
        /**
         * TextFields
         */

      
       
      
        Calendar c = Calendar.getInstance();
        c.setTime(DateUtilities.getCurYearBegin());
        c.add(Calendar.MONTH, 1);
        
        textStart = new CDFormattedTextField();
        textStart.setColumns(8);
        textStart.setText(DateUtilities.applicationFormat(c.getTime()));
        textEnd = new CDFormattedTextField();
        textEnd.setColumns(8);
      
        textPhone = new JTextField("", 15);
        textYear = new JTextField(datedate, 10);
        textClientFIO = new JTextField("", 50);
        textClientAdress1 = new JTextField("", 50);
        textClientPass1 = new JTextField("", 50);
        textClientCode = new JTextField("", 12);

        /**
         * Panel
         */
        form = new JPanel();
        form.setBorder(BorderFactory.createTitledBorder("Контракт"));
        MigLayout layout = new MigLayout();

        form.setLayout(layout);

       
        form.add(labelName);
        form.add(textName, "wrap");
        form.add(labelPassNumber);
        form.add(textPass, "wrap");

        form.add(labelClient, "wrap");
        form.add(labelClientFIO);
        form.add(textClientFIO, "wrap");
        form.add(labelClientPass);
        form.add(textClientPass1, "wrap");
        //form.add(textClientPass2, "wrap");
        form.add(labelClientAdress);
        form.add(textClientAdress1, "wrap");
        //form.add(textClientAdress2, "wrap");
        form.add(labelClientCode);
        form.add(textClientCode, "wrap");


        form.add(labelPhone);
        form.add(textPhone, "wrap");
        form.add(labelLevel);
        form.add(boxLevel,"wrap");
        form.add(labelForm);
        form.add(boxForma, "wrap");
        form.add(labelCountry);
        form.add(boxCountry, "wrap");
        form.add(labelFaculty);
        form.add(boxFaculty, "wrap");
        form.add(labelContractNumber);
        form.add(contractPanel, "wrap");
        form.add(labelSpeciality);
        form.add(textSpeciality, "wrap");
       // form.add(labelProgramm);
       // form.add(boxProgramm, "wrap");
        //form.add(labelVolume);
        //form.add(textVolume, "wrap");
       // form.add(labelYear);
       // form.add(textYear, "wrap");

        form.add(labelPrice);
        form.add(textPriceValue1, "wrap");
        form.add(labelPrice2);
        form.add(textPriceValue2, "wrap");
        form.add(labelStart);
        form.add(textStart, "wrap");
        form.add(labelEnd);
        form.add(textEnd, "wrap");

        form.add(buttonSave);
        form.add(buttonClearAll);
      //  form.add(buttonSaveEntrance);
        setContractNumber();
        //this.add(form);
        setPrices((String)boxForma.getSelectedItem(), (String)boxCountry.getSelectedItem(),(String)boxLevel.getSelectedItem());
        
		c.setTime(textStart.getDate());
		c.add(Calendar.YEAR, 4);
		c.add(Calendar.DAY_OF_MONTH, -1);
		textEnd.setText(DateUtilities.applicationFormat(c.getTime()));
       
		textStart.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				
				
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				
				Calendar c = Calendar.getInstance();
				c.setTime(textStart.getDate());
				if(boxLevel.getSelectedIndex()==0)
				{
					c.add(Calendar.YEAR, 4);
					c.add(Calendar.DAY_OF_MONTH, -1);
					years = 4;
				}
				else
				{
					c.add(Calendar.YEAR,2);
					c.add(Calendar.DAY_OF_MONTH, -1);
					years = 2;
				}
				textEnd.setText(DateUtilities.applicationFormat(c.getTime()));
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
			
				
			}
		});
       /* textStart.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				Calendar c = Calendar.getInstance();
				c.setTime(textStart.getDate());
				c.add(Calendar.YEAR, 4);
				c.add(Calendar.DAY_OF_MONTH, -1);
				textEnd.setText(DateUtilities.applicationFormat(c.getTime()));
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
			
				
			}
		}); 
        
        */

		 buttonSaveEntrance.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						Entrant st= createStudent();
						
						if(st!=null){
							try{
							Message m = DBWorker.instance().saveEntrant(st);
							JOptionPane.showMessageDialog(null,m.getText());
							}catch (RemoteException ex){
								  JOptionPane.showMessageDialog(null,ex.getMessage());
							}
						}
							
						
					}
				});
		 
		 
        
        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(boxFaculty.getSelectedItem()==null)
            		JOptionPane.showMessageDialog(null, "Не все обязательные поля заполнены!!!");
            	else
            	{

            		
            	 name = textName.getText();
            	 pass = textPass.getText();
            		contractNumber = textContractNumber.getText();
              	  contractDate = textContractDate.getText();
              
                country = boxCountry.getSelectedItem().toString();
                faculty = boxFaculty.getSelectedItem().toString();
               
                speciality = textSpeciality.getText();
              
                //volume = textVolume.getText();
                price1 = textPriceValue1.getText();
                start = textStart.getText();
                end = textEnd.getText();
              
                phone = textPhone.getText();
                price2 = textPriceValue2.getText();
                forma = boxForma.getSelectedItem().toString();
                clientFIO = textClientFIO.getText();
         
               	clientPass= textClientPass1.getText();
                
                clientAdress = textClientAdress1.getText();
                clientCode = textClientCode.getText();

                
                if(name.equals("")){
                     name=clientFIO;
                     pass=clientPass;
                }
                
                try{
                	if(country.equals("України") && boxLevel.getSelectedIndex()==0)
                		fs = new POIFSFileSystem(new FileInputStream(filepathAspUA));
                	if(!country.equals("України") && boxLevel.getSelectedIndex()==0)
                		fs = new POIFSFileSystem(new FileInputStream(filepathAspForeign));
                	if(country.equals("України") && boxLevel.getSelectedIndex()==1)
                		fs = new POIFSFileSystem(new FileInputStream(filepathDocUA));
                	if(!country.equals("України") && boxLevel.getSelectedIndex()==1)
                		fs = new POIFSFileSystem(new FileInputStream(filepathDocForeign));
                	
                    HWPFDocument doc = new HWPFDocument(fs);
                    doc = replaceText(doc, "ContractNumber", contractNumber);
                    doc = replaceText(doc, "contractDate", contractDate);
                   
                    doc = replaceText(doc, "Studname", name);
                    doc = replaceText(doc, "pass", pass);
                    doc = replaceText(doc, "country", country);
                    doc = replaceText(doc, "faculty", faculty);
                    doc = replaceText(doc, "speciality", speciality);
                   
                    doc = replaceText(doc, "priceFigure", price1);
                    doc = replaceText(doc, "start", start);
                    doc = replaceText(doc, "end", end);
                   
                    doc = replaceText(doc, "phone", phone);
                    doc = replaceText(doc, "Curyear", datedate);
                    doc = replaceText(doc, "priceString", price2);
                    doc = replaceText(doc, "forma", forma);
                    doc = replaceText(doc, "clientFIO", clientFIO);
                    doc = replaceText(doc, "clientPass", clientPass);
                    doc = replaceText(doc, "clientAdress", clientAdress);
                    doc = replaceText(doc, "clientCode", clientCode);
                    doc = replaceText(doc, "credits", volume);
                                      
                    doc=replaceText(doc,"dayIn",textStart.getText().split("/")[0]);
                    doc=replaceText(doc,"monthIn",textStart.getText().split("/")[1]);
                    doc=replaceText(doc,"yearIn",textStart.getText().split("/")[2]);
                    doc=replaceText(doc,"dayOut",textEnd.getText().split("/")[0]);
                    doc=replaceText(doc,"monthOut",textEnd.getText().split("/")[1]);
                    doc=replaceText(doc,"yearOut",textEnd.getText().split("/")[2]);
                    
                  
                    if(!clientFIO.equals("")&&!Info.OUTPUT_FILE_PATH.equals("."))
                    	OUTPUT_FILE=Info.OUTPUT_FILE_PATH+"/ASPIRANT/"+clientFIO.trim()+".doc";
                    else
                    	OUTPUT_FILE="new.doc";
                    	
                   
                 
                   OUTPUT_FILE= WordOperation.saveWord(OUTPUT_FILE, doc);
                  doc.close();
                   WordOperation.openWord(OUTPUT_FILE);
                }
                catch(FileNotFoundException ex){
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null,ex.getMessage());
                }
                catch (IOException ex){
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null,ex.getMessage());
                }
              //  dispose();
            }
            }
        });

      

        buttonClearAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	textName.setText("");
            	textPass.setText("");
                //textContractNumber.setText("");
              
              // boxCountry.setSelectedItem("");
                textPhone.setText("");
                boxFaculty.setSelectedIndex(0);
                boxForma.setSelectedIndex(0);
                boxCountry.setSelectedIndex(0);
                //boxLevel.setSelectedItem(" ");
                //textVolume.setText("");
                boxLevel.setSelectedIndex(0);
                textPhone.setText("");
              //  textDate.setText("");
              //  textStart.setText("");
              //  textEnd.setText("");
                textSpeciality.setText("");
                textClientFIO.setText("");
                textClientPass1.setText("");
                textClientAdress1.setText("");
                textClientCode.setText("");
             
                setPrices((String)boxForma.getSelectedItem(), (String)boxCountry.getSelectedItem(),(String)boxLevel.getSelectedItem());
             
                
            }
        });

        
        boxForma.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setPrices((String)boxForma.getSelectedItem(), (String)boxCountry.getSelectedItem(),(String)boxLevel.getSelectedItem());
				
			}
		});
        
        boxLevel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setPrices((String)boxForma.getSelectedItem(), (String)boxCountry.getSelectedItem(),(String)boxLevel.getSelectedItem());
				Calendar c = Calendar.getInstance();
				c.setTime(textStart.getDate());
				if(boxLevel.getSelectedIndex()==0)
				{
					c.add(Calendar.YEAR, 4);
					c.add(Calendar.DAY_OF_MONTH, -1);
				}
				else
				{
					c.add(Calendar.YEAR,2);
					c.add(Calendar.DAY_OF_MONTH, -1);
				}
				textEnd.setText(DateUtilities.applicationFormat(c.getTime()));
				setContractNumber();
			}
		});
       
        boxCountry.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setPrices((String)boxForma.getSelectedItem(), (String)boxCountry.getSelectedItem(),(String)boxLevel.getSelectedItem());
				setContractNumber();
			}
		});
        
        boxFaculty.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setContractNumber();
				
			}
		});
      
       
        for (int i=0;i<form.getComponentCount();i++)
        	  form.getComponent(i).setFont(NumberUtilities.font);  

    }

    protected void setContractNumber(){
    	if(boxCountry.getSelectedItem().equals("України")){
    		if(boxLevel.getSelectedIndex()==0) //кандидат наук (доктор философии)
    			textContractNumber.setText("А"+Faculties.listCodesUkr.get(boxFaculty.getSelectedIndex()));
    		else//доктор наук
    			textContractNumber.setText("Д"+Faculties.listCodesUkr.get(boxFaculty.getSelectedIndex()));
    	}
    	else{
    		if(boxLevel.getSelectedIndex()==0)
    			textContractNumber.setText("А"+Faculties.listCodesForeign.get(boxFaculty.getSelectedIndex()));
    		else
    			textContractNumber.setText("Д"+Faculties.listCodesForeign.get(boxFaculty.getSelectedIndex()));
    	}
    }
 
    public Entrant createStudent()
    {
    	
    	
    	if(boxCountry.getSelectedIndex()<0)
    	{
    		JOptionPane.showMessageDialog(null, "Не все обязательные поля заполнены!!! Не выбрана страна");
    		return null;
    	}
    	if(boxFaculty.getSelectedItem()==null || boxFaculty.getSelectedIndex()==0)
    		JOptionPane.showMessageDialog(null, "Не все обязательные поля заполнены!!! Не выбран факультет");
    	else
    	{
    		//разделить ФИО
	    	Entrant s = new Entrant();
	    	 
        	  
	    	s.StudSurname = textName.getText().split(" ")[0];
	    	s.StudName = textName.getText().split(" ")[1];
	    	s.StudMiddlename= textName.getText().split(" ")[2];
	    
	    	if(boxLevel.getSelectedIndex()==0){ //кандидат наук (доктор философии)
	    		s.Ref_LtoP=8;
	    		if(boxForma.getSelectedIndex()==0) //аспирант-дневая
		    		s.Ref_EducForm=5;
		    	else
		    		s.Ref_EducForm=6; //аспирант_заочная
	    	}
	    	else{
	    		s.Ref_LtoP=7; //doctor
	    		s.Ref_EducForm=11;//докторантура
		    
	    	}
	    	
	    	
	    	
	    	if(boxCountry.getSelectedIndex()>=0)
	    		s.Ref_Country=Countries.listCountriesID.get(boxCountry.getSelectedIndex());
	    	s.Ref_ArrivalLine=1;
	    	
	    	
	    	s.Ref_Language=1;
	    	s.Course = 1;
	    	s.Years=years;
	    //	s.Ref_Direction =idDirection;??????
	    	s.contractNumber=textContractNumber.getText();
	    	s.contractValue=Double.parseDouble(textPriceValue1.getText());
	    	s.Ref_ContractType=1;
	    
	    	return s;
    	}
    	return null;
    	
    }
     
    protected void setPrices(String form, String country,String level) {
	
                	 textPriceValue1.setText("");
                     textPriceValue2.setText("");
                   
                     volume="______";
                     
                	 if (form.equals("Денною") && country.equals("України") && level.equals("Доктор філософії"))
                	 {
                		  textPriceValue1.setText(prices.priceDayUkrDigits);
                          textPriceValue2.setText(prices.priceDayUkrString);
                         
                          volume=prices.creditsDay;
               	  	 }
                	 if (form.equals("Заочною") && country.equals("України") && level.equals("Доктор філософії"))
                	  {
                		 
                          textPriceValue1.setText(prices.priceZaoUkrDigits);
                          textPriceValue2.setText(prices.priceZaoUkrString);
                        
                          volume=prices.creditsZao;
                	  }
                	 
                	 if (form.equals("Денною") && !country.equals("України") && level.equals("Доктор філософії")){
                		 textPriceValue1.setText(prices.priceDayForeignDigits);
                         textPriceValue2.setText(prices.priceDayForeignString);
                        
                         volume=prices.creditsDay;
               	  }
                	 
                	  if (form.equals("Заочною") && !country.equals("України") && level.equals("Доктор філософії")){
                			 textPriceValue1.setText(prices.priceZaoForeignDigits);
                             textPriceValue2.setText(prices.priceZaoForeignString);
                           
                             volume=prices.creditsZao;
                	  }
                	  
                	  if (form.equals("Денною") && country.equals("України") && level.equals("Доктор наук"))
                 	 {
                 		  textPriceValue1.setText(prices.priceDoctorDayUkrDigits);
                          textPriceValue2.setText(prices.priceDoctorDayUkrString);
                        
                          volume="______";
                	  	 }		  
                	  if (form.equals("Денною") && !country.equals("України") && level.equals("Доктор наук")){
                 		 textPriceValue1.setText(prices.priceDoctorDayForeignDigits);
                          textPriceValue2.setText(prices.priceDoctorDayForeignString);
                       
                          volume="_____";
                	  }
		
	}
				
  
    /**
     * Функция замены текста в Word-файле
     * @param doc
     * @param findText
     * @param replaceText
     * @return
     */

    private static HWPFDocument replaceText(HWPFDocument doc, String findText, String replaceText){
        Range r1 = doc.getRange();

        for (int i = 0; i < r1.numSections(); ++i ) {
            Section s = r1.getSection(i);
            for (int x = 0; x < s.numParagraphs(); x++) {
                Paragraph p = s.getParagraph(x);
                for (int z = 0; z < p.numCharacterRuns(); z++) {
                    CharacterRun run = p.getCharacterRun(z);
                    String text = run.text();
                    if(text.contains(findText)) {
                        run.replaceText(findText, replaceText);
                    }
                }
            }
        }
        return doc;
    }

    /**
     * Функция сохранения Word-файла
     * @param filePath
     * @param doc
     * @throws FileNotFoundException
     * @throws IOException
     */

  
    
   
   
    
    public void readPrices() {
    	  BufferedReader bufRead=null;
    	try
    	{
    		FileReader input = new FileReader(Info.PATERN_PATH+"/asp_prices.csv");
            bufRead = new BufferedReader(input);
   	        String myLine;
            int k=1;
    	       
    	        while((myLine = bufRead.readLine())!= null){
    	        	
    	        	String[] arr = myLine.split(";");
    	        	switch(k)
    	        	{
    	        	case 1:
    	        		prices.priceDayUkrDigits=arr[2];
    	        		prices.priceDayUkrString=arr[3];
    	        		
    	        		prices.creditsDay=arr[4];
    	        		k++;break;
    	        	case 2:
    	        		prices.priceZaoUkrDigits=arr[2];
    	        		prices.priceZaoUkrString=arr[3];
    	        		
    	        		prices.creditsZao=arr[4];
    	        		k++;break;
    	        	case 3:
    	        		prices.priceDayForeignDigits=arr[2];
    	        		prices.priceDayForeignString=arr[3];
    	        		
    	        		k++;break;
    	        	case 4:
    	        		prices.priceZaoForeignDigits=arr[2];
    	        		prices.priceZaoForeignString=arr[3];
    	        		
    	        		k++;break;
    	        	
	    	        case 5:
		        		prices.priceDoctorDayUkrDigits=arr[2];
		        		prices.priceDoctorDayUkrString=arr[3];
		        		
		        		k++;break;
	        	
		    	case 6:
		    		prices.priceDoctorDayForeignDigits=arr[2];
		    		prices.priceDoctorDayForeignString=arr[3];
		    		
		    		k++;break;
    	
    	            }
    	    
    	        }
       
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		
    	}
    	finally
    	{
    		if(bufRead!=null)
  				try {
  					bufRead.close();
  				} catch (IOException e) {
  					
  					e.printStackTrace();
  				}
    		
    	}
  }
    /*
    private  void saveWord(String filePath, HWPFDocument doc) throws FileNotFoundException, IOException{
        FileOutputStream out = null;
        try{
            out = new FileOutputStream(filePath);
            doc.write(out);
                    
        }
        catch(IOException ex)
        {
        	try
        	{
        	OUTPUT_FILE = "new.doc";
            out = new FileOutputStream(OUTPUT_FILE);
            doc.write(out);
        	}
        	catch(IOException e)
            {
        	JOptionPane.showMessageDialog(null, "Файл new.doc открыт. Закройте его и повторите операцию");
            }
        }
        finally{
        	if(out!=null)
        		out.close();
            
        }
    }
    
    private void openWord() {
        Runtime rt = Runtime.getRuntime();
        String prefix = "";
        String prefixCurrentDir = "file:" + System.getProperty("user.dir") + System.getProperty("file.separator");
        if(OUTPUT_FILE.equals("new.doc"))
        	prefix = prefixCurrentDir;
        try {
        	//System.out.println(CDUtilities.WORD_PATH + " \"" + prefix + "test.doc" + "\"");
            rt.exec(Info.WORD_PATH + " \"" + prefix + OUTPUT_FILE + "\"");
        }
        catch (IOException e) {
        	 
        	  
        	JOptionPane.showMessageDialog(null, "Не возможно открыть"+e.getMessage());
        }
    }
    
*/
    /**
     * Запуск приложения
     * @param args
     */

   
    public JPanel createTab()
    {
    	return form;
    }

}
