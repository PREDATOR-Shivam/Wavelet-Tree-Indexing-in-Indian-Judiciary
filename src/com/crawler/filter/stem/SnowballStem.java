package com.crawler.filter.stem;

import java.util.Set;
import java.util.HashSet;

import opennlp.tools.stemmer.snowball.SnowballStemmer;

public class SnowballStem implements Stemming {

	String[] words;
	SnowballStemmer stemmer;
	
	public SnowballStem(String[] words) {
		this.words = words;
		this.stemmer = new SnowballStemmer(SnowballStemmer.ALGORITHM.ENGLISH);
	}
	
	@Override
	public Set<String> stemming() {
		Set<String> stemmedWordList = new HashSet<>();
		for (String word : words) {
			stemmedWordList.add(stemmer.stem(word).toString());
			
		}
		return stemmedWordList;
	}
	
	

}
