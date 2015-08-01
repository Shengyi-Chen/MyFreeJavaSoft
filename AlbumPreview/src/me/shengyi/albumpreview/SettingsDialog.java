package me.shengyi.albumpreview;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Window;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.util.ResourceBundle;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.JCheckBox;

public class SettingsDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1775763013343814347L;
	private final JPanel contentPanel = new JPanel();
	
	/**
	 * Create the dialog.
	 */
	public SettingsDialog(Frame owner) {
		super(owner);
		setForeground(Color.WHITE);
		setBackground(Color.DARK_GRAY);
		setModal(true);

		setUndecorated(true);
		setBounds(100, 100, 600, 500);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(owner);
		
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		SettingsDatabase cfgDb = new SettingsDatabase();		
		
		JLabel lblGripSize = new JLabel(ResourceBundle.getBundle("me.shengyi.albumpreview.messages").getString("SettingsDialog.lblGripSize.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblGripSize.setForeground(Color.WHITE);
		
		JSpinner spinnerH = new JSpinner();
		spinnerH.setModel(new SpinnerNumberModel((int)cfgDb.getCfgData().get(OPT_NAME.G_H.ordinal()).getValue(), 3, 10, 1));
		spinnerH.setForeground(new Color(240, 240, 240));
		spinnerH.setBackground(Color.DARK_GRAY);
		
		JLabel lblRows = new JLabel(ResourceBundle.getBundle("me.shengyi.albumpreview.messages").getString("SettingsDialog.lblRows.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblRows.setForeground(Color.WHITE);
		
		JSpinner spinnerW = new JSpinner();
		spinnerW.setModel(new SpinnerNumberModel((int)cfgDb.getCfgData().get(OPT_NAME.G_W.ordinal()).getValue(), 3, 6, 1));
		
		JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("me.shengyi.albumpreview.messages").getString("SettingsDialog.lblNewLabel.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblNewLabel.setForeground(Color.WHITE);
		
		JLabel lblInfomationUnderEach = new JLabel(ResourceBundle.getBundle("me.shengyi.albumpreview.messages").getString("SettingsDialog.lblInfomationUnderEach.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblInfomationUnderEach.setForeground(Color.WHITE);
		
		JCheckBox chckbxTitle = new JCheckBox(ResourceBundle.getBundle("me.shengyi.albumpreview.messages").getString("SettingsDialog.chckbxTitle.text")); //$NON-NLS-1$ //$NON-NLS-2$
		chckbxTitle.setBackground(Color.DARK_GRAY);
		chckbxTitle.setForeground(Color.WHITE);
		chckbxTitle.setSelected((boolean)cfgDb.getCfgData().get(OPT_NAME.HAS_TITLE.ordinal()).getValue());
		
		JCheckBox chckbxDate = new JCheckBox(ResourceBundle.getBundle("me.shengyi.albumpreview.messages").getString("SettingsDialog.chckbxDate.text")); //$NON-NLS-1$ //$NON-NLS-2$
		chckbxDate.setBackground(Color.DARK_GRAY);
		chckbxDate.setForeground(Color.WHITE);
		chckbxDate.setSelected((boolean) cfgDb.getCfgData().get(OPT_NAME.HAS_DATE.ordinal()).getValue());
		
		JCheckBox chckbxFileName = new JCheckBox(ResourceBundle.getBundle("me.shengyi.albumpreview.messages").getString("SettingsDialog.chckbxFileName.text")); //$NON-NLS-1$ //$NON-NLS-2$
		chckbxFileName.setBackground(Color.DARK_GRAY);
		chckbxFileName.setForeground(Color.WHITE);
		chckbxFileName.setSelected((boolean)cfgDb.getCfgData().get(OPT_NAME.HAS_NAME.ordinal()).getValue());
		
		JLabel lblDatastore = new JLabel(ResourceBundle.getBundle("me.shengyi.albumpreview.messages").getString("SettingsDialog.lblDatastore.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblDatastore.setForeground(Color.WHITE);
		lblDatastore.setBackground(Color.DARK_GRAY);		
		
		JRadioButton rdbtnStoreInA = new JRadioButton(ResourceBundle.getBundle("me.shengyi.albumpreview.messages").getString("SettingsDialog.rdbtnStoreInA.text")); //$NON-NLS-1$ //$NON-NLS-2$
		rdbtnStoreInA.setBackground(Color.DARK_GRAY);
		rdbtnStoreInA.setForeground(Color.WHITE);
		
		
		JRadioButton rdbtnStoreInFile = new JRadioButton(ResourceBundle.getBundle("me.shengyi.albumpreview.messages").getString("SettingsDialog.rdbtnStoreInFile.text")); //$NON-NLS-1$ //$NON-NLS-2$
		rdbtnStoreInFile.setForeground(Color.WHITE);
		rdbtnStoreInFile.setBackground(Color.DARK_GRAY);
		rdbtnStoreInFile.setSelected(((String)cfgDb.getCfgData().get(OPT_NAME.DB_ST.ordinal()).getValue()).equals("FS"));
		
		JRadioButton rdbtnPreviewOnly = new JRadioButton(ResourceBundle.getBundle("me.shengyi.albumpreview.messages").getString("SettingsDialog.rdbtnPreviewOnly.text")); //$NON-NLS-1$ //$NON-NLS-2$
		rdbtnPreviewOnly.setForeground(Color.WHITE);
		rdbtnPreviewOnly.setBackground(Color.DARK_GRAY);
		rdbtnPreviewOnly.setSelected(((String)cfgDb.getCfgData().get(OPT_NAME.DB_ST.ordinal()).getValue()).equals("PO"));
		
		ButtonGroup rdbtnGroup = new ButtonGroup();
		rdbtnGroup.add(rdbtnStoreInA);
		rdbtnGroup.add(rdbtnStoreInFile);
		rdbtnGroup.add(rdbtnPreviewOnly);
		
		rdbtnStoreInA.setSelected(((String)cfgDb.getCfgData().get(OPT_NAME.DB_ST.ordinal()).getValue()).equals("DB"));
		
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(rdbtnPreviewOnly, GroupLayout.PREFERRED_SIZE, 248, GroupLayout.PREFERRED_SIZE)
						.addComponent(rdbtnStoreInA)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(chckbxTitle)
							.addGap(134)
							.addComponent(chckbxFileName)
							.addGap(88)
							.addComponent(chckbxDate))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(spinnerH, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblGripSize, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblRows)
							.addGap(27)
							.addComponent(spinnerW, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel))
						.addComponent(lblInfomationUnderEach)
						.addComponent(lblDatastore)
						.addComponent(rdbtnStoreInFile, GroupLayout.PREFERRED_SIZE, 248, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(129, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblGripSize)
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(spinnerH, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblRows)
						.addComponent(lblNewLabel)
						.addComponent(spinnerW, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(26)
					.addComponent(lblInfomationUnderEach)
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(chckbxTitle)
						.addComponent(chckbxFileName)
						.addComponent(chckbxDate))
					.addGap(18)
					.addComponent(lblDatastore)
					.addGap(18)
					.addComponent(rdbtnStoreInA)
					.addGap(18)
					.addComponent(rdbtnStoreInFile)
					.addGap(18)
					.addComponent(rdbtnPreviewOnly)
					.addContainerGap(138, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.DARK_GRAY);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton(ResourceBundle.getBundle("me.shengyi.albumpreview.messages").getString("StartupWindow.lblHello.text")); //$NON-NLS-1$ //$NON-NLS-2$
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//TODO: OK pressed
						cfgDb.saveData(OPT_NAME.G_W, spinnerW.getValue());
						cfgDb.saveData(OPT_NAME.G_H, spinnerH.getValue());
						cfgDb.saveData(OPT_NAME.HAS_TITLE, chckbxTitle.isSelected());
						cfgDb.saveData(OPT_NAME.HAS_DATE, chckbxDate.isSelected());
						cfgDb.saveData(OPT_NAME.HAS_NAME, chckbxFileName.isSelected());
						
						if (rdbtnStoreInA.isSelected()){
							cfgDb.saveData(OPT_NAME.DB_ST, "DB");
						}
						if (rdbtnStoreInFile.isSelected()){
							cfgDb.saveData(OPT_NAME.DB_ST, "FS");
						}
						if (rdbtnPreviewOnly.isSelected()){
							cfgDb.saveData(OPT_NAME.DB_ST, "PO");
						}
						
						setVisible(false);
					}
				});
				okButton.setForeground(Color.WHITE);
				okButton.setBackground(Color.DARK_GRAY);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton(ResourceBundle.getBundle("me.shengyi.albumpreview.messages").getString("SettingsWindow.Cancel.text")); //$NON-NLS-1$ //$NON-NLS-2$
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//TODO: Cancel Pressed
						setVisible(false);
					}
				});
				cancelButton.setForeground(Color.WHITE);
				cancelButton.setBackground(Color.DARK_GRAY);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
