/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classifier;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author bpabisiak
 */
public class Classification {
    private List<String> firstFileTokens;
    private Map<String, Float> firstFileVector;
    
    private List<String> secondFileTokens;
    private Map<String, Float> secondFileVector;
    
    private Map<String, Float> commonWordsList;
    
    private List<String> stopWordsList;
    private List<List<String>> sjpWordsList;
    
    public Classification(List<String> stopWordsList, List<List<String>> sjpWordsList) {
        this.stopWordsList = stopWordsList;
        this.sjpWordsList = sjpWordsList;
    }
    
    public void loadAndParseFiles(String firstFilePath, String secondFilePath) throws FileNotFoundException, IOException {
        firstFileTokens = loadAndParseFile(firstFilePath);
        firstFileVector = prepareFileVector(firstFileTokens);
        
        secondFileTokens = loadAndParseFile(secondFilePath); 
        secondFileVector = prepareFileVector(secondFileTokens);
        
        commonWordsList = prepareCommonWordsMap();
        
        
        System.out.println("done");

//        //debug code, print tokes of first file
//        for(String w : secondFileWordsList) {
//            System.out.println("W:\t" + w);
//        }
        
    }   
    
    
    private Map<String, Float> prepareFileVector(List<String> tokens) {
        Map<String, Float> result = new HashMap<String, Float>();
        
        if(null != tokens) {
            for(String t : tokens) {
                result.put(t, ((float)Collections.frequency(tokens, t) / (float)tokens.size()));
            }
        }
        
        return result;
    }
    
    private Map<String, Float> prepareCommonWordsMap() {
        Map<String, Float> result = new HashMap<String, Float>();
        
        if(null != firstFileTokens && null != secondFileTokens) {
            
            int i = 0;
            
            for(String w : firstFileTokens) {
                result.put(w, (float)0.0);
                i++;
            }
//            System.out.println("ADDED first iteration: " + i);
            
            for(String w : secondFileTokens) {
                result.put(w, (float)0.0);
                i++;
            }

            
//            System.out.println("MAP SIZE: " + result.size());
//            System.out.println("WORDS COUNT: " + i);
        }
        
        return result;  
    }
    
    /**
     * @param firstFilePath - file path to the input file
     * @throws FileNotFoundException
     * @throws IOException 
     */
    private List<String> loadAndParseFile(String firstFilePath) throws FileNotFoundException, IOException {
        
        List<String> result = new ArrayList<String>();
        
        BufferedReader br = new BufferedReader(new FileReader(firstFilePath));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {

                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();

                if(line != null) {
                    String[] splitted = line.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");                
                    for(String word : splitted) {

                        //check stop words
                        boolean isStopWord = false;
                        for(String stopWord : stopWordsList) {
                            if(stopWord.equalsIgnoreCase(word)) {
                                isStopWord = true;
                                break;
                            }
                        }

                        //create tokens
                        if(!isStopWord) {
                            for(List<String> sjpWords : sjpWordsList) {
                                String token = "";
                                boolean isReplaced = false;
                                for(String sjpWord : sjpWords) {
                                    if(sjpWord.equalsIgnoreCase(word)) {
                                        token = sjpWords.get(0);
//                                        System.out.println(word + " : " + token);
                                        result.add(token);
                                        isReplaced = true;
                                        break;
                                    }
                                }
                                if(isReplaced) {
                                    break;
                                }
                            }
                        }
                    }                    
                }
            }
        } finally {
            br.close();
        }        
        
        return result;
    }

    public float checkFile(String filePath) {
        return (float) 0.0;
    }

}
