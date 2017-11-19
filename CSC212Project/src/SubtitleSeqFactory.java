	import java.io.*;
	import java.util.*;
	import java.nio.file.*;
	import java.lang.*;
	public class SubtitleSeqFactory {
		
		// Return an empty subtitles sequence 
		public static SubtitleSeq getSubtitleSeq() {
			
			SubtitleSeqC emptySubSeq = new SubtitleSeqC();
		
			return emptySubSeq;
		
		}

		// Load a subtitle sequence from an SRT file. If the file does not exist or
		// is corrupted (incorrect format), null is returned.
		public static SubtitleSeq loadSubtitleSeq(String fileName) {
			
			if(!fileName.toLowerCase().contains(".srt")) {
				return null;
			}
			
			SubtitleSeq tmpSeq = new SubtitleSeqC();
			BufferedReader buffer =null;
		
		try {
		buffer = new BufferedReader(new FileReader(fileName));
		}
		catch(FileNotFoundException e) {
			return null;
			
		}
		StringBuffer stringBuffer = new StringBuffer();
		String line = null;
					
				try {
					while((line=buffer.readLine())!=null) {
					stringBuffer.append(line).append("\n");
						
					}
				String toStringBuffer = stringBuffer.toString();
				String subsArray[] = toStringBuffer.split("\n\n");
				for(int i = 0 ;i<subsArray.length;i++) {
					Subtitle tmpSub = new SubtitleC(); 
					TimeC startTime = new TimeC();
					TimeC endTime = new TimeC();
					String text = null;

					String linesArray[]=subsArray[i].split("\n");
					try {
						startTime.setHH(Integer.parseInt(linesArray[1].charAt(0)+""+linesArray[1].charAt(1)));
						startTime.setMM(Integer.parseInt(linesArray[1].charAt(3)+""+linesArray[1].charAt(4)));
						startTime.setSS(Integer.parseInt(linesArray[1].charAt(6)+""+linesArray[1].charAt(7)));
						startTime.setMS(Integer.parseInt(linesArray[1].charAt(9)+""+linesArray[1].charAt(10)+""+linesArray[1].charAt(11)));
						endTime.setHH(Integer.parseInt(linesArray[1].charAt(17)+""+linesArray[1].charAt(18)));
						endTime.setMM(Integer.parseInt(linesArray[1].charAt(20)+""+linesArray[1].charAt(21)));
						endTime.setSS(Integer.parseInt(linesArray[1].charAt(23)+""+linesArray[1].charAt(24)));
						endTime.setMS(Integer.parseInt(linesArray[1].charAt(26)+""+linesArray[1].charAt(27)+""+linesArray[1].charAt(28)));
						}
						catch(NumberFormatException e) {
							return null;
						}
						tmpSub.setStartTime(startTime);
						startTime = null;
						tmpSub.setEndTime(endTime);
						endTime = null;
						
						if(linesArray.length==4) {
							text=linesArray[2]+"\n"+linesArray[3];
							tmpSub.setText(text);
							text = null;
						}
						else {
							text=linesArray[2];
							tmpSub.setText(text);
							text = null;
						}		
						tmpSeq.addSubtitle(tmpSub);
						tmpSub = null;
					}
				buffer.close();
					
				}
				catch(IOException e) {
					return null;
				}
			
			return tmpSeq;
		}
		
	}
