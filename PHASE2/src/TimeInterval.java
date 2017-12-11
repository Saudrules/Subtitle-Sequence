public class TimeInterval implements Comparable<TimeInterval> {
	
	private Time startTime;
	private Time endTime;
	
	public TimeInterval(Subtitle s) {
		startTime = s.getStartTime();
		endTime = s.getEndTime();
	}
	
	@Override
	public int compareTo(TimeInterval that) {
		if(toMS(startTime) > toMS(that.endTime))
			return 1;
		if(toMS(endTime) < toMS(that.startTime))
			return -1;
		return 0;
	}
	
	private int toMS(Time t){
		return (t.getHH()*3600000 + t.getMM()*60000 + t.getSS()*1000 + t.getMS()) ;
	}
	
}
