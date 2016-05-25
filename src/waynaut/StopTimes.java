package waynaut;

public class StopTimes {
	//trip_id,arrival_time,departure_time,stop_id,stop_sequence,stop_headsign,pickup_type,drop_off_type,shape_dist_traveled,attributes_ch
	private String tripId;
	private String arrivalTime;
	private String departureTime;
	private String stopId;
	private String stopSequence;
	private String stopHeadsign;
	private String pickupType;
	private String dropOffType;
	private String shapeDistTraveled;
	private String attributesCh;
	public String getTripId() {
		return tripId;
	}
	public void setTripId(String tripId) {
		this.tripId = tripId;
	}
	public String getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public String getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}
	public String getStopId() {
		return stopId;
	}
	public void setStopId(String stopId) {
		this.stopId = stopId;
	}
	public String getStopSequence() {
		return stopSequence;
	}
	public void setStopSequence(String stopSequence) {
		this.stopSequence = stopSequence;
	}
	public String getStopHeadsign() {
		return stopHeadsign;
	}
	public void setStopHeadsign(String stopHeadsign) {
		this.stopHeadsign = stopHeadsign;
	}
	public String getPickupType() {
		return pickupType;
	}
	public void setPickupType(String pickupType) {
		this.pickupType = pickupType;
	}
	public String getDropOffType() {
		return dropOffType;
	}
	public void setDropOffType(String dropOffType) {
		this.dropOffType = dropOffType;
	}
	public String getShapeDistTraveled() {
		return shapeDistTraveled;
	}
	public void setShapeDistTraveled(String shapeDistTraveled) {
		this.shapeDistTraveled = shapeDistTraveled;
	}
	public String getAttributesCh() {
		return attributesCh;
	}
	public void setAttributesCh(String attributesCh) {
		this.attributesCh = attributesCh;
	}
	
	@Override
	public String toString() {
		return "Id=" + tripId + ", arrivalTime=" + arrivalTime + "\n";
	}
}
