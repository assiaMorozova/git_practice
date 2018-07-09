package contract.law;
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
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

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
public class ContractLaw  {

    private JPanel form;
    private JPanel fioPanel;
    private JPanel contractPanel;
    private JPanel pasportPanel;
    private JLabel labelDateOfBirth;
    private CDFormattedTextField textDateOfBirth;
    
    
    Prices p= new Prices();
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
  //  private JLabel labelFaculty;
    
//    private JLabel labelProgramm;
//    private JLabel labelLevel;
   

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
   

    public String OUTPUT_FILE = "new.doc";
  
    /**
     * Variables for comboboxes
     */

  
    Map<String, ArrayList<Prices>> treeMap = new TreeMap<String, ArrayList<Prices>>();

    DefaultComboBoxModel specModel = new DefaultComboBoxModel();
    DefaultComboBoxModel progModel = new DefaultComboBoxModel();

    DateFormat dateF = new SimpleDateFormat("yyyy");
    Date dateD = new Date();

    Calendar cal = Calendar.getInstance();
    String datedate;



    /**
     * Variables for POI
     */

    private String filepath=Info.PATERN_PATH+ "/Pattern/ContractLaw.doc";
    public String APPLICATION_FILE = Info.PATERN_PATH+"/Pattern/application.doc";
    private POIFSFileSystem fs = null;
	private JLabel labelPrice;


