package waynaut;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.WordUtils;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

class FeedParser<T> {
	final Class<T> typeParameterClass;

    public FeedParser(Class<T> typeParameterClass) {	        
    	this.typeParameterClass = typeParameterClass;
	}
    
	    
	public <T> List<T> parseCSVToBeanList(Path filePath) throws IOException {
		// Parse using opencsv
		HeaderColumnNameTranslateMappingStrategy<T> strategy = new HeaderColumnNameTranslateMappingStrategy<T>();
        
		// Get header in the 1st row
		BufferedReader brTest = new BufferedReader(new FileReader(filePath.toString()));
		String[] columns = brTest .readLine().split(",");
		brTest.close();
		
        // Column mapping - general solution
        Map<String, String> columnMapping = createColumnMapping(columns);

        strategy.setType((Class<T>) this.typeParameterClass);
        strategy.setColumnMapping(columnMapping);
        CsvToBean<T> csvToBean = new CsvToBean<T>();
        CSVReader reader = new CSVReader(new FileReader(filePath.toString()));
        List<T> agencies = csvToBean.parse(strategy, reader);
        
        System.out.println("\nDone with " + filePath.toString());
        return agencies;
	}
	
	public static Map<String, String> createColumnMapping(String[] columns) {
		Map<String, String> columnMapping = new HashMap<String, String>();
		
        for (String column : columns) {
        	String newColumn = Utils.toCamelCase(column);
        	columnMapping.put(column, newColumn);
        }    
        return columnMapping;
	}
}
