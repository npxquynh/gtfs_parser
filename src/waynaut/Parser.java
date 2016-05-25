package waynaut;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.CSVFormat;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang.math.IntRange;

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
//		String[] requiredFiles = {"agency", "stops", "routes", "trips", "stop_times", "calendar", "calendar_dates"};
		String[] requiredFiles = {"agency"};
		
		for (String fileName : requiredFiles) {
			Path filePath = folder.resolve(fileName + ".txt");
			
			if (!Files.exists(filePath)) {
				System.out.format("Required file %s not existed", filePath);
				System.exit(0);
			}	
			
			
			FeedParser fp;
			String[] columns;
			switch (fileName) {
				case "agency":
					fp = new FeedParser<Agency>(Agency.class);	
					columns = "agency_id,agency_name,agency_url,agency_timezone,agency_lang,agency_phone".split(",");
					fp.parseCSVToBeanList(filePath, columns);
				case "calendar_dates":
					fp = new FeedParser<CalendarDate>(CalendarDate.class);
					columns = "service_id,date,exception_type".split(",");
					fp.parseCSVToBeanList(filePath, columns);
				case "stops":
					fp = new FeedParser<Stops>(Stops.class);
					columns = "stop_id,stop_code,stop_name,stop_desc,stop_lat,stop_lon,stop_elevation,zone_id,stop_url,location_type,parent_station,platform_code,ch_station_long_name,ch_station_synonym1,ch_station_synonym2,ch_station_synonym3,ch_station_synonym4".split(",");
					fp.parseCSVToBeanList(filePath, columns);
				case "routes":
					fp = new FeedParser<Routes>(Routes.class);
					columns = "route_id,agency_id,route_short_name,route_long_name,route_desc,route_type,route_url,route_color,route_text_color".split(",");
					fp.parseCSVToBeanList(filePath, columns);
				case "trips":
					fp = new FeedParser<Trips>(Trips.class);
					columns = "route_id,service_id,trip_id,trip_headsign,trip_short_name,direction_id,block_id,shape_id,bikes_allowed,attributes_ch".split(",");
					fp.parseCSVToBeanList(filePath, columns);
				case "stop_times":
					fp = new FeedParser<StopTimes>(StopTimes.class);
					columns = "trip_id,arrival_time,departure_time,stop_id,stop_sequence,stop_headsign,pickup_type,drop_off_type,shape_dist_traveled,attributes_ch".split(",");
					fp.parseCSVToBeanList(filePath, columns);
			}
		}
	}	
}

class MyParser {
	public <T> void parseCSV(Path filePath) {
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
			
			// Parse the 2nd time
			String headersString = StringUtils.join(headers, ",");
			System.out.println(headersString);
			parser = CSVParser.parse(file,  StandardCharsets.UTF_8, CSVFormat.EXCEL.withHeader(headers));
			
			// Ignore the header line
			System.out.println(parser.iterator().next());

			List<T> csvValues = new ArrayList<T>();
			
			for (CSVRecord record : parser) {
				System.out.println(record);
			}
			parser.close();
		
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	    
	public <T> void parseCSVToBeanList(Path filePath, String[] columns) throws IOException {
		// Parse using opencsv
		HeaderColumnNameTranslateMappingStrategy<T> strategy = new HeaderColumnNameTranslateMappingStrategy<T>();
        
        // Column mapping - general solution
        Map<String, String> columnMapping = createColumnMapping(columns);

        strategy.setType((Class<T>) this.typeParameterClass);
        strategy.setColumnMapping(columnMapping);
        CsvToBean<T> csvToBean = new CsvToBean<T>();
        CSVReader reader = new CSVReader(new FileReader(filePath.toString()));
        List<T> agencies = csvToBean.parse(strategy, reader);
        
        System.out.println(agencies);
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
