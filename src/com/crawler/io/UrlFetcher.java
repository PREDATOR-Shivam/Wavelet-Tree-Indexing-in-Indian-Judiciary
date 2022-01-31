package com.crawler.io;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class UrlFetcher {
	
	private static Set<String> uniqueURL = new HashSet<>();
	private static String siteUrl;
	private Set<String> pdfURLs = new HashSet<>();
	private static int count = 0;
	
	public UrlFetcher() {
		siteUrl = "allahabadhighcourt";
		this.getLink("http://www.allahabadhighcourt.in/");
	}
	
	public Set<String> getPdfURLs() {
		return pdfURLs;
	}
	
	public void getLink(String url) {
		try {
			if(UrlFetcher.count >= 100) {
				return;
			} else {
				UrlFetcher.count++;
			}
			 Document doc = Jsoup.connect(url).userAgent("Mozilla").get();
             Elements links = doc.select("a");

             if (links.isEmpty()) {
                return;
             }

             links.stream().map((link) -> link.attr("abs:href")).forEachOrdered((this_url) -> {
                 boolean add = uniqueURL.add(this_url);
                 if (this_url.endsWith(".pdf")) {
                	 pdfURLs.add(this_url);
                 } else if (add && this_url.contains(siteUrl)) {
                	 System.out.println(this_url); 
                     getLink(this_url);
                 }
             });
			
		}catch(IOException e) {
			System.out.println(e);
		}
	}

}
