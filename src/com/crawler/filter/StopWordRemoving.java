package com.crawler.filter;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Stream;
import java.util.stream.Collectors;

public class StopWordRemoving {

	private List<String> stopWords;
	private String originalText;
	
	public StopWordRemoving(List<String> stopWords){
		this.stopWords = stopWords;
		this.originalText = "";
	}
	
	
	public void setOriginalText(String originalText) {
		this.originalText = originalText;
	}
	
	public String removeStopWords() {
		ArrayList<String> allWords = Stream.of(originalText.split(" ")).collect(Collectors.toCollection(ArrayList<String> :: new));
		allWords.removeAll(stopWords);
		String result = allWords.stream().collect(Collectors.joining(" "));
		return result;
	}
}
