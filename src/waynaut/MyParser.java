package waynaut;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;

class MyParser<T> {
	final Class<T> typeParameterClass;

    public MyParser(Class<T> typeParameterClass) {	        
    	this.typeParameterClass = typeParameterClass;
	}
    
	public <T> List<T> parseCSV(Path filePath) {
		List<T> csvValues = new ArrayList<T>();
		
		CSVParser parser;
		try {
			File file = filePath.toFile();
			
			// Parse the 1st time to get the header
			parser = CSVParser.parse(file, StandardCharsets.UTF_8, CSVFormat.EXCEL);
			
			// Get first row as header
			CSVRecord headersRecord = parser.iterator().next();
			String[] headers = new String[headersRecord.size()]; 
			Map<String, Integer> headersMap = new HashMap<String, Integer>();
			for (int i = 0; i < headersRecord.size(); ++i) {
				String header = Utils.toCamelCase(headersRecord.get(i)); 
				headers[i] = header;
				headersMap.put(header, i);
			}
			parser.close();
			
			// Parse the rest of the CSV
			String headersString = StringUtils.join(headers, ",");
			parser = CSVParser.parse(file,  StandardCharsets.UTF_8, CSVFormat.EXCEL.withHeader(headers));
			
			// Ignore the header line
			parser.iterator().next();
					
			for (CSVRecord record : parser) {
				@SuppressWarnings("unchecked")
				T instance = (T) typeParameterClass.getDeclaredConstructor(Map.class).newInstance(record.toMap());
				csvValues.add(instance);
			}
			parser.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("\nDone with " + filePath.toString());
		return csvValues;
	}
}