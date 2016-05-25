package waynaut;

public class Transfers {
	private String fromStopId;
	private String toStopId;
	private String transferType;
	private String minTransferTime;
	public String getFromStopId() {
		return fromStopId;
	}
	public void setFromStopId(String fromStopId) {
		this.fromStopId = fromStopId;
	}
	public String getToStopId() {
		return toStopId;
	}
	public void setToStopId(String toStopId) {
		this.toStopId = toStopId;
	}
	public String getTransferType() {
		return transferType;
	}
	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}
	public String getMinTransferTime() {
		return minTransferTime;
	}
	public void setMinTransferTime(String minTransferTime) {
		this.minTransferTime = minTransferTime;
	}
	
	@Override
	public String toString() {
		return String.format("Transfer %s --> %s takes %s", fromStopId, toStopId, minTransferTime); 
	}
}
