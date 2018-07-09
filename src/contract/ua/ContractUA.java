package contract.ua;
//import com.sun.xml.internal.bind.v2.TODO;
//import com.sun.xml.internal.bind.v2.util.ByteArrayOutputStreamEx;
import net.miginfocom.swing.MigLayout;
import receipt.ReceiptBuilder;
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
import org.apache.poi.ss.usermodel.Textbox;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by Слава on 20.05.2017.
 */
public class ContractUA  {

    private JPanel form;
    private JPanel contractPanel;
    private JPanel buttonPanel;
    private JLabel labelDateOfBirth;
    private CDFormattedTextField textDateOfBirth;
    

    /**
     * Labels
     */

    private JLabel labelContractNumber;
    private JLabel labelDate;
    private JLabel labelName;
    private JLabel labelCountry;
    private JLabel labelFaculty;
    private JLabel labelSpeciality;
    private JLabel labelProgramm;
    private JLabel labelLevel;
    //private JLabel labelVolume;
    private JTextField textPriceValue1;
    private JTextField textPriceValue2;
    private JLabel labelStart;
    private JLabel labelEnd;
    //private JLabel labelSeries;
    private JLabel labelPassNumber;
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

    /**
     * Comboboxes
     */


    private JComboBox boxFaculty;
    private JComboBox boxSpeciality;
    private JComboBox boxProgramm;
    private JComboBox boxLevel;
    private JComboBox boxForma;

    /**
     * TextFields
     */

    private JTextField textContractNumber;
    CDFormattedTextField textDate;
    private JTextField textName;
    private JTextField textVolume;
    private CDFormattedTextField textStart;
    private CDFormattedTextField textEnd;
    private CDFormattedTextField textContractDate;
    private JTextField textPass;
    private JTextField textPhone;
    private JTextField textYear;
    private JTextField textClientFIO;
    private JTextField textClientPass1;
    private JTextField textClientAdress1;
    private JTextField textClientCode;


    /**
     * Buttons
     */

    private JButton buttonSave;
    private JButton buttonClearAll;
    private JButton buttonSaveEntrance;
	private JButton buttonReceipt;

    /**
     * Variables for DOC
     */

    private String country;
    private String contractNumber;
    private String contractDate;
    private String name;
    private String faculty;
    private String speciality;
    private String programm;
    private String level;
    private String volume;
    private String price1;
    private String start;
    private String end;
    private String pass;
    private String phone;
    //private String date;
    private String clientFIO;
    private String clientPass;
    private String clientAdress;
    private String clientCode;
    private String price2;
    private String price5;
    private String price6;
    private String forma;
    private int totalSum;

    public String OUTPUT_FILE = "new.doc";
  
    /**
     * Variables for comboboxes
     */


    private String[] faculties = new String[22];


    private ArrayList<ArrayList<Speciality>> specialities = new ArrayList<ArrayList<Speciality>>();
    private ArrayList<Speciality> specs = new ArrayList<Speciality>();


    Map<String, ArrayList<Speciality>> treeMap = new TreeMap<String, ArrayList<Speciality>>();

    DefaultComboBoxModel specModel = new DefaultComboBoxModel();
    DefaultComboBoxModel progModel = new DefaultComboBoxModel();

    DateFormat dateF = new SimpleDateFormat("yyyy");
    Date dateD = new Date();

    Calendar cal = Calendar.getInstance();
    String datedate;



    /**
     * Variables for POI
     */

    private String filepath =Info.PATERN_PATH+"/Pattern/contractUa.doc";
    private String filepathMag =Info.PATERN_PATH+ "/Pattern/ContractUaMag.doc";

    private POIFSFileSystem fs = null;

    private float yearsEducation=4;
    private int   idDirection ;

    /**
     * Конструктор
     * @throws IOException
     */

  
    public ContractUA() throws IOException{

    

    	try{
     
        readPrices();

        initComponents();
    	}catch(IOException ex)
    	{
    		JOptionPane.showMessageDialog(null, ex.getMessage());

    	}
     
    }

    /**
     * Наполнение окна
     * @throws IOException
     */

