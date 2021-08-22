import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

class LineWrap {
  private int lineWidth;

  LineWrap (int lineWidth) {
      this.lineWidth = lineWidth;
  }

  //-------------------------------------------------------------------------
  /**
   * The greedy implementation keeps consuming words from the given list
   * of words while keeping track of how much space is left on the current line.
   * If the current word with a preceding space would fit on the current line, the
   * algorithm continues with the remaining words and an adjusted space.
   * If the word preceded by a space does not fit on the current line, a new line
   * is inserted instead and the algorithm continues with the rest of the words and
   * an adjusted space.
  */
  String greedy (List<String> words, int space){
      try{
          String word = words.getFirst();
          int length = word.length();
          List<String> rest = words.getRest();

          if (space >= length + 1){
              return " " + word + greedy(rest, space - length -1);
          }else{
              return "\n" + word + greedy(rest, lineWidth - length);
          }
      }catch (Exception E){
          return "";
      }
  }

  /**
   * A simple way of running the greedy algorithm
   */
  static String runGreedy (String s, int lineWidth) throws NoSuchElementE {
        List<String> words = List.fromArray(s.split("\\s"));
        LineWrap wrap = new LineWrap(lineWidth);
        String w = words.getFirst();
        String para = w + wrap.greedy(words.getRest(), lineWidth - w.length());
        return para;
    }

  //-------------------------------------------------------------------------

  /** A hash table for use with the top down dynamic programming solution */
  static Map<Pair<List<String>,Integer>,Pair<String,Integer>> hash = new HashMap<>();

  /**
   * The greedy algorithm always adds words to the current line as long as they
   * would fit. The dynamic programming algorithm instead considers two options
   * for each word: add it to the current line, or insert a new line before
   * the word. For each possibility, an estimate of "badness" is calculated
   * and the best option is chosen. The "badness" of a solution is the sum
   * of the cubes of the amount of space left on each line (except the last line).
   * For example, if the line width is 6 and we have the following text:
   *
   * 1234
   * 12 45
   * 123
   * 12
   *
   * then we calculate the badness as follows: the first line has 2 unused spaces
   * at the end, the next line has 1, and the third has 3. The final line is perfect
   * by definition. So the "badness" estimate is:
   * 2^3 + 1^3 + 3^3 = 8 + 1 + 27 = 36
   *
   * In contrast if the text was:
   *
   * 1234
   * 12 45
   * 123 12
   *
   * the "badness" would be: 2^3 + 1^3 = 8 + 1 = 9
   *
   * so we prefer the second arrangement.
   *
   * I strongly suggest you first write a plain recursive solution and test it on the small
   * example (test1). Once that words properly, you can add the hash table management to
   * get your final dynamic programming solution. Without the hash table, the algorithm
   * will really not work on even a moderate size paragraph.
  */
  Pair<String,Integer> dpTD(List<String> words, int space) {
      Pair<List<String>,Integer> a = new Pair<>(words,space);
      if (hash.containsKey(a)){
          return hash.get(a);
      }
      Pair<String, Integer> result = null;
      try {
          Pair<String, Integer> x1;
          Pair<String, Integer> x2;
          Pair<String, Integer> x3;
          String word = words.getFirst();
          List<String> rest = words.getRest();

          if (word.length() + 1 > space) {
              x1 = dpTD(rest, lineWidth - word.length());
              result = new Pair<>("\n" + word + x1.getFst(), space*space*space + x1.getSnd());

          } else {
              // new line
              x2 = dpTD(rest, lineWidth - word.length());
              int c1 = space*space*space + x2.getSnd();

              // same line
              x3 = dpTD(rest, space - word.length() - 1);

              if (c1 <= x3.getSnd()) {
                  result = new Pair<>("\n" + word + x2.getFst(), space*space*space + x2.getSnd());
              } else {
                  result = new Pair<>(" " + word + x3.getFst(), x3.getSnd());
              }
          }
          hash.put(a, result);
          return result;

      } catch (Exception E) {
        return new Pair<>("",0);
      }
  }



  /**
   * A simple way of running the dynamic programming algorithm
   */
  static String runDP (String s, int lineWidth) throws NoSuchElementE {
      hash = new WeakHashMap<>();
      List<String> words = List.fromArray(s.split("\\s"));
      LineWrap wrap = new LineWrap(lineWidth);
      String w = words.getFirst();
      Pair<String, Integer> sub = wrap.dpTD(words.getRest(), lineWidth - w.length());
      String para = w + sub.getFst();
      return para;
  }

    //-------------------------------------------------------------------------

  /**
   * Here you are to implement the dynamic programming algorithm in a bottom-up fashion.
   * You should still use a hash table as shown below but its management is done
   * "manually" not implicitly when entering / exiting recursive calls.
   */
  static String dpBU (String s, int lineWidth) {
      Map<Pair<Integer, Integer>, Pair<String, Integer>> hash = new HashMap<>();
      String[] words = s.split("\\s");

      int cost1 = 0;
      int cost2 = 0;
      int cost3 = 0;

      int length = words.length;

      Pair<String, Integer> x1;
      Pair<String, Integer> x2;
      Pair<String, Integer> x3;

      for (int i = 0; i < lineWidth; i++) {

          Pair<Integer, Integer> pair1 = new Pair<Integer, Integer>(words.length, i);
          Pair<String, Integer> pair2 = new Pair<String, Integer>("", 0);
          hash.put(pair1, pair2);
      }

      for (int index = length - 1;  index > 0; index--) {

          String word = words[index];
          for (int space = 0; space <= lineWidth; space++) {

              if (word.length() + 1 > space) {
                  x1 = hash.get(new Pair(index + 1, lineWidth - word.length()));
                  cost1 = x1.getSnd() + (int) Math.pow(space, 3);

                  hash.put(new Pair(index, space), new Pair("\n" + word + x1.getFst(), cost1));

              } else {
                  x2 = hash.get(new Pair(index + 1, lineWidth - word.length()));
                  cost2 = x2.getSnd() + space * space * space;

                  x3 = hash.get(new Pair(index + 1, space - word.length()-1));
                  cost3 = x3.getSnd();

                  if (cost2 <= cost3) {
                      hash.put(new Pair<Integer, Integer>(index, space),
                              new Pair<String, Integer>("\n" + word + x2.getFst(), cost2));
                  } else {
                      hash.put(new Pair<Integer, Integer>(index, space),
                              new Pair<String, Integer>( " " + word + x3.getFst(), cost3));
                  }
              }
          }
      }
      String word = words[0];
      Pair<String, Integer> rest = hash.get(new Pair<>(1, (lineWidth - word.length())));
      return word + rest.getFst();
  }
}
