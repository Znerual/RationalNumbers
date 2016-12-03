import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by laurenz on 03.12.16.
 */
public class BigRationalNumber {
    private BigInteger m_zaehler;
    private BigInteger m_nenner;
    @SuppressWarnings("WeakerAccess")
    public BigRationalNumber() {
        m_zaehler = BigInteger.ZERO;
        m_nenner = BigInteger.ONE;
    }
    @SuppressWarnings("WeakerAccess")
    public BigRationalNumber(BigInteger number) {
        m_zaehler = number; //was passiert wenn number außerhalb verändert wird
        m_nenner = BigInteger.ONE;
    }
    @SuppressWarnings("unused")
    public BigRationalNumber(long number) {
        m_zaehler = BigInteger.valueOf(number);
        m_nenner = BigInteger.ONE;
    }
    public BigRationalNumber(BigInteger zaehler, BigInteger nenner) throws Exception{
        if (nenner == null || nenner.signum() == 0) throw new Exception("Cant divide through 0");
        m_zaehler = zaehler;
        m_nenner = nenner;
        if (zaehler == null || zaehler.signum() == 0) m_nenner = BigInteger.ONE;
    }
    @SuppressWarnings("unused")
    public BigRationalNumber(long zaehler, long nenner) throws Exception {
        if (nenner == 0) throw  new Exception("Cant divide through 0");
        if (zaehler == 0) {
            m_nenner = BigInteger.ONE;
            m_zaehler = BigInteger.ZERO;
        } else {
            m_nenner = BigInteger.valueOf(nenner);
            m_zaehler = BigInteger.valueOf(zaehler);
        }
    }
    public BigRationalNumber(BigDecimal number, @SuppressWarnings("SameParameterValue") int precision) throws Exception {
        precision = precision > 18 ? 18 : precision;
        BigRationalNumber[] summanden = new BigRationalNumber[precision];
        long firstTerm = (long) number.doubleValue();
        number = number.subtract(BigDecimal.valueOf(firstTerm));
        fillArray(summanden,number, 0, precision);
        addAll(summanden, 1);
        summanden[0].kuerzen();
        summanden[0] = BigRationalNumber.add(summanden[0], new BigRationalNumber(BigInteger.valueOf(firstTerm))); //mit nummer verbessern
        m_nenner = summanden[0].m_nenner;
        m_zaehler = summanden[0].m_zaehler;
    }
    @SuppressWarnings("WeakerAccess")
    public boolean isNull() {
        return m_zaehler.signum() == 0;
    }
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof BigRationalNumber)) return false;
        if (other == this) return true;
        return m_nenner.equals(((BigRationalNumber) other).m_nenner) && m_zaehler.equals(((BigRationalNumber) other).m_zaehler);
    }
    @Override
    public int hashCode() {
        final int prime = 37;
        int result = 1;
        result *= prime + m_zaehler.hashCode();
        result *= prime + m_nenner.hashCode();
        return result;
    }
    private void fillArray(BigRationalNumber[] summanden, BigDecimal number,  int counter, int precision) throws Exception {
        if (counter >= precision) return;
        number = number.multiply(BigDecimal.valueOf(10));
        int digit =  number.intValue();
        summanden[counter] = new BigRationalNumber(number.toBigInteger(), BigInteger.TEN.pow(counter + 1));
        if (number.compareTo(BigDecimal.ZERO) == 1) {
            fillArray(summanden, (number.subtract(BigDecimal.valueOf(digit))), ++counter, precision);
        }

    }
    private void addAll(BigRationalNumber[] summanden, int index) throws Exception {
        if (index > summanden.length - 1) return;
        summanden[0] = BigRationalNumber.add(summanden[0], summanden[index]);
        addAll(summanden, index + 1);

    }
    @SuppressWarnings("unused")
    public void multiply(BigInteger faktor) {
        m_zaehler = m_zaehler.multiply(faktor);
    }
    @SuppressWarnings("unused")
    public void divide(BigInteger faktor) throws Exception{
        if (faktor == null || faktor.signum() == 0) throw new Exception("Cant divide through 0");
        m_nenner = m_nenner.multiply(faktor);
    }
    @SuppressWarnings("WeakerAccess")
    public static BigRationalNumber negate(BigRationalNumber n1) {
        n1.m_zaehler = n1.m_zaehler.negate();
        return n1;
    }
    public static BigRationalNumber add(BigRationalNumber number1, BigRationalNumber number2) throws  Exception{
        if (number1 == null || number1.isNull()) return number2;
        if (number2 == null || number2.isNull()) return number1;
        BigRationalNumber n1 = number1.copy();
        BigRationalNumber n2 = number2.copy();
        gemeinsamerNenner(n1,n2);
        return new BigRationalNumber(n1.m_zaehler.add(n2.m_zaehler), n1.m_nenner);
    }
    public static BigRationalNumber subtract(BigRationalNumber number1, BigRationalNumber number2) throws  Exception{
        if (number1 == null || number1.isNull()) return BigRationalNumber.negate(number2);
        if (number2 == null || number2.isNull()) return number1;
        BigRationalNumber n1 = number1.copy();
        BigRationalNumber n2 = number2.copy();
        gemeinsamerNenner(n1,n2);
        return new BigRationalNumber(n1.m_zaehler.subtract(n2.m_zaehler), n1.m_nenner);
    }
    public static BigRationalNumber multiply(BigRationalNumber number1, BigRationalNumber number2) throws  Exception {
        if (number1 == null || number1.isNull() || number2 == null || number2.isNull()) return new BigRationalNumber();
        BigRationalNumber n1 = number1.copy();
        BigRationalNumber n2 = number2.copy();
        return new BigRationalNumber(n1.m_zaehler.multiply(n2.m_zaehler), n1.m_nenner.multiply(n2.m_nenner));
    }
    public static BigRationalNumber divide(BigRationalNumber number1, BigRationalNumber number2) throws  Exception {
        if (number1 == null || number1.isNull()) return new BigRationalNumber();
        if (number2 == null || number2.isNull()) throw new Exception("Cant divide through 0");
        BigRationalNumber n1 = number1.copy();
        BigRationalNumber n2 = number2.copy();
        return new BigRationalNumber(n1.m_zaehler.multiply(n2.m_nenner), n1.m_nenner.multiply(n2.m_zaehler));
    }
    private static void gemeinsamerNenner(BigRationalNumber n1, BigRationalNumber n2) {
        if (n1 == null || n1.isNull() || n2 == null || n2.isNull()) return;
        if (n1.m_nenner.equals(n2.m_nenner)) return;
        BigInteger kgv = kgV(n1.m_nenner,n2.m_nenner);
        n1.m_zaehler = n1.m_zaehler.multiply(kgv.divide(n1.m_nenner));
        n1.m_nenner = kgv; //hoffen das n1 nenner und n2 nenner nicht ident sonder nur gleich
        n2.m_zaehler = n2.m_zaehler.multiply(kgv.divide(n2.m_nenner));
        n2.m_nenner = kgv;
    }
    public void kuerzen() {
        BigInteger teiler = m_zaehler.gcd(m_nenner);
        m_zaehler = m_zaehler.divide(teiler);
        m_nenner = m_nenner.divide(teiler);
    }
    private static BigInteger kgV(BigInteger n1, BigInteger n2) {
        BigInteger result = BigInteger.ONE;
        result = result.multiply(n1);
        result = result.multiply(n2);
        result = result.abs().divide(n1.gcd(n2));
        return result;
    }
    private BigRationalNumber copy() throws Exception {
        BigInteger newZ = BigInteger.ZERO;
        BigInteger newN = BigInteger.ZERO;
        newZ = newZ.add(this.m_zaehler);
        newN = newN.add(this.m_nenner);
        return new BigRationalNumber(newZ, newN);
    }
    public String toString() {
        return m_zaehler.toString() + "/" + m_nenner.toString();
    }
}
