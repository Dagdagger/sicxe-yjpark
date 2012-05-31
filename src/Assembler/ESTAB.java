package Assembler;

// ETAB 생성 및 관리를 위한 Class
public class ESTAB {

	private int csectIdx;
	private String contolSection;
	private int symbolIdx;
	private String symbol;
	private int address;
	private int length;
	
	public ESTAB() {
	}

	public int getCsectIdx() {
		return csectIdx;
	}

	public void setCsectIdx(int csectIdx) {
		this.csectIdx = csectIdx;
	}
	
	public String getContolSection() {
		return contolSection;
	}

	public void setContolSection(String contolSection) {
		this.contolSection = contolSection;
	}

	public int getSymbolIdx() {
		return symbolIdx;
	}

	public void setSymbolIdx(int symbolIdx) {
		this.symbolIdx = symbolIdx;
	}
	
	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public int getAddress() {
		return address;
	}

	public void setAddress(int address) {
		this.address = address;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
	
}