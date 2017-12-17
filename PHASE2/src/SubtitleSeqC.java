public class SubtitleSeqC implements SubtitleSeq {

	private SortedMap<TimeInterval, Subtitle> sortedSubs = new SortedBST<TimeInterval, Subtitle>();
	
	@Override
	public void addSubtitle(Subtitle st) {
		sortedSubs.insert(new TimeInterval(st.getStartTime(),st.getEndTime()),st);
	}
	@Override
	public List<Subtitle> getSubtitles() {
		List<Subtitle> tmp = new LinkedList<Subtitle>();

		sortedSubs.findFirst();
		while (!sortedSubs.last()) {
			tmp.insert(sortedSubs.retrieve().second);
			sortedSubs.findNext();
		}
		tmp.insert(sortedSubs.retrieve().second);

		return tmp;
	}

	@Override
	public Subtitle getSubtitle(Time time) {
		
		if(sortedSubs.find(new TimeInterval(time,time)))
			return sortedSubs.retrieve().second;
		return null;
	}

	@Override
	public int nbNodesInSearchPath(Time time) {
		if(sortedSubs.empty())
			return 0;
		else {
			TimeInterval tmp = new TimeInterval(time, time);
			return sortedSubs.nbNodesInSearchPath(tmp);
		}
	}

	@Override
	public List<Subtitle> getSubtitles(Time startTime, Time endTime) {
		List<Subtitle> tmpList = new LinkedList<Subtitle>();

		sortedSubs.findFirst();
		while (!sortedSubs.last()) {
			if (toMS(sortedSubs.retrieve().second.getEndTime()) >= toMS(startTime)
					&& (toMS(sortedSubs.retrieve().second.getStartTime()) <= toMS(endTime)))
				tmpList.insert(sortedSubs.retrieve().second);

			sortedSubs.findNext();
		}
		if (toMS(sortedSubs.retrieve().second.getEndTime()) >= toMS(startTime)
				&& (toMS(sortedSubs.retrieve().second.getStartTime()) <= toMS(endTime)))
			tmpList.insert(sortedSubs.retrieve().second);

		return tmpList;
	}

	@Override
	public void shift(int offset) {
		if(this.sortedSubs.empty() )
			return;
		else {
			sortedSubs.findFirst();
			while(!sortedSubs.last()) {
				int msStartTime = toMS(sortedSubs.retrieve().second.getStartTime());
				int msEndTime = toMS(sortedSubs.retrieve().second.getEndTime());
			
				msStartTime += offset;
				msEndTime += offset;
			
				if(msStartTime < 0)
					msStartTime = 0;
				if(msEndTime < 0) 
					msEndTime = 0;	
			
				Time startTime = toTime(msStartTime);
				Time endTime = toTime(msEndTime);
			
				Subtitle tmp = sortedSubs.retrieve().second;
			
				tmp.setStartTime(startTime);
				tmp.setEndTime(endTime);
			
				
				sortedSubs.update(tmp);
				if(msEndTime == 0)
					sortedSubs.remove();
				else
					sortedSubs.findNext();
			}
			int msStartTime = toMS(sortedSubs.retrieve().second.getStartTime());
			int msEndTime = toMS(sortedSubs.retrieve().second.getEndTime());
		
			msStartTime += offset;
			msEndTime += offset;
		
			if(msStartTime < 0)
				msStartTime = 0;
			if(msEndTime < 0) 
				msEndTime = 0;
				
			Time startTime = toTime(msStartTime);
			Time endTime = toTime(msEndTime);
		
			Subtitle tmp = sortedSubs.retrieve().second;
		
			tmp.setStartTime(startTime);
			tmp.setEndTime(endTime);
			if(msEndTime == 0)
				sortedSubs.remove();
			else	
				sortedSubs.update(tmp);
		}
	}

	private static int toMS(Time t) {
		return (t.getHH() * 3600000 + t.getMM() * 60000 + t.getSS() * 1000 + t.getMS());
	}

	private static Time toTime(int ms) {
		int RealTimeHH = ((ms / 3600000) % 24);
		int RealTimeMM = ((ms / 60000) % 60);
		int RealTimeSS = ((ms / 1000) % 60);
		int RealTimeMS = ms % 1000;
		Time R = new TimeC();
		R.setHH(RealTimeHH);
		R.setMM(RealTimeMM);
		R.setSS(RealTimeSS);
		R.setMS(RealTimeMS);
		return R;
	}

	// Try compareTo later
	private boolean isOverlapping(Subtitle st) {
		sortedSubs.findFirst();
		while (!sortedSubs.last()) {
			Pair<TimeInterval, Subtitle> sub = sortedSubs.retrieve();
			Time end = sub.second.getEndTime();
			Time start = sub.second.getStartTime();
			if (toMS(end) >= toMS(st.getStartTime()) && (toMS(start) <= toMS(st.getEndTime())))
				return true;
			sortedSubs.findNext();
		}
		Pair<TimeInterval, Subtitle> sub = sortedSubs.retrieve();
		Time end = sub.second.getEndTime();
		Time start = sub.second.getStartTime();
		if (toMS(end) >= toMS(st.getStartTime()) && (toMS(start) <= toMS(st.getEndTime())))
			return true;
		return false;
	}
}
