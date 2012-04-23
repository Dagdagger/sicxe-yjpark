// 
public class LOCCTR {
	public LOCCTR() {}
	
	private String LOCCTR = "";
	public void setLOCCTR(String StartAddress) {
		this.LOCCTR = Integer.toHexString((int)Integer.parseInt(StartAddress, 16)).toUpperCase();
	}
	public String getLOCCTR() {
		return this.LOCCTR;
	}	
	public void addLOCCTR(int formattype) {
		this.LOCCTR = Integer.toHexString((int)Integer.parseInt(this.LOCCTR, 16) + formattype).toUpperCase();
	}
//	public String printLOCCTR() {
//		return this.LOCCTR;
//	}
}
