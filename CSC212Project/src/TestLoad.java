import java.io.*;

public class TestLoad {

	public static void main(String[] args) {
		
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
