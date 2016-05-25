package waynaut;

public class Trips {
// route_id,service_id,trip_id,trip_headsign,trip_short_name,direction_id,block_id,shape_id,bikes_allowed,attributes_ch
	private String routeId;
	private String serviceId;
	private String tripId;
	private String tripHeadsign;
	private String tripShortName;
	private String directionId;
	private String blockId;
	private String shapeId;
	private String bikesAllowed;
	private String attributesCh;
	
	public String getRouteId() {
		return routeId;
	}
	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getTripId() {
		return tripId;
	}
	public void setTripId(String tripId) {
		this.tripId = tripId;
	}
	public String getTripHeadsign() {
		return tripHeadsign;
	}
	public void setTripHeadsign(String tripHeadsign) {
		this.tripHeadsign = tripHeadsign;
	}
	public String getTripShortName() {
		return tripShortName;
	}
	public void setTripShortName(String tripShortName) {
		this.tripShortName = tripShortName;
	}
	public String getDirectionId() {
		return directionId;
	}
	public void setDirectionId(String directionId) {
		this.directionId = directionId;
	}
	public String getBlockId() {
		return blockId;
	}
	public void setBlockId(String blockId) {
		this.blockId = blockId;
	}
	public String getShapeId() {
		return shapeId;
	}
	public void setShapeId(String shapeId) {
		this.shapeId = shapeId;
	}
	public String getBikesAllowed() {
		return bikesAllowed;
	}
	public void setBikesAllowed(String bikesAllowed) {
		this.bikesAllowed = bikesAllowed;
	}
	public String getAttributesCh() {
		return attributesCh;
	}
	public void setAttributesCh(String attributesCh) {
		this.attributesCh = attributesCh;
	}
	
	@Override
	public String toString() {
		return String.format("Trip %s is named %s", tripId, tripShortName);
	}
}
