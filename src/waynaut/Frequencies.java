package waynaut;

public class Frequencies {
// trip_id,start_time,end_time,headway_secs
	private String tripId;
	private String startTime;
	private String endTime;
	private String headwaySecs;
	public String getTripId() {
		return tripId;
	}
	public void setTripId(String tripId) {
		this.tripId = tripId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getHeadwaySecs() {
		return headwaySecs;
	}
	public void setHeadwaySecs(String headwaySecs) {
		this.headwaySecs = headwaySecs;
	}
	
	@Override
	public String toString() {
		return String.format("Trip %s starts at %s, ends at %s\n" , tripId, startTime, endTime);
	}
}
