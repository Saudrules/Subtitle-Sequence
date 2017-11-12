
public class TimeC implements Time {
	private int HH;
	private int MM;
	private int SS;
	private int MS;
	
	@Override
	public int getHH() {
		// TODO Auto-generated method stub
		return HH;
	}

	@Override
	public int getMM() {
		// TODO Auto-generated method stub
		return MM;
	}

	@Override
	public int getSS() {
		// TODO Auto-generated method stub
		return SS;
	}

	@Override
	public int getMS() {
		// TODO Auto-generated method stub
		return MS;
	}

	@Override
	public void setHH(int hh) {
		this.HH = hh;
		
	}

	@Override
	public void setMM(int mm) {
		this.MM = mm;
		
	}

	@Override
	public void setSS(int ss) {
		this.SS = ss;
		
	}

	@Override
	public void setMS(int ms) {
		this.MS = ms;
		
	}

}
