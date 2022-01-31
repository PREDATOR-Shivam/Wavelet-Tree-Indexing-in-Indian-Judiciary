package com.crawler.compression;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

import com.crawler.indexing.wavelet.WaveletSingle;
import com.crawler.indexing.wavelet.WaveletParallel;

public class LZWCompression {
	
	private static int dictMaxSize = 65_536;
	
	private static short compressData(BufferedReader inputFile, BufferedWriter encodedFile) {
		Map<String, Short> dictionary = new HashMap<>();
		String s = null;
		short i = 0;
		for (; i < 256; i++) {
			s = String.valueOf((char)i);
			dictionary.putIfAbsent(s, i);
		}
		s = "";
		char c;
		int value = 0;
		try {
			while((value = inputFile.read()) != -1) {
				c = (char) value;
				s = s + c;
				if (dictionary.size() == dictMaxSize) {
					dictionary.clear();
				}
				if (!dictionary.containsKey(s)) {
					dictionary.put(s,i);
					i++;
					s = s.substring(0, s.length() - 1);
					encodedFile.write(dictionary.get(s) + " ");
					s = String.valueOf(c);
				}
					
			}
			if (!s.isEmpty()) {
				encodedFile.write(dictionary.get(s) + " ");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  i;
		
	}
	
	public static void lzwHelper(String inputFilePath, String encodedFilePath, String outputFilePath, String sigmaFilePath, int choice) {
		Short j = 0;
		try(FileReader reader = new FileReader(inputFilePath); BufferedReader inputFileReader = new BufferedReader(reader);
			FileWriter writer1 = new FileWriter(sigmaFilePath); BufferedWriter sigmaFileWriter = new BufferedWriter(writer1);
			FileWriter writer2 = new FileWriter(encodedFilePath); BufferedWriter encodedFileWriter = new BufferedWriter(writer2)){
				j = LZWCompression.compressData(inputFileReader,  encodedFileWriter);
				for (short i = 0; i < j; i++) {
					sigmaFileWriter.write(i + " ");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 if (choice == 1) {
			WaveletSingle wavelet = new WaveletSingle();
			wavelet.createTreeHelper(j, encodedFilePath, outputFilePath);
		 } else if (choice == 2) {
			 WaveletParallel wavelet = new WaveletParallel();
			 wavelet.createTreeHelper(j, encodedFilePath, outputFilePath);
		 }
			
	}

}
