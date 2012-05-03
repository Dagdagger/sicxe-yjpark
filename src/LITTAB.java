
public class LITTAB {
	String literalName, operandValue;
	int length, address;
	
	public String getLiteralName() {
		return literalName;
	}
	public void setLiteralName(String literalName) {
		this.literalName = literalName;
		String valueType = literalName.substring(0,1);

		
		if(valueType.equals("C")) { // Character 인 경우로 해당 ASCII 값으로 value 설정
			String[] tmpStr = literalName.split("'");
			String valueStr = tmpStr[1];

			// value 길이만큼 설정
			this.length = valueStr.length();
			
			// ASCII hexadecimal 로 변환, 최초, 1회 할당
			this.operandValue = Integer.toHexString(valueStr.substring(0, 1).codePointAt(0)).toUpperCase();
			
			int i = 1;
			while(i<valueStr.length()) {
				// ASCII hexadecimal 로 변환
				this.operandValue += Integer.toHexString(valueStr.substring(i++, i).codePointAt(0)).toUpperCase();
			}	
			
		} else if(valueType.equals("X")) { // Hexadecimal 인 경우로 해당 hex 값으로 value 설정
			this.length = literalName.split("'")[1].length();
			
			this.operandValue = literalName.split("'")[1];
			
		} else {
			
		}

	}
	public String getOperandValue() {
		return operandValue;
	}
	public void setOperandValue(String operandValue) {
		this.operandValue = operandValue;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getAddress() {
		return address;
	}
	public void setAddress(int address) {
		this.address = address;
	}
	
}
