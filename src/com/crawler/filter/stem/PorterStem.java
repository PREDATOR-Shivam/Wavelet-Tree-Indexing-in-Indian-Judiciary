package com.crawler.filter.stem;

import opennlp.tools.stemmer.PorterStemmer;

import java.util.Set;
import java.util.HashSet;

public class PorterStem implements Stemming {

	String[] words;
	PorterStemmer stemmer;
	
	public PorterStem(String[] words) {
		this.words = words;
		stemmer = new PorterStemmer();
	}
	
	@Override
	public Set<String> stemming() {
		// TODO Auto-generated method stub
		Set<String> stemmedWordList = new HashSet<>();
		for (String word : words) {
			stemmedWordList.add(stemmer.stem(word));
		}
		return stemmedWordList;
	}
	
	

}
