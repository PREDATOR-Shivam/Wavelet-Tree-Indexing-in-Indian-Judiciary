package com.crawler.filter;

import opennlp.tools.tokenize.SimpleTokenizer;

public class TextToken {

	private String originalText;
	
	public TextToken() {
		this.originalText = "";
	}

	public void setOriginalText(String originalText) {
		this.originalText = originalText;
	}
	
	public String[] tokenizer() {
		SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
		String[] tokens = tokenizer.tokenize(originalText);
		return tokens;
	}	
}
