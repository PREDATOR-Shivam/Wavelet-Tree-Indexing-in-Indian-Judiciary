package com.crawler.filter.lemma;


import opennlp.tools.lemmatizer.DictionaryLemmatizer;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

public class DictionaryLemmatizor {
	
	public static String[] lemmatize(String[] tokens) {
		String[] lemma = null;
		try {
		InputStream posModelIn = new FileInputStream("resources\\en-pos-maxent.bin");
		
		POSModel posModel = new POSModel(posModelIn);
		
		POSTaggerME posTagger = new POSTaggerME(posModel);
		
		String[] tags = posTagger.tag(tokens);
		
		InputStream dictLemmatizer = new FileInputStream("resources\\Dictionary.txt");
		
		DictionaryLemmatizer lemmatizer = new DictionaryLemmatizer(dictLemmatizer);
		
		lemma = lemmatizer.lemmatize(tokens, tags);
		
		}catch(FileNotFoundException e) {
			System.out.println(e);
		}catch (IOException e) {
			System.out.println(e);
		}
		return lemma;
	}

}
