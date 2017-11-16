
public class SubtitleSeqC implements SubtitleSeq {
	
	private List<Subtitle> ListofSubs = new LinkedList<Subtitle>() ;
	
	
	@Override
	public void addSubtitle(Subtitle st) {
		if(ListofSubs.empty())
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
		}
	}

	@Override
	public List<Subtitle> getSubtitles(){
		return this.ListofSubs;
	}
	
	@Override
	public Subtitle getSubtitle(Time time) {
		if(this.ListofSubs.empty())
			return null;
		ListofSubs.findFirst();
		while(!ListofSubs.last()){
			if(toMS(ListofSubs.retrieve().getStartTime()) == toMS(time))
				return ListofSubs.retrieve();
			ListofSubs.findNext();
		}
		if(toMS(ListofSubs.retrieve().getStartTime()) == toMS(time))
			return ListofSubs.retrieve();
		
		return null;
	}
	
	@Override
	public List<Subtitle> getSubtitles(Time startTime, Time endTime) {
		List<Subtitle> tmpList;
		SubtitleSeq tmpSeq = new SubtitleSeqC();
		
		ListofSubs.findFirst();
		while(!ListofSubs.last()) {
			if((toMS(startTime)>=toMS(ListofSubs.retrieve().getStartTime()))&&
					(toMS(endTime)<=toMS(ListofSubs.retrieve().getEndTime())))
				tmpSeq.addSubtitle(ListofSubs.retrieve());
			ListofSubs.findNext();
		}
		if((toMS(startTime)>=toMS(ListofSubs.retrieve().getStartTime()))&&
				(toMS(endTime)<=toMS(ListofSubs.retrieve().getEndTime())))
			tmpSeq.addSubtitle(ListofSubs.retrieve());
		tmpList = tmpSeq.getSubtitles();
		return tmpList;
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
		if(this.ListofSubs.empty() )
			return;
		else {
			ListofSubs.findFirst();
			while(!ListofSubs.last()) {
			int mST = toMS(ListofSubs.retrieve().getStartTime());
			int mET = toMS(ListofSubs.retrieve().getEndTime());
			
			mST += offset;
			mET += offset;
			
			if(mST < 0)
				mST = 0;
			if(mET < 0) {
				ListofSubs.remove();
				ListofSubs.findNext();
				continue;
			}	
			
			Time sT = toTime(mST);
			Time eT = toTime(mET);
			
			Subtitle tmp = ListofSubs.retrieve();
			
			tmp.setStartTime(sT);
			tmp.setEndTime(eT);
			
			ListofSubs.update(tmp);
			ListofSubs.findNext();
			}//end of while loop
			int mST = toMS(ListofSubs.retrieve().getStartTime());
			int mET = toMS(ListofSubs.retrieve().getEndTime());
			
			mST += offset;
			mET += offset;
			
			if(mST < 0)
				mST = 0;
			if(mET < 0) {
				ListofSubs.remove();
				return;
			}	
			
			Time sT = toTime(mST);
			Time eT = toTime(mET);
			
			Subtitle tmp = ListofSubs.retrieve();
			
			tmp.setStartTime(sT);
			tmp.setEndTime(eT);
			
			ListofSubs.update(tmp);
		}
	}

	@Override
	public void cut(Time startTime, Time endTime) {
		if(this.ListofSubs.empty())
			return;
		else{
			int offset = (toMS(endTime)-toMS(startTime))*(-1);
			ListofSubs.findFirst();
			while(!ListofSubs.last()){
				while((toMS(startTime)>=toMS(ListofSubs.retrieve().getStartTime()))&&
						(toMS(endTime)<=toMS(ListofSubs.retrieve().getEndTime()))) {
							this.ListofSubs.remove();
							this.ListofSubs.findNext();
				}
				this.ListofSubs.findNext();
			}
				if((toMS(startTime)>=toMS(ListofSubs.retrieve().getStartTime()))&&
						(toMS(endTime)<=toMS(ListofSubs.retrieve().getEndTime()))) {
					this.ListofSubs.remove();
				}
		shift(offset);
		}
	}
		
		private int toMS(Time t){
			return (t.getHH()*3600000 + t.getMM()*60000 + t.getSS()*1000 + t.getMS()) ;
		}
		
		private Time toTime(int ms){
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
			List<Subtitle> lis;
			Subtitle sub = new SubtitleC();
			SubtitleSeq seq = new SubtitleSeqC();
			Time s=new TimeC(),e=new TimeC();;
			String text;
			s.setHH(0);
			s.setMM(0);
			s.setSS(35);
			s.setMS(536);
			sub.setStartTime(s);
			e.setHH(0);
			e.setMM(0);
			e.setSS(37);
			e.setMS(746);
			sub.setEndTime(e);
			sub.setText("[whistling]");
			seq.addSubtitle(sub);
			s.setHH(0);
			s.setMM(1);
			s.setSS(3);
			s.setMS(180);
			sub.setStartTime(s);
			e.setHH(0);
			e.setMM(1);
			e.setSS(5);
			e.setMS(732);
			sub.setEndTime(e);
			sub.setText("[Winnie the Pooh theme song]");
			seq.addSubtitle(sub);
			lis=seq.getSubtitles();
			lis.findFirst();
			System.out.println(lis.retrieve().getText());
			lis.findNext();
			System.out.println(lis.retrieve().getText());
			
		}
}

//d7e9 add
/*	if(ListofSubs.empty())
ListofSubs.insert(st);
else {
Subtitle tmpSub;
int tmpTime = toMS(st.getStartTime());
int curTime;

ListofSubs.findFirst();
while(!ListofSubs.last()) {
	curTime = toMS(ListofSubs.retrieve().getStartTime());
	if(tmpTime<curTime) {
		tmpSub= ListofSubs.retrieve();
		ListofSubs.update(st);
		ListofSubs.insert(tmpSub);
		return;
	}
	ListofSubs.findNext();
}
curTime = toMS(ListofSubs.retrieve().getStartTime());
if(tmpTime<curTime) {
	tmpSub = ListofSubs.retrieve();
	ListofSubs.update(st);
	ListofSubs.insert(tmpSub);
	return;
}

ListofSubs.insert(st);
ListofSubs.findNext();
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
