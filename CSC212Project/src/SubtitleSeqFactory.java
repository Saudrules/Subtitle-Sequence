	import java.io.*;
	import java.util.*;
	import java.nio.file.*;
	public class SubtitleSeqFactory {
		
		// Return an empty subtitles sequence 
		public static SubtitleSeq getSubtitleSeq() {
		SubtitleSeqC emptySubSeq = new SubtitleSeqC();
		return emptySubSeq;
		}

		// Load a subtitle sequence from an SRT file. If the file does not exist or
		// is corrupted (incorrect format), null is returned.
		public static SubtitleSeq loadSubtitleSeq(String fileName) {
			SubtitleSeq tmpSeq = new SubtitleSeqC();
			Subtitle tmpSub = new SubtitleC(); 
			TimeC sTime = new TimeC();
			TimeC eTime = new TimeC();
			String index, timeLine, text = null;
			BufferedReader s =null;
		
		try {
		s = new BufferedReader(new FileReader(fileName));
		}
		catch(FileNotFoundException e) {
			System.out.println("FILE NOT FOUND!");
			return null;
			
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
							return null;
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
					
					
				}
				catch(IOException e) {
					System.out.println("io e");
					return null;
				}
			
			return tmpSeq;
		}
		private static boolean isNotCorrupt(String []aS) {
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
