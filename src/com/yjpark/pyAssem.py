'''
Created on 2012. 5. 19.

@author: absolujin
'''

# -*- coding: utf-8 -*-

# for path
import os
path = os.path.dirname(__file__)
inFile = os.path.join(path+os.sep+os.pardir+os.sep+os.pardir+os.sep+os.pardir, "input.txt")

# file open
rfp = open(inFile, 'r')

# read whole lines
rlines = rfp.readlines()

# OPTAB Class
#ot = OPTAB()

# Directive Format = 0
# Memonic, Format, Hexadecimal, Offset, OperandNumber
# --------------------
OPCODE = (
        ("ADD", 3, 0x18, 3, 1),
        ("ADDF", 3, 0x58, 3, 1),
        ("ADDR", 2, 0x90, 2, 2),
        ("CLEAR", 2, 0xB4, 2, 1),
        ("COMP", 3, 0x28, 3, 1),
        ("COMPF", 3, 0x88, 3, 1),
        ("COMPR", 2, 0xA0, 2, 2),
        ("DIV", 3, 0x24, 3, 1),
        ("DIVF", 3, 0x64, 3, 1),
        ("DIVR", 2, 0x9C, 2, 2),
        ("FIX", 1, 0xC4, 1, 0),
        ("FLOAT", 1, 0xC0, 1, 0),
        ("HIO", 1, 0xF4, 1, 0),
        ("J", 3, 0x3C, 3, 1),
        ("JEQ", 3, 0x30, 3, 1),
        ("JGT", 3, 0x34, 3, 1),
        ("JLT", 3, 0x38, 3, 1),
        ("JSUB", 3, 0x48, 3, 1),
        ("LDA", 3, 0x00, 3, 1),
        ("LDB", 3, 0x68, 3, 1),
        ("LDCH", 3, 0x50, 3, 1),
        ("LDF", 3, 0x70, 3, 1),
        ("LDL", 3, 0x08, 3, 1),
        ("LDS", 3, 0x6C, 3, 1),
        ("LDT", 3, 0x74, 3, 1),
        ("LDX", 3, 0x04, 3, 1),
        ("LPS", 3, 0xD0, 3, 1),
        ("MUL", 3, 0x20, 3, 1),
        ("MULF", 3, 0x60, 3, 1),
        ("MULR", 2, 0x98, 2, 2),
        ("NORM", 1, 0xC8, 1, 0),
        ("OR", 3, 0x44, 3, 1),
        ("RD", 3, 0xD8, 3, 1),
        ("RMO", 2, 0xAC, 2, 2),
        ("RSUB", 3, 0x4C, 3, 0),
        ("SHIFTL", 2, 0xA4, 2, 2),
        ("SHIFTR", 2, 0xA8, 2, 2),
        ("SIO", 1, 0xF0, 1, 0),
        ("SSK", 3, 0xED, 3, 1),
        ("STA", 3, 0x0C, 3, 1),
        ("STB", 3, 0x78, 3, 1),
        ("STCH", 3, 0x54, 3, 1),
        ("STF", 3, 0x80, 3, 1),
        ("STI", 3, 0xD4, 3, 1),
        ("STL", 3, 0x14, 3, 1),
        ("STS", 3, 0x7C, 3, 1),
        ("STSW", 3, 0xE8, 3, 1),
        ("STT", 3, 0x84, 3, 1),
        ("STX", 3, 0x10, 3, 1),
        ("SUB", 3, 0x1C, 3, 1),
        ("SUBF", 3, 0x5C, 3, 1),
        ("SUBR", 2, 0x94, 2, 2),
        ("SVC", 2, 0xB0, 2, 1),
        ("TD", 3, 0xE0, 3, 1),
        ("TIO", 1, 0xF8, 1, 0),
        ("TIX", 3, 0x2C, 3, 1),
        ("TIXR", 2, 0xB8, 2, 1),
        ("WD", 3, 0xDC, 3, 1),

        # Directive
        ("BASE", 0, 0xFF1, 0, 1),
        ("BYTE", 0, 0xFF2, 1, 1),
        ("CSECT", 0, 0xFF3, 0, 1),
        ("END", 0, 0xFF4, 0, 1),
        ("EQU", 0, 0xFF5, 0, 1),
        ("EXTDEF", 0, 0xFF6, 0, 1),
        ("EXTREF", 0, 0xFF7, 0, 1),
        ("LTORG", 0, 0xFF8, 0, 0),
        ("NOBASE", 0, 0xFF9, 0, 0),
        ("ORG", 0, 0xFFA, 0, 1),
        ("RESB", 0, 0xFFB, 1, 1),
        ("RESW", 0, 0xFFC, 3, 1),
        ("START", 0, 0xFFD, 0, 1),
        ("USE", 0, 0xFFE, 0, 1),
        ("WORD", 0, 0xFFF, 3, 1)

    )

