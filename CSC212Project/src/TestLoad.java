import java.io.*;

public class TestLoad {

	public static void main(String[] args) {
		SubtitleSeqC tmpSeq = new SubtitleSeqC();
		SubtitleC tmpSub = new SubtitleC(); 
		TimeC sTime = new TimeC();
		TimeC eTime = new TimeC();
		String index, timeLine, text;
		BufferedReader s =null;
	
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
			TestLoad help = new TestLoad();
			System.out.println(help.isNotCorrupt(sA));
				for(int i=0;i<sA.length;i++) {
					
				 if(sA[i].matches("^\\d+$")) {
						index = sA[i];
					}
					else if(sA[i].matches("^\\d{2}:\\d{2}:\\d{2},\\d{3}.*\\d{2}:\\d{2}:\\d{2},\\d{3}$")) {
						timeLine = sA[i];
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
						
						//parsing and setting time
						//System.out.println(timeLine);
					}
					else if(sA[i].matches(".+")) {
						text =sA[i];
						tmpSub.setText(text);
					}
					else if(sA[i].matches("")) {
						tmpSeq.addSubtitle(tmpSub);
						String wS=sA[i];
						System.out.println(wS);
					}
					

				}
				
			}
			catch(IOException e) {
				e.printStackTrace();;
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
