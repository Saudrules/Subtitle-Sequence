
public class SubtitleSeqC implements SubtitleSeq {
    List<Subtitle> ListofSubs = new LinkedList<Subtitle>();
	@Override
	public void addSubtitle(Subtitle st) {
		ListofSubs.insert(st);
		
	}

	@Override
	public List<Subtitle> getSubtitles(){
		return this.ListofSubs;
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
		if(this.ListofSubs.empty())
			return;
		ListofSubs.findFirst();
		while(!ListofSubs.last()) {
			if(ListofSubs.retrieve().getText().equalsIgnoreCase(str))
				ListofSubs.remove();
			ListofSubs.findNext();
		}
		if(ListofSubs.retrieve().getText().equalsIgnoreCase(str))
			ListofSubs.remove();
	}

	@Overrid
	public void replace(String str1, String str2) {
		if(this.ListofSubs.empty())
			return;
		ListofSubs.findFirst();
		while(!ListofSubs.last()) {
			Subtitle tmp = ListofSubs.retrieve();
			if(tmp.getText().equalsIgnoreCase(str1))
				tmp.setText(str2);
			ListofSubs.update(tmp);
			ListofSubs.findNext();
		}
		Subtitle tmp = ListofSubs.retrieve();
		if(tmp.getText().equalsIgnoreCase(str1))
			tmp.setText(str2);
		ListofSubs.update(tmp);
		
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
