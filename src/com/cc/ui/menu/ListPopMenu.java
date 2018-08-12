package com.secquan.ui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import sun.awt.windows.ThemeReader;
import sun.swing.SwingUtilities2;

import com.secquan.ui.AboutDialog;
import com.secquan.ui.AddDialog;
import com.secquan.ui.ConfigDialog;
import com.secquan.ui.MainFrame;
import com.secquan.ui.panel.ListPanel;
import com.secquan.util.Configuration;
import com.secquan.util.DbDao;

public class ListPopMenu extends JPopupMenu {
	private JScrollPane listPane;
	private JMenuItem add, edit, delete, database, filemanager, shell, about,config,headconfig,forceshell;
	private JTable list;
	private JPopupMenu pop;

	public ListPopMenu(JPanel panel, JScrollPane listPane) {
		// TODO Auto-generated constructor stub
		this.listPane = listPane;
		pop = this;
		this.listPane.addMouseListener(new MouseAction());
		MenuAction action = new MenuAction();
		add = new JMenuItem("添加");
		JMenu skins = new JMenu("皮肤");
		about = new JMenuItem("关于");
		config = new JMenuItem("设置");
		LookAndFeelInfo[] looks = UIManager.getInstalledLookAndFeels();
		ButtonGroup group = new ButtonGroup();
		JCheckBoxMenuItem gskin = new JCheckBoxMenuItem("Graphite");
		SkinAction action1 = new SkinAction();
		action1.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceGraphiteLookAndFeel");
		gskin.addActionListener(action1);
		group.add(gskin);
		skins.add(gskin);
		for(LookAndFeelInfo look : looks)
		{
			JCheckBoxMenuItem skin = new JCheckBoxMenuItem(look.getName());
			String lookname = look.getName();
			SkinAction action2 = new SkinAction();
			action2.setLookAndFeel(look.getClassName());
			skin.addActionListener(action2);
			group.add(skin);
			skins.add(skin);
			
			
		}	
		add.addActionListener(action);
		about.addActionListener(action);
		config.addActionListener(action);
		pop.add(add);
		/* 
		 * 取消皮肤显示
		 * 
		
		*/
		pop.add(skins);
		pop.add(about);
		this.addSeparator();
		pop.add(config);
		panel.add(this);
	}

	public ListPopMenu(JPanel panel, JTable list) {
		
		this.list = list;
		pop = this;
		this.list.addMouseListener(new SelectedMouseAction());
		MenuAction action = new MenuAction();
		filemanager = new JMenuItem("文件管理");
		database = new JMenuItem("数据库管理");
		shell = new JMenuItem("模拟终端");
		headconfig = new JMenuItem("请求头设置");
		forceshell = new JMenuItem("破解shell");
		database.addActionListener(action);
		filemanager.addActionListener(action);
		shell.addActionListener(action);
		headconfig.addActionListener(action);
		forceshell.addActionListener(action);
		this.add(filemanager);
		this.add(database);
		this.add(shell);
		this.add(headconfig);
		this.add(forceshell);
		this.addSeparator();
		add = new JMenuItem("添加");
		edit = new JMenuItem("编辑");
		delete = new JMenuItem("删除");
		config = new JMenuItem("设置");
		
		this.add(add);
		this.add(edit);
		this.add(delete);
		
		this.addSeparator();
		this.add(config);
		
		add.addActionListener(action);
		edit.addActionListener(action);
		delete.addActionListener(action);
		
		config.addActionListener(action);
		database.setEnabled(true);
		panel.add(this);
	}

	private String getOne(JTable list) {
		String data = "";
		for (int i = 0; i < 8; i++) {
			if (list.getValueAt(list.getSelectedRow(), i) != null) {
				data += list.getValueAt(list.getSelectedRow(), i).toString()
						+ "\t";
			} else {
				data += " " + "\t";
			}
		}
		return data;
	}

	class SelectedMouseAction extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			if (e.getButton() == MouseEvent.BUTTON3) {
				int row = list.rowAtPoint(e.getPoint());
				list.setRowSelectionInterval(row, row);
				pop.show(list, e.getX(), e.getY());
			} else if (e.getClickCount() == 2) {
				MainFrame.tab.setUrl(getOne(list));
				Runnable run = new Runnable() {
					public void run() {
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								MainFrame.tab.addPanel("filemanager");
							}
						});	
					}
				};
				new Thread(run).start();
			}
		}
	}

	class MouseAction extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			if (e.getButton() == MouseEvent.BUTTON3) {
				pop.show(listPane, e.getX(), e.getY());
			}
		}
	}
	/**
	 * 定义action动作类型
	 * @author wyyw
	 * 2017-12-5 09:51:12
	 *
	 */
	class MenuAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == add) {
				AddDialog add = new AddDialog();
			} else if (e.getSource() == edit) {
				AddDialog edit = new AddDialog(getOne(list));
			} else if (e.getSource() == delete) {
				int button = JOptionPane.showConfirmDialog(MainFrame.main,
						"确认删除？", "提示", JOptionPane.YES_NO_OPTION);
				if (button == 0) {
					Statement stmt = DbDao.getInstance().getStmt();
					try {
						String id = getOne(list).split("\t")[0];
						stmt.executeUpdate("delete from data where id=" + id);
						ListPanel listpanel= (ListPanel)MainFrame.tab.getSelectedComponent();
						listpanel.getModel().remove(id);
						listpanel.getStatus().setText("删除成功");
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			} else if (e.getSource() == filemanager) {
				MainFrame.tab.setUrl(getOne(list));
				Runnable run = new Runnable() {
					public void run() {
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								MainFrame.tab.addPanel("filemanager");
							}
						});
						
					}
				};
				new Thread(run).start();
			} else if (e.getSource() == database) {
				MainFrame.tab.setUrl(getOne(list));
				MainFrame.tab.addPanel("database");
			} else if (e.getSource() == shell) {
				MainFrame.tab.setUrl(getOne(list));
				Runnable run = new Runnable() {
					public void run() {
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								MainFrame.tab.addPanel("shell");
							}
						});
						
					}
				};
				new Thread(run).start();
			} else if (e.getSource() == about) {
				AboutDialog a = new AboutDialog();
			} else if (e.getSource() == config) // 如果是点击配置 或者是请求头配置 2017-12-5
			{
				new ConfigDialog("0");
			}else if(e.getSource() == headconfig){
				new ConfigDialog("1"); // 当请求头配置被点击时 2017-12-5
			} else if(e.getSource() == forceshell){ // 破解shell密码
				String str = getOne(list);
				MainFrame.tab.setUrl(getOne(list));
				Runnable run = new Runnable() {
					public void run() {
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								MainFrame.tab.addPanel("forceshell");
							}
						});
						
					}
				};
				new Thread(run).start();
				
			}
			
			
		}

	}
	class SkinAction implements ActionListener
	{
		private String LookAndFeel;
		public String getLookAndFeel() {
			return LookAndFeel;
		}
		public void setLookAndFeel(String lookAndFeel) {
			LookAndFeel = lookAndFeel;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			
			// TODO Auto-generated method stub
			try {
				Configuration config = new Configuration();
				config.setValue("SKIN", this.LookAndFeel);
				UIManager.setLookAndFeel(this.LookAndFeel);
				SwingUtilities.updateComponentTreeUI(MainFrame.main);
				SwingUtilities.updateComponentTreeUI(pop);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
			}
		}
	}

}