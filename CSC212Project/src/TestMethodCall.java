	import java.io.*;
	import java.util.*;
	import java.nio.file.*;
public class TestMethodCall {
	public static void main(String[] args) throws Exception {
		SubtitleSeq seq = SubtitleSeqFactory.loadSubtitleSeq("winnie-the-pooh-2011.srt");
		List<Subtitle> l = seq.getSubtitles();
		l.findFirst();
		while(!l.last()) {
			System.out.println(l.retrieve().getText());
			System.out.println();
			l.findNext();
		}
		System.out.println(l.retrieve().getText());
	}
}
