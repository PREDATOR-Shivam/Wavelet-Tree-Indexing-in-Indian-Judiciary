package com.crawler.indexing.wavelet;

public interface Wavelet {
	
	public void createTreeHelper(short sigmaLength, String nFilePath, String outputFilePath);
	
	public void printTree(Node<Byte> root, String outputFilePath);

}
