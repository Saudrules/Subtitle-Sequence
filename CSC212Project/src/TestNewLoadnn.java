import java.io.*;

public class TestNewLoadnn {

	public static void main(String[] args) throws Exception {
		SubtitleSeq tmpSeq = new SubtitleSeqC();
		Subtitle tmpSub = new SubtitleC(); 
		TimeC sTime = new TimeC();
		TimeC eTime = new TimeC();
		String index, timeLine, text = null;
		BufferedReader s =null;
	
	try {
	s = new BufferedReader(new FileReader("winnie-the-pooh-2011.srt"));
	}
	catch(FileNotFoundException e) {
		System.out.println("FILE NOT FOUND!");
		e.printStackTrace();
		
	}
	StringBuffer sF = new StringBuffer();
	String tL = null;
				
			try {
				while((tL=s.readLine())!=null) {
				sF.append(tL).append("\n");
					
				}
			String strF=sF.toString();
			String sA[]=strF.split("\n\n");
			for(int i = 0 ;i<sA.length;i++) {
				String sB[]=sA[i].split("\n");
				try {
					sTime.setHH(Integer.parseInt(sB[1].charAt(0)+""+sB[1].charAt(1)));
					sTime.setMM(Integer.parseInt(sB[1].charAt(3)+""+sB[1].charAt(4)));
					sTime.setSS(Integer.parseInt(sB[1].charAt(6)+""+sB[1].charAt(7)));
					sTime.setMS(Integer.parseInt(sB[1].charAt(9)+""+sB[1].charAt(10)+""+sB[1].charAt(11)));
					eTime.setHH(Integer.parseInt(sB[1].charAt(17)+""+sB[1].charAt(18)));
					eTime.setMM(Integer.parseInt(sB[1].charAt(20)+""+sB[1].charAt(21)));
					eTime.setSS(Integer.parseInt(sB[1].charAt(23)+""+sB[1].charAt(24)));
					eTime.setMS(Integer.parseInt(sB[1].charAt(26)+""+sB[1].charAt(27)+""+sB[1].charAt(28)));
					}
					catch(NumberFormatException e) {
						System.out.println("PARSING ERROR");
						e.printStackTrace();
					}
					tmpSub.setStartTime(sTime);
					tmpSub.setEndTime(eTime);
					
					if(sB.length==4) {
						text=sB[2]+"\n"+sB[3];
						tmpSub.setText(text);
					}
					else {
						text=sB[2];
					tmpSub.setText(text);
					}		
					tmpSeq.addSubtitle(tmpSub);
				}
				
			s.close();
			}
			catch(IOException e) {
				System.out.println("io e");
				e.printStackTrace();
			}
			
			List<Subtitle> tmpList = tmpSeq.getSubtitles();
			tmpList.findFirst();
			while(!tmpList.last()) {
				int timePointer=0;
				while(toMS(tmpList.retrieve().getStartTime())>=timePointer) {
					System.out.println(tmpList.retrieve().getText());
					System.out.println();
					timePointer+=toMS(tmpList.retrieve().getEndTime());
				}
				tmpList.findNext();
			}
	}
	private static int toMS(Time t){
		return (t.getHH()*3600000 + t.getMM()*60000 + t.getSS()*1000 + t.getMS()) ;
	}
}