# Memonic, Number
# --------------------
Register = (
        ("A", 0),
        ("X", 1),
        ("L", 2),
        ("B", 3),
        ("S", 4),
        ("T", 5),
        ("F", 6),
        ("PC", 8),
        ("SW", 9)
)

def getOpcode(mnemonic):
    i = 0;
    while i <  OPCODE.__len__():
        if mnemonic == OPCODE[i][0]:
            return OPCODE[i]
        i = i + 1

# =================================================== #
def combineList(list1, list2):
    list1.insert(list1.__len__(), list2)
    return list1
# =================================================== #
def getLitVal(operand):
    lit_ar = operand.split("'")
    litval = ""
    
    # hex code
    if lit_ar[0] == "=X":
        litval = lit_ar[1]
        
    # ASCII
    elif lit_ar[0] == "=C":
        i = 0
        while i < lit_ar[1].__len__():
            litval += hex(ord(lit_ar[1][i])).upper().replace("0X", "")
            i += 1
        
    return litval
# =================================================== #
def addLiteral(tList, locctr):
    lIdx = 0
    items = LITTAB.items()
    while lIdx < items.__len__():
        tList.append([{"label":"*"}, {"mnemonic":items[0][0]}, {"opcode":items[0][1]}, {"locctr":locctr}, {"operand":""}, {"comment":""}])
        locctr += items[0][1].__len__()/2
        lIdx += 1
    
    gap = items.__len__()
    
    LITTAB.clear()
    
    return tList, gap, locctr
# =================================================== #
                
# === PASS1 START ================================================ #
# insert into Line line by line
i = 0
cIdx = 0
gap = 0

CodeLine = []
inputLine = []

# indexing key value
STAB = {}
LITTAB = {}

locctr = 0x0
pcctr = 0x0

