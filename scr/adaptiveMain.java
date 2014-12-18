package adaptiveHuffman;

import java.awt.Choice;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class adaptiveMain {

	static treeNode root, ser;
	static int swa = -1;

	static void updateCode(treeNode x, String code) {
		x.code = code;
		if (x.right != null)
			updateCode(x.right, code + "1");
		if (x.left != null)
			updateCode(x.left, code + "0");
	}

	static void rec(treeNode ro, int num, int weight) {
		if (ro == null)
			return;

		if (weight >= ro.weigth && ro.number > num) {
			boolean flag = true;
			if (ro.isPearent && ro.left.number == num && ro.right.number == num) {
				flag = false;
			}
			if (flag) {
				swa = Math.max(swa, ro.number);
			}
		}

		rec(ro.left, num, weight);
		rec(ro.right, num, weight);
	}

	static treeNode bestNode(treeNode ro, int num, int weight) {

		swa = -1;
		rec(root, num, weight);

		if (swa == -1)
			return null;
		else
			return treeSearch1(root, swa);

	}

	static void updateSwap(treeNode loc) {

		while (!loc.isRoot()) {
			treeNode temp = bestNode(root, loc.number, loc.weigth);
			// System.out.println("temp " + temp + " " + loc.number + " " +
			// loc.weigth);

			if (temp != null) {

				// System.out.println("will swap " + temp.number + " "
				// + loc.number + " " + temp.weigth + " " + loc.weigth
				// + " " + temp.value + " " + loc.value);

				treeNode.Swap(loc, temp);
				updateCode(root, "");

				// System.out.println();
				// printTree(root, "");
				updateCode(temp, temp.code);

				loc = temp;

			}

			loc.increment();
			loc = loc.pearent;
		}

		loc.increment();
	}

	static void updateTree(treeNode NYT, boolean status, char newValue) {

		// System.out.println(localNYT + "\n" + NYT );
		if (status) {

			NYT.increment();
			NYT.split(newValue);

			if (!NYT.isRoot()) {
				// System.out.println("parent " + NYT.pearent + " " + newValue);
				NYT = NYT.pearent;
				updateSwap(NYT);
			}

		} else {
			updateSwap(NYT);
		}
	}

	static treeNode treeSearch1(treeNode ro, int target) {
		treeNode tem1 = null, tem2 = null;
		if (ro.isPearent) {
			if (ro.number == target)
				return ro;
			if (ro.left != null)
				tem1 = treeSearch1(ro.left, target);
			if (ro.right != null)
				tem2 = treeSearch1(ro.right, target);
		} else {
			if (ro.number == target)
				return ro;

			return null;
		}

		if (tem1 == null && tem2 == null)
			return null;
		else if (tem1 != null)
			return tem1;
		else
			return tem2;
	}

	static treeNode treeSearch(treeNode ro, char target) {

		treeNode tem1 = null, tem2 = null;
		if (ro.isPearent) {
			if (ro.left != null)
				tem1 = treeSearch(ro.left, target);
			if (ro.right != null)
				tem2 = treeSearch(ro.right, target);
		} else {
			if (ro.value == target)
				return ro;
			return null;
		}

		if (tem1 == null && tem2 == null)
			return null;
		else if (tem1 != null)
			return tem1;
		else
			return tem2;
	}

	public static String compress(String data) {

		String result = "";

		root = new treeNode();
		root.rootInit();

		treeNode NYT = root, search;

		char target;

		for (int i = 0; i < data.length(); i++) {

			target = data.charAt(i);
			search = treeSearch(root, target);

			if (search == null) {
				// System.out.println("YES " + NYT.code + "*");
				result += NYT.code
						+ String.format("%07d", Integer.parseInt(Integer
								.toBinaryString(target)));
				updateTree(NYT, true, target);
				NYT = NYT.left;
			} else {
				// System.out.println(data.charAt(i) + " " + search.code);
				result += search.code;
				updateTree(search, false, target);
			}
			// printTree(root,"");
			// System.out.println();
			// System.out.println("result " + result);
		}
		// printTree(root, "");
		return result;
	}

	static void printTree(treeNode ro, String space) {
		System.out.println(space + ro.toString()
				+ (ro.isRoot() ? " Root " : " " + ro.pearent.number));

		if (ro.right != null)
			printTree(ro.right, space + "\t");

		if (ro.left != null)
			printTree(ro.left, space + "\t");

	}

	static char toChar(String temp) {
		int ch = 0, len = temp.length() - 1;
		for (int i = 0; i < len + 1; i++) {
			ch += (temp.charAt(i) == '1' ? 1 : 0) * Math.pow(2, len - i);
		}
		// System.out.println((char) ch);
		return (char) ch;
	}

	static int treeSearch(treeNode ro, String code, int lev) {

		if (code.equals(""))
			return treeNode.NODE_IS_NYT;
		// System.out.println(code.equals(""));
		if (code.equals(ro.code)) {
			if (ro.isPearent)
				return treeNode.NODE_IS_PARENT;
			else if (ro.value == '\1')
				return treeNode.NODE_IS_NYT;
			else {
				ser = ro;
				return ro.value;
			}

		} else if (ro.left != null
				&& code.charAt(lev) == ro.left.code.charAt(lev))
			return treeSearch(ro.left, code, lev + 1);
		else if (ro.right != null
				&& code.charAt(lev) == ro.right.code.charAt(lev))
			return treeSearch(ro.right, code, lev + 1);

		return treeNode.NODE_IS_PARENT;
	}

	public static String deCompress(String data) {
		String res = "", temp = "";
		root = new treeNode();
		root.rootInit();
		treeNode NYT = root;
		int respond;

		char newValue1 = toChar(data.substring(0, 7));
		res += newValue1;
		updateTree(NYT, true, newValue1);
		NYT = NYT.left;

		for (int i = 7; i < data.length(); i++) {
			temp += data.charAt(i);

			respond = treeSearch(root, temp, 0);

			// System.out.println(respond + " " + temp);

			if (respond == treeNode.NODE_IS_NYT) {
				char newValue = toChar(data.substring(i, i + 8));
				res += newValue;
				i += 7;
				updateTree(NYT, true, newValue);
				NYT = NYT.left;
				temp = "";
			} else if (respond != treeNode.NODE_IS_PARENT) {
				char newValue = (char) respond;
				updateTree(ser, false, newValue);
				res += newValue;
				temp = "";
			}
		}

		return res;
	}

	public static void main(String[] args) {
		Scanner x = new Scanner(System.in);
		System.out.println("1 for compressing\n2 for decompressing\n3 Exit");

		while (true) {
			int Choice = x.nextInt();
			if (Choice == 1) {
				String line = x.next();
				String result = compress(line);
				System.out.println("Compressed Data\n" + result
						+ "\nTree Of Compressing");
				printTree(root, "");
			} else if (Choice == 2) {
				String code = x.next();
				String data = deCompress(code);
				System.out.println("Decompressed Data\n" + data
						+ "\nTree Of deCompressing");
				printTree(root, "");
			} else
				break;

		}

		// ** // this part of code for testing

		// String data = "mohamed fouad morsy attia ahmed salem mansour";
		// // String data = "abcccaa";
		// String result = compress(data);
		//
		// System.out.println("Compressed Data\n" + result
		// + "\nTree Of Compressing");
		// printTree(root, "");
		//
		// root = new treeNode();
		//
		// // String compressed = "110000101100010001100011101000101";
		// String compressed = result;
		// String output = deCompress(compressed);
		// System.out.println("Decompressed Data\n" + output
		// + "\nTree Of deCompressing");
		// printTree(root, "");
	}

}
