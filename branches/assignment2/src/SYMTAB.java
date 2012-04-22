
public class SYMTAB {

	// 각 SYMBOL 에 대한 접근 index
	private int idx;
	// SYMBOL
	private String SYMBOL;
	// Address TYPE: Absolute / Relative
	private char TYPE;
	// Pass1 에서 결정된 ADDRESS VALUE
	private String VALUE;
	
	// 기본 생성자
	public SYMTAB() {}
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getSYMBOL() {
		return SYMBOL;
	}
	public void setSYMBOL(String symbol) {
		SYMBOL = symbol;
	}
	public char getTYPE() {
		return TYPE;
	}
	public void setTYPE(char type) {
		TYPE = type;
	}
	public String getVALUE() {
		return VALUE;
	}
	public void setVALUE(String value) {
		VALUE = value;
	}	
}
