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
		
		int DEFAULT_N_ELEMENT = 5;
		int noElementsToPrint = DEFAULT_N_ELEMENT;
		if (args.length > 1) {
			noElementsToPrint = Integer.parseInt(args[1]);
		}
			
		
		if (!checkFeed(folder)) {
			System.out.println("The folder provided is not the in the correct format");
			System.exit(0);
		}
		
		// Required classes files in the feed
		String[] files = {"agency", "stops", "routes", "trips", "stop_times", "calendar", "calendar_dates", "feed_info", "frequencies", "transfers"};
		
		for (String fileName : files) {
			Path filePath = folder.resolve(fileName + ".txt");
			
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
				printFirstNthItems(fp.parseCSVToBeanList(filePath), noElementsToPrint);
			}
			if (mp != null) {
				printFirstNthItems(mp.parseCSV(filePath), noElementsToPrint);
			}
		}
	}	

	public static boolean checkFeed(Path folder) {
		String[] requiredFiles = {"agency", "stops", "routes", "trips", "stop_times", "calendar"};
		
		for (String fileName : requiredFiles) {
			Path filePath = folder.resolve(fileName + ".txt");
			if (!Files.exists(filePath)) {
				return false;
			}
		}
		return true;
	}
	
	public static <T> void printFirstNthItems(List<T> list, int N) {
		int i = 0;
		for (T item : list) {
			if (i == N) {
				break;
			}
			i += 1;
			System.out.println(item);
		}
	}
}
