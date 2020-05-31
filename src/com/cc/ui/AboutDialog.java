package com.cc.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dialog;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import com.cc.util.GBC;

public class AboutDialog extends JDialog {
	private JPanel about_pane;
	private JLabel img_label;
	private JEditorPane about_info;
	private Icon icon;

	public AboutDialog() {

		super(MainFrame.main, "ShellBox", true);
		this.setComponent();
		// 初始化布局和控件

		this.setVisible(true);

	}

	private void setComponent() {
		about_pane = new JPanel();
		about_pane.setLayout(new GridBagLayout());
		about_pane.setOpaque(true);
		about_pane.setBackground(Color.white);

		GBC gbclogo = new GBC(0, 0, 1, 1).setFill(GBC.BOTH).setInsets(0, 0, 0, 0);
		GBC gbccontent1 = new GBC(1, 0, 1, 1).setFill(GBC.BOTH).setWeight(1, 1).setInsets(20, 0, 0, 0);

		// img
		img_label = new JLabel();

		try {
			icon = new ImageIcon(getClass().getResource("/com/cc/images/logos.png"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		img_label.setSize(50, 50);
		img_label.setBackground(Color.white);
		img_label.setIcon(icon);
		img_label.setOpaque(true);

		// text
		JEditorPane about_info = new JEditorPane();
		about_info.setEditable(false);
		about_info.setContentType("text/html");
		String copy = "<html><body><div><h3 style=\"text-align:center;\">Copyright(c) 2017-2017 SecQuan</h3></div>"
				+ "<div style=\"font-size:10px;text-align:center;\">Home:<a href=\"https://www.secquan.org/\">https://www.secquan.org/</a></div>"
				+ "<br \\><div style=\"font-size:10px;\">Just do it。</div><div></div>"
				+ "<div style=\"text-align:right;font-size:9px;\">Thank Chora & MelodyZX </div>"
				+ "</body></html>";
		about_info.setText(copy.toString());
		// 超链接事件
		about_info.addHyperlinkListener(new HyperlinkListener() {
			@Override
			public void hyperlinkUpdate(HyperlinkEvent e) {
				// TODO Auto-generated method stub
				if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
					try {
						Desktop.getDesktop().browse(new URI("https://localhost/"));
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}

		});
		about_info.setOpaque(false);
		about_info.setBackground(Color.white);

		about_pane.add(about_info, gbccontent1);
		about_pane.add(img_label, gbclogo);

		this.add(about_pane);
		this.setSize(400, 250);
		this.setResizable(false);
		this.setTitle("About");
		this.setLocationRelativeTo(MainFrame.main);
	}

}