    private void initComponents() throws IOException{

        /**
         * Labels
         */

     
       
        datedate =DateUtilities.getCurrentPaymentYearName();


       labelContractNumber = new JLabel("№ контракта *");
        labelDate = new JLabel("Дата контракта");
        labelName = new JLabel("ПІБ студента*");
      //  labelCountry = new JLabel("Громадянин *");
      
        labelFaculty = new JLabel("Факультет");
        labelSpeciality = new JLabel("Спеціальність");
        labelProgramm = new JLabel("Освітня програма");
        labelLevel = new JLabel("Ступінь");
        //labelVolume = new JLabel("Объем нагрузки");
        labelPrice = new JLabel("Оплата за рік");
        labelPrice2 = new JLabel("Оплата за рік (словами)");
        labelForm = new JLabel("Форма");
        labelYear = new JLabel("Навчальний рік");
        labelClient = new JLabel("Замовник:");
        labelClientFIO = new JLabel("ПІБ Замовника ");
        labelClientPass = new JLabel("Паспорт замовника ");
        labelClientAdress = new JLabel("Адреса замовника* ");
        labelClientCode = new JLabel("Ідент. код замовника *");


        textPriceValue1 = new JTextField("",15);
        textPriceValue2 = new JTextField("", 35);
        labelStart = new JLabel("Термін з");
        labelEnd = new JLabel("Термін по");
        //labelSeries = new JLabel("Паспорт серия *");
      
        labelPhone = new JLabel("Телефон *");

        /**
         * Buttons
         */

        buttonSave = new JButton("Зберегти");
       
        buttonClearAll = new JButton("Стерти все");
        buttonSaveEntrance = new JButton("Создать карту");
        buttonReceipt = new JButton("Квитанция");

        /**
         * Comboboxes
         */

      //  boxCountry = new JComboBox(Countries.listCountriesUa.toArray());
       // AutoCompleteDecorator.decorate(boxCountry);
       // boxCountry.setEditable(true);
        boxFaculty = new JComboBox(faculties);
        boxFaculty.setSelectedItem(" ");
        boxSpeciality = new JComboBox();
        boxSpeciality.setModel(specModel);
        boxProgramm = new JComboBox();
        boxProgramm.setModel(progModel);
        boxLevel = new JComboBox(new String[] {"Бакалавр", "Магістр","Магістр (ОНП)"});
        boxForma = new JComboBox(new String[]{"Денною", "Заочною"});

        /**
         * TextFields
         */

        textContractNumber = new JTextField("", 10);
        textDate = new CDFormattedTextField();
        textDate.setColumns(6);
        textName = new JTextField("", 50);
        //textVolume = new JTextField("", 5);
        textStart = new CDFormattedTextField();
        textStart.setColumns(10);
        textStart.setText(DateUtilities.applicationFormat(DateUtilities.getCurYearBegin()));
        textEnd = new CDFormattedTextField();
        textEnd.setColumns(10);
       
        textPhone = new JTextField("", 15);
        textYear = new JTextField(datedate, 10);
        textClientFIO = new JTextField("", 50);
        textClientAdress1 = new JTextField("", 50);
        textClientPass1 = new JTextField("", 50);
        textClientCode = new JTextField("", 12);

        /**
         * Panel
         */
        
        
     
        ///////--- PASPORT PANEL --------------/////////
        
        buttonPanel = new JPanel();
        
        labelDateOfBirth = new JLabel("Дата народження");
        textDateOfBirth =new CDFormattedTextField();
        textDateOfBirth.setColumns(8);
      
        textPass = new JTextField("", 50);
        labelPassNumber = new JLabel("Паспорт студента *");
       
        /*labelDateOfBirth.setFont(NumberUtilities.font);
        textDateOfBirth.setFont(NumberUtilities.font);
        textPass.setFont(NumberUtilities.font);
        labelPassNumber.setFont(NumberUtilities.font);*/
        
      //  pasportPanel.add(textPass);
    //    pasportPanel.add(labelDateOfBirth);
        //pasportPanel.add(textDateOfBirth);
        
       
        /////////////////////////////////////
    
        
        form = new JPanel();
        form.setBorder(BorderFactory.createTitledBorder("Контракт"));
        MigLayout layout = new MigLayout();

        form.setLayout(layout);

        
        //form.add(labelContractNumber);
        //form.add(textContractNumber, "wrap");
        //form.add(labelDate);
        //form.add(textDate, "wrap");
        form.add(labelName);
        form.add(textName, "wrap");
        form.add(labelPassNumber);
        
        form.add(textPass,"wrap");
        form.add(labelDateOfBirth);
        form.add(textDateOfBirth,"wrap");
        
        //form.add(pasportPanel,"wrap");
        
        form.add(labelForm);
        form.add(boxForma, "wrap");
        form.add(labelLevel);
        form.add(boxLevel, "wrap");
 

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
      //  form.add(labelCountry);
       // form.add(boxCountry, "wrap");
        form.add(labelFaculty);
        form.add(boxFaculty, "wrap");
        form.add(labelSpeciality);
        form.add(boxSpeciality, "wrap");
        form.add(labelProgramm);
        form.add(boxProgramm, "wrap");
      //  form.add(labelContractNumber);
       // form.add(textContractNumber, "wrap");
        //form.add(labelYear);
        //form.add(textYear, "wrap");
        
        //////////////////////// CONTRACT PANEL ////////////////////
        contractPanel = new JPanel();
        textContractNumber = new JTextField("", 10);
        
        textContractDate =new CDFormattedTextField();
        textContractDate.setColumns(8);
        textContractDate.setText(DateUtilities.applicationFormat(DateUtilities.getCurrentDate()));
        
        textYear = new JTextField(datedate, 10);
        
        textContractNumber.setFont(NumberUtilities.font);
        textContractDate.setFont(NumberUtilities.font);
        textYear.setFont(NumberUtilities.font);
        
        labelYear = new JLabel("Учебный год:");
       
        labelDate.setFont(NumberUtilities.font);
        labelYear.setFont(NumberUtilities.font);
        
        contractPanel.add(textContractNumber);
        contractPanel.add(labelDate);
        contractPanel.add(textContractDate);
        contractPanel.add(labelYear);
        contractPanel.add(textYear);
        
        form.add(labelContractNumber);
        form.add(contractPanel,"wrap");
        /////////////////////////////////////

        form.add(labelPrice);
        form.add(textPriceValue1, "wrap");
        form.add(labelPrice2);
        form.add(textPriceValue2, "wrap");
        form.add(labelStart);
        form.add(textStart, "wrap");
        form.add(labelEnd);
        form.add(textEnd, "wrap");

        
        form.add(buttonSave);
      //  form.add(buttonClear);
        buttonSaveEntrance.setFont(NumberUtilities.font);
        buttonReceipt.setFont(NumberUtilities.font);
        buttonClearAll.setFont(NumberUtilities.font);
        buttonPanel.add(buttonSaveEntrance);
        buttonPanel.add(buttonReceipt);
        buttonPanel.add(buttonClearAll);
        form.add(buttonPanel);
     
      
      
        
       
        buttonReceipt.addActionListener(new ActionListener() {
			
 			@Override
 			public void actionPerformed(ActionEvent e) {
 				   contractNumber = textContractNumber.getText();
 				   faculty = boxFaculty.getSelectedItem().toString();
 				   
 				  name = textName.getText();
 	              
 	             
 	                price2 = textPriceValue1.getText();
 	                
 				ReceiptBuilder.createReceipt(datedate, contractNumber, faculty, name, price2, "");
 				
 			}
 		});
        
    
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
            	if(boxFaculty.getSelectedItem()==null || boxFaculty.getSelectedIndex()==0)
            		JOptionPane.showMessageDialog(null, "Не все обязательные поля заполнены!!!");
            	else
            	{


            		 
                contractNumber = textContractNumber.getText();
                //date = textDate.getText();
                contractDate = textContractDate.getText();
                name = textName.getText();
                country = "Украйни";//boxCountry.getSelectedItem().toString();
                faculty = boxFaculty.getSelectedItem().toString();
                speciality = boxSpeciality.getSelectedItem().toString();
                programm = boxProgramm.getSelectedItem().toString();
                level = boxLevel.getSelectedItem().toString();
                //volume = textVolume.getText();
                price1 = textPriceValue1.getText();
                start = textStart.getText();
                end = textEnd.getText();
                pass = textPass.getText() ;
                phone = textPhone.getText();
                price2 = textPriceValue2.getText();
                forma = boxForma.getSelectedItem().toString();
                clientFIO = textClientFIO.getText();
                totalSum = (int)yearsEducation*Integer.parseInt(textPriceValue1.getText().trim());
                
                

        		if(yearsEducation==4){
        			price5="________________________";
        			price6="________________________";
                 	
        		 }
        		 if(yearsEducation==5){
        		        		 
                 	price5=textPriceValue1.getText()+" грн.("+price2+")";
                 	price6="________________________";
        		 }
        		 if(yearsEducation==6){
                 
        			 price5=textPriceValue1.getText()+" грн.("+price2+")";
        			 price6=textPriceValue1.getText()+" грн.("+price2+")";
        		 }
        		 
                if(clientFIO.equals("")){
                    clientFIO = name;
                    clientPass =pass;
                }
                else
                	clientPass= textClientPass1.getText();
                
                clientAdress = textClientAdress1.getText();
                clientCode = textClientCode.getText();

                try{
                	if(level.equals("Магістр") && !programm.equals("Медицина") )
                		fs = new POIFSFileSystem(new FileInputStream(filepathMag));
                	else
                		fs = new POIFSFileSystem(new FileInputStream(filepath));
                    HWPFDocument doc = new HWPFDocument(fs);
                    doc = replaceText(doc, "ContractNumber", contractNumber);
                    doc = replaceText(doc, "contractDate", contractDate);
                    doc = replaceText(doc, "Studname", name);
                    doc = replaceText(doc, "country", country);
                    doc = replaceText(doc, "faculty", faculty);
                    doc = replaceText(doc, "speciality", speciality);
                    doc = replaceText(doc, "programm", programm);
                    doc = replaceText(doc, "level", level);
                 
                    doc = replaceText(doc, "priceFigure", price1);
                    doc = replaceText(doc, "priceFive", price5);
                    doc = replaceText(doc, "priceSix", price6);
                    doc = replaceText(doc, "start", start);
                    doc = replaceText(doc, "end", end);
                    doc = replaceText(doc, "pass", pass);
                    doc = replaceText(doc, "phone", phone);
                    doc = replaceText(doc, "Curyear", datedate);
                    doc = replaceText(doc, "priceString", price2);
                    doc = replaceText(doc, "forma", forma);
                    doc = replaceText(doc, "clientFIO", clientFIO);
                    doc = replaceText(doc, "clientPass", clientPass);
                    doc = replaceText(doc, "clientAdress", clientAdress);
                    doc = replaceText(doc, "clientCode", clientCode);
                    doc = replaceText(doc, "credits", volume);
                    doc = replaceText(doc, "totalSum", totalSum+"");
                    doc = replaceText(doc, "dateOfBirth", textDateOfBirth.getText());
                    doc=replaceText(doc,"dayIn",textStart.getText().split("/")[0]);
                    doc=replaceText(doc,"monthIn",textStart.getText().split("/")[1]);
                    doc=replaceText(doc,"yearIn",textStart.getText().split("/")[2]);
                    doc=replaceText(doc,"dayOut",textEnd.getText().split("/")[0]);
                    doc=replaceText(doc,"monthOut",textEnd.getText().split("/")[1]);
                    doc=replaceText(doc,"yearOut",textEnd.getText().split("/")[2]);
                    

                    	
                    if(!name.equals("")&&!Info.OUTPUT_FILE_PATH.equals("."))
                    	OUTPUT_FILE=Info.OUTPUT_FILE_PATH+"/UKRAINE/"+name+".doc";
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
                textContractNumber.setText("");
                textName.setText("");
              // boxCountry.setSelectedItem("");
                textPhone.setText("");
                boxFaculty.setSelectedItem(" ");
                specModel.removeAllElements();
                progModel.removeAllElements();
                 boxLevel.setSelectedIndex(0);
                boxForma.setSelectedIndex(0);
                //boxLevel.setSelectedItem(" ");
                //textVolume.setText("");
                textPriceValue1.setText("");
                textPriceValue2.setText("");
                textPass.setText("");
                textPhone.setText("");
                textDate.setText("");
              //  textStart.setText("");
                textEnd.setText("");
                textClientFIO.setText("");
                textClientPass1.setText("");
                textClientAdress1.setText("");
                textClientCode.setText("");
                textPriceValue1.setText("");
                textPriceValue2.setText("");
              //  textYear.setText("");
                textDateOfBirth.setText("");
                contractNumber="";
            }
        });

