package contract.eng;
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
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jdesktop.swingx.sort.SortUtils;

import contract.eng.Speciality;
import main.TestFrame;

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
public class ContractEng {

    private JPanel form;
    private JPanel fioPanel;
    private JPanel contractPanel;
    private JPanel pasportPanel;
    private JLabel labelDateOfBirth;
    private CDFormattedTextField textDateOfBirth;
    

  
   /* ArrayList<String> listCountriesRu=new ArrayList<String>();
    ArrayList<String> listFirmsRu=new ArrayList<String>();
    ArrayList<String> listCountriesEng=new ArrayList<String>();
    ArrayList<String> listFirmsEng=new ArrayList<String>();
    ArrayList<String> listFirmsCodes=new ArrayList<String>();
    ArrayList<String> listFirmsHeads=new ArrayList<String>();
    ArrayList<String> listFirmsHeadsEng=new ArrayList<String>();
    */
    /**
     * Labels
     */

    private JLabel labelContractNumber;
    private JLabel labelFirm;
    private JLabel labelContactPerson;
    private JLabel labelContactPersonEng;
    private JLabel labelName;
    private JLabel labelNameEng;
    private JLabel labelCountry;
   private JLabel labelFaculty;
    
    private JLabel labelProgramm;
    private JLabel labelLevel;
    private JLabel labelSpeciality;

    private JLabel labelStart;
   private JLabel labelEnd;
    //private JLabel labelSeries;
    private JLabel labelPassNumber;
    private JLabel labelPhone;
    private JLabel labelForm;
 /*   private JLabel labelForm;
    private JLabel labelYear;
    private JLabel labelClient;
    private JLabel labelClientFIO;
    private JLabel labelClientPass;
    private JLabel labelClientAdress;
    */
    private JLabel labelLanguage;
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
    private JComboBox boxFirma;
    private JComboBox boxLang;
    private JComboBox boxForma;

    /**
     * TextFields
     */

    private JTextField textContractNumber;
    private JTextField textContactPerson;
    private JTextField textContactPersonEng;
    CDFormattedTextField textContractDate;
    private JTextField textStudSurname;
    private JTextField textStudName;
    private JTextField textStudMiddleName;
    
    private JTextField textNameEng;
    private JTextField textPass;
    private CDFormattedTextField textStart;
    private CDFormattedTextField textEnd;
    private JTextField textPriceValue;

    private JTextField textPhone;

  
    private JTextField textYear;

    /**
     * Buttons
     */

    private JButton buttonSave;

    private JButton buttonClearAll;
    private JButton buttonApplication;
    private JButton buttonSaveEntrance;
	private JButton buttonClearFirm;
	private JButton buttonReceipt;
    /**
     * Variables for DOC
     */

    private String country;
    private String countryEng;
    private String contractNumber;
    private String name;
    private String nameEng;
    private String firma;
    private String firmaEng;
    private String contactPerson;
    private String contactPersonEng;
    private String start;
    private String end;
    private String pass;
    private String phone;

    private String contractDate;
    private String volume;
    private String faculty;
    private String speciality;
    private String programm;
    private String level;
    private String facultyEng;
    private String specialityEng;
    private String programmEng;

    ////////////////
    private String total="";
    private String PriceUa="";
    private String PriceUsd="";
    private String priceFirm="";
    private String formaUa="";
    private String formaEng="";
   
    private String requisiteRu="";
    private String requisiteEng="";
    //////////////////
    private String contractValue;

    public String OUTPUT_FILE = "new.doc";
  
    /**
     * Variables for comboboxes
     */

    float years=0;
    int idDirection;
    private int numYear=4;
    private ArrayList<String> faculties =new ArrayList<String>();
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

    private String filepath=Info.PATERN_PATH+ "/Pattern/ContractEngMaster.doc";
  
    private String filepathBacalavr =Info.PATERN_PATH+ "/Pattern/ContractEngBacalavr.doc";
    
    private String filepathOwn = Info.PATERN_PATH+"/Pattern/ContractEngMasterOwn.doc";
    private String filepathBacalavrOwn =Info.PATERN_PATH+ "/Pattern/ContractEngBacalavrOwn.doc";
    
    
    public String APPLICATION_FILE = Info.PATERN_PATH+"/Pattern/application.doc";
    private POIFSFileSystem fs = null;
	private JLabel labelPrice;


