package com.cc.ui;

import javax.swing.*;

import com.cc.ui.panel.DatabasePanel;
import com.cc.ui.panel.FileManagerPanel;
import com.cc.ui.panel.HeadPanel;
import com.cc.ui.panel.ListPanel;
import com.cc.ui.panel.ShellPanel;
import com.cc.ui.panel.TextPanel;

public class TabFrame extends JTabbedPane {
	private ListPanel list;
	private String url;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public JPanel addPanel(String type) {
		switch (type) {
		case "list":
			if(list==null)
			{
				list = new ListPanel();
				this.addTab("Home", list);
				this.setSelectedIndex(this.indexOfComponent(list));
			}
			return list;
		case "database":
			DatabasePanel database = new DatabasePanel();
			this.addTab("Database Manager", database);
			this.setSelectedIndex(this.indexOfComponent(database));
	        this.setTabComponentAt(this.getTabCount()-1, new HeadPanel(database));
	        return database;
		case "filemanager":
			FileManagerPanel filemanager = new FileManagerPanel();
			this.addTab("File Manager", filemanager);
			this.setSelectedIndex(this.indexOfComponent(filemanager));
	        this.setTabComponentAt(this.getTabCount()-1, new HeadPanel(filemanager));
	        return filemanager;
		case "shell":
			ShellPanel shell = new ShellPanel();
			this.addTab("Virtual Terminal", shell);
			this.setSelectedIndex(this.indexOfComponent(shell));
		    this.setTabComponentAt(this.getTabCount()-1, new HeadPanel(shell));
			shell.setVisible(true);
			shell.requestFocus();
			return shell;
		case "text":
			TextPanel text = new TextPanel();
			this.addTab("Text View", text);
			this.setSelectedIndex(this.indexOfComponent(text));
		    this.setTabComponentAt(this.getTabCount()-1, new HeadPanel(text));
			return text;
		case "forceshell": // 添加破解asp 或php shell密码
			ForceShellPanel forceShellPanel = new ForceShellPanel(url);
			this.addTab("Crack Shell", forceShellPanel);
			this.setSelectedIndex(this.indexOfComponent(forceShellPanel));
		    this.setTabComponentAt(this.getTabCount()-1, new HeadPanel(forceShellPanel));
			return forceShellPanel;
		default:
			return null;
		}
	
	}

}
