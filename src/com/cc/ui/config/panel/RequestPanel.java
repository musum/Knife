package com.cc.ui.config.panel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import com.cc.ui.ConfigDialog;
import com.cc.ui.MainFrame;
import com.cc.util.Common;
import com.cc.util.Configuration;
import com.cc.util.Safe;

public class RequestPanel extends JPanel{
	private JTextPane content;
	private JRadioButton open,close;
	public RequestPanel() {
		this.setLayout(new BorderLayout());		
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BorderLayout());
		content = new JTextPane();
		JScrollPane scontent = new JScrollPane(content);
		contentPanel.add(scontent);
		JPanel buttonPanel = new JPanel();
		ButtonGroup group = new ButtonGroup();
		open = new JRadioButton("Open");
		close = new JRadioButton("Close");
		group.add(open);
		group.add(close);
		Configuration config = new Configuration();
		String status = config.getValue("REQUEST_STATUS");
		String data = config.getValue("REQUEST_DATA");
		if(status.equals("1"))
		{
			open.setSelected(true);
		} else 
		{
			close.setSelected(true);
		}
		content.setText(data);
		JButton ok = new JButton("OK");
		JButton cancle = new JButton("Cancle");
		buttonPanel.add(open);
		buttonPanel.add(close);
		buttonPanel.add(ok);
		buttonPanel.add(cancle);
		cancle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConfigDialog.cdialog.hide();
			}
		});
		ok.addActionListener(new ButtonAction());
		this.add(contentPanel,BorderLayout.CENTER);
		this.add(buttonPanel,BorderLayout.SOUTH);
	}
	class ButtonAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			Configuration config = new Configuration();
			String status;
			String data = content.getText().trim();
			if(close.isSelected() || data.equals(""))
			{
				status="0";
			} else
			{
				status="1";
			}
			Safe.REQUEST_DATA = data;
			Safe.REQUEST_STATUS = status;
			config.setValue("REQUEST_DATA", data);
			config.setValue("REQUEST_STATUS", status);
			Common.map.clear();	// 静态Map，如果不清空，则会一直往Map里注入数据。
			Common.getData();
			JOptionPane.showMessageDialog(ConfigDialog.cdialog, "Set headers ok", "Info", JOptionPane.DEFAULT_OPTION);
		}
		
	}
}
