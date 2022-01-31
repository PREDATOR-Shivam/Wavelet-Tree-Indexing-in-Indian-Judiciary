package com.crawler.inverted.index ;
import java.util.*;

class Index {
    Map<Integer,String> sources;
    HashMap<String, HashSet<Integer>> index;

    Index(){
        sources = new HashMap<Integer,String>();
        index = new HashMap<String, HashSet<Integer>>();
    }

    public void buildIndex(Set<String> lemmatizedSet , int i ){
        for(String token : lemmatizedSet){
                String ln = token ;
                        if (!index.containsKey(ln))
                            index.put(ln, new HashSet<Integer>());
                        index.get(ln).add(i);        
            } 
        
        
    }

    public void find(String phrase){
        String[] words = phrase.split("\\W+");
        HashSet<Integer> res = new HashSet<Integer>(index.get(words[0].toLowerCase()));
        for(String word: words){
            res.retainAll(index.get(word));
        }

        if(res.size()==0) {
            System.out.println("Not found");
            return;
        }
        System.out.println("Found in: ");
        for(int num : res){
            System.out.println("\t"+sources.get(num));
        }
    }
}

public class InvertedIndex {
	

	public static HashMap<String, HashSet<Integer>> invertIndex(Set<String> lemmatizedSet , int i , HashMap<String, HashSet<Integer>> mp ) {
        Index index = new Index();
        index.index.putAll(mp) ;
        index.buildIndex( lemmatizedSet , i );
        return index.index ;

}
}