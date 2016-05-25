package waynaut;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.CSVFormat;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;

public class Parser {
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		if (args.length == 0) {
			System.out.println("Please provide the folder for GTFS");
			System.exit(0);
		}
		
		Path folder = Paths.get(args[0]);
		System.out.println("Parsing GTFS from " + folder);
		
		// Required classes files in the feed
		String[] requiredFiles = {"agency", "stops", "routes", "trips", "stop_times", "calendar"};
//		String[] requiredFiles = {"calendar"};
		
		for (String fileName : requiredFiles) {
			Path filePath = folder.resolve(fileName + ".txt");
			
			if (!Files.exists(filePath)) {
				System.out.format("Required file %s not existed", filePath);
				System.exit(0);
			}	
			
			FeedParser fp = null;
			String[] columns;
			
			MyParser mp = null;
			switch (fileName) {
			case "agency":
				fp = new FeedParser<Agency>(Agency.class);
				break;
			case "stops":
				fp = new FeedParser<Stops>(Stops.class);
				break;
			case "routes":
				fp = new FeedParser<Routes>(Routes.class);
				break;
			case "trips":
				fp = new FeedParser<Trips>(Trips.class);
				break;
			case "stop_times":
				fp = new FeedParser<StopTimes>(StopTimes.class);
				break;
			case "calendar":
				// to handle the case where I need to convert from String -> Boolean, or String -> Date
				mp = new MyParser<Calendar>(Calendar.class);
				break;
			}			
			
			// Print the first 5 lines
			if (fp != null) {
				printFirstNthItems(fp.parseCSVToBeanList(filePath), 5);
			}
			if (mp != null) {
				printFirstNthItems(mp.parseCSV(filePath), 5);
			}
		}
		
		String[] optionalFiles = {"calendar_dates", "feed_info", "frequencies", "transfers"};
		
		for (String fileName : optionalFiles) {
			Path filePath = folder.resolve(fileName + ".txt");
			
			if (Files.exists(filePath)) {
				FeedParser fp = null;
				
				switch (fileName) {
				case "calendar_dates":
					fp = new FeedParser<CalendarDate>(CalendarDate.class);
					break;
				case "feed_info":
					fp = new FeedParser<FeedInfo>(FeedInfo.class);
					break;
				case "frequencies":
					fp = new FeedParser<Frequencies>(Frequencies.class);
					break;
				case "transfers":
					fp = new FeedParser<Transfers>(Transfers.class);
					break;
				}
				
				// Print the first 5 lines
				if (fp != null) {
					printFirstNthItems(fp.parseCSVToBeanList(filePath), 5);
				}
			}
		}
	}	

	public static <T> void printFirstNthItems(List<T> list, int N) {
		int i = 0;
		for (T item : list) {
			i += 1;
			if (i == N) {
				break;
			}
			System.out.println(item);
		}
	}
}

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
				String header = toCamelCase(headersRecord.get(i)); 
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
		return csvValues;
	}
	
	public String toCamelCase(String s) {
		return WordUtils.uncapitalize(WordUtils.capitalize(
				s, new char[]{'_'}).replaceAll("_", ""));		
	}
}

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
        
        System.out.println("Done with " + filePath.toString() + "\n");
        return agencies;
	}
	
	public static Map<String, String> createColumnMapping(String[] columns) {
		Map<String, String> columnMapping = new HashMap<String, String>();
		
        for (String column : columns) {
        	String newColumn = toCamelCase(column);
        	columnMapping.put(column, newColumn);
        }    
        return columnMapping;
	}
	
	public static String toCamelCase(String s) {
		return WordUtils.uncapitalize(WordUtils.capitalize(
				s, new char[]{'_'}).replaceAll("_", ""));		
	}
}
