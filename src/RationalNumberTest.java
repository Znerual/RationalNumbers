import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by laurenz on 02.12.16.
 */
public class RationalNumberTest {
    @Test
    public void kuerzen() throws Exception {
        RationalNumber n = new RationalNumber(62,2);
        n.kuerzen();
        assertEquals("31/1", n.toString());
    }
    @Test
    public void gemeinsamerNenner() throws Exception {
        RationalNumber n1 = new RationalNumber(4,5);
        RationalNumber n2 = new RationalNumber(2, 6);
        RationalNumber.gemeinsamerNenner(n1, n2);
        assertEquals("24/30", n1.toString());
        assertEquals("10/30", n2.toString());
    }
    @Test
    public void add() throws Exception {
        RationalNumber n1 = new RationalNumber(4,5);
        RationalNumber n2 = new RationalNumber(2, 6);
        RationalNumber n3 = new RationalNumber();
        RationalNumber result = RationalNumber.add(n1, n2);
        assertEquals("34/30", result.toString());
        result = RationalNumber.add(n1, n3);
        assertEquals(n1.toString(), result.toString());
    }
    @Test
    public void subtract() throws Exception {
        RationalNumber n1 = new RationalNumber(4,5);
        RationalNumber n2 = new RationalNumber(2, 6);
        RationalNumber n3 = new RationalNumber();
        RationalNumber result = RationalNumber.subtract(n1, n2);
        assertEquals("7/15", result.toString());
        result = RationalNumber.subtract(n2, n3);
        assertEquals("2/6", result.toString());
        result = RationalNumber.subtract(n3, n1);
        assertEquals("-4/5", result.toString());
    }
    @Test
    public void mulitply() throws Exception {
        RationalNumber n1 = new RationalNumber(4,5);
        RationalNumber n2 = new RationalNumber(2, 6);
        RationalNumber n3 = new RationalNumber();
        RationalNumber result = RationalNumber.multiply(n1,n2);
        assertEquals("8/30",result.toString());
        result = RationalNumber.multiply(n2, n3);
        assertEquals(new RationalNumber().toString(), result.toString());
    }
    @Test
    public void divide() throws Exception {
        RationalNumber n1 = new RationalNumber(4,5);
        RationalNumber n2 = new RationalNumber(2, 6);
        RationalNumber n3 = new RationalNumber();
        RationalNumber result = RationalNumber.divide(n1,n2);
        assertEquals("24/10",result.toString());
        result = RationalNumber.divide(n3, n2);
        assertEquals(new RationalNumber().toString(), result.toString());
    }
    @Test
    public void init() throws Exception {
        RationalNumber n1 = new RationalNumber(2.5, 1);
        assertEquals("5/2", n1.toString());
        RationalNumber n2 = new RationalNumber(6.125, 5);
        assertEquals("49/8", n2.toString());


    }
}