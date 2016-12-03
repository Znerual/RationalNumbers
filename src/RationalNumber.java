import java.math.BigDecimal;

/**
 * Created by laurenz on 02.12.16.
 */
//BigDecimal erreichen aufgrund eines Problems mit der Max größe der Longs nur eine genauigkeit von rund 8-9 stellen, für genauere Berechnungen müsste man auf BigInteger umstellen
public class RationalNumber {
    private long m_zaehler;
    private long m_nenner;
    public RationalNumber() {
        m_zaehler = 0;
        m_nenner = 1;
    }
    @SuppressWarnings("WeakerAccess")
    public RationalNumber(long number) {
        m_zaehler = number;
        m_nenner = 1L;
    }
    private void fillArray(RationalNumber[] summanden, double number,  int counter, int precision) throws Exception {
        if (counter >= precision) return;
        int digit = (int) (number *= 10);
        summanden[counter] = new RationalNumber(digit, (long) (Math.pow(10, counter + 1)));
        fillArray(summanden, (number - digit), ++counter, precision);
    }
    private void fillArray(RationalNumber[] summanden, BigDecimal number,  int counter, int precision) throws Exception {
        if (counter >= precision) return;
        number = number.multiply(BigDecimal.valueOf(10));
        int digit =  number.intValue();
        summanden[counter] = new RationalNumber(digit, (long) (Math.pow(10, counter + 1)));
        if (number.compareTo(BigDecimal.ZERO) == 1) {
            fillArray(summanden, (number.subtract(BigDecimal.valueOf(digit))), ++counter, precision);
        }

    }
    private void addAll(RationalNumber[] summanden, int index) throws Exception {
        if (index > summanden.length - 1) return;
        summanden[0] = RationalNumber.add(summanden[0], summanden[index]);
        addAll(summanden, index + 1);
        //summanden[0].kuerzen();

    }
    @SuppressWarnings("unused")
    public RationalNumber(BigDecimal number, int precision) throws Exception {
        precision = precision > 18 ? 18 : precision;
        RationalNumber[] summanden = new RationalNumber[precision];
        long firstTerm = (long) number.doubleValue();
       number = number.subtract(BigDecimal.valueOf(firstTerm));
        fillArray(summanden,number, 0, precision);
        //makeAllNennersSame(summanden, 0);
        addAll(summanden, 1);
        summanden[0] = RationalNumber.add(summanden[0], new RationalNumber(firstTerm));
        summanden[0].kuerzen();
        m_nenner = summanden[0].m_nenner;
        m_zaehler = summanden[0].m_zaehler;
    }
    public RationalNumber(double number, int precision) throws Exception{
        precision = precision > 18 ? 18 : precision;
        RationalNumber[] summanden = new RationalNumber[precision];
        long firstTerm = (long) number;
        number -= firstTerm;
        fillArray(summanden,number, 0, precision);
        //makeAllNennersSame(summanden, 0);
        addAll(summanden, 1);
        summanden[0] = RationalNumber.add(summanden[0], new RationalNumber(firstTerm));
        summanden[0].kuerzen();
        m_nenner = summanden[0].m_nenner;
        m_zaehler = summanden[0].m_zaehler;

    }
    public RationalNumber(int zaehler, int nenner) throws Exception {
        if (nenner == 0) throw new Exception("Cant divide by 0");
        m_zaehler = zaehler;
        m_nenner = nenner;
        if (zaehler == 0) m_nenner = 1;
    }
    @SuppressWarnings("WeakerAccess")
    public RationalNumber(long zaehler, long nenner) throws Exception {
        if (nenner == 0) throw new Exception("Cant divide by 0");
        m_zaehler = zaehler;
        m_nenner = nenner;
        if (zaehler == 0) m_nenner = 1;
    }
    @SuppressWarnings("WeakerAccess")
    public void multiply(long factor) {
        m_zaehler *= factor;
    }
    @SuppressWarnings("unused")
    public void divide(long factor) throws  Exception {
        if (factor < 0) {
            this.multiply(-1);
            m_nenner *= -factor;
        } else if (factor > 0) {
            m_nenner *= factor;
        } else {
            throw new Exception("Cant divide through 0");
        }

    }
    public static RationalNumber multiply(RationalNumber number1, RationalNumber number2) throws Exception {
        if (number1 == null || number2 == null) return new RationalNumber();
        //result.kuerzen();
        return new RationalNumber(number1.m_zaehler * number2.m_zaehler, number1.m_nenner * number2.m_nenner);
    }
    public static RationalNumber divide(RationalNumber number1, RationalNumber number2) throws Exception{
        if (number1 == null || number1.isNull()) return new RationalNumber();
        if (number2 == null || number2.isNull()) throw new Exception("Cant divide through null");
        //result.kuerzen();
        return new RationalNumber(number1.m_zaehler * number2.m_nenner, number1.m_nenner * number2.m_zaehler);
    }
    public static RationalNumber add(RationalNumber number1, RationalNumber number2) throws Exception {
        if (number1 == null || number1.isNull()) return number2;
        if (number2 == null || number2.isNull()) return number1;
        RationalNumber n1 = number1.copy();
        RationalNumber n2 = number2.copy();
        gemeinsamerNenner(n1, n2);
        // result.kuerzen();
        return new RationalNumber(n1.m_zaehler + n2.m_zaehler, n1.m_nenner);
    }
    public static RationalNumber subtract(RationalNumber number1, RationalNumber number2)  throws  Exception{
        if (number1 == null || number1.isNull()) {
            if (number2 != null && !number2.isNull()) {
                number2.multiply(-1);
                return number2;
            }
            return null;
        } else {
            if (number2 == null || number2.isNull()) {
                return number1;
            }
            RationalNumber n1 = number1.copy();
            RationalNumber n2 = number2.copy();
            gemeinsamerNenner(n1, n2);
            RationalNumber result = new RationalNumber(n1.m_zaehler - n2.m_zaehler, n1.m_nenner);
            result.kuerzen();
            return  result;
        }
    }
    public static void gemeinsamerNenner(RationalNumber n1, RationalNumber n2)  {
        if (n1 != null && n2 != null && !n1.isNull() && !n2.isNull()) {
            if (n1.m_nenner != n2.m_nenner) {
                long klgv = kgV(n1.m_nenner, n2.m_nenner);
                if (klgv > n1.m_nenner) {
                    n1.m_zaehler *= (klgv / n1.m_nenner);
                    n1.m_nenner = klgv;
                }
                if (klgv > n2.m_nenner) {
                    n2.m_zaehler *= (klgv / n2.m_nenner);
                    n2.m_nenner = klgv;
                }

            }
        }
    }
    public void kuerzen() {
        long divider = ggT(m_zaehler, m_nenner);
        m_zaehler /= divider;
        m_nenner /= divider;
    }

