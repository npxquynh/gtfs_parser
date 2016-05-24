package waynaut;

public class Stops {
// ch_station_synonym3,ch_station_synonym4
	
	private String stopId;
	private String stopCode;
	private String stopName;
	private String stopDesc;
	private float stopLat;
	private float stopLon;
	private float stopElevation;
	private String zoneId;
	private String zoneUrl;
	private String locationType;
	private String parentStation;
	private String platformCode;
	private String chStation;
	private String chStationLongName;
	private String chStationSynonym1;
	private String chStationSynonym2;
	private String chStationSynonym3;
	private String chStationSynonym4;
	public String getStopId() {
		return stopId;
	}
	public void setStopId(String stopId) {
		this.stopId = stopId;
	}
	public String getStopCode() {
		return stopCode;
	}
	public void setStopCode(String stopCode) {
		this.stopCode = stopCode;
	}
	public String getStopName() {
		return stopName;
	}
	public void setStopName(String stopName) {
		this.stopName = stopName;
	}
	public String getStopDesc() {
		return stopDesc;
	}
	public void setStopDesc(String stopDesc) {
		this.stopDesc = stopDesc;
	}
	public float getStopLat() {
		return stopLat;
	}
	public void setStopLat(float stopLat) {
		this.stopLat = stopLat;
	}
	public float getStopLon() {
		return stopLon;
	}
	public void setStopLon(float stopLon) {
		this.stopLon = stopLon;
	}
	public float getStopElevation() {
		return stopElevation;
	}
	public void setStopElevation(float stopElevation) {
		this.stopElevation = stopElevation;
	}
	public String getZoneId() {
		return zoneId;
	}
	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}
	public String getZoneUrl() {
		return zoneUrl;
	}
	public void setZoneUrl(String zoneUrl) {
		this.zoneUrl = zoneUrl;
	}
	public String getLocationType() {
		return locationType;
	}
	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}
	public String getParentStation() {
		return parentStation;
	}
	public void setParentStation(String parentStation) {
		this.parentStation = parentStation;
	}
	public String getPlatformCode() {
		return platformCode;
	}
	public void setPlatformCode(String platformCode) {
		this.platformCode = platformCode;
	}
	public String getChStation() {
		return chStation;
	}
	public void setChStation(String chStation) {
		this.chStation = chStation;
	}
	public String getChStationLongName() {
		return chStationLongName;
	}
	public void setChStationLongName(String chStationLongName) {
		this.chStationLongName = chStationLongName;
	}
	public String getChStationSynonym1() {
		return chStationSynonym1;
	}
	public void setChStationSynonym1(String chStationSynonym1) {
		this.chStationSynonym1 = chStationSynonym1;
	}
	public String getChStationSynonym2() {
		return chStationSynonym2;
	}
	public void setChStationSynonym2(String chStationSynonym2) {
		this.chStationSynonym2 = chStationSynonym2;
	}
	public String getChStationSynonym3() {
		return chStationSynonym3;
	}
	public void setChStationSynonym3(String chStationSynonym3) {
		this.chStationSynonym3 = chStationSynonym3;
	}
	public String getChStationSynonym4() {
		return chStationSynonym4;
	}
	public void setChStationSynonym4(String chStationSynonym4) {
		this.chStationSynonym4 = chStationSynonym4;
	}
	
	@Override 
	public String toString() {
		return "Id=" + stopId + ",Name=" + stopName +"\n";
	}
}
