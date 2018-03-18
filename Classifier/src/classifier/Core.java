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
public class Core {
    
    private static List<String> stopWordsList;
    private static List<List<String>> sjpWordsList;
    
    public static void execute() throws IOException {
        stopWordsList = loadStopWords("C:\\Users\\bpabisiak\\Documents\\NetBeansProjects\\Classifier\\resources\\stopwords.txt");
        sjpWordsList = loadSjpWords("C:\\Users\\bpabisiak\\Documents\\NetBeansProjects\\Classifier\\resources\\sjp.txt");



        //TODO parsowanie
        Classification cls1 = new Classification(stopWordsList, sjpWordsList);
        cls1.loadFiles("C:\\Users\\bpabisiak\\Documents\\NetBeansProjects\\Classifier\\resources\\ads\\it_1.txt", 
                "C:\\Users\\bpabisiak\\Documents\\NetBeansProjects\\Classifier\\resources\\ads\\it_2.txt");
        
        System.out.println("Hello World!");     
    }
    
    private static  List<List<String>> loadSjpWords(String sjpWordsPath) throws FileNotFoundException, IOException {
        List<List<String>> result = new ArrayList<List<String>>();
        
        BufferedReader br = new BufferedReader(new FileReader(sjpWordsPath));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {

                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();

                if(null != line) {
                    String[] singleWord = line.split(", ");
                    ArrayList<String> singleWordInLowerCase = new ArrayList<String>();
                    
                    for(String w : singleWord) {
                        singleWordInLowerCase.add(w.toLowerCase());
                    }
                    
                    
                    result.add(singleWordInLowerCase);
                }


            }
//debug code
//            for(List<String> ww : sjpWordsList) {
//                
//                for(String w : ww)
//                System.out.println("W: " + w);
//            }
        } finally {
            br.close();
        }  
        
        return result;
    }
    
    private static List<String> loadStopWords(String stopWordsPath) throws FileNotFoundException, IOException {
        
        List<String> result = new ArrayList<String>();
        
        BufferedReader br = new BufferedReader(new FileReader(stopWordsPath));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {

                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();

                if(null != line) {
                    result.add(line);
                }


            }
//debug code
//            for(String w : stopWordsList) {
//                System.out.println("W: " + w);
//            }
        } finally {
            br.close();
        }        
        
        return result;
    }
    
}