        boxLevel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setPricesandYears((String)boxForma.getSelectedItem(), (String)boxLevel.getSelectedItem(),(String) boxFaculty.getSelectedItem(),(String) boxSpeciality.getSelectedItem(),(String) boxProgramm.getSelectedItem());
				
			}
		});
        boxForma.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setPricesandYears((String)boxForma.getSelectedItem(), (String)boxLevel.getSelectedItem(),(String) boxFaculty.getSelectedItem(),(String) boxSpeciality.getSelectedItem(),(String) boxProgramm.getSelectedItem());
				
			}
		});
        boxFaculty.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object o = boxFaculty.getSelectedItem();
                if(o != null && !o.equals(" ")) 
                {
                
                        ArrayList<Speciality> specialities = treeMap.get(o);
                    	//contractNumber=specialities.get(0).contractPrefix;
                        contractNumber = Faculties.listCodesUkr.get(Faculties.faculties.indexOf(o));
                        textContractNumber.setText(Faculties.listCodesUkr.get(Faculties.faculties.indexOf(o)));
                        specModel.removeAllElements();
                        progModel.removeAllElements();
                        for (Speciality speciality : specialities) {
                            if (specModel.getIndexOf(speciality.specName) == -1) {
                            	
                                specModel.addElement(speciality.specName);
                            }
                        }
                        
                        setPricesandYears((String)boxForma.getSelectedItem(), (String)boxLevel.getSelectedItem(),(String) boxFaculty.getSelectedItem(),(String) boxSpeciality.getSelectedItem(),(String) boxProgramm.getSelectedItem());
                	               
                }
                else{
                	setPricesandYears((String)boxForma.getSelectedItem(), (String)boxLevel.getSelectedItem(),null,(String) boxSpeciality.getSelectedItem(),(String) boxProgramm.getSelectedItem());
                    specModel.removeAllElements();
                    progModel.removeAllElements();
                }
            }
        });

        boxSpeciality.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
           
                Object o = boxFaculty.getSelectedItem();
                if (o != null  && !o.equals(" ")) {
                	
                        ArrayList<Speciality> specialities = treeMap.get(o);
                      
                        progModel.removeAllElements();
                        for(Speciality speciality : specialities) {
                            if(boxSpeciality.getSelectedItem()!=null && boxSpeciality.getSelectedItem().equals(speciality.specName)){
                                progModel.addElement(speciality.eduProgram);
                            }
                        }
                        setPricesandYears((String)boxForma.getSelectedItem(), (String)boxLevel.getSelectedItem(),(String) boxFaculty.getSelectedItem(),(String) boxSpeciality.getSelectedItem(),(String) boxProgramm.getSelectedItem());
                    
                }
                else{
                    progModel.removeAllElements();
                }
            }
        });

        boxProgramm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	 setPricesandYears((String)boxForma.getSelectedItem(), (String)boxLevel.getSelectedItem(),(String) boxFaculty.getSelectedItem(),(String) boxSpeciality.getSelectedItem(),(String) boxProgramm.getSelectedItem());
              
            }
        });
        
        for (int i=0;i<form.getComponentCount();i++)
        	  form.getComponent(i).setFont(NumberUtilities.font);

    }

    public void setPricesandYears(String form, String level, String faculty, String spec, String educProgram)
    {
    	
 

		 textPriceValue1.setText("");
        textPriceValue2.setText("");
        volume="";
    	if(faculty!=null && !faculty.equals(" ") && form!=null && spec!=null && educProgram!=null && level!=null)
    	{
    		
    
    	  ArrayList<Speciality> specialities = treeMap.get(faculty);
    
    	
    	  for(Speciality speciality : specialities) {
    		
    		 
              if(speciality.eduProgram.equals(educProgram)){
            	  idDirection = speciality.idDirection;
                  if (level.equals("Бакалавр") && form.equals("Денною")) {
                  	Calendar c= Calendar.getInstance();
                  	c.setTime(DateUtilities.getCurYearBegin());
                  	c.set(Calendar.MONTH, Calendar.JUNE);
                  	c.set(Calendar.DAY_OF_MONTH, 30);
					c.add(Calendar.YEAR, 4);
                    textEnd.setText(DateUtilities.applicationFormat(c.getTime()));
                    textPriceValue1.setText(speciality.priceBachDayUkrDigits);
                    textPriceValue2.setText(speciality.priceBachDayUkrString);
                    volume="240";
                    yearsEducation=4;
                    
                  }
                  if (level.equals("Бакалавр") && form.equals("Заочною")) {
                            
                	  Calendar c= Calendar.getInstance();
                    	c.setTime(DateUtilities.getCurYearBegin());
                    	c.set(Calendar.MONTH, Calendar.JUNE);
                    	c.set(Calendar.DAY_OF_MONTH, 30);
  					c.add(Calendar.YEAR, speciality.numberZaYears);
                      textEnd.setText(DateUtilities.applicationFormat(c.getTime()));
                  	  textPriceValue1.setText(speciality.priceBachZaUkrDigits);
                      textPriceValue2.setText(speciality.priceBachZaUkrString);
                      volume="240";
                      yearsEducation = speciality.numberZaYears;
                    
                  }
                  
                  if (level.equals("Магістр (ОНП)") && form.equals("Денною")){
                	  Calendar c= Calendar.getInstance();
                    	c.setTime(DateUtilities.getCurYearBegin());
                    	c.set(Calendar.MONTH, Calendar.MAY);
                    	c.set(Calendar.DAY_OF_MONTH, 31);
  					c.add(Calendar.YEAR, 2);
                      textEnd.setText(DateUtilities.applicationFormat(c.getTime()));
                    	textPriceValue1.setText(speciality.priceMagDenDigitsONP);
                      textPriceValue2.setText(speciality.priceMagDenStringONP);
                      volume="120";
                      yearsEducation = 1.9f;
                  }
                  if (level.equals("Магістр (ОНП)") && form.equals("Заочною")) {
                	  Calendar c= Calendar.getInstance();
                  	c.setTime(DateUtilities.getCurYearBegin());
                  	c.set(Calendar.MONTH, Calendar.MAY);
                  	c.set(Calendar.DAY_OF_MONTH, 31);
					c.add(Calendar.YEAR, 2);
                    textEnd.setText(DateUtilities.applicationFormat(c.getTime()));
                  	textPriceValue1.setText(speciality.priceMagZaDigitsONP);
                    textPriceValue2.setText(speciality.priceMagZaStringONP);
                    volume="120";
                    yearsEducation = 1.9f; 
                  }
                  
                  if (level.equals("Магістр") && form.equals("Денною")) {
                  	Calendar c= Calendar.getInstance();
                  	c.setTime(DateUtilities.getCurYearBegin());
                  	c.set(Calendar.MONTH, Calendar.DECEMBER);
                  	c.set(Calendar.DAY_OF_MONTH, 31);
					c.add(Calendar.YEAR, 1);
                    textEnd.setText(DateUtilities.applicationFormat(c.getTime()));
                  	textPriceValue1.setText(speciality.priceMagDayUkrDigits);
                    textPriceValue2.setText(speciality.priceMagDayUkrString);
                    volume="90";
                    yearsEducation = 1.4f;
                    if(speciality.eduProgram.equals("Медицина"))
                    {
                     	c= Calendar.getInstance();
                      	c.setTime(DateUtilities.getCurYearBegin());
                      	c.set(Calendar.MONTH, Calendar.JUNE);
                      	c.set(Calendar.DAY_OF_MONTH, 30);
    					c.add(Calendar.YEAR, 6);
                        textEnd.setText(DateUtilities.applicationFormat(c.getTime()));
                    	volume="360";
                    	yearsEducation = 6;
                    }
                  }
                  if (level.equals("Магістр") && form.equals("Заочною")) {
                	  	Calendar c= Calendar.getInstance();
                      	c.setTime(DateUtilities.getCurYearBegin());
                      	c.set(Calendar.MONTH, Calendar.DECEMBER);
                      	c.set(Calendar.DAY_OF_MONTH, 31);
    					c.add(Calendar.YEAR, 1);
                        textEnd.setText(DateUtilities.applicationFormat(c.getTime()));
                        textPriceValue1.setText(speciality.priceMagZaUkrDigits);
                      textPriceValue2.setText(speciality.priceMagZaUkrString);
                      volume="90";
                      yearsEducation = 1.4f;
                  }
              }
          }
    	}
    
    	
    }
    public void readPrices() throws IOException{
        FileReader input = new FileReader(Info.PATERN_PATH+ "/prices_ua.csv");
        BufferedReader bufRead = new BufferedReader(input);
        String myLine;
        int f = 1;
        faculties[0] = " ";

       
        while((myLine = bufRead.readLine())!= null){
        	
            Speciality spec = new Speciality();
            String[] arr = myLine.split(";");
        
           
                if(arr[0].equals("")){ //this means we have the same faculty but another speciality or education program
                    spec.specName = arr[1].trim();
                    spec.eduProgram = arr[2].trim();

                    spec.priceBachDayUkrDigits = arr[3];
                    spec.priceBachDayUkrString = arr[4];
                   // spec.priceBachDayEngDigits = arr[5];
                   // spec.priceBachDayEngString = arr[6];

                    spec.priceBachZaUkrDigits = arr[5];
                    spec.priceBachZaUkrString = arr[6];
                    //spec.priceBachZaEngDigits = arr[9];
                    //spec.priceBachZaEngString = arr[10];

                    spec.priceMagDayUkrDigits = arr[7];
                    spec.priceMagDayUkrString = arr[8];
                   // spec.priceMagDayEngDigits = arr[13];
                   // spec.priceMagDayEngString = arr[14];

                    spec.priceMagZaUkrDigits = arr[9];
                    spec.priceMagZaUkrString = arr[10];
                    spec.numberZaYears=Integer.parseInt(arr[11]);
                    spec.idDirection=Integer.parseInt(arr[12]);
                    spec.priceMagDenDigitsONP=arr[13];
                    spec.priceMagDenStringONP=arr[14];
                    spec.priceMagZaDigitsONP=arr[15];
                    spec.priceMagZaStringONP=arr[16];
                   // spec.priceMagZaEngDigits = arr[17];
                   // spec.priceMagZaEngString = arr[18];
                    

                    ArrayList<Speciality> ss=treeMap.get(faculties[f-1]);
                    ss.add(spec);
                }
                else{ //this means we have new faculty
                    specs=new ArrayList<Speciality>();
                    faculties[f] = arr[0].trim();
                    f++;

                    spec.specName = arr[1].trim();
                    spec.eduProgram = arr[2].trim();
                    spec.priceBachDayUkrDigits = arr[3];
                    spec.priceBachDayUkrString = arr[4];
                   // spec.priceBachDayEngDigits = arr[5];
                   // spec.priceBachDayEngString = arr[6];

                    spec.priceBachZaUkrDigits = arr[5];
                    spec.priceBachZaUkrString = arr[6];
                    //spec.priceBachZaEngDigits = arr[9];
                    //spec.priceBachZaEngString = arr[10];

                    spec.priceMagDayUkrDigits = arr[7];
                    spec.priceMagDayUkrString = arr[8];
                   // spec.priceMagDayEngDigits = arr[13];
                   // spec.priceMagDayEngString = arr[14];

                    spec.priceMagZaUkrDigits = arr[9];
                    spec.priceMagZaUkrString = arr[10];
                    spec.numberZaYears=Integer.parseInt(arr[11]);
                    spec.idDirection=Integer.parseInt(arr[12]);
                    spec.priceMagDenDigitsONP=arr[13];
                    spec.priceMagDenStringONP=arr[14];
                    spec.priceMagZaDigitsONP=arr[15];
                    spec.priceMagZaStringONP=arr[16];
                   // spec.priceMagZaEngDigits = arr[17];
                   // spec.priceMagZaEngString = arr[18];

                    specs.add(spec);
                    treeMap.put(arr[0],specs);
                }
            
        }
        bufRead.close();
     //  System.out.println(treeMap);
    }
    /**
     * Функция замены текста в Word-файле
     * @param doc
     * @param findText
     * @param replaceText
     * @return
     */

    public Entrant createStudent()
    {
    	
    	
    	if(boxFaculty.getSelectedItem()==null || boxFaculty.getSelectedIndex()==0)
    		JOptionPane.showMessageDialog(null, "Не все обязательные поля заполнены!!! Не выбран факультет");
    	else
    	{
    		//разделить ФИО
	    	Entrant s = new Entrant();
	    	phone = textPhone.getText();
                       
	    	clientFIO = textClientFIO.getText();
            if(clientFIO.equals(""))
                clientFIO = textName.getText().split(" ")[1]+" "+textName.getText().split(" ")[2];
            else
            	clientFIO = textClientFIO.getText().split(" ")[1]+" "+textClientFIO.getText().split(" ")[2];
      
        	 s.payer = clientFIO+", "+phone; 
	    	s.StudSurname = textName.getText().split(" ")[0];
	    	s.StudName = textName.getText().split(" ")[1];
	    	s.StudMiddlename= textName.getText().split(" ")[2];
	    	s.dateOfBirth = textDateOfBirth.getDate();
	    	if(boxLevel.getSelectedIndex()==0){ //бакалавр
	    		s.Ref_LtoP=1;
	    		s.contractValue=Double.parseDouble(textPriceValue1.getText().trim());
	    	}
	    	else{
	    		s.Ref_LtoP=4; //magistr
	    		if(idDirection==28)//Medici
	    			s.contractValue=  Double.parseDouble(textPriceValue1.getText().trim());
	    		else
	    			s.contractValue= new BigDecimal( Double.parseDouble(textPriceValue1.getText().trim())*10/(yearsEducation*10)).setScale(0, RoundingMode.UP).doubleValue();
	    		 
	    	}
	    	
	    	if(boxForma.getSelectedIndex()==0) //denna
	    		s.Ref_EducForm=1;
	    	else
	    		s.Ref_EducForm=2; //zaoxhna
	    	
	    	
	    	s.Ref_Country=1;
	    	
	    	s.Ref_ArrivalLine=1;
	    	    	
	    	s.Ref_Language=1;
	    	
	    	s.Course = 1;
	    	
	    	s.Years=yearsEducation;
	    	
	    	s.Ref_Direction =idDirection;
	    	s.contractNumber=textContractNumber.getText();
	    	
	    	s.Ref_ContractType=1;
	   
	    	return s;
    	}
    	return null;
    	
    }
    
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



  
    
    private static void saveWord(String filePath, HWPFDocument doc) throws FileNotFoundException, IOException{
        FileOutputStream out = null;
        try{
            out = new FileOutputStream(filePath);
            doc.write(out);
          //  out.close();
           
        }
        catch(IOException ex)
        {
        	JOptionPane.showMessageDialog(null, "Файл new.doc открыт. Закройте его и повторите операцию");
        }
        finally{
        	if(out!=null)
        		out.close();
            
        }
    }
    /**
     * Функция извлечения названий стран из текстового файла
     * @return
     * @throws IOException
     */

    
    
    private void openWord() {
        Runtime rt = Runtime.getRuntime();
        String prefix = "file:" + System.getProperty("user.dir") + System.getProperty("file.separator");
        try {
        	//System.out.println(CDUtilities.WORD_PATH + " \"" + prefix + "test.doc" + "\"");
            rt.exec(Info.WORD_PATH + " \"" + prefix + OUTPUT_FILE + "\"");
        }
        catch (IOException e) {
        	JOptionPane.showMessageDialog(null, "Не возможно открыть"+e.getMessage());
        }
    }
    

    /**
     * Запуск приложения
     * @param args
     */

    
    public JPanel createTab()
    {
    	return form;
    }
}