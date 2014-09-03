package net.shchastnyi.serializer.messages;

public class AllWrappersInOne {

    public byte aByte;
    public short aShort;
    public int anInt;
    public long aLong;
    public float aFloat;
    public double aDouble;
    public char aChar;
    public boolean aBoolean;

    public AllWrappersInOne() {}

    public AllWrappersInOne(byte aByte, short aShort, int anInt, long aLong,
                            float aFloat, double aDouble,
                            char aChar, boolean aBoolean) {
        this.aByte = aByte;
        this.aShort = aShort;
        this.anInt = anInt;
        this.aLong = aLong;
        this.aFloat = aFloat;
        this.aDouble = aDouble;
        this.aChar = aChar;
        this.aBoolean = aBoolean;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AllWrappersInOne that = (AllWrappersInOne) o;

        if (aBoolean != that.aBoolean) return false;
        if (aByte != that.aByte) return false;
        if (aChar != that.aChar) return false;
        if (Double.compare(that.aDouble, aDouble) != 0) return false;
        if (Float.compare(that.aFloat, aFloat) != 0) return false;
        if (aLong != that.aLong) return false;
        if (aShort != that.aShort) return false;
        if (anInt != that.anInt) return false;

        return true;
    }

}