for line in rlines:
    # split line by "\t"
    inputLine.append(line.replace("\n", "").split("\t"))
    CodeLine.append([])

    # skip to comment line
    if inputLine[i][0] == "." :
        if inputLine[i].__len__() == 2:
            CodeLine[cIdx].insert(CodeLine[cIdx].__len__(), {"commentLine":inputLine[i][0]+"\t"+inputLine[i][1]})
        else: 
            CodeLine[cIdx].insert(CodeLine[cIdx].__len__(), {"commentLine":inputLine[i][0]})
        # move to next index
        i = i + 1
        cIdx = i + gap
        continue


    # [LABEL]    [OPCODE]    [OPERAND]    [COMMENT]
    j = 0
    while j < len(inputLine[i]):
        
        # skip to empty line
        if inputLine[i][j] == "" :
            # move to next index
            j = j + 1
            continue

        # [LABEL]
        if j == 0:
            # add to SYMTAB dictionary
            STAB[inputLine[i][j]] = locctr
            
            # [label] [mnemonic] [opcode] [locctr] [operand] [comment] [objectCode]
            CodeLine[cIdx].insert(CodeLine[cIdx].__len__(), {"label":inputLine[i][j]})
                
        # [OPCODE]
        elif j == 1:
            mnemonic = inputLine[i][j]
            
            # === control locctr ==== #
            # initalize to location counter
            if mnemonic == "CSECT":
                locctr = 0
                pcctr = 0
                
            # "+"
            if mnemonic[0] == "+":
                # 1 ~ end
                mnemonic = inputLine[i][j][1:inputLine[i][j].__len__()]
                # extended
                pcctr += 1
                
            # mnemonic -> opcode
            opcodeList = getOpcode(mnemonic)
            # location counter increase by operand offset
            pcctr += opcodeList[3]
            
            # add to location counter into CodeLine
            # [label] [mnemonic] [opcode] [locctr] [operand] [comment] [objectCode]
            CodeLine[cIdx].insert(CodeLine[cIdx].__len__(), {"mnemonic":inputLine[i][j]})
            
            # except directive
            if opcodeList[1] != 0:
                CodeLine[cIdx].insert(CodeLine[cIdx].__len__(), {"opcode":opcodeList[2]})
            
            if mnemonic != "EXTDEF" and mnemonic != "EXTREF" \
                and mnemonic != "LTORG" and mnemonic != "END":
                CodeLine[cIdx].insert(CodeLine[cIdx].__len__(), {"locctr":locctr})         
     
            # LTORG - LITTAB
            if mnemonic == "LTORG":
                CodeLine, gap, pcctr = addLiteral(CodeLine, locctr)
                cIdx += gap
            
            # RESW
            if mnemonic == "RESW":
                pcctr = locctr + (int(inputLine[i][j+1]) * 3)
            # RESB
            if mnemonic == "RESB":
                pcctr = locctr + ((int(inputLine[i][j+1]) * 1))
          
            if mnemonic == "EQU":
                # minus case
                mnemonic_ar = inputLine[i][j+1].split("-")
                mnemonic_val = STAB.get(mnemonic_ar[0])
                if mnemonic_ar.__len__() > 1:
                    mIdx = 1
                    while mIdx < mnemonic_ar.__len__():
                        mnemonic_val -= STAB.get(mnemonic_ar[mIdx])
                        mIdx += 1
                        
                    locctr = mnemonic_val
                    CodeLine[cIdx].insert(CodeLine[cIdx].__len__(), {"locctr":locctr})
                    
#        # [OPERAND]
        elif j == 2:
            operand = inputLine[i][j]
            CodeLine[cIdx].insert(CodeLine[cIdx].__len__(), {"operand":operand})
            
            # "="
            if operand[0] == "=":             
                # add to LITTAB
                LITTAB[inputLine[i][j]] = getLitVal(inputLine[i][j])
                
#            
        # [COMMENT]
        elif j == 3:
            # [label] [mnemonic] [opcode] [locctr] [operand] [comment] [objectCode]
            CodeLine[cIdx].insert(CodeLine[cIdx].__len__(), {"comment":inputLine[i][j]})
        
        # while len index
        j = j + 1
    

    # pcctr = locctr
    locctr = pcctr
    
    # for line i
    i += 1
    cIdx = i + gap

# End of Program - LITTAB
CodeLine, gap, locctr = addLiteral(CodeLine, locctr)
# === PASS1 END ================================================ #

# === PASS2 START ================================================ #

locctr = ""
label = ""
mnemonic = ""
operand = ""
opcode = ""
commentLine = ""

address = ""
objectCode = 0x0
    
i = 0
while i < CodeLine.__len__():
    
    nflag = 0
    iflag = 0
    xflag = 0
    bflag = 0
    pflag = 0
    eflag = 0
    
    j = 0
    
    # [label] [mnemonic] [mnemonic] [locctr] [operand] [comment] [opcode-objectCode]
    while j < CodeLine[i].__len__():
        
        # [locctr] [label] [opcode] [operand] [objectCode]      
        if CodeLine[i][j].get("locctr") != None:
            locctr = CodeLine[i][j].get("locctr")
        if CodeLine[i][j].get("label") != None:
            label = CodeLine[i][j].get("label")
        if CodeLine[i][j].get("mnemonic") != None:
            mnemonic = CodeLine[i][j].get("mnemonic")        
        if CodeLine[i][j].get("operand") != None:
            operand = CodeLine[i][j].get("operand")      
        if CodeLine[i][j].get("opcode") != None:
            opcode = CodeLine[i][j].get("opcode")
        if CodeLine[i][j].get("commentLine") != None:
            commentLine = CodeLine[i][j].get("commentLine")
            
        j += 1
        
    # mnemonic -> opcode
    opcodeList = getOpcode(mnemonic)
    if opcodeList != None:
        offset = opcodeList[3]
    
    # simple
    nflag = 1
    iflag = 1
    
    if operand != "":
        # indirect - @
        if operand[0] == "@":
            nflag = 1
            iflag = 0
            
        # immediate - #
        elif operand[0] == "#":
            nflag = 0
            iflag = 1

    # LOOP x
    op_ar = operand.split(",")
    if op_ar.__len__() > 1:
        if op_ar[1] == "X":
            xflag = 1

    #  base relative
    bflag = 0
    
    # PC relative
    plfag = 1
    
    # extended
    if mnemonic[0] == "+":
        offset += 1
        bflag = 0
        pflag = 0
        eflag = 1
        
        
    pcctr = locctr + offset
    
    
