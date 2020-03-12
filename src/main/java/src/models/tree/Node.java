package src.models.tree;

import javax.swing.tree.TreeNode;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class Node implements TreeNode, Serializable {

	private String name;
	private transient Node parent;
	private List<Node> children;

	public Node(String name) {
		this.name = name;
		this.children = new ArrayList<>();
	}

	public void addChild(Node child) {
		child.parent = this;
		this.children.add(child);
	}

	public void removeChild(Node child) {
		children.remove(child);
	}

	public List<Node> getChildren() {
		return children;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public TreeNode getChildAt(int index) {
		return this.children.get(index);
	}

	@Override
	public int getChildCount() {
		return this.children.size();
	}

	@Override
	public TreeNode getParent() {
		return this.parent;
	}

	@Override
	public int getIndex(TreeNode treeNode) {
		return this.children.indexOf(treeNode);
	}

	@Override
	public boolean getAllowsChildren() {
		return false;
	}

	@Override
	public boolean isLeaf() {
		return this.children.isEmpty();
	}

	@Override
	public Enumeration<? extends TreeNode> children() {
		return null;
	}
}
