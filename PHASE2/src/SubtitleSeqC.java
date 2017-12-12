public class SubtitleSeqC implements SubtitleSeq {
	
	
	private SortedMap<TimeInterval,Subtitle> sortedSubs = new SortedBST<TimeInterval,Subtitle>() ;
	
	
	@Override
	public void addSubtitle(Subtitle st) {
		if(sortedSubs.empty())
			sortedSubs.insert(new TimeInterval(st), st);
		
		else if(!isOverlapping(st)) {
			Subtitle tmpSub;
			TimeInterval tmpTime = new TimeInterval(st);
			TimeInterval curTime;

			sortedSubs.findFirst();
			while(!sortedSubs.last()) {
				curTime = new TimeInterval(sortedSubs.retrieve().second);
				if(tmpTime.compareTo(curTime) < 0) {
					tmpSub = sortedSubs.retrieve().second;
					sortedSubs.update(st);
					sortedSubs.insert(new TimeInterval(tmpSub), tmpSub);
					return;
				}
				sortedSubs.findNext();
			}
			curTime = new TimeInterval(sortedSubs.retrieve().second);
			if(tmpTime.compareTo(curTime) < 0) {
				tmpSub = sortedSubs.retrieve().second;
				sortedSubs.update(st);
				sortedSubs.insert(new TimeInterval(tmpSub), tmpSub);
				return;
			}

			sortedSubs.insert(new TimeInterval(st), st);
			sortedSubs.findNext();
		}
		
		else 
			return;
	}

	@Override
	public List<Subtitle> getSubtitles(){
		List<Subtitle> tmp = new LinkedList<Subtitle>();
		
		sortedSubs.findFirst();
		while(!sortedSubs.last()) {
			addSubtitle(sortedSubs.retrieve().second, tmp);
			sortedSubs.findNext();
		}
		addSubtitle(sortedSubs.retrieve().second, tmp);
		
		return tmp;
	}
	
	@Override
	public Subtitle getSubtitle(Time time) {
		if(this.sortedSubs.empty())
			return null;
		sortedSubs.findFirst();
		while(!sortedSubs.last()){
			if(toMS(sortedSubs.retrieve().second.getStartTime()) <= toMS(time) &&
					toMS(sortedSubs.retrieve().second.getEndTime()) >= toMS(time))
				return sortedSubs.retrieve().second;
			sortedSubs.findNext();
		}
		if(toMS(sortedSubs.retrieve().second.getStartTime()) <= toMS(time) &&
				toMS(sortedSubs.retrieve().second.getEndTime()) >= toMS(time))
			return sortedSubs.retrieve().second;
		
		return null;
	}
	
	@Override
	public int nbNodesInSearchPath(Time time) {
		int cpt = 0;
		sortedSubs.findFirst();
		while(!sortedSubs.last()){
			cpt++;
			if(toMS(sortedSubs.retrieve().second.getStartTime()) <= toMS(time) &&
					toMS(sortedSubs.retrieve().second.getEndTime()) >= toMS(time))
						return cpt;
			sortedSubs.findNext();
		}
		
		cpt++;
		return cpt;
	}
	
	@Override
	public List<Subtitle> getSubtitles(Time startTime, Time endTime) {
		
	}

	
	@Override
	public void shift(int offset) {
		
	}
		
	private static int toMS(Time t){
			return (t.getHH()*3600000 + t.getMM()*60000 + t.getSS()*1000 + t.getMS()) ;
	}
		
		private static Time toTime(int ms){
			int RealTimeHH = ((ms/3600000)%24) ;
			int RealTimeMM = ((ms/60000)%60) ;
			int RealTimeSS = ((ms/1000)%60) ;
			int RealTimeMS = ms%1000 ;
			Time R = new TimeC();
			R.setHH(RealTimeHH);
			R.setMM(RealTimeMM);
			R.setSS(RealTimeSS);
			R.setMS(RealTimeMS);
			return R;
		}
		//Try compareTo later
		private boolean isOverlapping(Subtitle st) {
			sortedSubs.findFirst();
			while(!sortedSubs.last()) {
			Pair<TimeInterval, Subtitle> sub = sortedSubs.retrieve();
			Time end = sub.second.getEndTime();
			Time start = sub.second.getStartTime();
				if(toMS(end)>=toMS(st.getStartTime())&&
						(toMS(start)<=toMS(st.getEndTime())))
					return true;
				sortedSubs.findNext();
			}
			Pair<TimeInterval, Subtitle> sub = sortedSubs.retrieve();
			Time end = sub.second.getEndTime();
			Time start = sub.second.getStartTime();
			if(toMS(end)>=toMS(st.getStartTime())&&
					(toMS(start)<=toMS(st.getEndTime())))
				return true;
			return false;
		}
		
		private void addSubtitle(Subtitle st, List<Subtitle> listOfSubs) {
			
			if(listOfSubs.empty())
				listOfSubs.insert(st);
				else if(!isOverlapping(st)) {
				Subtitle tmpSub;
				int tmpTime = toMS(st.getStartTime());
				int curTime;

				listOfSubs.findFirst();
				while(!listOfSubs.last()) {
					curTime = toMS(listOfSubs.retrieve().getStartTime());
					if(tmpTime<curTime) {
						tmpSub = listOfSubs.retrieve();
						listOfSubs.update(st);
						listOfSubs.insert(tmpSub);
						return;
					}
					listOfSubs.findNext();
				}
				curTime = toMS(listOfSubs.retrieve().getStartTime());
				if(tmpTime<curTime) {
					tmpSub = listOfSubs.retrieve();
					listOfSubs.update(st);
					listOfSubs.insert(tmpSub);
					return;
				}

				listOfSubs.insert(st);
				listOfSubs.findNext();
				}
				else 
					return;
		}
		

}
