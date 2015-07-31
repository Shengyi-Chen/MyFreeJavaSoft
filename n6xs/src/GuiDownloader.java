

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingWorker;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.awt.Color;
import java.io.*;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;

import javax.swing.SwingConstants;
import java.awt.Font;

public class GuiDownloader extends JFrame 
							implements PropertyChangeListener, MouseListener  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5672578923233236169L;
	private JPanel contentPane;
	private JTextField textFieldStart;
	private JButton btnOpenNxscomWeb;
	private JButton btnDownload;
	private JTextField textFieldEnd;
	private JTextField textField;
	private ArrayList<Task> taskList;
	private int currentStep = 0, total, startN, endN;
	private String bookFolderPath;
	private JLabel labelPercentage;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuiDownloader frame = new GuiDownloader();
					frame.setVisible(true);					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the frame.
	 */
	public GuiDownloader() {
		setAlwaysOnTop(true);
		setTitle("n6xs.com book downloader");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 515, 167);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("104px"),
				FormFactory.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("249px"),
				FormFactory.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("100px"),},
			new RowSpec[] {
				FormFactory.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("22px"),
				FormFactory.NARROW_LINE_GAP_ROWSPEC,
				RowSpec.decode("22px"),
				FormFactory.NARROW_LINE_GAP_ROWSPEC,
				RowSpec.decode("22px"),
				FormFactory.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("25px"),}));
		
		JLabel lblArticleNumber = new JLabel("Start link:");
		lblArticleNumber.setFont(new Font("Times New Roman", Font.BOLD, 22));
		contentPane.add(lblArticleNumber, "2, 4, fill, center");
		
		textFieldStart = new JTextField();
		contentPane.add(textFieldStart, "4, 4, 3, 1, fill, top");
		textFieldStart.setColumns(10);
		
		btnOpenNxscomWeb = new JButton("Open web");
		btnOpenNxscomWeb.setBackground(Color.WHITE);
		btnOpenNxscomWeb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equalsIgnoreCase("Open web")){
					if(Desktop.isDesktopSupported()){
			            Desktop desktop = Desktop.getDesktop();
			            try {
			                desktop.browse(new URI("http://www.n6xs.com/"));
			            } catch (IOException | URISyntaxException e11) {
			                // TODO Auto-generated catch block
			                e11.printStackTrace();
			            }
			        }else{
			            Runtime runtime = Runtime.getRuntime();
			            try {
			                runtime.exec("xdg-open " + "http://www.n6xs.com/");
			            } catch (IOException e1) {
			                // TODO Auto-generated catch block
			                e1.printStackTrace();
			            }
			        }
				}
			}
		});
		
		labelPercentage = new JLabel("- %");
		labelPercentage.setFont(new Font("Vladimir Script", Font.PLAIN, 22));
		labelPercentage.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(labelPercentage, "2, 8");
		contentPane.add(btnOpenNxscomWeb, "4, 8, right, top");
		
		btnDownload = new JButton("Download");
		btnDownload.setBackground(Color.WHITE);
		btnDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equalsIgnoreCase("Download")){					
					startDownload();					
				}			
			}
		});
		contentPane.add(btnDownload, "6, 8, fill, top");
		
		JLabel lblEndingPage = new JLabel("End link:");
		lblEndingPage.setFont(new Font("Times New Roman", Font.BOLD, 22));
		contentPane.add(lblEndingPage, "2, 6, fill, center");
		
		textFieldEnd = new JTextField();
		textFieldEnd.setColumns(10);
		contentPane.add(textFieldEnd, "4, 6, 3, 1, fill, top");
		
		JLabel lblNewLabel = new JLabel("Book title: ");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 22));
		contentPane.add(lblNewLabel, "2, 2, fill, center");
		
		textField = new JTextField();		
		textField.setColumns(10);
		contentPane.add(textField, "4, 2, 3, 1, fill, top");
	}
	
	private void startDownload(){		
		String bookFolderName = textField.getText();
		String sText = textFieldStart.getText();
		String eText = textFieldEnd.getText();
		taskList = new ArrayList<Task>();
		
		try {
			if (bookFolderName != null && !bookFolderName.isEmpty()){
				String parentPath = GuiDownloader.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
				if(parentPath.endsWith("null")) {
					parentPath = parentPath.substring(0, parentPath.length()-4);
				}
				
				File bookFolder = new File(parentPath + textField.getText());
				if (bookFolder.exists() || bookFolder.isFile()){
					bookFolder.delete();
				}
				bookFolder.mkdirs();
				bookFolderPath = bookFolder.getAbsolutePath();
			
				if (sText.contains("=") && eText.contains("=")){				
					startN = Integer.parseInt(sText.substring(sText.indexOf("=")+1));
					endN = Integer.parseInt(eText.substring(eText.indexOf("=")+1));
					
					btnDownload.setEnabled(false);
					labelPercentage.setText(Integer.toString(currentStep) +" %");
					setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					
				    currentStep = 0;
				    total = 0;
					for (int x=Integer.min(startN, endN); x <= Integer.max(startN, endN); x++){
						Task task = new Task(Integer.toString(x), bookFolder.getAbsolutePath() + System.getProperty("file.separator")/*, Integer.max(startN, endN) - Integer.min(startN, endN) + 1*/);
					    task.addPropertyChangeListener(this);
					    taskList.add(task);					      
					}
					total = taskList.size();
					
					Task taskManager = new Task(true);
					taskManager.addPropertyChangeListener(this);    
					taskManager.execute();
				}
			}
		}
		catch (MalformedURLException | URISyntaxException e) {			
			e.printStackTrace();
		}
		//
	}
	
	class Task extends SwingWorker<Void, Void> {
        /*
         * Main task. Executed in background thread.
         */
		private downloader.n6xs.com.Article2HTML a2html;
		//private int totalFileNumber;
		private boolean myTester = false;
		
		public Task(boolean isManager){
			myTester = isManager;
		}
		
		
		public Task(String downloadNumber, String fileDirectoryPath/*, int totalFiles*/) throws MalformedURLException{
			a2html = new downloader.n6xs.com.Article2HTML(downloadNumber, fileDirectoryPath);
			//totalFileNumber = totalFiles;			
		}
		
        @Override
        public Void doInBackground() {
        	if (myTester){        		
        		// 3 is default multi thread number
        		while (taskList.size() > 3){        			
        				for (int i=0; i<3; i++){				
        					taskList.get(i).execute();        				
        			}        			
        			for (int i=0; i<3;){
        				if(taskList.get(0).isDone()){            				
            				taskList.remove(0);
            				i++;
            			}else{
            				try {            					         					
    							Thread.sleep(3456);
    						} catch (InterruptedException e) {
    							// TODO Auto-generated catch block
    							e.printStackTrace();
    						}
            			}
        			}      		
	        		
	        		for (int i=0; i<taskList.size(); i++){        				
						taskList.get(i).execute();	
	        		}        			
        		}
        				
        	}
        	
        	else{
        		setProgress(currentStep++);
        		try {
					Thread.sleep(345);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}        		
	            a2html.doDownload();        
	        }
        	
        	return null;
        }

        /*
         * Executed in event dispatching thread
         */
        @Override
        public void done() {
        	if (myTester){        		
        		try {
					Thread.sleep(567);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		btnDownload.setEnabled(true);        		
        		labelPercentage.setText("100 %");
        		setCursor(null);
        		java.io.File folderPath = new java.io.File(bookFolderPath);
        		if(folderPath.isDirectory()){
        			String outputName = folderPath.getName();
        			if (outputName.contains("null")){
        				outputName = outputName.substring(4);
        			}
        			java.io.File outputFile = new java.io.File(outputName + ".txt");
        			
        			java.io.File allFiles[] =  folderPath.listFiles();
        			try {
        				
        				if (!outputFile.exists()){
            				outputFile.createNewFile();
            			}else{
            				outputFile.delete();
            				outputFile.createNewFile();        				
            			}
        				FileWriter fwt = new FileWriter(outputFile, true);
        				FileReader frd = null;
						for (int i=0; i<allFiles.length; i++){
							frd = new FileReader(allFiles[i]);
							int charFromFile = 0;
							int i2 = 0;
							while ((charFromFile = frd.read()) != -1){
								if (i2<4) {
									if (charFromFile == (char)'\n'){
										i2++;
									}
									continue;
								}
								
								fwt.write(charFromFile);
							}
	        			}
						frd.close();
						fwt.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        		}
        	}
        }
    }

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		if ("progress" == evt.getPropertyName()) {
            int progress = (Integer) evt.getNewValue();
            labelPercentage.setText(Double.toString(((double)progress/(double)total)*100.0).substring(0, 4) + " %");
        }
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
