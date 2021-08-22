import java.util.HashMap;

class NoChange extends Exception {}

public class Main {

    public static void main (String[] args) throws NoChange {
        List<Coin> scoins =
                new NodeL<>(new Coin(25),
                        new NodeL<>(new Coin(10),
                                new NodeL<>(new Coin(5),
                                        new NodeL<>(new Coin(1),
                                                new EmptyL<>()))));

        System.out.println();

        HashMap<Coin,Integer> hash = new HashMap<>();
        makeChangeGreedy(hash,3, scoins);
        System.out.printf("Making change for %d using usual coins: %s%n",3, hash);

        hash.clear();
        makeChangeGreedy(hash,7, scoins);
        System.out.printf("Making change for %d using usual coins: %s%n",7, hash);

        hash.clear();
        makeChangeGreedy(hash,44, scoins);
        System.out.printf("Making change for %d using usual coins: %s%n",44, hash);

        hash.clear();
        makeChangeGreedy(hash,117, scoins);
        System.out.printf("Making change for %d using usual coins: %s%n",117, hash);

        System.out.println();

        List<Coin> wcoins =
                new NodeL<>(new Coin(17),
                        new NodeL<>(new Coin(7),
                                new NodeL<>(new Coin(3),
                                        new EmptyL<>())));

        hash.clear();
        makeChangeGreedy(hash,3, wcoins);
        System.out.printf("Making change for %d using weird coins: %s%n",3, hash);

        hash.clear();
        makeChangeGreedy(hash,7, wcoins);
        System.out.printf("Making change for %d using weird coins: %s%n",7, hash);

        hash.clear();
        makeChangeGreedy(hash,44, wcoins);
        System.out.printf("Making change for %d using weird coins: %s%n",44, hash);

        try {
            hash.clear();
            makeChangeGreedy(hash, 117, wcoins);
            System.out.printf("Making change for %d using weird coins: %s%n", 117, hash);
        }
        catch (NoChange e) {
            System.out.printf("Failed to make change for %d using weird coins%n", 117);
        }

        System.out.println();

        hash.clear();
        makeChangeSearch(hash,3, wcoins);
        System.out.printf("Making change for %d using weird coins: %s%n",3, hash);

        hash.clear();
        makeChangeSearch(hash,7, wcoins);
        System.out.printf("Making change for %d using weird coins: %s%n",7, hash);

        hash.clear();
        makeChangeSearch(hash,44, wcoins);
        System.out.printf("Making change for %d using weird coins: %s%n",44, hash);

        hash.clear();
        makeChangeSearch(hash,117, wcoins);
        System.out.printf("Making change for %d using weird coins: %s%n",117, hash);

    }


    // given list of coins sorted from largest to smallest, add up to the given amount
    // use the greedy approach to do this
    static void makeChangeGreedy (HashMap<Coin,Integer> result, int amount, List<Coin> coins) throws NoChange {
        try {
            int numUsed = 0;
            Coin first = coins.getFirst();
            while (first.getValue() <= amount) {
                numUsed += 1;
                amount -= first.getValue();
                result.put(first, numUsed);
            }
            if (!coins.getRest().isEmpty()) {
                makeChangeGreedy(result, amount, coins.getRest());
            }
        } catch (NoSuchElementE e) {
            throw new NoChange();
        }
    }


    // given list of coins sorted from largest to smallest, add up to the given amount
    // use dynamic programming to do this
    static void makeChangeSearch (HashMap<Coin,Integer> result, int amount, List<Coin> coins) throws NoChange {
        try {
            Coin first = coins.getFirst();
            if (first.getValue() <= amount) {
                try {
                    if (result.get(first) == null){
                        result.put(first, 1);
                    } else{
                        result.put(first, result.get(first)+1);
                    }
                    amount -= first.getValue();
                    makeChangeSearch(result, amount, coins);

                } catch (Exception e) {
                    result.put(first, result.get(first)-1);
                    makeChangeSearch(result, amount, coins.getRest());
                }
            } else{
                makeChangeSearch(result, amount, coins.getRest());
            }
        }
        catch (NoSuchElementE e ){
            throw new NoChange();
        }

        catch(Exception e) {
            if (amount != 0){
                throw new NoChange();
            }

        }
    }
}
