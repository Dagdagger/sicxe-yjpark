/**
 * 
 */

/**
 * @author absolujin
 *
 */
public class OneLine {
	private String LABEL;
	private String MEMONIC;
	private String OPERAND;
	private String OPCODE;
	
	public void checkLABEL(){};
	public void checkMEMONIC(){};
	public void checkOPERAND(){};

	public OneLine () {}
	
	public OneLine (String[] line) {
		// 읽어드린 파일의 한 라인의 split 된 String[] 의 길이 형식은 1~4 이며,
		//각 길이에 따라 해당 자리의 값을 설정한다.
		if(line.length > 0 && line.length < 5) {
			for(int i=0; i<line.length; i++) {
				switch(i) {
					case 0:
						this.LABEL = line[i];
					case 1:
						this.MEMONIC = line[i];
					case 2:
						this.OPERAND = line[i];
					case 3:
						this.OPCODE = line[i];
				}
			}
		} else {
			new OneLine();
		}
	}
	
	public String getElement(String str) {
		String retValue = "";
		
//		switch(str) {
//		case "LABEL":
//			retValue = this.LABEL;
//		case "MEMONIC":
//			retValue = this.MEMONIC;
//		case "OPERAND":
//			retValue = this.OPERAND;
//		case "OPCODE":
//			retValue = this.OPCODE;
//		}
		
		return retValue;
	}
}
