// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   XEToyAssembler2.java

package sp.interfacepack;

import java.io.File;
import java.util.Vector;

public interface XEToyAssembler2
{

    public abstract Vector parseData(File file);

    public abstract Vector changeImmediateCode(Vector vector);

    public abstract Vector changeObjectCode(Vector vector);
}
