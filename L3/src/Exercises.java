import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;


// ------------------------------------------------------------------------
// Solve the followign 5 eercises using the five approaches.
// Examples and explanations of the five approaches can be found in Main.java.
// ------------------------------------------------------------------------

public class Exercises {
    private static List<Integer> ints = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,2,8,8,8));

    // ------------------------------------------------------------------------
    // Exercise I: multiply the elements in the list
    //
    // Thinking point:
    // Take note that there isn't a method using the map-reduce approach. Why?
    // ------------------------------------------------------------------------

    static int mul1 () {

        int result = 1;
        if(ints.size() ==0){
            result =0;
        }
        for(int i = 0;i < ints.size();i++){
            result = result * ints.get(i);
        }
        return result;
    }


    static int mul2 () {

        int result = 1;
        if(ints.size() ==0){
            result =0;
        }
        for (int x: ints){
            result = x *result;
        }
        return result;
    }


    static int mul3 () {
        ListIterator<Integer> iterator = ints.listIterator();
        int result = 1;
        if(ints.size() ==0){
            result =0;
        }
        while(iterator.hasNext()){
            int x = iterator.next();
            result = x *result;
        }
        return result;
    }


    static int mul5 () {
	    if(ints.size() != 0) {
            int result = 1;// = ints.stream().reduce(1, (x, y))->x
            return result;
        }else {
            return 0;
        }
    }

    // ------------------------------------------------------------------------
    // Exercise II: check if all elements in the list are even
    // ------------------------------------------------------------------------

    static boolean even1 () {
        boolean flag = true;
        for(int i = 0;i < ints.size();i++){
            if (ints.get(i)%2==0){
                flag = true;
            }else{
                flag = false;
            }
        }
        return flag;
    }

    static boolean even2 () {
        for(int x: ints){
            if(x%2 !=0){
                return false;
            }
        }
        return true;
    }

    static boolean even3 () {
        ListIterator<Integer> iterator = ints.listIterator();
        while(iterator.hasNext()){
            int x = iterator.next();
            if(x%2 !=0){
                return false;
            }
        }return true;
    }


    static boolean even4 () {
	//map-reduce approach
        return false;
    }

    static boolean even5 () {
	//reduce approach
        return false;
    }

    // ------------------------------------------------------------------------
    // Exercise III: compute the maximum number in the list
    //
    // Thinking point:
    // Take note that there isn't a method using the map-reduce approach. Why?
    // ------------------------------------------------------------------------

    static int max1() {
        int max = 0;
        for(int i = 0; i < ints.size(); i++){
            if(ints.get(i) > max){
                max = ints.get(i);
            }
        }
        return max;
    }


    static int max2() {
        int max = 0;
        for(int x: ints){
            if(x > max){
                max = x;
            }
        }
        return max;
    }


    static int max3 () {
        ListIterator<Integer> iterator = ints.listIterator();
        int max = 0;
        while(iterator.hasNext()) {
            int x = iterator.next();
            if (x > max) {
                max = x;
            }
        }
        return max;
    }

    static int max5 () {
	//reduce approach
        return 0;
    }

    // ------------------------------------------------------------------------
    // Exercise IV: count occurrences of c in the list
    // ------------------------------------------------------------------------

    static int count1 (int c) {
        int num = 0;
        for (int i = 0; i < ints.size(); i++) {
            if (ints.get(i) == c) {
                num += 1;
            }
        }return num;
    }

    static int count2 (int c) {
        int num =0;
        for(int x: ints){
            if(x == c){
                num += 1;
            }
        }return num;
    }

    static int count3 (int c) {
        ListIterator<Integer> iterator = ints.listIterator();
        int num = 0;
        while(iterator.hasNext()) {
            int x = iterator.next();
            if (x == num) {
                num += 1;
            }
        }
        return num;
    }

    static int count4 (int c) {
	//map-reduce approach
        return 0;
    }

    static int count5 (int c) {
	//reduce approach
        return 0;
    }

    // ------------------------------------------------------------------------
    // Exercise V: triplicate each element in the list
    //
    // Thinking point:
    // Take note that there isn't a solution for this using the reduce approach. Why?
    // ------------------------------------------------------------------------
 /*
    static List<Integer> trip1 () {
        //for loop approach
        return new List<Integer>();
    }

    static List<Integer> trip2 () {
        //foreach approach
        return new List<Integer>();
    }

    static List<Integer> trip3 () {
        //iterator approach
        return new List<Integer>();
    }

    static List<Integer> trip4 () {
	//map-reduce approach
        return new List<Integer>();
    }
*/
}
