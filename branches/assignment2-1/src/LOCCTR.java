// LOCCTR 을 구현하기 위한 클래스
// operand 의 길이만큼 증가시킨다

public class LOCCTR {
	public LOCCTR() {}
	
	private String LOCCTR = "";
	public void setLOCCTR(String StartAddress) {
		this.LOCCTR = Integer.toHexString((int)Integer.parseInt(StartAddress, 16)).toUpperCase();
	}
	public int getLOCCTR() {
		return (int)Integer.parseInt(this.LOCCTR, 16);
	}	
	public void addLOCCTR(int formattype) {
		// formattype 의 크기만큼 증가
		this.LOCCTR = Integer.toHexString((int)Integer.parseInt(this.LOCCTR, 16) + formattype).toUpperCase();
	}
//	public String printLOCCTR() {
//		return this.LOCCTR;
//	}
}
