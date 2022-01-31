package com.crawler.indexing.wavelet;

import java.util.List;
import java.util.Queue;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;

public class WaveletSingle implements Wavelet{

	private Node<Byte> createTree(List<Integer> input, int start, int end, byte b) {
		if (start == end) {
			Node<Byte> root = new Node<>();
			for (int i = 0; i < input.size(); i++) {
				root.list.add(b);
			}
			return root;
		}
		int mid = start + (end - start) / 2;
		Byte k = 0;
		Node<Byte> root = new Node<>();
		ArrayList<Integer> leftList = new ArrayList<>();
		ArrayList<Integer> rightList = new ArrayList<>();
		for (int i = 0; i < input.size(); i++) {
			if (input.get(i) <= mid) {
				k = 0;
				leftList.add(input.get(i));
				root.list.add(k);
			}else {
				k = 1;
				rightList.add(input.get(i));
				root.list.add(k);
			}
		}
		Byte l = 0;
		Byte r = 1;
		root.left =  createTree(leftList, start, mid, l);
		root.right = createTree(rightList, mid + 1, end, r);
		return root;
	}

	public void createTreeHelper(short sigmaLength, String nFilePath, String outputFilePath) {
		try(Scanner nFileReader = new Scanner(new File(nFilePath));) {
			List<Integer> input = new ArrayList<>();
			List<Integer> N = new ArrayList<>();
			List<Integer> sigma = new ArrayList<>();
			for (int i = 0; i < sigmaLength; i++) {
				sigma.add(i);
			}
			int min = 0, max = 0, val;
			while(nFileReader.hasNextInt()) {
				val = nFileReader.nextInt();
				if (val > max) {
					max = val;
				}else if(val < min) {
					min = val;
				}
				N.add(val);
			}
			for (int el : N) {
				input.add(sigma.indexOf(el));
			}
			byte b = 0;
			double start = System.currentTimeMillis();
			Node<Byte> root = createTree(input, min, max, b);
			double end = System.currentTimeMillis();
			System.out.println("Construction Time of Tree " + (end - start));
			printTree(root, outputFilePath);
			System.out.println((end - start));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public  void printTree(Node<Byte> root, String outputFilePath) {
		if (root == null) {
			return;
		}
		try (FileWriter outputWriter = new FileWriter(outputFilePath); BufferedWriter outputFileWriter= new BufferedWriter(outputWriter);) {
			Queue<Node<Byte>> queue = new LinkedList<>();
			queue.add(root);
			Node<Byte> curr = null;
			outputFileWriter.write(root.list + "\n");
			System.out.println(root.list);
			while(!queue.isEmpty()) {
				int size = queue.size();
				for (int i = 0; i < size; i++) {
					curr = queue.poll();
					if (curr.left != null) {
						queue.add(curr.left);
						outputFileWriter.write(curr.left.list + "\n");
						System.out.println(curr.left.list);
					}
					if (curr.right != null) {
						queue.add(curr.right);
						outputFileWriter.write(curr.right.list + "\n");
						System.out.println(curr.right.list);
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
