public class SubtitleSeqC implements SubtitleSeq {
	
	
	private List<Subtitle> listOfSubs = new LinkedList<Subtitle>() ;
	
	
	@Override
	public void addSubtitle(Subtitle st) {
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
					tmpSub= listOfSubs.retrieve();
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

	@Override
	public List<Subtitle> getSubtitles(){
		return this.listOfSubs;
	}
	
	@Override
	public Subtitle getSubtitle(Time time) {
		if(this.listOfSubs.empty())
			return null;
		listOfSubs.findFirst();
		while(!listOfSubs.last()){
			if(toMS(listOfSubs.retrieve().getStartTime()) <= toMS(time) &&
					toMS(listOfSubs.retrieve().getEndTime()) >= toMS(time))
				return listOfSubs.retrieve();
			listOfSubs.findNext();
		}
		if(toMS(listOfSubs.retrieve().getStartTime()) <= toMS(time) &&
				toMS(listOfSubs.retrieve().getEndTime()) >= toMS(time))
			return listOfSubs.retrieve();
		
		return null;
	}
	
	@Override
	public List<Subtitle> getSubtitles(Time startTime, Time endTime) {
		List<Subtitle> tmpList =new LinkedList<Subtitle>();
		
		
		listOfSubs.findFirst();
		while(!listOfSubs.last()) {
			if(toMS(listOfSubs.retrieve().getEndTime())>=toMS(startTime)&&
					(toMS(listOfSubs.retrieve().getStartTime())<=toMS(endTime)))
				tmpList.insert(listOfSubs.retrieve());
			
			listOfSubs.findNext();
		}
		if(toMS(listOfSubs.retrieve().getEndTime())>=toMS(startTime)&&
				(toMS(listOfSubs.retrieve().getStartTime())<=toMS(endTime)))
			tmpList.insert(listOfSubs.retrieve());
		
		return tmpList;
	}

	
	@Override
	public void shift(int offset) {
		if(this.listOfSubs.empty() )
			return;
		else {
			listOfSubs.findFirst();
			while(!listOfSubs.last()) {
				int msStartTime = toMS(listOfSubs.retrieve().getStartTime());
				int msEndTime = toMS(listOfSubs.retrieve().getEndTime());
			
				msStartTime += offset;
				msEndTime += offset;
			
				if(msStartTime < 0)
					msStartTime = 0;
				if(msEndTime < 0) 
					msEndTime = 0;	
			
				Time startTime = toTime(msStartTime);
				Time endTime = toTime(msEndTime);
			
				Subtitle tmp = listOfSubs.retrieve();
			
				tmp.setStartTime(startTime);
				tmp.setEndTime(endTime);
			
				
				listOfSubs.update(tmp);
				if(msEndTime == 0)
					listOfSubs.remove();
				else
					listOfSubs.findNext();
			}
			int msStartTime = toMS(listOfSubs.retrieve().getStartTime());
			int msEndTime = toMS(listOfSubs.retrieve().getEndTime());
		
			msStartTime += offset;
			msEndTime += offset;
		
			if(msStartTime < 0)
				msStartTime = 0;
			if(msEndTime < 0) 
				msEndTime = 0;
				
			Time startTime = toTime(msStartTime);
			Time endTime = toTime(msEndTime);
		
			Subtitle tmp = listOfSubs.retrieve();
		
			tmp.setStartTime(startTime);
			tmp.setEndTime(endTime);
			if(msEndTime == 0)
				listOfSubs.remove();
			else	
				listOfSubs.update(tmp);
		}
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
		
		private boolean isOverlapping(Subtitle st) {
			listOfSubs.findFirst();
			while(!listOfSubs.last()) {
				if(toMS(listOfSubs.retrieve().getEndTime())>=toMS(st.getStartTime())&&
						(toMS(listOfSubs.retrieve().getStartTime())<=toMS(st.getEndTime())))
					return true;
				listOfSubs.findNext();
			}
			if(toMS(listOfSubs.retrieve().getEndTime())>=toMS(st.getStartTime())&&
					(toMS(listOfSubs.retrieve().getStartTime())<=toMS(st.getEndTime())))
				return true;
			return false;
		}

		@Override
		public int nbNodesInSearchPath(Time time) {
			// TODO Auto-generated method stub
			return 0;
		}

}
