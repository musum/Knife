package com.cc.model;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import com.cc.util.NodeData;
import com.cc.util.NodeData.DataType;



public class DatabaseTreeCellRenderer extends DefaultTreeCellRenderer {

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		// TODO Auto-generated method stub
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
				row, hasFocus);
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
		NodeData data = (NodeData) node.getUserObject();
		ImageIcon icon = null;
		switch (data.nodetype) {
		case DataType.DATABASE:
			icon = new ImageIcon(getClass().getResource("/com/cc/images/database.png"));
			break;
		case DataType.TABLE:
			icon = new ImageIcon(getClass().getResource("/com/cc/images/table.png"));
			break;
		}
		this.setIcon(icon);
		return this;
	}
}
