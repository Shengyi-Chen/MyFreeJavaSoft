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

	private final JPanel contentPanel = new JPanel();
	/**
	 * Create the dialog.
	 */
	public SettingsDialog(Frame owner) {
		super(owner);
		setForeground(Color.WHITE);
		setBackground(Color.DARK_GRAY);
		setModal(true);
		//setModalExclusionType(JDialog.ModalExclusionType.APPLICATION_EXCLUDE);
		setUndecorated(true);
		setBounds(100, 100, 600, 500);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(owner);
		
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JLabel lblGripSize = new JLabel(ResourceBundle.getBundle("me.shengyi.albumpreview.messages").getString("SettingsDialog.lblGripSize.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblGripSize.setForeground(Color.WHITE);
		
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(3, 3, 10, 1));
		spinner.setForeground(new Color(240, 240, 240));
		spinner.setBackground(Color.DARK_GRAY);
		
		JLabel lblRows = new JLabel(ResourceBundle.getBundle("me.shengyi.albumpreview.messages").getString("SettingsDialog.lblRows.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblRows.setForeground(Color.WHITE);
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setModel(new SpinnerNumberModel(3, 3, 6, 1));
		
		JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("me.shengyi.albumpreview.messages").getString("SettingsDialog.lblNewLabel.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblNewLabel.setForeground(Color.WHITE);
		
		JLabel lblInfomationUnderEach = new JLabel(ResourceBundle.getBundle("me.shengyi.albumpreview.messages").getString("SettingsDialog.lblInfomationUnderEach.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblInfomationUnderEach.setForeground(Color.WHITE);
		
		JCheckBox chckbxTitle = new JCheckBox(ResourceBundle.getBundle("me.shengyi.albumpreview.messages").getString("SettingsDialog.chckbxTitle.text")); //$NON-NLS-1$ //$NON-NLS-2$
		chckbxTitle.setBackground(Color.DARK_GRAY);
		chckbxTitle.setForeground(Color.WHITE);
		
		JCheckBox chckbxDate = new JCheckBox(ResourceBundle.getBundle("me.shengyi.albumpreview.messages").getString("SettingsDialog.chckbxDate.text")); //$NON-NLS-1$ //$NON-NLS-2$
		chckbxDate.setBackground(Color.DARK_GRAY);
		chckbxDate.setForeground(Color.WHITE);
		
		JCheckBox chckbxFileName = new JCheckBox(ResourceBundle.getBundle("me.shengyi.albumpreview.messages").getString("SettingsDialog.chckbxFileName.text")); //$NON-NLS-1$ //$NON-NLS-2$
		chckbxFileName.setBackground(Color.DARK_GRAY);
		chckbxFileName.setForeground(Color.WHITE);
		
		JLabel lblDatastore = new JLabel(ResourceBundle.getBundle("me.shengyi.albumpreview.messages").getString("SettingsDialog.lblDatastore.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblDatastore.setForeground(Color.WHITE);
		lblDatastore.setBackground(Color.DARK_GRAY);
		
		JRadioButton rdbtnStoreInA = new JRadioButton(ResourceBundle.getBundle("me.shengyi.albumpreview.messages").getString("SettingsDialog.rdbtnStoreInA.text")); //$NON-NLS-1$ //$NON-NLS-2$
		rdbtnStoreInA.setBackground(Color.DARK_GRAY);
		rdbtnStoreInA.setForeground(Color.WHITE);
		
		JRadioButton rdbtnStoreInFile = new JRadioButton(ResourceBundle.getBundle("me.shengyi.albumpreview.messages").getString("SettingsDialog.rdbtnStoreInFile.text")); //$NON-NLS-1$ //$NON-NLS-2$
		rdbtnStoreInFile.setForeground(Color.WHITE);
		rdbtnStoreInFile.setBackground(Color.DARK_GRAY);
		
		JRadioButton rdbtnPreviewOnly = new JRadioButton(ResourceBundle.getBundle("me.shengyi.albumpreview.messages").getString("SettingsDialog.rdbtnPreviewOnly.text")); //$NON-NLS-1$ //$NON-NLS-2$
		rdbtnPreviewOnly.setForeground(Color.WHITE);
		rdbtnPreviewOnly.setBackground(Color.DARK_GRAY);
		
		ButtonGroup rdbtnGroup = new ButtonGroup();
		rdbtnGroup.add(rdbtnStoreInA);
		rdbtnGroup.add(rdbtnStoreInFile);
		rdbtnGroup.add(rdbtnPreviewOnly);
		
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
								.addComponent(spinner, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblGripSize, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblRows)
							.addGap(27)
							.addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
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
						.addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblRows)
						.addComponent(lblNewLabel)
						.addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
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
