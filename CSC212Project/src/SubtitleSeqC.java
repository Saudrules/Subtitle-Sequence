
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
	//ADD EMPTY CONDITION
	@Override
	public Subtitle getSubtitle(Time time) {
		ListofSubs.findFirst();
		while(!ListofSubs.last()){
			if(ListofSubs.retrieve().getStartTime().equals(time))
				return ListofSubs.retrieve();
			ListofSubs.findNext();
		}
		if(ListofSubs.retrieve().getStartTime().equals(time))
			return ListofSubs.retrieve();
		
		return null;
	}
	//ADD EMPTY CONDITION
	@Override
	public List<Subtitle> getSubtitles(Time startTime, Time endTime) {
		List<Subtitle> TimeList = new LinkedList<Subtitle>();
		ListofSubs.findFirst();
		while(!ListofSubs.last()){
		 if(ListofSubs.retrieve().getStartTime().equals(startTime))
			 ListofSubs.findNext();
		while(!ListofSubs.retrieve().getEndTime().equals(endTime))
			TimeList.insert(ListofSubs.retrieve());
		}
		return TimeList;
			
	}

	@Override
	public List<Subtitle> getSubtitles(String str) {
	    List<Subtitle> tmp = new LinkedList<Subtitle>();
	    if (ListofSubs.empty()) {
			return tmp;
		}
	    else {
		    ListofSubs.findFirst();

		    while (!ListofSubs.last()) {
				if (ListofSubs.retrieve().getText().equalsIgnoreCase(str)) {
					tmp.insert(ListofSubs.retrieve());
				}
				ListofSubs.findNext();
			}
		    if (ListofSubs.retrieve().getText().equalsIgnoreCase(str)) {
				tmp.insert(ListofSubs.retrieve());
			}
		}
	    return tmp;
		
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

	@Override
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
		if(this.ListofSubs.empty() || (offset<0) )
			return;
		else {
			ListofSubs.findFirst();
			while(!ListofSubs.last()) {
				Subtitle tmp = ListofSubs.retrieve();
				int tmpSTime = tmp.getStartTime().getMS();
				tmpSTime += offset;
				tmp.getStartTime().setMS(tmpSTime);
				int tmpETime = tmp.getEndTime().getMS();
				tmpETime += offset;
				tmp.getStartTime().setMS(tmpETime);
			}
			Subtitle tmp = ListofSubs.retrieve();
			int tmpSTime = tmp.getStartTime().getMS();
			tmpSTime += offset;
			tmp.getStartTime().setMS(tmpSTime);
			int tmpETime = tmp.getEndTime().getMS();
			tmpETime += offset;
			tmp.getStartTime().setMS(tmpETime);
		}
	}

	@Override
	public void cut(Time startTime, Time endTime) {
		// TODO Auto-generated method stub
		
	}

}
