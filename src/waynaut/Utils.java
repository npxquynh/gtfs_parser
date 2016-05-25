package waynaut;

import org.apache.commons.lang.WordUtils;

public class Utils {
	public static String toCamelCase(String s) {
		return WordUtils.uncapitalize(WordUtils.capitalize(
				s, new char[]{'_'}).replaceAll("_", ""));		
	}
}
