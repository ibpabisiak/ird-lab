/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apriori;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author bpabisiak
 */
public class AprioriCore {
    
    public static final int min = 2;
    
    public static List<List<String>> input;
    public static Map<String, Integer> oneElements;
    public static List<TwoElements> twoElements;
    
    public static void exectue() {
        input = prepareInput();
        oneElements = prepareOneElementsArr();
        twoElements = prepareTwoElementsArr();
        

    }
    
    public static List<TwoElements> prepareTwoElementsArr() {
        List<TwoElements> result = new ArrayList<TwoElements>();
        
        for (Map.Entry<String, Integer> entry : oneElements.entrySet())
        {
            if(entry.getValue() >= min) {
                
                
            }
        }
        
        
        return result;
    }
    
    private static boolean isPairOneTheList(String val1, String val2) {
        boolean result = false;
        
        for(TwoElements pair : twoElements) {
            if((pair.firstItem.equals(val1) || pair.firstItem.equals(val2))
                    && (pair.secondItem.equals(val1) || pair.secondItem.equals(val2))) {
                result = true;
                break;
            }
        }
        
        return result;
    }
    
    public static Map<String, Integer> prepareOneElementsArr() {
        Map<String, Integer> result = new HashMap<String, Integer>();
        
        for(List<String> items : input) {
            for(String item : items) {
                
                if(result.containsKey(item)) {
                    int count = result.get(item) + 1;
                    result.put(item, count);
                } else {
                    result.put(item, 1);
                }                
            }
        }
        
        return result;
    }
    
    public static List<List<String>> prepareInput() {
        List<List<String>> result = new ArrayList<List<String>>();

        List<String> items = new ArrayList<String>();
        items.add("piwo");
        items.add("pampersy");
        result.add(items);
        
        items = new ArrayList<String>();
        items.add("piwo");
        items.add("jablko");
        result.add(items);
        
        items = new ArrayList<String>();
        items.add("piwo");
        items.add("pampersy");
        result.add(items); 
        
        items = new ArrayList<String>();
        items.add("jablko");
        result.add(items);
        
        items = new ArrayList<String>();
        items.add("piwo");
        items.add("pampersy");
        items.add("banan");
        result.add(items);    
        
        return result;
    }
}
