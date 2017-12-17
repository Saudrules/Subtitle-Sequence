public class TimeInterval implements Comparable<TimeInterval> {
	
	private Integer startTime;
	private Integer endTime;
		public TimeInterval(Time start, Time end) {
		startTime = new Integer(toMS(start));
		endTime = new Integer(toMS(end));
	}
	
	
	
	//Use implementation of Comparable<Time> in the Time interface if this doesn't work
	@Override
	public int compareTo(TimeInterval that) {
		if(startTime.compareTo(that.endTime) > 0)
			return 1;
		if(endTime.compareTo(that.startTime) < 0)
			return -1;
		
		return 0;
	}
	
	private int toMS(Time t){
		return (t.getHH()*3600000 + t.getMM()*60000 + t.getSS()*1000 + t.getMS()) ;
	}
	
}
