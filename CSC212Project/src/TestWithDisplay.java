
public class TestWithDisplay {

	public static void main(String[] args) throws Exception{
		SubtitleSeqFactory s = new SubtitleSeqFactory();
		SubtitleSeq srt =  s.loadSubtitleSeq("winnie-the-pooh-2011.srt");
		if(srt == null)
			System.out.println("null");
		else {
			List<Subtitle> sList = srt.getSubtitles();
			sList.findFirst();
			while(!sList.last()) {
				System.out.println(sList.retrieve().getText());
				sList.findFirst();
			}
		}
	}
	
}
