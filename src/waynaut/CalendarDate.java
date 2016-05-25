package waynaut;

public class CalendarDate {
	private String serviceId;
	private String date;
	private int exceptionType;
	
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getExceptionType() {
		return exceptionType;
	}
	public void setExceptionType(int exceptionType) {
		this.exceptionType = exceptionType;
	}
	
	@Override
	public String toString(){
		return "Id=" + serviceId +", Date=" + date;
	}
}
