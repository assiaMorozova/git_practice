package contract.med;
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

import main.TestFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by Слава on 20.05.2017.
 */
public class ContractMED  {

    private JPanel form;
    private JPanel fioPanel;
    private JPanel pasportPanel;
    private JLabel labelDateOfBirth;
    private CDFormattedTextField textDateOfBirth;
    
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
    private JLabel labelPrice;
    
//    private JLabel labelProgramm;
//    private JLabel labelLevel;
   
   
    
    private JLabel labelStart;
   private JLabel labelEnd;
    //private JLabel labelSeries;
    private JLabel labelPassNumber;
    private JLabel labelPhone;
 
 /*   private JLabel labelForm;
    private JLabel labelYear;
    private JLabel labelClient;
    private JLabel labelClientFIO;
    private JLabel labelClientPass;
    private JLabel labelClientAdress;
    */
    private JLabel labelLanguage;

    /**
     * Comboboxes
     */

    private JComboBox boxCountry;
   
    private JComboBox boxFirma;
    private JComboBox boxLang;

    /**
     * TextFields
     */
    private JTextField textPriceValue;
    private JTextField textStudSurname;
    private JTextField textStudName;
    private JTextField textStudMiddleName;
    
    private JTextField textContractNumber;
    private JTextField textContactPerson;
    private JTextField textContactPersonEng;
    CDFormattedTextField textDate;
    
    private JTextField textNameEng;
    private JTextField textPass;
    private CDFormattedTextField textStart;
    private CDFormattedTextField textEnd;

    private JTextField textPhone;
   


    /**
     * Buttons
     */

    private JButton buttonSave;
    private JButton buttonClear;
    private JButton buttonClearAll;
    private JButton buttonApplication;
    private JButton buttonSaveEntrance;

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
    private String faculty;
    private String contractDate;

    public String OUTPUT_FILE = "new.doc";
    public String APPLICATION_FILE = Info.PATERN_PATH+"/Pattern/application.doc";

    /**
     * Variables for comboboxes
     */

  
  

    DefaultComboBoxModel specModel = new DefaultComboBoxModel();
    DefaultComboBoxModel progModel = new DefaultComboBoxModel();

    DateFormat dateF = new SimpleDateFormat("yyyy");
    Date dateD = new Date();

    Calendar cal = Calendar.getInstance();
    String datedate;



    /**
     * Variables for POI
     */

    private String filepath ="";//Info.PATERN_PATH+ "/Pattern/ContractMedRu.doc"; 
 
    private POIFSFileSystem fs = null;
	private JLabel labelContractDate;
	private JLabel labelYear;
	private CDFormattedTextField textContractDate;
	private JTextField textYear;
	private JPanel contractPanel;
	private JButton buttonReceipt;
	private JButton buttonClearFirm;





