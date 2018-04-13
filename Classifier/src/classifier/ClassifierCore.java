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
public class ClassifierCore {
    
    private static List<String> stopWordsList;
    private static List<List<String>> sjpWordsList;
    private static final String USER_DIR = System.getProperty("user.dir");
    
    
    public static void execute() throws IOException {
        stopWordsList = loadStopWords(USER_DIR + "\\resources\\stopwords.txt");
        sjpWordsList = loadSjpWords(USER_DIR + "\\resources\\sjp.txt");

        Classification cls1 = new Classification(stopWordsList, sjpWordsList);
        cls1.loadAndParseFiles(USER_DIR + "\\resources\\job_ads\\it_1.txt", 
                USER_DIR + "\\resources\\job_ads\\it_2.txt", 
                USER_DIR + "\\resources\\job_ads\\it_3.txt");
        
//        Classification cls2 = new Classification(stopWordsList, sjpWordsList);
//        cls2.loadAndParseFiles(
//                USER_DIR + "\\resources\\job_ads\\bd_1.txt", 
//                USER_DIR + "\\resources\\job_ads\\bd_2.txt", 
//                USER_DIR + "\\resources\\job_ads\\bd_3.txt" );
       
        
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
