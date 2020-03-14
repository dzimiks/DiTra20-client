package src.models.tree;

import src.observer.MainObserver;
import src.observer.Observable;
import src.observer.ObserverList;
import src.observer.ObserverNotification;

import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class Node implements TreeNode, Observable, Serializable {

	private String name;
	private transient Node parent;
	private List<Node> children;
	protected transient ObserverList observerList;

	public Node(String name) {
		this.name = name;
		this.children = new ArrayList<>();
		this.observerList = new ObserverList();
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

	public TreePath getPath() {
		TreeNode node = this;
		List<Object> nodes = new ArrayList<>();

		nodes.add(node);
		node = node.getParent();

		while (node != null) {
			nodes.add(0, node);
			node = node.getParent();
		}

		return nodes.isEmpty() ? null : new TreePath(nodes.toArray());
	}

	@Override
	public void addObserver(MainObserver observer) {
		System.out.println(name + ": addObserver()");
		this.observerList.addObserver(observer);
	}

	public void initObserverList() {
		this.observerList = new ObserverList();

		for (Node child : getChildren()) {
			child.initObserverList();
		}
	}

	public ObserverList getObserverList() {
		return observerList;
	}

	public void notifyObservers(ObserverNotification notification, Object object) {
		this.observerList.notifyObservers(notification, object);
	}
}
