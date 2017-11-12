
public class SubtitleC implements Subtitle {
	private Time startTime;
	private Time endTime;
	private String text;
	
	@Override
	public Time getStartTime() {
		return this.startTime;
	}

	@Override
	public Time getEndTime() {
		return this.endTime;
	}

	@Override
	public String getText() {
		return this.text;
	}

	@Override
	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	@Override
	public void setEndTime(Time endTime) {
		this.endTime=endTime;
		
	}

	@Override
	public void setText(String text) {
		this.text=text;
		
	}

}
