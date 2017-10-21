package me.rei_m.hyakuninisshu.domain.model.karuta;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ImageNoTest {

    @Test
    public void testValidArg001() throws Exception {
        ImageNo imageNo = new ImageNo("001");
        assertThat(imageNo.value(), is("001"));
    }

    @Test
    public void testValidArg050() throws Exception {
        ImageNo imageNo = new ImageNo("050");
        assertThat(imageNo.value(), is("050"));
    }

    @Test
    public void testValidArg100() throws Exception {
        ImageNo imageNo = new ImageNo("100");
        assertThat(imageNo.value(), is("100"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidArg000() throws Exception {
        new ImageNo("000");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidArg101() throws Exception {
        new ImageNo("101");
    }

    @Test
    public void equals() throws Exception {
        ImageNo imageNo1 = new ImageNo("100");
        ImageNo imageNo2 = new ImageNo("100");
        assertThat(imageNo1.equals(imageNo2), is(true));
    }

    @Test
    public void notEquals() throws Exception {
        ImageNo imageNo1 = new ImageNo("100");
        ImageNo imageNo2 = new ImageNo("001");
        assertThat(imageNo1.equals(imageNo2), is(false));
    }
}
