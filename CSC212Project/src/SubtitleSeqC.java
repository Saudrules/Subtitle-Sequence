
public class SubtitleSeqC implements SubtitleSeq {
	
	private List<Subtitle> ListofSubs = new LinkedList<Subtitle>() ;
	
	
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
		if(this.ListofSubs.empty())
			return null;
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
	
	@Override
	public List<Subtitle> getSubtitles(Time startTime, Time endTime) {
		List<Subtitle> TimeList = new LinkedList<Subtitle>();
		if(this.ListofSubs.empty())
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
		}	
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
	//Add conditions for when offset increases MS to 1000+ or SS to 60 etc...
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
		if(this.ListofSubs.empty())
			return;
		else {
			int size=0;
			this.ListofSubs.findFirst();
			while(!this.ListofSubs.last()) {
				if(this.ListofSubs.retrieve().getStartTime().equals(startTime)) {
					while(!this.ListofSubs.last()) {
						size++;
						this.ListofSubs.findNext();
					}
				}
				this.ListofSubs.findNext();
			}
			Subtitle[]tmpSubs= new Subtitle[size];
			this.ListofSubs.findFirst();
			while(!this.ListofSubs.last()) {
				if(this.ListofSubs.retrieve().getStartTime().equals(startTime)) {
					for(int i = 0; i<=size-1;i++) {
						tmpSubs[i] = this.ListofSubs.retrieve();
						this.ListofSubs.findNext();
					}
					
				}
				this.ListofSubs.findNext();
			}
			this.ListofSubs.findFirst();
			while(!this.ListofSubs.last()) {
				if(this.ListofSubs.retrieve().getStartTime().equals(startTime)) {
					while(!this.ListofSubs.retrieve().getEndTime().equals(endTime)) {
						this.ListofSubs.remove();
						this.ListofSubs.findNext();
					}
					if(this.ListofSubs.retrieve().getEndTime().equals(endTime)) {
						this.ListofSubs.remove();
						this.ListofSubs.findNext();
					}
					for(int i = 0; i<=size-1;i++) {
						Time tmpSTime = tmpSubs[i].getStartTime();
						Time tmpETime = tmpSubs[i].getEndTime();
						Subtitle tmpNode= this.ListofSubs.retrieve();
						tmpNode.setStartTime(tmpSTime);
						tmpNode.setEndTime(tmpETime);
						this.ListofSubs.update(tmpNode);
						this.ListofSubs.findNext();
					}
				}
				this.ListofSubs.findNext();
			}
		}
	}
		
		private int ConToMi(Time t){
			int total = t.getHH()*3600000 + t.getMM()*60000 + t.getSS()*1000 + t.getMS(); 
			
			return total ;
		}
		private Time BackToTime(int ms){
			
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

}
/* 
//Our Previous cut method
public void cut(Time startTime, Time endTime) {
	if(this.ListofSubs.empty())
		return;
	else {
		this.ListofSubs.findFirst();
		while(!this.ListofSubs.last()) {
			if(this.ListofSubs.retrieve().getStartTime().equals(startTime)) {
				while(!this.ListofSubs.retrieve().getEndTime().equals(endTime)) {
					this.ListofSubs.remove();
					this.ListofSubs.findNext();
				}
			}	
			if(this.ListofSubs.retrieve().getEndTime().equals(endTime)) {
				this.ListofSubs.remove();
				this.ListofSubs.findNext();
			}
			this.ListofSubs.findNext();
		}
	}
} */
