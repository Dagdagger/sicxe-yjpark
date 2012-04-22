// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CodeLineDTO.java

package sp.dtopack;


public class CodeLineDTO
{

    public CodeLineDTO()
    {
    }

    public String getLineString()
    {
        return lineString;
    }

    public void setLineString(String lineString)
    {
        this.lineString = lineString;
    }

    public String getOperand1()
    {
        return operand1;
    }

    public void setOperand1(String operand1)
    {
        this.operand1 = operand1;
    }

    public String getOperand2()
    {
        return operand2;
    }

    public void setOperand2(String operand2)
    {
        this.operand2 = operand2;
    }

    public int getAddress()
    {
        return address;
    }

    public void setAddress(int address)
    {
        this.address = address;
    }

    public int getOpcode()
    {
        return opcode;
    }

    public void setOpcode(int opcode)
    {
        this.opcode = opcode;
    }

    public int getLabel()
    {
        return label;
    }

    public void setLabel(int label)
    {
        this.label = label;
    }

    String lineString;
    String operand1;
    String operand2;
    int address;
    int opcode;
    int label;
}
