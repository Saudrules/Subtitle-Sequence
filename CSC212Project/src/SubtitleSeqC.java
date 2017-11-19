
public class SubtitleSeqC implements SubtitleSeq {
	
	private List<Subtitle> listOfSubs = new LinkedList<Subtitle>() ;
	
	
	@Override
	public void addSubtitle(Subtitle st) {
		if(listOfSubs.empty())
			listOfSubs.insert(st);
			else {
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
		List<Subtitle> tmpList;
		SubtitleSeq tmpSeq = new SubtitleSeqC();
		
		listOfSubs.findFirst();
		while(!listOfSubs.last()) {
			if((toMS(startTime)>=toMS(listOfSubs.retrieve().getStartTime()))&&
					(toMS(endTime)<=toMS(listOfSubs.retrieve().getEndTime())))
				tmpSeq.addSubtitle(listOfSubs.retrieve());
			listOfSubs.findNext();
		}
		if((toMS(startTime)>=toMS(listOfSubs.retrieve().getStartTime()))&&
				(toMS(endTime)<=toMS(listOfSubs.retrieve().getEndTime())))
			tmpSeq.addSubtitle(listOfSubs.retrieve());
		tmpList = tmpSeq.getSubtitles();
		return tmpList;
	}

	@Override
	public List<Subtitle> getSubtitles(String str) {
	    SubtitleSeq tmpSeq = new SubtitleSeqC();
	    if (listOfSubs.empty()) {
			return null;
		}
	    else {
		    listOfSubs.findFirst();

		    while (!listOfSubs.last()) {
				if (listOfSubs.retrieve().getText().contains(str)) {
					tmpSeq.addSubtitle(listOfSubs.retrieve());
				}
				listOfSubs.findNext();
			}
		    if (listOfSubs.retrieve().getText().contains(str)) {
				tmpSeq.addSubtitle(listOfSubs.retrieve());

			}
		}
	    return tmpSeq.getSubtitles();
		
	}


	@Override
	public void remove(String str) {
		if(this.listOfSubs.empty())
			return;
		listOfSubs.findFirst();
		while(!listOfSubs.last()) {
			if(listOfSubs.retrieve().getText().contains(str))
				listOfSubs.remove();
			listOfSubs.findNext();
		}
		if(listOfSubs.retrieve().getText().contains(str))
			listOfSubs.remove();
	}

	@Override
	public void replace(String str1, String str2) {
		if(this.listOfSubs.empty())
			return;
		listOfSubs.findFirst();
		while(!listOfSubs.last()) {
			Subtitle tmp = listOfSubs.retrieve();
			if(tmp.getText().equalsIgnoreCase(str1))
				tmp.setText(str2);
			listOfSubs.update(tmp);
			listOfSubs.findNext();
		}
		Subtitle tmp = listOfSubs.retrieve();
		if(tmp.getText().equalsIgnoreCase(str1))
			tmp.setText(str2);
		listOfSubs.update(tmp);
		
	}
	@Override
	public void shift(int offset) {
		if(this.listOfSubs.empty() )
			return;
		else {
			listOfSubs.findFirst();
			while(!listOfSubs.last()) {
			int mST = toMS(listOfSubs.retrieve().getStartTime());
			int mET = toMS(listOfSubs.retrieve().getEndTime());
			
			mST += offset;
			mET += offset;
			
			if(mST < 0)
				mST = 0;
			if(mET < 0) {
				listOfSubs.remove();
				listOfSubs.findNext();
				continue;
			}	
			
			Time sT = toTime(mST);
			Time eT = toTime(mET);
			
			Subtitle tmp = listOfSubs.retrieve();
			
			tmp.setStartTime(sT);
			tmp.setEndTime(eT);
			
			listOfSubs.update(tmp);
			listOfSubs.findNext();
			}//end of while loop
			int mST = toMS(listOfSubs.retrieve().getStartTime());
			int mET = toMS(listOfSubs.retrieve().getEndTime());
			
			mST += offset;
			mET += offset;
			
			if(mST < 0)
				mST = 0;
			if(mET < 0) {
				listOfSubs.remove();
				return;
			}	
			
			Time sT = toTime(mST);
			Time eT = toTime(mET);
			
			Subtitle tmp = listOfSubs.retrieve();
			
			tmp.setStartTime(sT);
			tmp.setEndTime(eT);
			
			listOfSubs.update(tmp);
		}
	}

	@Override
	public void cut(Time startTime, Time endTime) {
		if(this.listOfSubs.empty())
			return;
		else{
			int offset = (toMS(endTime)-toMS(startTime))*(-1);
			listOfSubs.findFirst();
			while(!listOfSubs.last()){
				while((toMS(startTime)>=toMS(listOfSubs.retrieve().getStartTime()))&&
						(toMS(endTime)<=toMS(listOfSubs.retrieve().getEndTime()))) {
							this.listOfSubs.remove();
							this.listOfSubs.findNext();
				}
				this.listOfSubs.findNext();
			}
				if((toMS(startTime)>=toMS(listOfSubs.retrieve().getStartTime()))&&
						(toMS(endTime)<=toMS(listOfSubs.retrieve().getEndTime()))) {
					this.listOfSubs.remove();
				}
		shift(offset);
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
		
		public static void main(String[] args) throws Exception {
			SubtitleSeq seq = SubtitleSeqFactory.loadSubtitleSeq("winnie-the-pooh-2011.srt");
			List<Subtitle> l = seq.getSubtitles();
			Time tmp=new TimeC();
			Time tmp1=new TimeC();
			seq.replace("[whistling]", "Hitler");
			  l.findFirst();
			  while(!l.last()) {
				System.out.println(l.retrieve().getText());
				System.out.println();
				l.findNext();
			}
			System.out.println(l.retrieve().getText());
		}	
}



//our old add
/*if(ListofSubs.empty())
ListofSubs.insert(st);
else {
	ListofSubs.findFirst();
	while(!ListofSubs.last()) {
		if(toMS(st.getStartTime()) > toMS(ListofSubs.retrieve().getEndTime())) {
			ListofSubs.insert(st);
			return;
		}
		else if(toMS(st.getStartTime()) < toMS(ListofSubs.retrieve().getEndTime())) {
			Subtitle tmpSub= ListofSubs.retrieve();
			ListofSubs.update(st);
			ListofSubs.insert(tmpSub);
			return;
		}
		ListofSubs.findNext();
	}
	if(toMS(st.getStartTime()) > toMS(ListofSubs.retrieve().getEndTime())) {
		ListofSubs.insert(st);
	}
	else if(toMS(st.getStartTime()) < toMS(ListofSubs.retrieve().getEndTime())) {
		Subtitle tmpSub= ListofSubs.retrieve();
		ListofSubs.update(st);
		ListofSubs.insert(tmpSub);
	}
}*/

//pre get by start and end
/*if(this.ListofSubs.empty())
return TimeList;
else {	
ListofSubs.findFirst();
while(!ListofSubs.last()){
	if(ListofSubs.retrieve().getStartTime().equals(startTime)) {
	while(!ListofSubs.retrieve().getEndTime().equals(endTime)) {
		TimeList.insert(ListofSubs.retrieve());
		ListofSubs.findNext();
	}
	if(ListofSubs.retrieve().getEndTime().equals(endTime)) {
		TimeList.insert(ListofSubs.retrieve());
		ListofSubs.findNext();
	}
   }
	ListofSubs.findNext();
}
return TimeList;
}	*/
