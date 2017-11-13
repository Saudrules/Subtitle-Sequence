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
		
		}
	}
