package waynaut;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class Calendar {
//service_id,monday,tuesday,wednesday,thursday,friday,saturday,sunday,start_date,end_date
	private String serviceId;
	private boolean monday;
	private boolean tuesday;
	private boolean wednesday;
	private boolean thursday;
	private boolean friday;
	private boolean saturday;
	private boolean sunday;
	private Date startDate;
	private Date endDate;
	
	public Calendar(Map<String, String> map) {
		for (Map.Entry<String, String> entry : map.entrySet()) {
			try {
				String setMethodName = "set" + StringUtils.capitalize(entry.getKey());
				this.getClass().getDeclaredMethod(setMethodName, String.class).invoke(this, entry.getValue());
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public boolean isMonday() {
		return monday;
	}
	public void setMonday(String monday) {
		this.monday = boolFromString(monday);
	}
	public boolean isTuesday() {
		return tuesday;
	}
	public void setTuesday(String tuesday) {
		this.tuesday = boolFromString(tuesday);
	}
	public boolean isWednesday() {
		return wednesday;
	}
	public void setWednesday(String wednesday) {
		this.wednesday = boolFromString(wednesday);
	}
	public boolean isThursday() {
		return thursday;
	}
	public void setThursday(String thursday) {
		this.thursday = boolFromString(thursday);
	}
	public boolean isFriday() {
		return friday;
	}
	public void setFriday(String friday) {
		this.friday = boolFromString(friday);
	}
	public boolean isSaturday() {
		return saturday;
	}
	public void setSaturday(String saturday) {
		this.saturday = boolFromString(saturday);
	}
	public boolean isSunday() {
		return sunday;
	}
	public void setSunday(String sunday) {
		this.sunday = boolFromString(sunday);
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public void setStartDate(String startDate){
		this.startDate = dateFromString(startDate);
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = dateFromString(endDate);
	}
	
	@Override
	public String toString() {
		return "StartDate = " + startDate + ", EndDate = " + endDate;
	}
	
	private boolean boolFromString(String s) {
		if (s.compareTo("1") == 0) {
			return true;
		}
		return false;
	}
	
	private Date dateFromString(String s) {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		Date date = null;
		try {
			date = df.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	
}
