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
    
    public void loadFiles(String firstFilePath, String secondFilePath) throws FileNotFoundException, IOException {
        firstFileWordsList = loadAndParseFile(firstFilePath);
//        secondFileWordsList = loadAndParseFile(secondFilePath);

        
        for(String w : firstFileWordsList) {
            System.out.println("W:\t" + w);
        }
        
    }   
    
    //TODO this method is not ready yet
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
                        result.add(word);
                    }                    
                }
            }
            String everything = sb.toString();
        } finally {
            br.close();
        }        
        
        return result;
    }
}
