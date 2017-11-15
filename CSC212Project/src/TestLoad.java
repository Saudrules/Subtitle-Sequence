import java.io.*;

public class TestLoad {

	public static void main(String[] args) {
	/*	SubtitleSeqC seq = new SubtitleSeqC();
		SubtitleC sub = new SubtitleC();
		
		seq = (SubtitleSeqC) SubtitleSeqFactory.loadSubtitleSeq("winnie-the-pooh-2011.srt");
		seq.getSubtitles().findFirst();
		while(!seq.getSubtitles().last()) {
			System.out.println(seq.getSubtitles().retrieve().getText());
		seq.getSubtitles().findNext();
		}
		System.out.println(seq.getSubtitles().retrieve().getText());
*/
	
		SubtitleSeqC tmpSeq = new SubtitleSeqC();
		SubtitleC tmpSub = new SubtitleC(); 
		TimeC sTime = new TimeC();
		TimeC eTime = new TimeC();
		BufferedReader s =null;
		String index, timeLine, text = null;

		
	
	try {
	s = new BufferedReader(new FileReader("winnie-the-pooh-2011.srt"));
	}
	catch(FileNotFoundException e) {
		e.printStackTrace();
		
	}
	StringBuffer sF = new StringBuffer();
	String tL = null;
				
			try {
				while((tL=s.readLine())!=null) {
				sF.append(tL).append("\n");
					
				}
			String strF=sF.toString();
			String sA[]=strF.split("\n");
			//System.out.println(sA[10]+"\n"+sA[11]);
				for(int i=0;i<sA.length;i++) {
					
				 if(sA[i].matches("^\\d+$")) {
						index = sA[i];
					}
					else if(sA[i].matches("^\\d{2}:\\d{2}:\\d{2},\\d{3}.*\\d{2}:\\d{2}:\\d{2},\\d{3}$")) {
						timeLine = sA[i];
						int sHH,sMM,sSS,sMS,eHH,eMM,eSS,eMS;
						sHH=Integer.parseInt(timeLine.charAt(0)+""+timeLine.charAt(1));
						sMM=Integer.parseInt(timeLine.charAt(3)+""+timeLine.charAt(4));
						sSS=Integer.parseInt(timeLine.charAt(6)+""+timeLine.charAt(7));
						sMS=Integer.parseInt(timeLine.charAt(9)+""+timeLine.charAt(10)+""+timeLine.charAt(11));
						eHH=Integer.parseInt(timeLine.charAt(17)+""+timeLine.charAt(18));
						eMM=Integer.parseInt(timeLine.charAt(20)+""+timeLine.charAt(21));
						eSS=Integer.parseInt(timeLine.charAt(23)+""+timeLine.charAt(24));
						eMS=Integer.parseInt(timeLine.charAt(26)+""+timeLine.charAt(27)+""+timeLine.charAt(28));
						sTime.setHH(Integer.parseInt(timeLine.charAt(0)+""+timeLine.charAt(1)));
						sTime.setMM(Integer.parseInt(timeLine.charAt(3)+""+timeLine.charAt(4)));
						sTime.setSS(Integer.parseInt(timeLine.charAt(6)+""+timeLine.charAt(7)));
						sTime.setMS(Integer.parseInt(timeLine.charAt(9)+""+timeLine.charAt(10)+""+timeLine.charAt(11)));
						eTime.setHH(Integer.parseInt(timeLine.charAt(17)+""+timeLine.charAt(18)));
						eTime.setMM(Integer.parseInt(timeLine.charAt(20)+""+timeLine.charAt(21)));
						eTime.setSS(Integer.parseInt(timeLine.charAt(23)+""+timeLine.charAt(24)));
						eTime.setMS(Integer.parseInt(timeLine.charAt(26)+""+timeLine.charAt(27)+""+timeLine.charAt(28)));
						tmpSub.setStartTime(sTime);
						tmpSub.setEndTime(eTime);
						System.out.println(sHH+":"+sMM+":"+sSS+","+sMS+"-"+eHH+":"+eMM+":"+eSS+","+eMS);
						
						//parsing and setting time
						//System.out.println(timeLine);
					}
					else if(sA[i].matches(".+")) {
						
						text =sA[i];
						try {
						if(sA[i+1].matches(".+") && !(sA[i].matches(""))) {
							text +=sA[i+1];
							}
						}
						catch(ArrayIndexOutOfBoundsException e) {
							break;
						}
						tmpSub.setText(text);
						//System.out.println(text);
						
					}
					else if(sA[i].matches("")) {
						tmpSeq.addSubtitle(tmpSub);
						System.out.println(tmpSub.getText());
						String wS=sA[i];
						System.out.println(wS);
					}
					

				}
			
				
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		
	
	
		
}

	private boolean isNotCorrupt(String []aS) {
		for(int i=0;i<aS.length;i++) {
			
			 if(aS[i].matches("^\\d+$")) {
					continue;
				}
				else if(aS[i].matches("^\\d{2}:\\d{2}:\\d{2},\\d{3}.*\\d{2}:\\d{2}:\\d{2},\\d{3}$")) {
					continue;
				}
				else if(aS[i].matches(".+")) {
					continue;
				}
				else if(aS[i].matches("")) {
					continue;
				}
				else 
					return false;
			}
			return true;
	}

}
