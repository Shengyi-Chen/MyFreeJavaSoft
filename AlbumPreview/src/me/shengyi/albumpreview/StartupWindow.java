package me.shengyi.albumpreview;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.io.File;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JToolBar;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JList;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;

import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JSeparator;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JToggleButton;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.ListSelectionModel;

import java.awt.GridLayout;
import java.awt.Font;

import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.FlowLayout;


public class StartupWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3521345556169940549L;
	
	private JPanel contentPane;
	private JTextField textFieldTitle;
	private JTextField textFieldDate;
	private JTextField textField_3;
	private JTextField textFieldDim;
	private JTextField textFieldCamera;

	private Point mouseDownCompCoords;
	private Rectangle storedBounds;
	private Point storedLocation;
	private JToggleButton toggleButton;
	private JList<String> list;
	private DefaultListModel<String> listModel = new DefaultListModel<String>();
	private ArrayList<File> albumPhotoFiles = new ArrayList<File>();	
	private JScrollPane scrollPanePic;
	private int lastImageExtendWidth = 0;
	private static StartupWindow frame;
	private static int windowHeight = (int) (java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.7);
	private JButton btnSave;
	
	private File albumFile = null;
	
	/* 
	* Launch the application.
	*/
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new StartupWindow();
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
	public StartupWindow() {
				
		setUndecorated(true);
		setTitle(ResourceBundle.getBundle("me.shengyi.albumpreview.messages").getString("Window_Titile")); //$NON-NLS-1$ //$NON-NLS-2$
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setBounds(100, 100, 835, windowHeight);
		//setBounds(100, 100, 835, 810);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		storedBounds = getBounds();
		storedLocation = getLocation();
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		contentPane.add(toolBar, BorderLayout.NORTH);
		
		JButton btnBrowse = new JButton(ResourceBundle.getBundle("me.shengyi.albumpreview.messages").getString("StartupWindow.lblAddPhotos.text")); //$NON-NLS-1$ //$NON-NLS-2$
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileFilter imageFilter = new FileFilter(){
					private final String[] okFileExtensions = new String[] {".jpeg", ".jpg"};

					@Override
					public boolean accept(File f) {						
						if (f.isDirectory()) {
				            return true;
				        }			 
				        
				        for (String extension : okFileExtensions)
				        {
				        	if(f.getName().toLowerCase().endsWith(extension)){
				        		return true;
				        	}			        	
				        }			 
				        return false;
					}

					@Override
					public String getDescription() {
						return "JPEG images (.jpeg, .jpg)";
					}};
				JFileChooser jFc = new JFileChooser();				
				jFc.setDialogTitle(ResourceBundle.getBundle("me.shengyi.albumpreview.messages").getString("StartupWindow.jFc.DialogTitle.text"));//ResourceBundle.getBundle("me.shengyi.albumpreview.messages").getString("StartupWindow.lblAddPhotos.text")
				jFc.setFileFilter(imageFilter);
				jFc.setAcceptAllFileFilterUsed(false);
				jFc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				jFc.setMultiSelectionEnabled(true);
				
				int returnVal = jFc.showOpenDialog(getParent());
	            if (returnVal == JFileChooser.APPROVE_OPTION) {
	            	File[] files = jFc.getSelectedFiles();	            	
	            	//listModel.clear();
	            	for (File fileName : files){
	            		albumPhotoFiles.add(fileName);
	            		listModel.addElement(fileName.getName());
	            	}	            
			}
		}});
		toolBar.add(btnBrowse);
		
		JButton btnDelete = new JButton(ResourceBundle.getBundle("me.shengyi.albumpreview.messages").getString("StartupWindow.btnDelete.text")); //$NON-NLS-1$ //$NON-NLS-2$
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectI = list.getSelectedIndex();
				if(selectI>-1){
					list.setSelectedIndex(-1);
					listModel.remove(selectI);
					//TODO: Clear View
					initialPicView();
					textFieldCamera.setText("");
					textFieldTitle.setText("");
					textFieldDate.setText("");
					textFieldDim.setText("");
				}
			}
		});
		toolBar.add(btnDelete);
		
		JSeparator separator = new JSeparator();
		separator.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				Point currCoods = e.getLocationOnScreen();
				if (getExtendedState() != JFrame.MAXIMIZED_BOTH){
					setLocation(currCoods.x - mouseDownCompCoords.x, currCoods.y - mouseDownCompCoords.y);
				}	
			}
		});
		separator.setBackground(Color.LIGHT_GRAY);
		separator.setForeground(Color.RED);
		separator.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mouseDownCompCoords = e.getPoint();
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				storedLocation = getLocation();
				mouseDownCompCoords = null;
			}
		});
		toolBar.add(separator);
		
		JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("me.shengyi.albumpreview.messages").getString("StartupWindow.lblNewLabel.text_1")); //$NON-NLS-1$ //$NON-NLS-2$
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setForeground(Color.RED);
		toolBar.add(lblNewLabel);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				Point currCoods = e.getLocationOnScreen();
				if (getExtendedState() != JFrame.MAXIMIZED_BOTH){
					setLocation(currCoods.x - mouseDownCompCoords.x, currCoods.y - mouseDownCompCoords.y);
				}				
			}
		});
		separator_1.setForeground(Color.LIGHT_GRAY);
		separator_1.setBackground(Color.RED);
		separator_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mouseDownCompCoords = e.getPoint();
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				storedLocation = getLocation();
				mouseDownCompCoords = null;
			}
		});
		toolBar.add(separator_1);
		
		JButton btnPreviewSettings = new JButton(ResourceBundle.getBundle("me.shengyi.albumpreview.messages").getString("StartupWindow.btnPreviewSettings.text")); //$NON-NLS-1$ //$NON-NLS-2$
		btnPreviewSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO: Load settings dialog.
				SettingsDialog cfgDialog = new SettingsDialog(frame);
				cfgDialog.setVisible(true);
			}
		});
		toolBar.add(btnPreviewSettings);
		
		JButton btnCreate = new JButton(ResourceBundle.getBundle("me.shengyi.albumpreview.messages").getString("StartupWindow.btnCreate.text")); //$NON-NLS-1$ //$NON-NLS-2$
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO: Create Album database.
				//createPreview();
				FileFilter imageFilter = new FileFilter(){
					private final String[] okFileExtensions = new String[] {".albump", ".db"};

					@Override
					public boolean accept(File f) {						
						if (f.isDirectory()) {
				            return true;
				        }			 
				        
				        for (String extension : okFileExtensions)
				        {
				        	if(f.getName().toLowerCase().endsWith(extension)){
				        		return true;
				        	}			        	
				        }			 
				        return false;
					}

					@Override
					public String getDescription() {
						return "Album Prview Database (.albump, .db)";
					}};
				JFileChooser jFc = new JFileChooser();			
				jFc.setDialogTitle(ResourceBundle.getBundle("me.shengyi.albumpreview.messages").getString("StartupWindow.jAlbFc.Save.Dialog.Title.Text"));//ResourceBundle.getBundle("me.shengyi.albumpreview.messages").getString("StartupWindow.lblAddPhotos.text")
				jFc.setFileFilter(imageFilter);
				jFc.setAcceptAllFileFilterUsed(false);
				jFc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				jFc.setMultiSelectionEnabled(false);
				
				
				int returnVal = jFc.showSaveDialog(getParent());
	            if (returnVal == JFileChooser.APPROVE_OPTION) {
	            	File file = jFc.getSelectedFile();
	            	albumFile = file;
	            }
			}
		});
		
		JButton btnOpen = new JButton(ResourceBundle.getBundle("me.shengyi.albumpreview.messages").getString("StartupWindow.btnOpen.text")); //$NON-NLS-1$ //$NON-NLS-2$
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileFilter albumFilter = new FileFilter(){
					private final String[] okFileExtensions = new String[] {".albump", ".db"};
	
					@Override
					public boolean accept(File f) {						
						if (f.isDirectory()) {
				            return true;
				        }			 
				        
				        for (String extension : okFileExtensions)
				        {
				        	if(f.getName().toLowerCase().endsWith(extension)){
				        		return true;
				        	}			        	
				        }			 
				        return false;
					}
	
					@Override
					public String getDescription() {
						return "Album Prview Database (.albump, .db)";
					}};
				
				JFileChooser jAlbFc = new JFileChooser();
				jAlbFc.setDialogTitle(ResourceBundle.getBundle("me.shengyi.albumpreview.messages").getString("StartupWindow.jAlbFc.DialogTitle.text"));
				jAlbFc.setFileFilter(albumFilter);
				jAlbFc.setAcceptAllFileFilterUsed(false);
				jAlbFc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				jAlbFc.setMultiSelectionEnabled(false);
				
				int returnVal = jAlbFc.showOpenDialog(getParent());
	            if (returnVal == JFileChooser.APPROVE_OPTION) {
	            	File file = jAlbFc.getSelectedFile();
	            	//TODO: open Album
	            	albumFile = file;
	            }
	        }
		});
		toolBar.add(btnOpen);
		toolBar.add(btnCreate);
		
		JButton btnX = new JButton(ResourceBundle.getBundle("me.shengyi.albumpreview.messages").getString("StartupWindow.btnX.text")); //$NON-NLS-1$ //$NON-NLS-2$
		btnX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		
		toggleButton = new JToggleButton(ResourceBundle.getBundle("me.shengyi.albumpreview.messages").getString("StartupWindow.toggleButton.text"));
		toggleButton.setVisible(false);
		toggleButton.setEnabled(false);
		toggleButton.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (list.getSelectedIndex()>-1){
					if(((JToggleButton) e.getSource()).isSelected()){
						
					}else{
						
					}
				}
				
				/*if(((JToggleButton) e.getSource()).isSelected() && (getExtendedState() == JFrame.NORMAL)) {
					setMaximumSize();						
				}
				if (!((JToggleButton) e.getSource()).isSelected() && (getExtendedState() == JFrame.MAXIMIZED_BOTH)){					
					restoreNormalSize();
				}*/
					
			}
		});
		toolBar.add(toggleButton);
		toolBar.add(btnX);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel_1 = new JPanel();
		scrollPane.setViewportView(panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.rowHeights = new int[]{640, 0};
		gbl_panel_1.columnWeights = new double[]{1.0};
		gbl_panel_1.rowWeights = new double[]{1.0, 1.0};
		panel_1.setLayout(gbl_panel_1);
		
		scrollPanePic = new JScrollPane();
		GridBagConstraints gbc_scrollPanePic = new GridBagConstraints();
		gbc_scrollPanePic.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPanePic.fill = GridBagConstraints.BOTH;
		gbc_scrollPanePic.gridx = 0;
		gbc_scrollPanePic.gridy = 0;
		panel_1.add(scrollPanePic, gbc_scrollPanePic);		
		
		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 1;
		panel_1.add(panel_2, gbc_panel_2);
		panel_2.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.PREF_COLSPEC,
				ColumnSpec.decode("3dlu:grow"),
				FormFactory.PREF_COLSPEC,
				ColumnSpec.decode("3dlu:grow"),},
			new RowSpec[] {
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("22px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		
		JLabel lblName = new JLabel(ResourceBundle.getBundle("me.shengyi.albumpreview.messages").getString("StartupWindow.lblName.text_1")); //$NON-NLS-1$ //$NON-NLS-2$
		panel_2.add(lblName, "2, 2, right, center");
		
		textFieldTitle = new JTextField();
		
		textFieldTitle.setText("");
		textFieldTitle.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				if (textFieldTitle.getText().length()>0){
					btnSave.setEnabled(true);
				}else{
					btnSave.setEnabled(false);
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				if (textFieldTitle.getText().length()>0){
					btnSave.setEnabled(true);
				}else{
					btnSave.setEnabled(false);
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				if (textFieldTitle.getText().length()>0){
					btnSave.setEnabled(true);
				}else{
					btnSave.setEnabled(false);
				}
			}
			
		});
		panel_2.add(textFieldTitle, "3, 2, fill, top");
		textFieldTitle.setColumns(10);
		
		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO: Save title
				int selectedIndex = list.getSelectedIndex();
				if (selectedIndex > -1){
					JPEGImageLoader imageL = new JPEGImageLoader(albumPhotoFiles.get(selectedIndex));
					imageL.loadImage();
					String imageTitle = textFieldTitle.getText();
					if (null != imageTitle){
						imageL.setImageTitle(imageTitle);
					}					
				}
			}
		});
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 13));
		panel_2.add(btnSave, "4, 2");
		
		JLabel lblDate = new JLabel(ResourceBundle.getBundle("me.shengyi.albumpreview.messages").getString("StartupWindow.lblDate.text_1")); //$NON-NLS-1$ //$NON-NLS-2$
		panel_2.add(lblDate, "2, 4, right, default");
		
		textFieldDate = new JTextField();
		textFieldDate.setEditable(false);
		textFieldDate.setText("");
		panel_2.add(textFieldDate, "3, 4, fill, default");
		textFieldDate.setColumns(10);
		
		JLabel lblGps = new JLabel(ResourceBundle.getBundle("me.shengyi.albumpreview.messages").getString("StartupWindow.lblGps.text")); //$NON-NLS-1$ //$NON-NLS-2$
		panel_2.add(lblGps, "4, 4, right, default");
		
		textField_3 = new JTextField();
		textField_3.setEditable(false);
		textField_3.setText("");
		panel_2.add(textField_3, "5, 4, fill, default");
		textField_3.setColumns(10);
		
		JLabel lblDimensions = new JLabel(ResourceBundle.getBundle("me.shengyi.albumpreview.messages").getString("StartupWindow.lblDimensions.text")); //$NON-NLS-1$ //$NON-NLS-2$
		panel_2.add(lblDimensions, "2, 6, right, default");
		
		textFieldDim = new JTextField();
		textFieldDim.setEditable(false);
		textFieldDim.setText("");
		panel_2.add(textFieldDim, "3, 6, fill, default");
		textFieldDim.setColumns(10);
		
		JLabel lblCamera = new JLabel(ResourceBundle.getBundle("me.shengyi.albumpreview.messages").getString("StartupWindow.lblCamera.text")); //$NON-NLS-1$ //$NON-NLS-2$
		panel_2.add(lblCamera, "4, 6, right, default");
		
		textFieldCamera = new JTextField();
		textFieldCamera.setEditable(false);
		textFieldCamera.setText("");
		panel_2.add(textFieldCamera, "5, 6, fill, default");
		textFieldCamera.setColumns(10);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.WEST);
		panel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_2 = new JScrollPane();
		
		panel.add(scrollPane_2);
		
		list = new JList<String>();
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				//TODO: Draw photo and show info, setMainView
				if (e.getValueIsAdjusting()){
					int selectedIndex = ((JList) e.getSource()).getSelectedIndex();
					if (selectedIndex > -1)
						setMainView(albumPhotoFiles.get(selectedIndex));
				}
			}
		});
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setModel(listModel);
		scrollPane_2.setViewportView(list);
	}
	
	private void setMaximumSize(){
		storedBounds = getBounds();
		storedLocation = getLocation();					
		setExtendedState(getExtendedState()|JFrame.MAXIMIZED_BOTH);	
	}
	
	private void restoreNormalSize(){		
		setExtendedState(JFrame.NORMAL);
		setBounds(storedBounds);
		setLocation(storedLocation);
	}
	
	private void initialPicView(){
		BufferedImage img = new BufferedImage(512, 512, BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics2D g2dText = img.createGraphics();
		g2dText.setFont(new Font("Times New Roman", Font.BOLD, 22));
		g2dText.setColor(Color.BLACK);
		g2dText.drawString("Please selete a file from left!", 125, 512/2);
		
		
		JPanel panel_canvas = new JPanel(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 2048L;

			@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int newPicWidth = (img.getWidth()*(int)(windowHeight/1.265))/img.getHeight();
                int newPicHeight = (int)(windowHeight/1.265); //fixed height
                g.drawImage(img, (scrollPanePic.getWidth() - newPicWidth) / 2, (scrollPanePic.getHeight() - newPicHeight) / 2, newPicWidth, newPicHeight, null);
            }
		};
		scrollPanePic.setViewportView(panel_canvas);

		panel_canvas.setPreferredSize(new Dimension((img.getWidth()*(int)(windowHeight/1.265))/img.getHeight(), (int)(windowHeight/1.265)));
		
	}
	
	private void setMainView(File selectedFile){
		JPEGImageLoader jpegiLoader = new JPEGImageLoader(selectedFile);
		BufferedImage img = jpegiLoader.loadImage();
		if (img != null){			
			JPanel panel_canvas = new JPanel(){
				/**
				 * 
				 */
				private static final long serialVersionUID = 2048L;

				@Override
	            protected void paintComponent(Graphics g) {
	                super.paintComponent(g);
	                int newPicWidth = (img.getWidth()*(int)(windowHeight/1.265))/img.getHeight();
	                int newPicHeight = (int)(windowHeight/1.265); //fixed height
	                g.drawImage(img, (scrollPanePic.getWidth() - newPicWidth) / 2, (scrollPanePic.getHeight() - newPicHeight) / 2, newPicWidth, newPicHeight, null);
	            }
			};
			scrollPanePic.setViewportView(panel_canvas);
			
			panel_canvas.setPreferredSize(new Dimension((img.getWidth()*(int)(windowHeight/1.265))/img.getHeight(), (int)(windowHeight/1.265)));
			int imageExtendWidth = (img.getWidth()*(int)(windowHeight/1.265))/img.getHeight() - 560;
						
			if (imageExtendWidth > 0){
				if (lastImageExtendWidth != imageExtendWidth){
					setBounds(new Rectangle((int)getBounds().getWidth() + imageExtendWidth, (int)getBounds().getHeight()));
					setLocation(storedLocation);
					lastImageExtendWidth = imageExtendWidth;
				}
			}			
			else{				
				setBounds(new Rectangle(835, (int)getBounds().getHeight()));
				setLocation(storedLocation);
				lastImageExtendWidth = imageExtendWidth;
			}
			
			textFieldDim.setText(img.getWidth() + " X " + img.getHeight());
			textFieldDate.setText(jpegiLoader.getTakenDate());
			textFieldCamera.setText(jpegiLoader.getCameraInfo());
			textFieldTitle.setText(jpegiLoader.getImageTitle());
		}
	}
}