    /**
     * Конструктор
     * @throws IOException
     */

   
    
   
    public ContractEng() throws IOException{

    	
        	
    
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
        labelFirm= new JLabel("Фирма*");
        labelContactPerson= new JLabel("Представитель*");
        labelContactPersonEng= new JLabel("Представитель (анг)");
        labelName = new JLabel("ФИО студента*");
        labelNameEng = new JLabel("ФИО студента (анг)");
        labelCountry = new JLabel("Гражданин *");
       
        labelFaculty = new JLabel("Факультет");
        labelSpeciality = new JLabel("Специальность");
        labelProgramm = new JLabel("Образовательная программа");
        labelLevel = new JLabel("Клалификация");
    

        labelPrice = new JLabel("Стоимость контракта:");
     
        labelStart = new JLabel("Срок с");
        labelEnd = new JLabel("Срок по");
      
        //labelPassNumber = new JLabel("Паспорт студента *");
        labelPhone = new JLabel("Телефон *");

        labelForm = new JLabel("Форма");
      
        /**
         * Buttons
         */

        buttonSave = new JButton("Сохранить");
      
        buttonClearAll = new JButton("Стереть все");
        buttonApplication = new JButton("Заявление");
        buttonSaveEntrance = new JButton("Создать карту");
        buttonReceipt = new JButton("Квитанция");
        buttonClearFirm = new JButton("Очистить фирму");
        /**
         * Comboboxes
         */

        boxCountry = new JComboBox(Countries.listCountriesUa.toArray());
        boxCountry.setSelectedIndex(-1);
        AutoCompleteDecorator.decorate(boxCountry);
        boxCountry.setEditable(true);
       
        boxForma = new JComboBox(new String[]{"денною", "заочною"});
        
        boxFaculty = new JComboBox(faculties.toArray());
        boxFaculty.setSelectedItem(" ");
        boxSpeciality = new JComboBox();
        boxSpeciality.setModel(specModel);
        boxProgramm = new JComboBox();
        boxProgramm.setModel(progModel);
        boxLevel = new JComboBox(new String[] {"бакалавра","магістра","магістра (ОНП)"});
        boxFirma = new JComboBox(Firms.listFirmsUa.toArray());
        
        boxFirma.setSelectedIndex(-1);
        AutoCompleteDecorator.decorate(boxFirma);
        boxFirma.setEditable(true);
      
        
        /**
         * TextFields
         */

        //////////////////////////////////////////////////////////
        contractPanel = new JPanel();
        textContractNumber = new JTextField("", 10);
        //textContractNumber.setText("56/"+DateUtilities.getCurYearPrefix()+"-");
        
        
        labelContractDate = new JLabel("Дата контракта");
        labelYear = new JLabel("Учебный год:");
        
      
        
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
        
        ///////////////////////////////////////////////////
        
 
        
        textContactPerson = new JTextField("", 25);
        textContactPersonEng = new JTextField("", 25);
       
        textPriceValue = new JTextField("", 30);
        textNameEng = new JTextField("", 30);
        //textVolume = new JTextField("", 5);
        textStart = new CDFormattedTextField();
        textStart.setColumns(8);
        textStart.setText(DateUtilities.applicationFormat(DateUtilities.getCurYearBegin()));
        textEnd = new CDFormattedTextField();
        textEnd.setColumns(8);
       
     
        
       // textPass = new JTextField("", 30);
        textPhone = new JTextField("", 30);
       // textYear = new JTextField(datedate, 10);
      
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

        
        //form.add(labelDate);
        //form.add(textDate, "wrap");
        form.add(labelFirm);
        form.add(boxFirma);
     
        form.add(buttonClearFirm, "wrap");
        form.add(labelContactPerson);
        form.add(textContactPerson, "wrap");
        form.add(labelContactPersonEng);
        form.add(textContactPersonEng, "wrap");
        
        form.add(labelName);
        form.add(fioPanel, "wrap");
        form.add(labelNameEng);
        form.add(textNameEng, "wrap");
        form.add(labelCountry);
        form.add(boxCountry, "wrap");
        form.add(labelForm);
        form.add(boxForma, "wrap");
        form.add(labelLevel);
        form.add(boxLevel,"wrap");
        form.add(labelFaculty);
        form.add(boxFaculty, "wrap");
        form.add(labelSpeciality);
        form.add(boxSpeciality, "wrap");
        form.add(labelProgramm);
        form.add(boxProgramm, "wrap");
        form.add(labelContractNumber);
        form.add(contractPanel, "wrap");
        form.add(labelPrice);
        form.add(textPriceValue, "wrap");
        form.add(labelPassNumber);
        form.add(pasportPanel,"wrap");
        form.add(labelPhone);
        form.add(textPhone, "wrap");
       

        
        form.add(labelStart);
        form.add(textStart, "wrap");
        form.add(labelEnd);
        form.add(textEnd, "wrap");

        form.add(buttonSave);
    
        form.add(buttonClearAll,"wrap");
        form.add(buttonSaveEntrance);
        form.add(buttonApplication);
        form.add(buttonReceipt);
        
        
        buttonClearFirm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			boxFirma.setSelectedIndex(-1);
			textContactPerson.setText("");
				textContactPersonEng.setText("");
			}
		});
 
        	buttonReceipt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				   contractNumber = textContractNumber.getText();
				   faculty = boxFaculty.getSelectedItem().toString().split("=")[0].trim();;
				   name = textStudSurname.getText()+" "+textStudName.getText()+" "+textStudMiddleName.getText();
				   String price2 = contractValue+" $ ( курс НБУ на "+ DateUtilities.applicationFormat(DateUtilities.getCurrentDate()) + " равен "+ TestFrame.rateUsd*Float.parseFloat(contractValue) + " грн. )";   
				ReceiptBuilder.createReceipt(datedate, contractNumber, faculty, name, price2, "");
				
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
                     	 String facultyName= ((String)o).split("=")[0].trim();
                             ArrayList<Speciality> specialities = treeMap.get(o);
                            // contractNumber = Faculties.listCodesForeign.get(Faculties.faculties.indexOf(o));
                             textContractNumber.setText(Faculties.listCodesForeign.get(Faculties.faculties.indexOf(facultyName)));		
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


            
         
         

    boxLevel.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
							
			setPricesandYears((String)boxForma.getSelectedItem(), (String)boxLevel.getSelectedItem(),(String) boxFaculty.getSelectedItem(),(String) boxSpeciality.getSelectedItem(),(String) boxProgramm.getSelectedItem());
			
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
            
            	
            	if(boxCountry.getSelectedIndex()<0){
            		JOptionPane.showMessageDialog(null, "Не все обязательные поля заполнены!!! Не выбрана страна");
            		return;
            	}
            	
            	if(boxFaculty.getSelectedItem()==null|| boxFaculty.getSelectedIndex()==0) {	
            		JOptionPane.showMessageDialog(null, "Не все обязательные поля заполнены!!!");
            		return;
            		}
            	

            	
            	contractNumber = textContractNumber.getText();
            	contractDate = textContractDate.getText();
            	formaUa = boxForma.getSelectedItem().toString();               
                contactPerson=textContactPerson.getText();
                contactPersonEng=textContactPersonEng.getText();
                name = textStudSurname.getText()+" "+textStudName.getText()+" "+textStudMiddleName.getText();
            	nameEng = textNameEng.getText();
                country = (String)boxCountry.getSelectedItem();
                faculty = boxFaculty.getSelectedItem().toString();
                speciality = boxSpeciality.getSelectedItem().toString();
                programm = boxProgramm.getSelectedItem().toString();
                level = boxLevel.getSelectedItem().toString();
              
                countryEng = Countries.listCountriesEng.get(boxCountry.getSelectedIndex());
               start = textStart.getText();
                end = textEnd.getText();
                faculty=boxFaculty.getSelectedItem().toString().split("=")[0];
                facultyEng=boxFaculty.getSelectedItem().toString().split("=")[1];
                pass = textPass.getText() + " д.р. "+ textDateOfBirth.getText();
                phone=textPhone.getText();
              
                try{
				if(boxFirma.getSelectedIndex()<0) //идут как личный контракт
        		{
            	
            		if(level.equals("магістра") || level.equals("магістра (ОНП)") )
                		fs = new POIFSFileSystem(new FileInputStream(filepathOwn));
                	else
                		fs = new POIFSFileSystem(new FileInputStream(filepathBacalavrOwn));

	          		  firma="";
	          		  firmaEng = "";
	          		requisiteRu="";
	          		requisiteEng="";
        		}
            	else
            	{
            	   	if(level.equals("магістра") || level.equals("магістра (ОНП)") )
                		fs = new POIFSFileSystem(new FileInputStream(filepath));
                	else
                		fs = new POIFSFileSystem(new FileInputStream(filepathBacalavr));
            	   	
            	   	firma=(String)boxFirma.getSelectedItem();
            		  firmaEng = Firms.listFirmsEng.get(boxFirma.getSelectedIndex());
            		requisiteRu=Firms.listFirmsRequisite.get(boxFirma.getSelectedIndex());
            		requisiteEng=Firms.listFirmsRequisiteEng.get(boxFirma.getSelectedIndex());
            	 
            	}
                    HWPFDocument doc = new HWPFDocument(fs);
                    doc = replaceText(doc, "ContractNumber", contractNumber);
                    doc = replaceText(doc, "contractDate", contractDate);
                     doc = replaceText(doc, "firmRu", firma);
                    doc = replaceText(doc, "firmPersonRu", contactPerson);
                    doc = replaceText(doc, "firmEng", firmaEng);
                    doc = replaceText(doc, "firmPersonEng", contactPersonEng);
                    doc = replaceText(doc, "studnameRu", name);
                    doc = replaceText(doc, "studnameEng", nameEng);
                    
                    doc = replaceText(doc, "countryRu", country);
                    doc = replaceText(doc, "countryEng", countryEng);
                
                    doc = replaceText(doc, "passStud", pass);
                    doc = replaceText(doc, "phone", phone);
                
                                     
                    doc = replaceText(doc, "total", total);
                    doc = replaceText(doc, "PriceUa", PriceUa);
                    doc = replaceText(doc, "PriceUSD", PriceUsd);
                    doc = replaceText(doc, "priceFirm", priceFirm);
                    doc = replaceText(doc, "FormaUa", formaUa);
                    doc = replaceText(doc, "formaEng", formaEng); 
                    
                    doc = replaceText(doc, "credits", volume);
                    
                    doc = replaceText(doc, "facultyRu", faculty);
                    doc = replaceText(doc, "specialityRu", speciality);
                    doc = replaceText(doc, "programmRu", programm);
                    
                    doc = replaceText(doc, "facultyEng", facultyEng);
                    doc = replaceText(doc, "specialityEng", specialityEng);
                    doc = replaceText(doc, "programmEng", programmEng);
                    
                
                                      
                    doc=replaceText(doc,"dayIn",start.split("/")[0]);
                    doc=replaceText(doc,"monthIn",start.split("/")[1]);
                    doc=replaceText(doc,"yearIn",start.split("/")[2]);
                    doc=replaceText(doc,"dayOut",end.split("/")[0]);
                    doc=replaceText(doc,"monthOut",end.split("/")[1]);
                    doc=replaceText(doc,"yearOut",end.split("/")[2]);
                    
                    doc=replaceText(doc,"requisiteRu",requisiteRu);
                    doc=replaceText(doc,"requisiteEng",requisiteEng);	
                    		
                    if(!name.equals("")&&!Info.OUTPUT_FILE_PATH.equals("."))
                    	OUTPUT_FILE=Info.OUTPUT_FILE_PATH+"/ENGLISH/"+name.trim()+".doc";
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
            
        });
 buttonApplication.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(boxCountry.getSelectedIndex()<0){
            		JOptionPane.showMessageDialog(null, "Не все обязательные поля заполнены!!! Не выбрана страна");
            		return;
            	}
            	
            	if(boxFaculty.getSelectedItem()==null|| boxFaculty.getSelectedIndex()==0) {	
            		JOptionPane.showMessageDialog(null, "Не все обязательные поля заполнены!!!");
            		return;
            		}
            	
				name = textStudSurname.getText()+" "+textStudName.getText()+" "+textStudMiddleName.getText();
            	country = (String)boxCountry.getSelectedItem();
                 faculty=boxFaculty.getSelectedItem().toString().split("=")[0];
                
                try{
                	
                	fs = new POIFSFileSystem(new FileInputStream(APPLICATION_FILE));
                    HWPFDocument doc = new HWPFDocument(fs);
                   
                    doc = replaceText(doc, "studnameRu", name);
                  
                    
                    doc = replaceText(doc, "countryRu", country);
                    doc = replaceText(doc, "faculty", faculty);
                    doc = replaceText(doc, "dateOfBirth", textDateOfBirth.getText());
                                      
                                       	
                    		
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
              
            	boxForma.setSelectedIndex(0);
            	boxFirma.setSelectedIndex(-1);
            	textContactPerson.setText("");
            	textContactPersonEng.setText("");
            	textDateOfBirth.setText("");
          	  textStudSurname.setText("");
          	  textStudName.setText("");
          	  textStudMiddleName.setText("");
                textNameEng.setText("");
                boxCountry.setSelectedItem("");
        boxFaculty.setSelectedIndex(-1);
               textContractNumber.setText(""); 
                textPass.setText("");
                textPhone.setText("");
                
           
            }
        });

      
        boxFirma.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(boxFirma.getSelectedIndex()>=0)
				{
				textContactPerson.setText(Firms.listFirmsHeads.get(boxFirma.getSelectedIndex()));
				textContactPersonEng.setText(Firms.listFirmsHeadsEng.get(boxFirma.getSelectedIndex()));
				}
				
			}
		});
        
        for (int i=0;i<form.getComponentCount();i++)
        	  form.getComponent(i).setFont(NumberUtilities.font);
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

  
  
    public Entrant createStudent()
    {
    	
    	if(boxCountry.getSelectedIndex()<0)
    	{
    		JOptionPane.showMessageDialog(null, "Не все обязательные поля заполнены!!! Не выбрана страна");
    		return null;
    	}
    
    	else
    	{
    		//разделить ФИО
	    	Entrant s = new Entrant();
	    	 
	    	s.payer = textPhone.getText();   
	    	s.StudSurname = textStudSurname.getText();
	    	s.StudName = textStudName.getText();
	    	s.StudMiddlename= textStudMiddleName.getText();
	    	s.dateOfBirth = textDateOfBirth.getDate();
	    	
	    	if(boxForma.getSelectedIndex()==0) //denna
	    		s.Ref_EducForm=1;
	    	else
	    		s.Ref_EducForm=2; //zaoxhna
	    	
	    	if(boxLevel.getSelectedIndex()==0){ //бакалавр
	    		s.Ref_LtoP=1;
	    		s.contractValue=Double.parseDouble(contractValue.trim());
	    		
	    	}
	    	else{
	    		s.Ref_LtoP=4; //magistr
	    		s.contractValue=Double.parseDouble(contractValue.trim());	
	    		s.contractValue= new BigDecimal( s.contractValue*10/(years*10)).setScale(0, RoundingMode.UP).doubleValue();
	    	}
	    	
	    	
	    	
	    	if(boxCountry.getSelectedIndex()>=0)
	    		s.Ref_Country=Countries.listCountriesID.get(boxCountry.getSelectedIndex());
	    	s.Ref_ArrivalLine=0;
	    	if(boxFirma.getSelectedIndex()>=0)
				s.ArrivalLine=Firms.listFirmsCodes.get(boxFirma.getSelectedIndex());
	    	else
	    		s.Ref_ArrivalLine=1;//личный контракт
	    	
	    	
	        s.Ref_Language=2;//engl
	    
	    	
	    	s.Course = 1;
	    	s.Years=years;
	    	s.Ref_Direction =idDirection;
	    	s.contractNumber=textContractNumber.getText();
	    		    
	    	s.Ref_ContractType=2;
	    	
	    	return s;
    	}
    	
    	
    }

    public void setPricesandYears(String form, String level, String faculty, String spec, String educProgram)
    {
    	
    //	System.out.println(form+" "+level+" "+faculty+" "+spec+" "+educProgram+" ");

		textPriceValue.setText("");
       
   
        volume="";
    	if(faculty!=null && !faculty.equals(" ") && form!=null && spec!=null && educProgram!=null && level!=null)
    	{
    		
    
    	  ArrayList<Speciality> specialities = treeMap.get(faculty);
    
    	
    	  for(Speciality speciality : specialities) {
              
              if(speciality.eduProgram.equals(educProgram)){
            	  idDirection = speciality.idDirection;
            	  specialityEng=speciality.specNameEng;
            	  programmEng =speciality.eduProgramEng;
            	  
                  if (level.equals("бакалавра") && form.equals("денною")) {
                	  years = 4;
                  	Calendar c= Calendar.getInstance();
                  	c.setTime(DateUtilities.getCurYearBegin());
                  	c.set(Calendar.MONTH, Calendar.JUNE);
                  	c.set(Calendar.DAY_OF_MONTH, 30);
					c.add(Calendar.YEAR, 4);
                    textEnd.setText(DateUtilities.applicationFormat(c.getTime()));
                    textPriceValue.setText(speciality.priceBachDayDigits+"("+speciality.priceBachDayString+")");
                   
                     volume="240";
                    numYear= speciality.numberZaYears;
                    contractValue= speciality.priceBachDayDigits;
                    formaEng="full-time";   
                  }
                  if (level.equals("бакалавра") && form.equals("заочною")) {
                	  years =  speciality.numberZaYears; 
                	  Calendar c= Calendar.getInstance();
                    	c.setTime(DateUtilities.getCurYearBegin());
                    	c.set(Calendar.MONTH, Calendar.JUNE);
                    	c.set(Calendar.DAY_OF_MONTH, 30);
  					c.add(Calendar.YEAR, speciality.numberZaYears);
                      textEnd.setText(DateUtilities.applicationFormat(c.getTime()));
                      textPriceValue.setText(speciality.priceBachZaDigits+"("+speciality.priceBachZaString+")");
                      numYear= speciality.numberZaYears;
                      volume="240";
                      formaEng="part-time";   
                      contractValue= speciality.priceBachZaDigits;
                    
                  }
                  if (level.equals("магістра (ОНП)") && form.equals("денною")) {
                	  years = 1.9f;
                	  Calendar c= Calendar.getInstance();
                  	c.setTime(DateUtilities.getCurYearBegin());
                  	c.set(Calendar.MONTH, Calendar.MAY);
                  	c.set(Calendar.DAY_OF_MONTH, 31);
					c.add(Calendar.YEAR, 2);
                    textEnd.setText(DateUtilities.applicationFormat(c.getTime()));
                 
                    textPriceValue.setText(speciality.priceMagDenDigitsONP+"("+speciality.priceMagDenStringONP+")");
                   // if(!speciality.priceMagDayString.equals(""))
                    //textPriceValue3.setText(""+(Integer.parseInt(speciality.priceMagDayString)-Integer.parseInt(speciality.priceMagDayDigits))+"");
                    formaEng="full-time";   
                    volume="120";
                    contractValue= speciality.priceMagDenDigitsONP;
                    
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
    					textPriceValue.setText(speciality.priceMagZaDigitsONP+"("+speciality.priceMagZaStringONP+")");
    	               // if(!speciality.priceMagZaString.equals(""))
    	                //	textPriceValue3.setText(""+(Integer.parseInt(speciality.priceMagZaString)-Integer.parseInt(speciality.priceMagZaDigits))+"");
    	                formaEng="part-time";   
                      volume="120";
                      contractValue= speciality.priceMagZaDigitsONP;
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
                    textPriceValue.setText(speciality.priceMagDayDigits+"("+speciality.priceMagDayString+")");
                   // if(!speciality.priceMagDayString.equals(""))
                    //textPriceValue3.setText(""+(Integer.parseInt(speciality.priceMagDayString)-Integer.parseInt(speciality.priceMagDayDigits))+"");
                  
                    volume="90";
                    formaEng="full-time";   
                    contractValue= speciality.priceMagDayDigits;
                    
                  }
                  if (level.equals("магістра") && form.equals("заочною")) {
                	  years = 1.4f;
                	  	Calendar c= Calendar.getInstance();
                      	c.setTime(DateUtilities.getCurYearBegin());
                      	c.set(Calendar.MONTH, Calendar.DECEMBER);
                      	c.set(Calendar.DAY_OF_MONTH, 31);
    					c.add(Calendar.YEAR, 1);
    					textEnd.setText(DateUtilities.applicationFormat(c.getTime()));
    				   
    					textPriceValue.setText(speciality.priceMagZaDigits+"("+speciality.priceMagZaString+")");
    	              //  if(!speciality.priceMagZaString.equals(""))
    	                //	textPriceValue3.setText(""+(Integer.parseInt(speciality.priceMagZaString)-Integer.parseInt(speciality.priceMagZaDigits))+"");
    	                formaEng="part-time";       
                      volume="90";
                      contractValue= speciality.priceMagZaDigits;
                  }
              }
          }
    	}
    
    	
    }
    
    public void readPrices() throws IOException{
        FileReader input = new FileReader(Info.PATERN_PATH+ "/prices_foreing_eng.csv");
        BufferedReader bufRead = new BufferedReader(input);
        String myLine;
        int f = 1;
        faculties.add(" ");

        

        while((myLine = bufRead.readLine())!= null){
        	
            Speciality spec = new Speciality();
            String[] arr = myLine.split(";");
        
           
                if(arr[0].equals("")){ //this means we have the same faculty but another speciality or education program
                    spec.specName = arr[1].trim().split("=")[0].trim();
                    spec.specNameEng = arr[1].trim().split("=")[1];
                    spec.eduProgram = arr[2].trim().split("=")[0];
                    spec.eduProgramEng = arr[2].trim().split("=")[1];

                    spec.priceBachDayDigits = arr[3];
                    spec.priceBachDayString = arr[4];
                    spec.priceBachDayStringEng = arr[5];
                 

                    spec.priceBachZaDigits = arr[6];
                    spec.priceBachZaString = arr[7];
                    spec.priceBachZaStringEng = arr[8];

                    spec.priceMagDayDigits = arr[9];
                    spec.priceMagDayString = arr[10];
                    spec.priceMagDayStringEng = arr[11];
             

                    spec.priceMagZaDigits = arr[12];
                    spec.priceMagZaString = arr[13];
                    spec.priceMagZaString = arr[14];
                    
                    spec.numberZaYears=Integer.parseInt(arr[15]);
                   spec.idDirection=Integer.parseInt(arr[16]);
                   
                   spec.priceMagDenDigitsONP=arr[17];
                   spec.priceMagDenStringONP=arr[18];
                   spec.priceMagDenStringONPEng=arr[19];
                   spec.priceMagZaDigitsONP=arr[20];
                   spec.priceMagZaStringONP=arr[21];
                   spec.priceMagZaStringONPEng=arr[22];
                    

                    ArrayList<Speciality> ss=treeMap.get(faculties.get(f-1));
                    ss.add(spec);
                }
                else{ //this means we have new faculty
                    specs=new ArrayList<Speciality>();
                    faculties.add(arr[0].trim());
                    f++;

                    spec.specName = arr[1].trim().split("=")[0].trim();
                    spec.specNameEng = arr[1].trim().split("=")[1];
                    spec.eduProgram = arr[2].trim().split("=")[0];
                    spec.eduProgramEng = arr[2].trim().split("=")[1];
                   
                    spec.priceBachDayDigits = arr[3];
                    spec.priceBachDayString = arr[4];
                    spec.priceBachDayStringEng = arr[5];
                 

                    spec.priceBachZaDigits = arr[6];
                    spec.priceBachZaString = arr[7];
                    spec.priceBachZaStringEng = arr[8];

                    spec.priceMagDayDigits = arr[9];
                    spec.priceMagDayString = arr[10];
                    spec.priceMagDayStringEng = arr[11];
             

                    spec.priceMagZaDigits = arr[12];
                    spec.priceMagZaString = arr[13];
                    spec.priceMagZaString = arr[14];
                    
                    spec.numberZaYears=Integer.parseInt(arr[15]);
                   spec.idDirection=Integer.parseInt(arr[16]);
                   
                   spec.priceMagDenDigitsONP=arr[17];
                   spec.priceMagDenStringONP=arr[18];
                   spec.priceMagDenStringONPEng=arr[19];
                   spec.priceMagZaDigitsONP=arr[20];
                   spec.priceMagZaStringONP=arr[21];
                   spec.priceMagZaStringONPEng=arr[22];

                    specs.add(spec);
                    treeMap.put(arr[0],specs);
                }
            }
        bufRead.close();
     //  System.out.println(treeMap);
    }
    public JPanel createTab()
    {
    	return form;
    }

}