#    print type(opcode)
#    print opcode
    if type(opcode) == type(1):
        opcode_hex = "%x".upper() % opcode
#        print opcode_hex
#        print hex(int(opcode_hex, 16) << (offset*(2-1)*8))
        objectCode = 0x0
        
        # packing
        oIdx = 0
        while oIdx < len(opcode_hex):
            objectCode << 4
            objectCode |= int(opcode_hex[oIdx], 16)
            oIdx += 1
            print hex(objectCode)
            
        objectCode |= nflag << 1
        objectCode |= iflag
        objectCode << 4
        
        objectCode |= xflag << 3
        objectCode |= bflag << 2
        objectCode |= pflag << 1
        objectCode |= eflag
        objectCode << 4
        
        print objectCode
#        oIdx = 0
#        while oIdx < len(opcode_hex):
#            objectCode |= int(opcode_hex[oIdx], 16) << ((offset*2-oIdx+1)*8)
#            print hex(objectCode)
#            oIdx += 1
#        

        
#        opcode_hex = long(opcode_hex, 16)
#        print type(opcode_hex)
#        print opcode_hex
##        print type(opcode_hex)
##        print type()
##        opcode_hex = opcode_hex << (offset*(2-1)*8)
##        print opcode_hex
#        
#    else:
#        print "="+opcode
    

    
    i += 1
    
# === PASS2 END ================================================ #

i = 0
while i < CodeLine.__len__():
    
    locctr = ""
    label = ""
    mnemonic = ""
    operand = ""
    opcode = ""
    commentLine = ""
    
    j = 0
    
    # [label] [mnemonic] [mnemonic] [locctr] [operand] [comment] [opcode-objectCode]
    while j < CodeLine[i].__len__():
        
        # [locctr] [label] [opcode] [operand] [objectCode]      
        if CodeLine[i][j].get("locctr") != None:
            locctr = CodeLine[i][j].get("locctr")
        if CodeLine[i][j].get("label") != None:
            label = CodeLine[i][j].get("label")
        if CodeLine[i][j].get("mnemonic") != None:
            mnemonic = CodeLine[i][j].get("mnemonic")        
        if CodeLine[i][j].get("operand") != None:
            operand = CodeLine[i][j].get("operand")      
        if CodeLine[i][j].get("opcode") != None:
            opcode = CodeLine[i][j].get("opcode")
        if CodeLine[i][j].get("commentLine") != None:
            commentLine = CodeLine[i][j].get("commentLine")
            
        j += 1
 
 
    locctr_str = ""

    # formatting
    try:
        locctr_str = "%x".upper() % locctr
        zIdx = len(locctr_str)
        while zIdx < 4:
            locctr_str = "0"+locctr_str
            zIdx += 1
            
    except:
        locctr = ""
    
    
    try:
        opcode = "%x".upper() % opcode
    except:
        opcode = opcode
    
    
    if commentLine == "":
        print "%(locctr_str)s\t%(label)s\t%(mnemonic)s\t%(operand)s\t%(opcode)s" \
        % {"locctr_str":locctr_str, "label":label, "mnemonic":mnemonic, "operand":operand, "opcode":opcode}
    else:
        print "\t%(commentLine)s" % {"commentLine":commentLine}
    
    i += 1
    
    
#print STAB.get("WRREC")
print STAB.items()
#print LITTAB.items()
#print STAB.values()



# close input file
rfp.close();


#ofp = open("output.txt", 'w')
