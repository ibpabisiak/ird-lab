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
import java.util.List;

/**
 *
 * @author bpabisiak
 */
public class Classification {
    private List<String> firstFileWordsList;
    private List<String> secondFileWordsList;
    
    private List<String> stopWordsList;
    private List<List<String>> sjpWordsList;
    
    public Classification(List<String> stopWordsList, List<List<String>> sjpWordsList) {
        this.stopWordsList = stopWordsList;
        this.sjpWordsList = sjpWordsList;
    }
    
    public void loadAndParseFiles(String firstFilePath, String secondFilePath) throws FileNotFoundException, IOException {
        firstFileWordsList = loadAndParseFile(firstFilePath);
        secondFileWordsList = loadAndParseFile(secondFilePath);   
        
        System.out.println("done");

        //debug code, print tokes of first file
        for(String w : secondFileWordsList) {
            System.out.println("W:\t" + w);
        }
        
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
