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
import static java.lang.Math.sqrt;
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
    
    private List<String> fileToCategorizeTokens;
    private Map<String, Float> fileToCategoizeVector;
    


    private Map<String, Float> commonWordsList;
    

    
    private List<String> stopWordsList;
    private List<List<String>> sjpWordsList;
    
    public Classification(List<String> stopWordsList, List<List<String>> sjpWordsList) {
        this.stopWordsList = stopWordsList;
        this.sjpWordsList = sjpWordsList;
    }
    
    public void loadAndParseFiles(String firstFilePath, String secondFilePath, String fileToCategorize) throws FileNotFoundException, IOException {

        firstFileTokens = loadAndParseFile(firstFilePath);
        secondFileTokens = loadAndParseFile(secondFilePath); 
        fileToCategorizeTokens = loadAndParseFile(fileToCategorize);
        
        commonWordsList = prepareCommonWordsMap();
        
        firstFileVector = prepareFileVector(firstFileTokens);
        secondFileVector = prepareFileVector(secondFileTokens);
        fileToCategoizeVector = prepareFileVector(fileToCategorizeTokens);
        
        float val1 = cosinus(fileToCategoizeVector, firstFileVector);
        float val2 = cosinus(fileToCategoizeVector, secondFileVector);
        System.out.println("PODOBIENSTWO:\t" + (float)((val1+val2) / 2));      
        
        
    }   
    
    
    /**
     * 
     * @param t dokument tekstowy (wektor)
     * @param tw predefiniowany dokument tekstowy (wektor)
     * @return 
     */
    private Float cosinus(Map<String, Float> t, Map<String, Float> tw) {
        Float result = (float)0;
        
        List<Float> t2 = new ArrayList<Float>();
        List<Float> tw2 = new ArrayList<Float>();
        
        Iterator it = t.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            t2.add((Float) pair.getValue());
        }  
        
        it = tw.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            tw2.add((Float) pair.getValue());
        } 
        
        float suma1 = (float)0;
        for(int i = 0; i < t2.size(); i++) {
//            System.out.println("val: " + t2.get(i));
            suma1 += (float)((float)t2.get(i) * (float)tw2.get(i));
        }
        
        float suma2 = (float)0;
        for(int i = 0; i < t2.size(); i++) {
            suma2 += (float)((float)t2.get(i) * (float)t2.get(i));
        }
        suma2 = (float) sqrt(suma2);
        
        
        float suma3 = (float)0;
        for(int i = 0; i < t2.size(); i++) {
            suma3 += (float)((float)tw2.get(i) * (float)tw2.get(i));
        }
        suma3 = (float) sqrt(suma3);
        
        
        
        
        return (float)(suma1 / (float)(suma2 * suma3));
    }
    
    private Map<String, Float> prepareFileVector(List<String> tokens) {
        Map<String, Float> result = new HashMap<String, Float>();
        
        Iterator it = commonWordsList.entrySet().iterator();
        
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
//            System.out.println(pair.getKey() + " = " + pair.getValue());
            
            int i = 0;
            for(String w : tokens) {
                if(w.equals(pair.getKey()))
                    i++;
            }
            result.put(pair.getKey().toString(), ((float)i / (float)tokens.size()));
            
        }
        
//        if(null != tokens) {
//            for(String t : tokens) {
//                result.put(t, ((float)Collections.frequency(tokens, t) / (float)tokens.size()));
//            }
//        }
        
        
        System.out.println("MAP: " + result.size());
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
            
            for(String w : fileToCategorizeTokens) {
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
