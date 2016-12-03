import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.Assert.*;

/**
 * Created by laurenz on 03.12.16.
 */
public class BigRationalNumberTest {
    @Test
    public void kuerzen() throws Exception {
        BigRationalNumber n1 = new BigRationalNumber(BigInteger.valueOf(128), BigInteger.valueOf(64));
        n1.kuerzen();
        assertEquals("2/1", n1.toString());
    }


    @Test
    public void add() throws Exception {
        BigRationalNumber n1 = new BigRationalNumber(BigInteger.valueOf(5), BigInteger.valueOf(7));
        BigRationalNumber n2 = new BigRationalNumber(BigInteger.valueOf(12), BigInteger.valueOf(35));
        BigRationalNumber result = BigRationalNumber.add(n1,n2);
        assertEquals("37/35",result.toString());
    }

    @Test
    public void subtract() throws Exception {
        BigRationalNumber n1 = new BigRationalNumber(BigInteger.valueOf(5), BigInteger.valueOf(7));
        BigRationalNumber n2 = new BigRationalNumber(BigInteger.valueOf(12), BigInteger.valueOf(35));
        BigRationalNumber result = BigRationalNumber.subtract(n1,n2);
        assertEquals("13/35",result.toString());
    }

    @Test
    public void mulitply() throws Exception {
        BigRationalNumber n1 = new BigRationalNumber(BigInteger.valueOf(5), BigInteger.valueOf(7));
        BigRationalNumber n2 = new BigRationalNumber(BigInteger.valueOf(12), BigInteger.valueOf(35));
        BigRationalNumber result = BigRationalNumber.multiply(n1,n2);
        assertEquals("60/245",result.toString());
        result.kuerzen();
        assertEquals("12/49", result.toString());
    }

    @Test
    public void divide() throws Exception {
        BigRationalNumber n1 = new BigRationalNumber(BigInteger.valueOf(5), BigInteger.valueOf(7));
        BigRationalNumber n2 = new BigRationalNumber(BigInteger.valueOf(12), BigInteger.valueOf(35));
        BigRationalNumber result = BigRationalNumber.divide(n1,n2);
        assertEquals("175/84",result.toString());
        result.kuerzen();
        assertEquals("25/12", result.toString());
    }

    @Test
    public void init() throws Exception {
        BigRationalNumber bn4 = new BigRationalNumber(BigDecimal.valueOf(2.6), 2);
        assertEquals("13/5", bn4.toString());
    }

}