    private static long ggT(long a, long b) {
        if (b == 0) return a;
        return ggT(b, a%b);
    }
    private static long kgV(long a, long b) {
        return Math.abs(a * b) / ggT(a, b);
    }
    @SuppressWarnings("WeakerAccess")
    public boolean isNull() {
        return m_zaehler == 0;
    }

    public String toString() {
        return m_zaehler +"/" + m_nenner;
    }
    @SuppressWarnings("unused")
    public boolean valueEquals(Object other) throws  Exception{
        if (other instanceof Integer || other instanceof Long) {
            RationalNumber n1 = this.copy();
            n1.kuerzen();
            //noinspection ConstantConditions
            if (n1.m_nenner == 1 && n1.m_zaehler == (Long) other) {
                return true;
            }
        } else if (other instanceof String) {
            if (this.toString().equals(other)) return true;
            if (this.m_nenner == 1)
            try {
                int value = Integer.parseInt((String) other);
                if (value == this.m_zaehler) return true;
            } catch (NumberFormatException  exc) {
                return false;
            }
        }
        return false;
    }
    @Override
    public boolean equals(Object other){
        if (! (other instanceof RationalNumber)) return false;
        if (other == this) return true;
        return this.m_zaehler == ((RationalNumber) other).m_zaehler && this.m_nenner == ((RationalNumber) other).m_nenner;

    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result *= (prime + (m_zaehler ^ (m_zaehler >>> 32)));
        result *= (prime + (m_nenner ^ (m_nenner >>> 32)));
        return result;
    }
    @SuppressWarnings("WeakerAccess")
    public RationalNumber copy() throws Exception{
        return new RationalNumber(this.m_zaehler, this.m_nenner);
    }
}

