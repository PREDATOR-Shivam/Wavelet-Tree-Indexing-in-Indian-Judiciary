package com.crawler.filter;

import opennlp.tools.tokenize.DetokenizationDictionary;
import opennlp.tools.tokenize.DetokenizationDictionary.Operation;
import opennlp.tools.tokenize.DictionaryDetokenizer;

public class Detockenize {
	
	public static String deTokenize(String[] token, DetokenizationDictionary.Operation operation) {
		Operation[] operations = new Operation[token.length];
		for (int i = 0; i < token.length; i++) {
			operations[i] = operation;
		}
		DetokenizationDictionary dictionary = new DetokenizationDictionary(token, operations);
		DictionaryDetokenizer detokenizer = new DictionaryDetokenizer(dictionary);
		return detokenizer.detokenize(token, " ");
	}
	

}
