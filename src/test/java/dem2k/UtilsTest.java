package dem2k;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

import junit.framework.TestCase;

public class UtilsTest extends TestCase {

    public void testTrunc() {
        String s5 = Utils.toStringTrunc(0.12345678);
        Assert.assertEquals("0.12345",s5);

    }

    public void testIncludeToUpper() {
        ArrayList<String> list = new ArrayList<>();
        list.add("aaa");
        List<String> result = Utils.listToUpperCase(list);
        assertEquals("AAA", result.get(0));
    }
}
