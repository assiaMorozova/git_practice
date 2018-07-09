package contract.ru;
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
import org.apache.poi.util.SystemOutLogger;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jdesktop.swingx.sort.SortUtils;
import org.jdesktop.swingx.util.Contract;

import main.TestFrame;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
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
public class ContractRUS  {

	 private JPanel fioPanel;
    private JPanel form;
    private JPanel contractPanel;
    
    private JPanel pasportPanel;
    private JLabel labelDateOfBirth;
    private CDFormattedTextField textDateOfBirth;
    
    float years=0;
    int idDirection;
    /**
     * Labels
     */

    private JLabel labelContractNumber;
    private JLabel labelFirm;
    private JLabel labelContactPerson;
    private JLabel labelName;
    private JLabel labelCountry;
    private JLabel labelFaculty;
    private JLabel labelSpeciality;
    private JLabel labelProgramm;
    private JLabel labelLevel;
    private JTextField textPriceValue1;
    private JTextField textPriceValue2;
  //  private JTextField textPriceValue3;
    private JLabel labelStart;
    private JLabel labelEnd;
    private JLabel labelPassNumber;
    private JLabel labelPhone;
    private JLabel labelPrice;
    private JLabel labelPrice2;
 //   private JLabel labelPrice3;
    private JLabel labelForm;
    private JLabel labelContractDate;
    private JLabel labelYear;
   
    /**
     * Comboboxes
     */

    private JComboBox boxCountry;
    private JComboBox boxFaculty;
    private JComboBox boxSpeciality;
    private JComboBox boxProgramm;
    private JComboBox boxLevel;
    private JComboBox boxForma;
    private JComboBox boxFirma;
    
    /**
     * TextFields
     */

    private JTextField textContractNumber;
   
    private JTextField textStudSurname;
    private JTextField textStudName;
    private JTextField textStudMiddleName;
    
    private CDFormattedTextField textStart;
    private CDFormattedTextField textEnd;
    private  CDFormattedTextField textDate;
    private CDFormattedTextField textContractDate;
    private JTextField textYear;
    
    private JTextField textPass;
    private JTextField textPhone;
 
    private JTextField textContactPerson;
    

    /**
     * Buttons
     */

    private JButton buttonSave;
    private JButton buttonClear;
    private JButton buttonClearAll;
    private JButton buttonApplication;
    private JButton buttonSaveEntrance;
	private JButton buttonClearFirm;
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
  
    private String price2;
  //  private String price3;
    private String price5;
    private String forma;
    private String firma;
    private String contactPerson;
    private int numYear=4;
    private String contractValue;
    public String OUTPUT_FILE = "new.doc";
    public String APPLICATION_FILE = Info.PATERN_PATH+"/Pattern/application.doc";

    /**
     * Variables for comboboxes
     */

    
    private String[] faculties = new String[22];



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

 
    
    private String filepath = Info.PATERN_PATH+"/Pattern/ContractRu.doc";
    private String filepathBacalavr =Info.PATERN_PATH+ "/Pattern/ContractRuBacalavr.doc";
    
    private String filepathOwn = Info.PATERN_PATH+"/Pattern/ContractRuOwn.doc";
    private String filepathBacalavrOwn =Info.PATERN_PATH+ "/Pattern/ContractRuBacalavrOwn.doc";
    
	
    private POIFSFileSystem fs = null;
	
	



    /**
     * Конструктор
     * @throws IOException
     */


