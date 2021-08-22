import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;


public class MyLineWrapTest {
    // news from https://www.cnn.com/2020/02/19/world/juno-jupiter-water-mystery-scn/index.html
    @Test
    public void test1 () throws NoSuchElementE {
        String s = "When NASA's Galileo mission visited Jupiter in the 1990s and began its descent, the spacecraft's data was expected to solve a puzzle for scientists. They wanted to know how much water was present in Jupiter's atmosphere. But just as things were getting good, the probe stopped transmitting 57 minutes and 75 miles into its descent, crushed by the pressure of Jupiter's atmosphere. Intriguingly, Galileo detected that the amount of water in the atmosphere increased as it dove down. ";
        String s1 = LineWrap.runGreedy(s, 19);
        String s2 = LineWrap.runDP(s, 19);
        String s3 = LineWrap.dpBU(s, 19);
        System.out.println(s1);
        System.out.println(" ");
        System.out.println(s2);
        System.out.println(" ");
        System.out.println(s3);
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
    }

    @Test
    public void test2 () throws NoSuchElementE {
        String s = "However, scientists were disappointed when the data reflected ten times less water than anticipated. Enter NASA's Juno mission, which launched in 2011 and began conducting scientific flybys of the planet in 2016. Scientists have used data from the first eight flybys to determine the amount of water in Jupiter's atmosphere at the equator, according to a new study. The Juno mission was in part motivated by the need to determine the water abundance at multiple locations across the planet, the authors wrote in the study, which published recently in the journal Nature Astronomy.";
        String s1 = LineWrap.runGreedy(s, 25);
        String s2 = LineWrap.runDP(s, 25);
        String s3 = LineWrap.dpBU(s, 25);
        System.out.println(s1);
        System.out.println(" ");
        System.out.println(s2);
        System.out.println(" ");
        System.out.println(s3);
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
    }

    @Test
    public void test3 () throws NoSuchElementE {
        String s = "To be clear, water doesn't necessarily mean liquid water, but its components of hydrogen and oxygen. Understanding the amount of water on Jupiter can provide more information about the gas giant's past. The largest planet in our solar system was also likely the first to form after the sun. The theory of planet formation suggests that Jupiter received the bulk of the gas and dust leftover from our star.";
        String s1 = LineWrap.runGreedy(s, 128);
        String s2 = LineWrap.runDP(s, 128);
        String s3 = LineWrap.dpBU(s, 128);
        System.out.println(s1);
        System.out.println(" ");
        System.out.println(s2);
        System.out.println(" ");
        System.out.println(s3);
    }
}