    /**
     * Конструктор
     * @throws IOException
     */

   
    
   
    public ContractLaw() throws IOException{

    	
        	
    
       	try{
    		readPrices();
    		
        initComponents();
        setPrices();
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
        fioPanel.add(textStudMiddleName);
        
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
       
        labelLanguage = new JLabel("Язык обучения:*");

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
        boxForma = new JComboBox(new String[]{"денною", "заочною"});
      //  boxFaculty = new JComboBox(faculties);
       // boxFaculty.setSelectedItem(" ");
       // boxSpeciality = new JComboBox();
        //boxSpeciality.setModel(specModel);
        //boxProgramm = new JComboBox();
        
        boxFirma = new JComboBox(Firms.listFirmsUa.toArray());
        
        boxFirma.setSelectedIndex(-1);
        AutoCompleteDecorator.decorate(boxFirma);
        boxFirma.setEditable(true);
        boxLang = new JComboBox(new String[] { "английский","русский"});
        
        /**
         * TextFields
         */

        //////////////////////////////////////////////////////////
        contractPanel = new JPanel();
        textContractNumber = new JTextField("", 10);
        textContractNumber.setText("56/"+DateUtilities.getCurYearPrefix()+"-");
        
        
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
       
        textPriceValue = new JTextField("", 10);
        textNameEng = new JTextField("", 30);
        //textVolume = new JTextField("", 5);
        textStart = new CDFormattedTextField();
        textStart.setColumns(8);
        textStart.setText(DateUtilities.applicationFormat(DateUtilities.getCurYearBegin()));
        textEnd = new CDFormattedTextField();
        textEnd.setColumns(8);
       Calendar c= Calendar.getInstance();
      	c.setTime(DateUtilities.getCurYearBegin());
      	c.set(Calendar.MONTH, Calendar.JUNE);
      	c.set(Calendar.DAY_OF_MONTH, 30);
		c.add(Calendar.YEAR, 4);
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
        form.add(labelForm);
        form.add(boxForma, "wrap");
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
				   faculty = "Юридичний";
				   name = textStudSurname.getText()+" "+textStudName.getText()+" "+textStudMiddleName.getText();
	               
	                
				ReceiptBuilder.createReceipt(datedate, contractNumber, faculty, name, textPriceValue.getText(), "");
				
			}
		});

        boxLang.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setPrices();
				
			}
		});
        boxForma.addActionListener(new ActionListener() {
			
   			@Override
   			public void actionPerformed(ActionEvent e) {
   				setPrices();
   				
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
            
            	
            	if(boxCountry.getSelectedIndex()<0)
        		{
	        		JOptionPane.showMessageDialog(null, "Не все обязательные поля заполнены!!! Не выбрана страна");
	        		return;
        		}
            	
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
	          		  filepath =Info.PATERN_PATH+ "/Pattern/ContractLawOwn.doc" ;
	          		  firma="";
	          		  firmaEng = "";
	          		requisiteRu="";
	          		requisiteEng="";
	          	  }
                
                contactPerson=textContactPerson.getText();
                contactPersonEng=textContactPersonEng.getText();
                name = textStudSurname.getText()+" "+textStudName.getText()+" "+textStudMiddleName.getText();
            	nameEng = textNameEng.getText();
                country = (String)boxCountry.getSelectedItem();
                
                	countryEng = Countries.listCountriesEng.get(boxCountry.getSelectedIndex());
               start = textStart.getText();
                end = textEnd.getText();
               
                pass = textPass.getText() + " д.р. "+ textDateOfBirth.getText();
                phone=textPhone.getText();
                setPrices(boxForma.getSelectedItem().toString(),boxLang.getSelectedItem().toString());
                
                try{
                	
                	fs = new POIFSFileSystem(new FileInputStream(filepath));
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
                                      
                    doc=replaceText(doc,"dayIn",start.split("/")[0]);
                    doc=replaceText(doc,"monthIn",start.split("/")[1]);
                    doc=replaceText(doc,"yearIn",start.split("/")[2]);
                    doc=replaceText(doc,"dayOut",end.split("/")[0]);
                    doc=replaceText(doc,"monthOut",end.split("/")[1]);
                    doc=replaceText(doc,"yearOut",end.split("/")[2]);
                    
                    doc=replaceText(doc,"requisiteRu",requisiteRu);
                    doc=replaceText(doc,"requisiteEng",requisiteEng);	
                    		
                    if(!name.equals("")&&!Info.OUTPUT_FILE_PATH.equals("."))
                    	OUTPUT_FILE=Info.OUTPUT_FILE_PATH+"/LAW_ENGLISH/"+name.trim()+".doc";
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
                    doc = replaceText(doc, "faculty", "юридического");
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
            
            	textContractNumber.setText("56/"+DateUtilities.getCurYearPrefix()+"-");
          	  textStudSurname.setText("");
          	  textStudName.setText("");
          	  textStudMiddleName.setText("");
                textNameEng.setText("");
                boxCountry.setSelectedItem("");
                
                textPass.setText("");
                textPhone.setText("");
               
               
            }
        });

        buttonClearAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              
            	boxForma.setSelectedIndex(0);
            	boxFirma.setSelectedIndex(-1);
            	textContactPerson.setText("");
            	textContactPersonEng.setText("");
            	textContractNumber.setText("56/"+DateUtilities.getCurYearPrefix()+"-");
          	  textStudSurname.setText("");
          	  textStudName.setText("");
          	  textStudMiddleName.setText("");
                textNameEng.setText("");
                boxCountry.setSelectedItem("");
                boxLang.setSelectedIndex(0);
                
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

    public void readPrices() throws IOException{
        FileReader input = new FileReader( Info.PATERN_PATH+ "/law_prices.csv");
        BufferedReader bufRead = new BufferedReader(input);
        String myLine;
       int k=1;
       
        while((myLine = bufRead.readLine())!= null){
        	
        	String[] arr = myLine.split(";");
        	switch(k)
        	{
        	case 1:
        		p.priceBachDayTotalRu=arr[2];
        		p.priceBachDayUkrDigitsRu=arr[3];
        		p.priceBachDayUkrStringRu=arr[4];
        		p.priceBachDayEngStringRu=arr[5];
        		p.priceFirmBachDayRu=arr[6];
        	
        		k++;break;
        	case 2:
        		p.priceBachZaTotalRu=arr[2];
        		p.priceBachZaUkrDigitsRu=arr[3];
        		p.priceBachZaUkrStringRu=arr[4];
        		p.priceBachZaEngStringRu=arr[5];
        		p.priceFirmBachZaRu=arr[6];
        		
        		
        		k++;break;
        	case 3:
        		p.priceBachDayTotalEng=arr[2];
        		p.priceBachDayUkrDigitsEng=arr[3];
        		p.priceBachDayUkrStringEng=arr[4];
        		p.priceBachDayEngStringEng=arr[5];
        		p.priceFirmBachDayEng=arr[6];
        		
        		k++;break;
        	case 4:
        		p.priceBachZaTotalEng=arr[2];
        		p.priceBachZaUkrDigitsEng=arr[3];
        		p.priceBachZaUkrStringEng=arr[4];
        		p.priceBachZaEngStringEng=arr[5];
        		p.priceFirmBachZaEng=arr[6];
        		
        		k++;break;
        	}
            }
        bufRead.close();
        
     //  System.out.println(treeMap);
    }
    
    private void setPrices()   {
    	if(boxForma.getSelectedIndex()==0)//denna
    	{
    		if(boxLang.getSelectedIndex()==0)//eng
    			textPriceValue.setText(p.priceBachDayUkrDigitsEng);
    		if(boxLang.getSelectedIndex()==1)//ukr
    			textPriceValue.setText(p.priceBachDayUkrDigitsRu);
    	}
    	if(boxForma.getSelectedIndex()==1)//zao
    	{
    		if(boxLang.getSelectedIndex()==0)//eng
    			textPriceValue.setText(p.priceBachZaUkrDigitsEng);
    		if(boxLang.getSelectedIndex()==1)//ukr
    			textPriceValue.setText(p.priceBachZaUkrDigitsRu);
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

    /**
     * Функция сохранения Word-файла
     * @param filePath
     * @param doc
     * @throws FileNotFoundException
     * @throws IOException
     */

  
    public void setPrices(String form, String language)
    {
    	
    //	System.out.println(form+" "+level+" "+faculty+" "+spec+" "+educProgram+" ");

		     if (language.equals("русский") && form.equals("денною")) {
                  	
		    	    total = p.priceBachDayTotalRu;
		    	    PriceUa=p.priceBachDayUkrDigitsRu+" ("+p.priceBachDayUkrStringRu+")";
		    	    PriceUsd=p.priceBachDayUkrDigitsRu+System.getProperty("line.separator")+"("+p.priceBachDayEngStringRu+")";
		    	    priceFirm=p.priceFirmBachDayRu;
		    	    formaUa=form;
		    	    formaEng="full-time";   
		    		//contractNumber = p.contractBachDayRu;
		    		textPriceValue.setText(p.priceBachDayUkrDigitsRu);
		    		
		    		
                  }
               if (language.equals("русский") && form.equals("заочною")) {
                            
            	   total = p.priceBachZaTotalRu;
		    	    PriceUa=p.priceBachZaUkrDigitsRu+" ("+p.priceBachZaUkrStringRu+")";
		    	    PriceUsd=p.priceBachZaUkrDigitsRu.trim()+System.getProperty("line.separator")+"("+p.priceBachZaEngStringRu+")";
		    	    priceFirm=p.priceFirmBachZaRu;
		    	    formaUa=form;
		    	    formaEng="part-time";   
		    		//contractNumber = p.contractBachZaRu;
		    		textPriceValue.setText(p.priceBachZaUkrDigitsRu);
                    
                  }
                if (language.equals("английский") && form.equals("денною")) {
                 	
                	total = p.priceBachDayTotalEng;
		    	    PriceUa=p.priceBachDayUkrDigitsEng+" ("+p.priceBachDayUkrStringEng+")";
		    	    PriceUsd=p.priceBachDayUkrDigitsEng+System.getProperty("line.separator")+"("+p.priceBachDayEngStringEng+")";
		    	    priceFirm=p.priceFirmBachDayEng;
		    	    formaUa=form;
		    	    formaEng="full-time";   
		    		//contractNumber = p.contractBachDayEng;
		    		textPriceValue.setText(p.priceBachDayUkrDigitsEng);
                  }
                  if (language.equals("английский") && form.equals("заочною")) {
                	  	
                	total = p.priceBachZaTotalEng;
  		    	    PriceUa=p.priceBachZaUkrDigitsEng+" ("+p.priceBachZaUkrStringEng+")";
  		    	    PriceUsd=p.priceBachZaUkrDigitsEng+System.getProperty("line.separator")+"("+p.priceBachZaEngStringEng+")";
  		    	    priceFirm=p.priceFirmBachZaEng;
  		    	    formaUa=form;
  		    	    formaEng="part-time";   
  		    	//	contractNumber = p.contractBachZaEng;
  		    		textPriceValue.setText(p.priceBachZaUkrDigitsEng);
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
	    	s.Ref_LtoP=1;//back
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
	    	
	    	if(boxLang.getSelectedIndex()==0)
	    		s.Ref_Language=2;//engl
	    	else
	    		s.Ref_Language=1;//ukr
	    	
	    	s.Course = 1;
	    	s.Years=4;
	    	s.Ref_Direction =14;
	    	s.contractNumber=textContractNumber.getText();
	    	s.contractValue=Double.parseDouble(textPriceValue.getText());
	    	s.Ref_ContractType=2;
	    
	    	return s;
    	}
    	
    	
    }

    public JPanel createTab()
    {
    	return form;
    }

}