    public ContractMED() throws IOException{

    	
    	//Read prop
    	
    	
    	//  	System.out.println(WORD_PATH);
    
        /**
         * Read from txt
         */

    	try{
    		
        initComponents();
        setFilePath();
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
       //labelFaculty = new JLabel("Факультет");
        //labelSpeciality = new JLabel("Спеціальність");
       // labelProgramm = new JLabel("Освітня програма");
       // labelLevel = new JLabel("Ступінь");
        //labelVolume = new JLabel("Объем нагрузки");
       labelPrice = new JLabel("Стоимость контракта:");
       // labelPrice2 = new JLabel("Оплата Фирме:");
       // labelForm = new JLabel("Форма");
       // labelYear = new JLabel("Навчальний рік");
       // labelClient = new JLabel("Замовник:");
       // labelClientFIO = new JLabel("ПІБ Замовника ");
        //labelClientPass = new JLabel("Паспорт  ");
     //   labelClientAdress = new JLabel("Адреса замовника* ");
        labelLanguage = new JLabel("Язык обучения:*");


      //  textPriceValue1 = new JTextField("",10);
       // textPriceValue2 = new JTextField("", 10);
        labelStart = new JLabel("Срок с");
        labelEnd = new JLabel("Срок по");
        //labelSeries = new JLabel("Паспорт серия *");
    //    labelPassNumber = new JLabel("Паспорт студента *");
        labelPhone = new JLabel("Телефон *");

        /**
         * Buttons
         */

        buttonSave = new JButton("Сохранить");
        buttonClear = new JButton("Стереть *");
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
      //  boxFaculty = new JComboBox(faculties);
       // boxFaculty.setSelectedItem(" ");
       // boxSpeciality = new JComboBox();
        //boxSpeciality.setModel(specModel);
        //boxProgramm = new JComboBox();
        
        boxFirma = new JComboBox(Firms.listFirmsUa.toArray());
        
        boxFirma.setSelectedIndex(-1);
        AutoCompleteDecorator.decorate(boxFirma);
        boxFirma.setEditable(true);
        boxLang = new JComboBox(new String[] {"русский", "английский"});
        
        /**
         * TextFields
         */

        //////////////////////////////////////////////////////////
        contractPanel = new JPanel();
        textContractNumber = new JTextField("", 10);
        textContractNumber.setText("35/"+DateUtilities.getCurYearPrefix()+"-");
        
        
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
        textDate = new CDFormattedTextField();
        textDate.setColumns(8);
      
        textNameEng = new JTextField("", 30);
        textPriceValue = new JTextField("", 10);
        textStart = new CDFormattedTextField();
        textStart.setColumns(8);
        textStart.setText(DateUtilities.applicationFormat(DateUtilities.getCurYearBegin()));
        textEnd = new CDFormattedTextField();
        textEnd.setColumns(8);
       Calendar c= Calendar.getInstance();
      	c.setTime(DateUtilities.getCurYearBegin());
      	c.set(Calendar.MONTH, Calendar.JUNE);
      	c.set(Calendar.DAY_OF_MONTH, 30);
		c.add(Calendar.YEAR, 6);
        textEnd.setText(DateUtilities.applicationFormat(c.getTime()));
        
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

        form.add(labelContractNumber);
        form.add(contractPanel, "wrap");
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
       
        form.add(labelLanguage);
        form.add(boxLang, "wrap");
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
        form.add(buttonClear);
        form.add(buttonClearAll, "wrap");
        form.add(buttonApplication);
        form.add(buttonSaveEntrance);
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
				   faculty = "Медичний";
				   name = textStudSurname.getText()+" "+textStudName.getText()+" "+textStudMiddleName.getText();
	               
				   String price2="";
				  price2 = textPriceValue.getText()+" $ ( курс НБУ на "+ DateUtilities.applicationFormat(DateUtilities.getCurrentDate()) + " равен "+ TestFrame.rateUsd*Float.parseFloat(textPriceValue.getText()) + " грн. )";
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
          

			private String requisiteRu;
			private String requisiteEng;

			@Override
            public void actionPerformed(ActionEvent e) {
            
            	
            	if(boxCountry.getSelectedIndex()<0)
            	{
            		JOptionPane.showMessageDialog(null, "Не все обязательные поля заполнены!!! Не выбрана страна");
            		return;
            	}
            	
            		
            	//contractNumber = Firms.listFirmsCodes.get(boxFirma.getSelectedIndex());
            	contractNumber = textContractNumber.getText();
            	  contractDate = textContractDate.getText();
            	  
            	  if(boxFirma.getSelectedIndex()>=0){
            		  firma=(String)boxFirma.getSelectedItem();
            		  firmaEng = Firms.listFirmsEng.get(boxFirma.getSelectedIndex());
            		  requisiteRu=Firms.listFirmsRequisite.get(boxFirma.getSelectedIndex());
                	  requisiteEng=Firms.listFirmsRequisiteEng.get(boxFirma.getSelectedIndex());
            	  }
            	  else
            	  {
            		  requisiteRu="";
                	  requisiteEng="";
            		  firma="";
            		  firmaEng = "";
            	  }
                contactPerson=textContactPerson.getText();
                contactPersonEng=textContactPersonEng.getText();
            	name = textStudSurname.getText()+" "+textStudName.getText()+" "+textStudMiddleName.getText();
            	nameEng = textNameEng.getText();
                country = (String)boxCountry.getSelectedItem();
                countryEng = Countries.listCountriesEng.get(boxCountry.getSelectedIndex());
                start = textStart.getText();
                end = textEnd.getText();
               
                pass = textPass.getText();
                phone=textPhone.getText();
                
                
                try{
                	
                	fs = new POIFSFileSystem(new FileInputStream(filepath));
                    HWPFDocument doc = new HWPFDocument(fs);
                    doc = replaceText(doc, "contactNumber", contractNumber);
                    doc = replaceText(doc, "contractDate", contractDate);
                    //
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
                
                    doc = replaceText(doc, "dateOfBirth", textDateOfBirth.getText());                  
                    doc=replaceText(doc,"dayIn",start.split("/")[0]);
                    doc=replaceText(doc,"monthIn",start.split("/")[1]);
                    doc=replaceText(doc,"yearIn",start.split("/")[2]);
                    doc=replaceText(doc,"dayOut",end.split("/")[0]);
                    doc=replaceText(doc,"monthOut",end.split("/")[1]);
                    doc=replaceText(doc,"yearOut",end.split("/")[2]);
                    doc=replaceText(doc,"requisiteRu",requisiteRu);
                    doc=replaceText(doc,"requisiteEng",requisiteEng);	
                    	
                    		
                    if(!name.equals("")&&!Info.OUTPUT_FILE_PATH.equals("."))
                    	OUTPUT_FILE=Info.OUTPUT_FILE_PATH+"/MEDICAL_ENGLISH/"+name.trim()+".doc";
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
				
            	name = textStudSurname.getText()+" "+textStudName.getText()+" "+textStudMiddleName.getText();
            	country = (String)boxCountry.getSelectedItem();
                 
                
                try{
                	
                	fs = new POIFSFileSystem(new FileInputStream(APPLICATION_FILE));
                    HWPFDocument doc = new HWPFDocument(fs);
                   
                    doc = replaceText(doc, "studnameRu", name);
                  
                    
                    doc = replaceText(doc, "countryRu", country);
                    doc = replaceText(doc, "faculty", "медицинского");
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
        buttonClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            
            	textContractNumber.setText("35/"+DateUtilities.getCurYearPrefix()+"-");
            	  textStudSurname.setText("");
            	  textStudName.setText("");
            	  textStudMiddleName.setText("");
                textNameEng.setText("");
                boxCountry.setSelectedItem("");
                
                textPass.setText("");
                textPhone.setText("");
                textDateOfBirth.setText("");
               
            }
        });

        buttonClearAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              
            	 textContractNumber.setText("35/"+DateUtilities.getCurYearPrefix()+"-");
            	boxFirma.setSelectedIndex(-1);
            	textContactPerson.setText("");
            	textContactPersonEng.setText("");
            	textStudSurname.setText("");
            	textStudName.setText("");
            	textStudMiddleName.setText("");
                textNameEng.setText("");
                boxCountry.setSelectedItem("");
                boxLang.setSelectedIndex(0);
                
                textPass.setText("");
                textPhone.setText("");
                textDateOfBirth.setText("");
           
            }
        });

        boxLang.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setFilePath();
				
			}
		});
        boxFirma.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(boxFirma.getSelectedIndex()>=0){
					
				textContactPerson.setText(Firms.listFirmsHeads.get(boxFirma.getSelectedIndex()));
				textContactPersonEng.setText(Firms.listFirmsHeadsEng.get(boxFirma.getSelectedIndex()));
				}
				setFilePath();
				
			}
		});
        
        for (int i=0;i<form.getComponentCount();i++)
        	  form.getComponent(i).setFont(NumberUtilities.font);
    }

    private void setFilePath ()
    {
    	if(boxFirma.getSelectedIndex()>=0)	{
    		if(boxLang.getSelectedIndex()==0)
    			filepath=Info.PATERN_PATH+ "/Pattern/ContractMedRu.doc";
			else
				filepath = Info.PATERN_PATH+ "/Pattern/ContractMedEng.doc";
	  	}
    	else {
    		if(boxLang.getSelectedIndex()==0)
    			filepath=Info.PATERN_PATH+ "/Pattern/ContractMedRuOwn.doc";
			else
				filepath = Info.PATERN_PATH+ "/Pattern/ContractMedEngOwn.doc";
    	}
    }
    
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
	    	s.Ref_LtoP=4; //magistr
    		s.Ref_EducForm=1;//denna
	    	
	    	if(boxCountry.getSelectedIndex()>=0)
	    		s.Ref_Country=Countries.listCountriesID.get(boxCountry.getSelectedIndex());
	    	s.Ref_ArrivalLine=0;
	    	if(boxFirma.getSelectedIndex()>=0)
					s.ArrivalLine=Firms.listFirmsCodes.get(boxFirma.getSelectedIndex());
		    	else
		    		s.Ref_ArrivalLine=1;//личный контрак
	    	
	    	if(boxLang.getSelectedIndex()>=0)
	    		s.Ref_Language=boxLang.getSelectedIndex()+1;
	    	
	    	s.Course = 1;
	    	s.Years=6;
	    	s.Ref_Direction =28;
	    	s.contractNumber=textContractNumber.getText();
	    	s.contractValue=Double.parseDouble(textPriceValue.getText());
	    	s.Ref_ContractType=2;
	    
	    	return s;
    	}
    	
    	
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

 

    public JPanel createTab()
    {
    	return form;
    }

}

