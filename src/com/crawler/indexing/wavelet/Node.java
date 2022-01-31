package com.crawler.indexing.wavelet;

import java.util.ArrayList;

public class Node<T> {
	ArrayList<T> list;
	Node<T> left;
	Node<T> right;
	
	public Node() {
		list = new ArrayList<>();
		left = right = null;
	}

}
