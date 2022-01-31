package com.crawler.driver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.crawler.filter.StopWordRemoving;
import com.crawler.filter.TextToken;
import com.crawler.filter.lemma.DictionaryLemmatizor;
import com.crawler.inverted.index.InvertedIndex;
import com.crawler.io.ReadFiles;
import com.crawler.io.UrlFetcher;

public class DriverClass {

	public static void main(String[] args) {

		try (Scanner scanner = new Scanner(System.in)) {

			//URL fetching from high court website
			UrlFetcher urlFetcher = new UrlFetcher();
			Set<String> urls = urlFetcher.getPdfURLs();
			
			//Reading text from url and removing stop words
			List<String> textList = new ArrayList<>();
			List<String> stopWords = Files.readAllLines(Paths.get("resources\\StopWord.txt"));
			StopWordRemoving stopWordRemover = new StopWordRemoving(stopWords);
			
			for (String url : urls) {
				String originalText = ReadFiles.readFromURL(url);
				stopWordRemover.setOriginalText(originalText);
				String filteredText = stopWordRemover.removeStopWords();
				textList.add(filteredText);
			}
			
			//convert text into tokens
			List<String[]> tokens = new ArrayList<>();
			TextToken tokenizer = new TextToken();
            for(String text : textList) {
            	tokenizer.setOriginalText(text);
            	String[] token = tokenizer.tokenizer();
            	tokens.add(token);
            }
            
            //lemmatization
            List<Set<String>> lemmatizedTokens = new ArrayList<>();
            for (String[] token : tokens) {
            	String[] lemma = DictionaryLemmatizor.lemmatize(token);
            	Set<String> lemmatizedSet = new HashSet<>();
    			for(int i = 0; i < lemma.length; i++) {
    					lemmatizedSet.add(lemma[i]);
    			}
    			lemmatizedTokens.add(lemmatizedSet);
            }
			
            HashMap<String, HashSet<Integer>> index = new HashMap<String, HashSet<Integer>>() ;
            int i = 1 ;
            for (Set<String> lemmatizedSet : lemmatizedTokens) {
            	
				/*
				 * for (String token : lemmatizedSet) { System.out.println(token); }
				 */
            	System.out.println(lemmatizedSet) ;
            	// Inverse indexing of given lemmatizedSet
            	index = InvertedIndex.invertIndex(lemmatizedSet, i, index ) ;
            	System.out.println(lemmatizedSet.size());
            	System.out.println("--break--");
            	i++ ;
            }
            System.out.println(index) ;
            
			//words = fillArray.apply(words, lemmatizedSet);
			//DriverClass.detockenize.accept(words);
            //DriverClass.compressAndIndex(scanner);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*private static void compressAndIndex(Scanner s) {
		System.out.println("1. Single Wavelet Tree\n2. Parallel Wavelet Tree");
		int choice = s.nextInt();
		String inputFilePath = "resources" + File.separator + "FilteredText.txt";
		String encodedFilePath = "resources" + File.separator + "EncodedText.txt";
		String outputFilePath = "resources" + File.separator + "OutputText.txt";
		String sigmaFilePath = "resources" + File.separator + "SigmaText.txt";
		LZWCompression.lzwHelper(inputFilePath, encodedFilePath, outputFilePath, sigmaFilePath, choice);
	}
	
	private static BiFunction<String[], Set<String>, String[]> fillArray = (words, filteredWords) -> {
		words = new String[filteredWords.size()];
        Iterator<String> itr = filteredWords.iterator();
        for (int i = 0; itr.hasNext(); i++) {
        	words[i] = itr.next();
        }
        return words;
	};

	private static Consumer<String[]> detockenize = (words) -> {
		try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter("resources\\FilteredText.txt"))) {
			for (String word : words) {
				fileWriter.write(word);
				fileWriter.append(" ");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	};*/
}
