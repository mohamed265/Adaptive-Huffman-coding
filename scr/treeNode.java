package adaptiveHuffman;

public class treeNode {
	static final public int NYT = 100;
	static final int NODE_IS_PARENT = -1;
	static final int NODE_IS_NYT = -2;

	public int number, weigth;
	public boolean isPearent;
	public treeNode left, right, pearent;
	public char value;
	public String code;

	treeNode() {
		number = weigth = 0;
		isPearent = false;
		left = right = pearent = null;
		code = "";
		value = '\1';
	}

	treeNode(treeNode old) {
		number = old.number;
		weigth = old.weigth;
		isPearent = old.isPearent;
		left = old.left;
		right = old.right;
		pearent = old.pearent;
		code = old.code;
		value = old.value;
	}

	public void rootInit() {
		isPearent = true;
		number = NYT;
		code = "";
		pearent = null;
	}

	public void increment() {
		weigth++;
	}

	public boolean isRoot() {
		return pearent == null ? true : false;
	}

	public treeNode split(char newValue) {
		left = new treeNode();
		right = new treeNode();
		isPearent = true;

		right.code = code + "1";
		right.number = number - 1;
		right.pearent = this;
		right.value = newValue;
		right.increment();

		left.code = code + "0";
		left.number = number - 2;
		left.pearent = this;

		return left;
	}

	public String toString() {
		return number + " " + code + " " + value + " " + weigth + " "
				+ isPearent;
	}

	// public static void Swap(treeNode x, treeNode y) {
	// char tempValue = x.value;
	// x.value = y.value;
	// y.value = tempValue;
	//
	// // boolean tempIsParent = x.isPearent;
	// // x.isPearent = y.isPearent;
	// // y.isPearent = tempIsParent;
	//
	// String tempCode = x.code;
	// x.code = y.code;
	// y.code = tempCode;
	//
	// // int tempnumber = x.number;
	// // x.number = y.number;
	// // y.number = tempnumber;
	//
	//
	// treeNode tempPerantX = x.pearent;
	// treeNode tempPerantY = y.pearent;
	//
	// if (tempPerantX.left != null && tempPerantX.left.equals(x))
	// tempPerantX.left = y;
	// else if (tempPerantX.right != null && tempPerantX.right.equals(x))
	// tempPerantX.right = y;
	//
	// if (tempPerantY.left != null && tempPerantY.left.equals(y))
	// tempPerantY.left = x;
	// else if (tempPerantY.right != null && tempPerantY.right.equal(y))
	// tempPerantY.right = x;
	//
	// if(x.left != null)
	// x.left.pearent = y;
	// if(x.right != null)
	// x.right.pearent = y;
	//
	// if (y.left != null)
	// y.left.pearent = x;
	// if (y.right != null)
	// y.right.pearent = x;
	//
	// treeNode tempPerant = x.pearent;
	// x.pearent = y.pearent;
	// y.pearent = tempPerant;
	// }

	public static void Swap(treeNode x, treeNode y) {
		char tempValue = x.value;
		x.value = y.value;
		y.value = tempValue;

		treeNode templeft = x.left;
		x.left = y.left;
		y.left = templeft;

		boolean tempIsParent = x.isPearent;
		x.isPearent = y.isPearent;
		y.isPearent = tempIsParent;

		treeNode tempRight = x.right;
		x.right = y.right;
		y.right = tempRight;

		if (x.isPearent) {
			x.left.pearent = x;
			x.right.pearent = x;
		}

		if (y.isPearent) {
			y.left.pearent = y;
			y.right.pearent = y;
		}

		// if (x.left != null)
		// x.left.pearent = x;
		// if (x.right != null)
		// x.right.pearent = x;
		//
		// if (y.left != null)
		// y.left.pearent = y;
		// if (y.right != null)
		// y.right.pearent = y;
	}

	public boolean equal(treeNode n) {
		return number == n.number ? true : false;
	}

}
