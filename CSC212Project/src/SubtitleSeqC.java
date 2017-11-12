
public class SubtitleSeqC implements SubtitleSeq {
    List<Subtitle> ListofSubs = new LinkedList<Subtitle>();
	@Override
	public void addSubtitle(Subtitle st) {
		ListofSubs.insert(st);
		
	}

	@Override
	public List<Subtitle> getSubtitles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Subtitle getSubtitle(Time time) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Subtitle> getSubtitles(Time startTime, Time endTime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Subtitle> getSubtitles(String str) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(String str) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void replace(String str1, String str2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shift(int offset) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cut(Time startTime, Time endTime) {
		// TODO Auto-generated method stub
		
	}

}
