package structures;

import java.util.*;

/**
 * This class implements an HTML DOM Tree. Each node of the tree is a TagNode, with fields for
 * tag/text, first child and sibling.
 * 
 */
public class Tree {
	
	/**
	 * Root node
	 */
	TagNode root=null;
	
	/**
	 * Scanner used to read input HTML file when building the tree
	 */
	Scanner sc;
	
	/**
	 * Initializes this tree object with scanner for input HTML file
	 * 
	 * @param sc Scanner for input HTML file
	 */
	public Tree(Scanner sc) {
		this.sc = sc;
		root = null;
	}
	
	/**
	 * Builds the DOM tree from input HTML file, through scanner passed
	 * in to the constructor and stored in the sc field of this object. 
	 * 
	 * The root of the tree that is built is referenced by the root field of this object.
	 */
	public void build() {
		/** COMPLETE THIS METHOD **/		
		if(sc == null){
			return;
		}
		Stack<TagNode> tagStack = new Stack<TagNode>();
		String curr = sc.nextLine();
		root = new TagNode(curr.replace("<", "").replace(">", ""), null, null);
		tagStack.push(root);
		
		while(sc.hasNextLine()) {
			boolean x = false; 
			curr = sc.nextLine();
			
			if (curr.contains("<")) {
				curr = curr.replace("<", "").replace(">", "");
				x = true;
				}
			
			if (curr.contains("/")) {
				if(x==true){
					curr = curr.replace("<", "").replace(">", "");
					tagStack.pop();
					continue;
				}
				continue;
			}else {
				TagNode node = new TagNode(curr, null, null);
				if (tagStack.peek().firstChild != null){
					while (tagStack.peek().firstChild.sibling != null){
						tagStack.peek().firstChild = tagStack.peek().firstChild.sibling;
					}
					tagStack.peek().firstChild.sibling = node;
				} else if (tagStack.peek().firstChild == null){
					tagStack.peek().firstChild = node;
				}
				if(x==true)
					tagStack.push(node);
			}
		}
	}
	

	
	/**
	 * Replaces all occurrences of an old tag in the DOM tree with a new tag
	 * 
	 * @param oldTag Old tag
	 * @param newTag Replacement tag
	 */
	public void replaceTag(String oldTag, String newTag) {
		/** COMPLETE THIS METHOD **/
		
		replacetag(oldTag, newTag, root);
	}
	
	private void replacetag(String oldT, String newT, TagNode temp){
		if(temp == null){
			return;
		} else if (temp.tag.equals(oldT)){
			temp.tag = newT;
		}
		replacetag(oldT, newT, temp.firstChild);
		replacetag(oldT, newT, temp.sibling);
	}
	
	/**
	 * Boldfaces every column of the given row of the table in the DOM tree. The boldface (b)
	 * tag appears directly under the td tag of every column of this row.
	 * 
	 * @param row Row to bold, first row is numbered 1 (not 0).
	 */
	public void boldRow(int row) {
		/** COMPLETE THIS METHOD **/
		if(row<=0){
			return;
		}else{
			bold(row, root);
		}
	}
	
	private void bold(int row, TagNode node){
		if(node == null){
			return;
		}else if(node != null){
			TagNode rowt = node.firstChild;
			if (node.tag.equals("table")){
				for(int count = 1; count < row; count++ ) {
					if (node.firstChild.sibling != null){
						rowt = rowt.sibling;
						}
				}
				TagNode node2 = rowt.firstChild;
				if(node2!=null){
					while (node2 != null) {
						TagNode result = new TagNode("b", node2.firstChild, null);
						node2.firstChild = result;
						node2 = node2.sibling;
						continue;
					}
				}
			}
		}
		
		bold(row, node.firstChild);
		bold(row, node.sibling);

	}

	
	/**
	 * Remove all occurrences of a tag from the DOM tree. If the tag is p, em, or b, all occurrences of the tag
	 * are removed. If the tag is ol or ul, then All occurrences of such a tag are removed from the tree, and, 
	 * in addition, all the li tags immediately under the removed tag are converted to p tags. 
	 * 
	 * @param tag Tag to be removed, can be p, em, b, ol, or ul
	 */
	public void removeTag(String tag) {
		/** COMPLETE THIS METHOD **/
	}


	
	/**
	 * Adds a tag around all occurrences of a word in the DOM tree.
	 * 
	 * @param word Word around which tag is to be added
	 * @param tag Tag to be added
	 */
	public void addTag(String word, String tag) {
		/** COMPLETE THIS METHOD **/	
	}
	
	/**
	 * Gets the HTML represented by this DOM tree. The returned string includes
	 * new lines, so that when it is printed, it will be identical to the
	 * input file from which the DOM tree was built.
	 * 
	 * @return HTML string, including new lines. 
	 */
	public String getHTML() {
		StringBuilder sb = new StringBuilder();
		getHTML(root, sb);
		return sb.toString();
	}
	
	private void getHTML(TagNode root, StringBuilder sb) {
		for (TagNode ptr=root; ptr != null;ptr=ptr.sibling) {
			if (ptr.firstChild == null) {
				sb.append(ptr.tag);
				sb.append("\n");
			} else {
				sb.append("<");
				sb.append(ptr.tag);
				sb.append(">\n");
				getHTML(ptr.firstChild, sb);
				sb.append("</");
				sb.append(ptr.tag);
				sb.append(">\n");	
			}
		}
	}
	
	/**
	 * Prints the DOM tree. 
	 *
	 */
	public void print() {
		print(root, 1);
	}
	
	private void print(TagNode root, int level) {
		for (TagNode ptr=root; ptr != null;ptr=ptr.sibling) {
			for (int i=0; i < level-1; i++) {
				System.out.print("      ");
			};
			if (root != this.root) {
				System.out.print("|---- ");
			} else {
				System.out.print("      ");
			}
			System.out.println(ptr.tag);
			if (ptr.firstChild != null) {
				print(ptr.firstChild, level+1);
			}
		}
	}
}
