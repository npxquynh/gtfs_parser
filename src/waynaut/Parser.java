package waynaut;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
		String[] requiredFiles = {"agency"};
		
		for (String fileName : requiredFiles) {
			Path filePath = folder.resolve(fileName + ".txt");
			
			if (!Files.exists(filePath)) {
				System.out.format("Required file %s not existed", filePath);
				System.exit(0);
			}
			
			// Read the 1st line to get the header
			
//			File file = new File(filePath.toString());

//			CSVParser parser = CSVParser.parse(file, StandardCharsets.UTF_8, CSVFormat.EXCEL.withFirstRecordAsHeader());
//			for (CSVRecord record : parser) {
//				System.out.println(record);
//			}
			
			// Parse using opencsv
			HeaderColumnNameTranslateMappingStrategy<Agency> agencyStrategy = new HeaderColumnNameTranslateMappingStrategy<Agency>();
	        
	        // Column mapping - general solution
	        Map<String, String> columnMapping = new HashMap<String, String>();
	        String[] columns = {"agency_id", "agency_name", "agency_url", "agency_timezone", "agency_lang", "agency_phone"};
	        for (String column : columns) {
	        	String newColumn = WordUtils.uncapitalize(WordUtils.capitalize(column, new char[]{'_'}).replaceAll("_", ""));
	        	columnMapping.put(column, newColumn);
	        }
	        
	        agencyStrategy.setType(Agency.class);
	        agencyStrategy.setColumnMapping(columnMapping);
	        CsvToBean<Agency> csvToBean = new CsvToBean<Agency>();
	        CSVReader reader = new CSVReader(new FileReader(filePath.toString()));
	        List<Agency> agencies = csvToBean.parse(agencyStrategy, reader);
	        
	        System.out.println(agencies);
	        
		}
	}	
}
