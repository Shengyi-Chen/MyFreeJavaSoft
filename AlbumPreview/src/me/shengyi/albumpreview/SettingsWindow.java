package me.shengyi.albumpreview;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import java.awt.Dimension;
import java.util.ResourceBundle;

public class SettingsWindow extends JFrame {
	public SettingsWindow() {
		setSize(new Dimension(600, 500));
		setResizable(false);
		setPreferredSize(new Dimension(600, 500));
		setMinimumSize(new Dimension(600, 500));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setUndecorated(true);
		
		JButton btnOk = new JButton(ResourceBundle.getBundle("me.shengyi.albumpreview.messages").getString("StartupWindow.lblHello.text")); //$NON-NLS-1$ //$NON-NLS-2$
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnOk)
					.addContainerGap(439, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(562, Short.MAX_VALUE)
					.addComponent(btnOk)
					.addContainerGap())
		);
		getContentPane().setLayout(groupLayout);
	}
}