    public ContractRUS() throws IOException{

    	//Read prop
    
    	
    	//  	System.out.println(WORD_PATH);
    
        /**
         * Read from txt
         */

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


    	fioPanel = new JPanel();
    	
        textStudSurname = new JTextField("", 15);
        textStudName = new JTextField("", 10);
        textStudMiddleName = new JTextField("", 10);
     
        textStudSurname.setFont(NumberUtilities.font);
        textStudName.setFont(NumberUtilities.font);
        textStudMiddleName.setFont(NumberUtilities.font);
        fioPanel.add(textStudSurname);
        fioPanel.add(textStudName);
       // fioPanel.add(textStudMiddleName);
        
        /**
         * Labels
         */
    	

        cal.add(Calendar.YEAR, 1);
        Date nextYear = cal.getTime();

        datedate =DateUtilities.getCurrentPaymentYearName();


        labelContractNumber = new JLabel("№ контракта *");
        labelContractDate = new JLabel("Дата контракта");
        labelFirm= new JLabel("Фирма*");
        labelContactPerson= new JLabel("Представитель*");
        labelName = new JLabel("ФИО студента*");
        labelCountry = new JLabel("Гражданство *");
        labelFaculty = new JLabel("Факультет");
        labelSpeciality = new JLabel("Специальность");
        labelProgramm = new JLabel("Образовательная программа");
        labelLevel = new JLabel("Клалификация");
    
        labelPrice = new JLabel("Стоимость обучения (образ.усл. бак)");
        labelPrice2 = new JLabel("Оплата за весь срок (календ. год бак)");
       // labelPrice3 = new JLabel("Оплата фирме");
        labelForm = new JLabel("Форма");
    


        textPriceValue1 = new JTextField("",15);
        textPriceValue2 = new JTextField("", 35);
     //   textPriceValue3 = new JTextField("", 15);
        labelStart = new JLabel("Срок с");
        labelEnd = new JLabel("Срок по");
      
       
        labelPhone = new JLabel("Телефон *");

        /**
         * Buttons
         */

        buttonSave = new JButton("Сохранить");
        buttonClear = new JButton("Очистить *");
        buttonClearFirm = new JButton("Очистить фирму");
        buttonClearAll = new JButton("Очистить все");
        buttonApplication = new JButton("Заявление");
        buttonSaveEntrance = new JButton("Создать карту");
        buttonReceipt = new JButton("Квитанция");

        /**
         * Comboboxes
         */

        boxCountry = new JComboBox(Countries.listCountriesUa.toArray());
        AutoCompleteDecorator.decorate(boxCountry);
        boxCountry.setEditable(true);
        boxCountry.setSelectedIndex(-1);;
        boxFaculty = new JComboBox(faculties);
        boxFaculty.setSelectedItem(" ");
        boxSpeciality = new JComboBox();
        boxSpeciality.setModel(specModel);
        boxProgramm = new JComboBox();
        boxProgramm.setModel(progModel);
        boxLevel = new JComboBox(new String[] {"бакалавра","магістра","магістра (ОНП)"});
        boxForma = new JComboBox(new String[]{"денною", "заочною"});
        boxFirma = new JComboBox(Firms.listFirmsUa.toArray());
        boxFirma.setSelectedIndex(-1);
        /**
         * TextFields
         */

        textContactPerson = new JTextField("", 25);
        textContractNumber = new JTextField("", 10);
        textDate = new CDFormattedTextField();
        textDate.setColumns(8);
        /*textStudSurname = new JTextField("", 10);
        textStudName = new JTextField("", 10);
        textStudMiddleName = new JTextField("", 10);
      */
        textStart = new CDFormattedTextField();
        textStart.setColumns(8);
        textStart.setText(DateUtilities.applicationFormat(DateUtilities.getCurYearBegin()));
        textEnd = new CDFormattedTextField();
        textEnd.setColumns(8);
       // textPass = new JTextField("", 20);
        textPhone = new JTextField("", 20);
        textYear = new JTextField(datedate, 10);

 ////////////////////////////////////////init contract panel
        
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
       
        labelContractDate.setFont(NumberUtilities.font);
        labelYear.setFont(NumberUtilities.font);
        
        contractPanel.add(textContractNumber);
        contractPanel.add(labelContractDate);
        contractPanel.add(textContractDate);
        contractPanel.add(labelYear);
        contractPanel.add(textYear);
       
        
        ////////////////////////////////////////////
 ///////--- PASPORT PANEL --------------/////////
        
        pasportPanel = new JPanel();
        
        labelDateOfBirth = new JLabel("д.р.");
        textDateOfBirth =new CDFormattedTextField();
        textDateOfBirth.setColumns(8);
      
        textPass = new JTextField("", 30);
        labelPassNumber = new JLabel("Паспорт студента *");
       
        labelDateOfBirth.setFont(NumberUtilities.font);
        textDateOfBirth.setFont(NumberUtilities.font);
        textPass.setFont(NumberUtilities.font);
        labelPassNumber.setFont(NumberUtilities.font);
        
        pasportPanel.add(textPass);
        pasportPanel.add(labelDateOfBirth);
        pasportPanel.add(textDateOfBirth);
        
       
        /////////////////////////////////////


        /**
         * Panel
         */
        form = new JPanel();
      
      
        form.setBorder(BorderFactory.createTitledBorder("Контракт"));
        MigLayout layout = new MigLayout();

        
        form.setLayout(layout);
      
        form.add(labelFirm);
        form.add(boxFirma);
        form.add(buttonClearFirm, "wrap");
        form.add(labelContactPerson);
        form.add(textContactPerson, "wrap");
        form.add(labelName);
        form.add(fioPanel,"wrap");
        
        
        form.add(labelCountry);
        form.add(boxCountry, "wrap");
        form.add(labelForm);
        form.add(boxForma, "wrap");
        form.add(labelFaculty);
        form.add(boxFaculty, "wrap");
        form.add(labelSpeciality);
        form.add(boxSpeciality, "wrap");
        form.add(labelProgramm);
        form.add(boxProgramm, "wrap");
        ///////////////////////////////////
        form.add(labelContractNumber);
        form.add(contractPanel,"wrap");
     //   form.add(textContractNumber, "wrap");
        form.add(labelLevel);
        form.add(boxLevel, "wrap");
        
        form.add(labelPrice);
        form.add(textPriceValue1, "wrap");
        form.add(labelPrice2);
        form.add(textPriceValue2, "wrap");
     //   form.add(labelPrice3);
      //  form.add(textPriceValue3, "wrap");
        form.add(labelStart);
        form.add(textStart, "wrap");
        form.add(labelEnd);
        form.add(textEnd, "wrap");
        
        form.add(labelPassNumber);
        form.add(pasportPanel,"wrap");
        form.add(labelPhone);
        form.add(textPhone, "wrap");
       
        
            
        form.add(buttonSave);
       
        form.add(buttonClear);
        
        form.add(buttonClearAll,"wrap");
        form.add(buttonSaveEntrance);
        form.add(buttonApplication);
      
        form.add(buttonReceipt);

        buttonClearFirm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			boxFirma.setSelectedIndex(-1);
			textContactPerson.setText("");
				
			}
		});
        buttonReceipt.addActionListener(new ActionListener() {
			
     			@Override
     			public void actionPerformed(ActionEvent e) {
     				
     		          	contractNumber = textContractNumber.getText();
     				   faculty = boxFaculty.getSelectedItem().toString();
     				  name = textStudSurname.getText()+" "+textStudName.getText()+" "+textStudMiddleName.getText();
     	              
     	           
     	               price2 = contractValue+" $ ( курс НБУ на "+ DateUtilities.applicationFormat(DateUtilities.getCurrentDate()) + " равен "+ TestFrame.rateUsd*Float.parseFloat(contractValue) + " грн. )";
     	                
     				ReceiptBuilder.createReceipt(datedate, contractNumber, faculty, name, price2, "");
     				//jd.dispose();
     				
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
            private String requisiteRu;

			@Override
            public void actionPerformed(ActionEvent e) {
            
            	
            	if(boxCountry.getSelectedIndex()<0)
            	{
            		JOptionPane.showMessageDialog(null, "Не все обязательные поля заполнены!!! Не выбрана страна");
            		return;
            	}
            	
            	if(boxFaculty.getSelectedItem()==null|| boxFaculty.getSelectedIndex()==0)
            		JOptionPane.showMessageDialog(null, "Не все обязательные поля заполнены!!!");
            	else
            	{

            	contractNumber = textContractNumber.getText();
            	if(boxFirma.getSelectedIndex()>=0)
        		{
            		firma=boxFirma.getSelectedItem().toString();
        		}
            	else 
            		firma="";
            	
            
                contractDate = textContractDate.getText();
            	contactPerson=textContactPerson.getText();
                name = textStudSurname.getText()+" "+textStudName.getText()+" "+textStudMiddleName.getText();
              
                country = boxCountry.getSelectedItem().toString();
                faculty = boxFaculty.getSelectedItem().toString();
                speciality = boxSpeciality.getSelectedItem().toString();
                programm = boxProgramm.getSelectedItem().toString();
                level = boxLevel.getSelectedItem().toString();
              
                price1 = textPriceValue1.getText();
                price2 = textPriceValue2.getText();
              //  price3 = textPriceValue3.getText();
                if(numYear==5)
                	price5=textPriceValue2.getText();
                else
                	price5="________________________";

                start = textStart.getText();
                end = textEnd.getText();
                
                pass = textPass.getText() ;
                phone = textPhone.getText();
                
                forma = boxForma.getSelectedItem().toString();
                HWPFDocument doc =null;
               
                try{
                	
					if(boxFirma.getSelectedIndex()<0) //идут как личный контракт
            		{
                	
                		if(level.equals("магістра") || level.equals("магістра (ОНП)") )
	                		fs = new POIFSFileSystem(new FileInputStream(filepathOwn));
	                	else
	                		fs = new POIFSFileSystem(new FileInputStream(filepathBacalavrOwn));
                		requisiteRu="";
            		}
                	else
                	{
                	   	if(level.equals("магістра") || level.equals("магістра (ОНП)") )
	                		fs = new POIFSFileSystem(new FileInputStream(filepath));
	                	else
	                		fs = new POIFSFileSystem(new FileInputStream(filepathBacalavr));
                	   	
                	   	requisiteRu=Firms.listFirmsRequisite.get(boxFirma.getSelectedIndex());
                	}
                
                
                     doc = new HWPFDocument(fs);
                    doc = replaceText(doc, "contactNumber", contractNumber);
                    doc = replaceText(doc, "contractDate", contractDate);
                    doc = replaceText(doc, "firmRu", firma);
                    doc = replaceText(doc, "firmPersonRu", contactPerson);
                    doc = replaceText(doc, "Studname", name);
                    doc = replaceText(doc, "country", country);
                    doc = replaceText(doc, "faculty", faculty);
                    doc = replaceText(doc, "speciality", speciality);
                    doc = replaceText(doc, "programm", programm);
                    doc = replaceText(doc, "level", level);
                 
                    doc = replaceText(doc, "priceTotal", price1);
                    doc = replaceText(doc, "priceFirst", price2);
                    doc = replaceText(doc, "priceFive", price5);
                  //  doc = replaceText(doc, "priceFirm", price3);
                    doc = replaceText(doc, "start", start);
                    doc = replaceText(doc, "end", end);
                    doc = replaceText(doc, "pass", pass);
                    doc = replaceText(doc, "phone", phone);
                   
                   
                    doc = replaceText(doc, "forma", forma);
                 
                    doc = replaceText(doc, "credits", volume);
                    doc = replaceText(doc, "dateOfBirth", textDateOfBirth.getText());                  
                    doc=replaceText(doc,"dayIn",textStart.getText().split("/")[0]);
                    doc=replaceText(doc,"monthIn",textStart.getText().split("/")[1]);
                    doc=replaceText(doc,"yearIn",textStart.getText().split("/")[2]);
                    doc=replaceText(doc,"dayOut",textEnd.getText().split("/")[0]);
                    doc=replaceText(doc,"monthOut",textEnd.getText().split("/")[1]);
                    doc=replaceText(doc,"yearOut",textEnd.getText().split("/")[2]);
                    doc=replaceText(doc,"requisiteRu",requisiteRu);
                   	

                    	
                    		
                    if(!name.equals("")&&!Info.OUTPUT_FILE_PATH.equals("."))
                    	OUTPUT_FILE=Info.OUTPUT_FILE_PATH+"/FOREIGN/"+name.trim()+".doc";
                    else
                    	OUTPUT_FILE="new.doc";
                    	
                  // System.out.println(doc);
                 
                   OUTPUT_FILE= WordOperation.saveWord(OUTPUT_FILE, doc);
                  doc.close();
                   WordOperation.openWord(OUTPUT_FILE);
                   
                   
                }
                catch(Exception ex){
                	if(doc!=null )
						try {
							doc.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null,ex.getMessage());
                    
                }
                
                
            }
            }
        });

        buttonApplication.addActionListener(new ActionListener() {
			
 			@Override
 			public void actionPerformed(ActionEvent e) {
 				
 				if(boxCountry.getSelectedIndex()<0)
            	{
            		JOptionPane.showMessageDialog(null, "Не все обязательные поля заполнены!!! Не выбрана страна");
            		return;
            	}
            	if(boxFaculty.getSelectedIndex()==0)
        		{
        		JOptionPane.showMessageDialog(null, "Не все обязательные поля заполнены!!! Не выбран факультет");
        		return;
        		}
            	
                name = textStudSurname.getText()+" "+textStudName.getText()+" "+textStudMiddleName.getText();
             	country = (String)boxCountry.getSelectedItem();
             	  faculty = boxFaculty.getSelectedItem().toString(); 
                 
                 try{
                 	
                 	fs = new POIFSFileSystem(new FileInputStream(APPLICATION_FILE));
                     HWPFDocument doc = new HWPFDocument(fs);
                    
                     doc = replaceText(doc, "studnameRu", name);
                   
                     
                     doc = replaceText(doc, "countryRu", country);
                    
                     doc = replaceText(doc, "faculty", faculty);
                                       
                                        	
                     		
                     WordOperation.saveWord("new.doc", doc);
                     doc.close();
                     WordOperation.openWord("new.doc");
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
 				
 			
 		});

        buttonClearAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textContractNumber.setText("");
            	  boxFirma.setSelectedIndex(-1);
            	  textStudSurname.setText("");
            	  textStudName.setText("");
            	  textStudMiddleName.setText("");
              //  name = textStudSurname.getText()+" "+textStudName.getText()+" "+textStudMiddleName.getText();
            	  textDateOfBirth.setText("");
                textContactPerson.setText("");
                boxCountry.setSelectedItem("");
                textPhone.setText("");
                boxFaculty.setSelectedItem(" ");
                specModel.removeAllElements();
                progModel.removeAllElements();
               
               // boxProgramm.setSelectedItem(" ");
                //boxLevel.setSelectedItem(" ");
                //textVolume.setText("");
                textPriceValue1.setText("");
                textPriceValue2.setText("");
              //  textPriceValue3.setText("");
                textPass.setText("");
                textPhone.setText("");
                textDate.setText("");
              //  textStart.setText("");
                textEnd.setText("");
               
               // textYear.setText("");
                boxForma.setSelectedIndex(0);
               // boxLevel.setSelectedIndex(0);;
                //contractNumber="";
            }
        });

        buttonClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textContractNumber.setText("");
            //	  boxFirma.setSelectedIndex(-1);
               // textName.setText("");
             //   textContactPerson.setText("");
                textStudSurname.setText("");
          	  textStudName.setText("");
          	  textStudMiddleName.setText("");
                boxCountry.setSelectedItem("");
                textPhone.setText("");
                boxFaculty.setSelectedItem(" ");
                specModel.removeAllElements();
                progModel.removeAllElements();
                textDateOfBirth.setText("");
               // boxProgramm.setSelectedItem(" ");
                //boxLevel.setSelectedItem(" ");
                //textVolume.setText("");
                textPriceValue1.setText("");
                textPriceValue2.setText("");
               // textPriceValue3.setText("");
                textPass.setText("");
                textPhone.setText("");
                textDate.setText("");
              //  textStart.setText("");
                textEnd.setText("");
               
              //  textYear.setText("");
                boxForma.setSelectedIndex(0);
               // boxLevel.setSelectedIndex(0);;
                //contractNumber="";
            }
        });
        boxLevel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
								
				setPricesandYears((String)boxForma.getSelectedItem(), (String)boxLevel.getSelectedItem(),(String) boxFaculty.getSelectedItem(),(String) boxSpeciality.getSelectedItem(),(String) boxProgramm.getSelectedItem());
				
			}
		});
        boxFirma.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(boxFirma.getSelectedIndex()>=0)
					textContactPerson.setText(Firms.listFirmsHeads.get(boxFirma.getSelectedIndex()));
				
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
                       // contractNumber = Faculties.listCodesForeign.get(Faculties.faculties.indexOf(o));
                        textContractNumber.setText(Faculties.listCodesForeign.get(Faculties.faculties.indexOf(o)));		
                    //    contractNumber=specialities.get(0).contractPrefix;
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
            	//System.out.println(boxSpeciality.getSelectedItem());
                Object o = boxFaculty.getSelectedItem();
                if (o != null  && !o.equals(" ")) {
                	
                        ArrayList<Speciality> specialities = treeMap.get(o);
                       // System.out.println(specialities);
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
	    	 
	    	s.payer = textPhone.getText();
        	  
	    	s.StudSurname = textStudSurname.getText();
	    	s.StudName = textStudName.getText();
	    	s.StudMiddlename= textStudMiddleName.getText();
	    	s.dateOfBirth = textDateOfBirth.getDate();
	    
	    	if(boxLevel.getSelectedIndex()==0){ //бакалавр
	    		s.Ref_LtoP=1;
	    		s.contractValue=Double.parseDouble(contractValue.trim());
	    		
	    	}
	    	else{
	    		s.Ref_LtoP=4; //magistr
	    		s.contractValue=Double.parseDouble(contractValue.trim());	
	    		s.contractValue= new BigDecimal( s.contractValue*10/(years*10)).setScale(0, RoundingMode.UP).doubleValue();
	    	}
	    	
	    	if(boxForma.getSelectedIndex()==0) //denna
	    		s.Ref_EducForm=1;
	    	else
	    		s.Ref_EducForm=2; //zaoxhna
	    	
	    	if(boxCountry.getSelectedIndex()>=0)
	    		s.Ref_Country=Countries.listCountriesID.get(boxCountry.getSelectedIndex());
	    	s.Ref_ArrivalLine=0;
	    	if(boxFirma.getSelectedIndex()>=0)
				s.ArrivalLine=Firms.listFirmsCodes.get(boxFirma.getSelectedIndex());
	    	else
	    		s.Ref_ArrivalLine=1;//личный контракт
	    	
	    	s.Ref_Language=1;
	    	s.Course = 1;
	    	s.Years=years;
	    	s.Ref_Direction =idDirection;
	    	s.contractNumber=textContractNumber.getText();
	    
	    	s.Ref_ContractType=2;
	    
	    	return s;
    	}
    	return null;
    	
    }
    
    public void setPricesandYears(String form, String level, String faculty, String spec, String educProgram)
    {
    	
    //	System.out.println(form+" "+level+" "+faculty+" "+spec+" "+educProgram+" ");
    	contractValue="";
		textPriceValue1.setText("");
        textPriceValue2.setText("");
        //textPriceValue3.setText("");
        volume="";
    	if(faculty!=null && !faculty.equals(" ") && form!=null && spec!=null && educProgram!=null && level!=null)
    	{
    		
    
    	  ArrayList<Speciality> specialities = treeMap.get(faculty);
    
    	
    	  for(Speciality speciality : specialities) {
              
              if(speciality.eduProgram.equals(educProgram)){
            	  idDirection = speciality.idDirection;
                  if (level.equals("бакалавра") && form.equals("денною")) {
                	  years = 4;
                  	Calendar c= Calendar.getInstance();
                  	c.setTime(DateUtilities.getCurYearBegin());
                  	c.set(Calendar.MONTH, Calendar.JUNE);
                  	c.set(Calendar.DAY_OF_MONTH, 30);
					c.add(Calendar.YEAR, 4);
                    textEnd.setText(DateUtilities.applicationFormat(c.getTime()));
                    textPriceValue1.setText(""+Integer.parseInt(speciality.priceBachDayDigits)*4);
                    textPriceValue2.setText(speciality.priceBachDayDigits+" ("+speciality.priceBachDayString+" )");
                 //   if(!speciality.priceBachDayString.equals(""))
                   // 	textPriceValue3.setText(""+(Integer.parseInt(speciality.priceBachDayString)-Integer.parseInt(speciality.priceBachDayDigits))+"");
                    volume="240";
                    numYear= speciality.numberZaYears;
                    contractValue=speciality.priceBachDayDigits;
                    
                  }
                  if (level.equals("бакалавра") && form.equals("заочною")) {
                	  years =  speciality.numberZaYears; 
                	  Calendar c= Calendar.getInstance();
                    	c.setTime(DateUtilities.getCurYearBegin());
                    	c.set(Calendar.MONTH, Calendar.JUNE);
                    	c.set(Calendar.DAY_OF_MONTH, 30);
  					c.add(Calendar.YEAR, speciality.numberZaYears);
                      textEnd.setText(DateUtilities.applicationFormat(c.getTime()));
                      textPriceValue1.setText(""+Integer.parseInt(speciality.priceBachZaDigits)*speciality.numberZaYears);
                      textPriceValue2.setText(speciality.priceBachZaDigits+" ("+speciality.priceBachZaString+" )");
                   //   if(!speciality.priceBachZaString.equals(""))
                    //	  textPriceValue3.setText(""+(Integer.parseInt(speciality.priceBachZaString)-Integer.parseInt(speciality.priceBachZaDigits))+"");
                      numYear= speciality.numberZaYears;
                      volume="240";
                      contractValue=speciality.priceBachZaDigits;
                  }
                  if (level.equals("магістра (ОНП)") && form.equals("денною")) {
                	  years = 1.9f;
                	  Calendar c= Calendar.getInstance();
                  	c.setTime(DateUtilities.getCurYearBegin());
                  	c.set(Calendar.MONTH, Calendar.MAY);
                  	c.set(Calendar.DAY_OF_MONTH, 31);
					c.add(Calendar.YEAR, 2);
                    textEnd.setText(DateUtilities.applicationFormat(c.getTime()));
                 
                    textPriceValue2.setText(speciality.priceMagDenDigitsONP+"("+speciality.priceMagDenStringONP+")");
                   // if(!speciality.priceMagDayString.equals(""))
                    //textPriceValue3.setText(""+(Integer.parseInt(speciality.priceMagDayString)-Integer.parseInt(speciality.priceMagDayDigits))+"");
                  
                    volume="120";
                    contractValue=speciality.priceMagDenDigitsONP;
                    
                  }
                  if (level.equals("магістра (ОНП)") && form.equals("заочною")) {
                	  years = 1.9f;
                	  Calendar c= Calendar.getInstance();
                  	c.setTime(DateUtilities.getCurYearBegin());
                  	c.set(Calendar.MONTH, Calendar.MAY);
                  	c.set(Calendar.DAY_OF_MONTH, 31);
					c.add(Calendar.YEAR, 2);
    					textEnd.setText(DateUtilities.applicationFormat(c.getTime()));
    				   // textPriceValue1.setText(speciality.priceMagZaDigitsONP);
    	                textPriceValue2.setText(speciality.priceMagZaDigitsONP+"("+speciality.priceMagZaStringONP+")");
    	               // if(!speciality.priceMagZaString.equals(""))
    	                //	textPriceValue3.setText(""+(Integer.parseInt(speciality.priceMagZaString)-Integer.parseInt(speciality.priceMagZaDigits))+"");
    	                contractValue= speciality.priceMagZaDigitsONP;
                      volume="120";
                  }
                  if (level.equals("магістра") && form.equals("денною")) {
                	  years = 1.4f;
                  	Calendar c= Calendar.getInstance();
                  	c.setTime(DateUtilities.getCurYearBegin());
                  	c.set(Calendar.MONTH, Calendar.DECEMBER);
                  	c.set(Calendar.DAY_OF_MONTH, 31);
					c.add(Calendar.YEAR, 1);
                    textEnd.setText(DateUtilities.applicationFormat(c.getTime()));
                  //  textPriceValue1.setText();
                    textPriceValue2.setText(speciality.priceMagDayDigits+"("+speciality.priceMagDayString+")");
                   // if(!speciality.priceMagDayString.equals(""))
                    //textPriceValue3.setText(""+(Integer.parseInt(speciality.priceMagDayString)-Integer.parseInt(speciality.priceMagDayDigits))+"");
                    contractValue=speciality.priceMagDayDigits;
                    volume="90";
                    
                  }
                  if (level.equals("магістра") && form.equals("заочною")) {
                	  years = 1.4f;
                	  	Calendar c= Calendar.getInstance();
                      	c.setTime(DateUtilities.getCurYearBegin());
                      	c.set(Calendar.MONTH, Calendar.DECEMBER);
                      	c.set(Calendar.DAY_OF_MONTH, 31);
    					c.add(Calendar.YEAR, 1);
    					textEnd.setText(DateUtilities.applicationFormat(c.getTime()));
    				   
    	                textPriceValue2.setText(speciality.priceMagZaDigits+"("+speciality.priceMagZaString+")");
    	              //  if(!speciality.priceMagZaString.equals(""))
    	                //	textPriceValue3.setText(""+(Integer.parseInt(speciality.priceMagZaString)-Integer.parseInt(speciality.priceMagZaDigits))+"");
    	                contractValue=speciality.priceMagZaDigits;

                      volume="90";
                  }
              }
          }
    	}
    
    	
    }
    public void readPrices() throws IOException{
        FileReader input = new FileReader(Info.PATERN_PATH+ "/prices_foreign.csv");
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

                    spec.priceBachDayDigits = arr[3];
                    spec.priceBachDayString = arr[4];
                 

                    spec.priceBachZaDigits = arr[5];
                    spec.priceBachZaString = arr[6];
       

                    spec.priceMagDayDigits = arr[7];
                    spec.priceMagDayString = arr[8];
             

                    spec.priceMagZaDigits = arr[9];
                    spec.priceMagZaString = arr[10];
                    spec.numberZaYears=Integer.parseInt(arr[11]);
                   spec.idDirection=Integer.parseInt(arr[12]);
                   
                   spec.priceMagDenDigitsONP=arr[13];
                   spec.priceMagDenStringONP=arr[14];
                   spec.priceMagZaDigitsONP=arr[15];
                   spec.priceMagZaStringONP=arr[16];
                    

                    ArrayList<Speciality> ss=treeMap.get(faculties[f-1]);
                    ss.add(spec);
                }
                else{ //this means we have new faculty
                    specs=new ArrayList<Speciality>();
                    faculties[f] = arr[0].trim();
                    f++;

                    spec.specName = arr[1].trim();
                    spec.eduProgram = arr[2].trim();
                    spec.priceBachDayDigits = arr[3];
                    spec.priceBachDayString = arr[4];
        

                    spec.priceBachZaDigits = arr[5];
                    spec.priceBachZaString = arr[6];
            

                    spec.priceMagDayDigits = arr[7];
                    spec.priceMagDayString = arr[8];
                 
                    spec.priceMagZaDigits = arr[9];
                    spec.priceMagZaString = arr[10];
                    spec.numberZaYears=Integer.parseInt(arr[11]);
                    spec.idDirection=Integer.parseInt(arr[12]);
                    
                    spec.priceMagDenDigitsONP=arr[13];
                    spec.priceMagDenStringONP=arr[14];
                    spec.priceMagZaDigitsONP=arr[15];
                    spec.priceMagZaStringONP=arr[16];

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
    
    public JPanel createTab()
    {
    	return form;
    }



}
