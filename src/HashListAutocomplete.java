import java.util.*;

public class HashListAutocomplete implements Autocompletor {
    private static final int MAX_PREFIX = 10;
    private Map<String, List<Term>> myMap;
    private int mySize;

    public HashListAutocomplete(String[] terms, double[] weights) {
        if (terms == null || weights == null) {
            throw new NullPointerException("One or more arguments null");
        }

        initialize(terms, weights);
    }

    @Override
    public List<Term> topMatches(String prefix, int k) {
        if (prefix.length() > MAX_PREFIX) prefix = prefix.substring(0, MAX_PREFIX);
        if (myMap.containsKey(prefix)) {
            return myMap.get(prefix).subList(0, Math.min(k, myMap.get(prefix).size()));
        } else {
            //returning empty list if myMap doesn't contain key
            return List.of();
        }

    }

    @Override
    public void initialize(String[] terms, double[] weights) throws IllegalArgumentException {
        if (terms.length != weights.length)
            throw new IllegalArgumentException("terms and weights length are not the same");

        myMap = new HashMap<String, List<Term>>();
        //iterate over all terms (and corresponding weights)
        for (int i = 0; i < terms.length; i++) {
            Term currentTerm = new Term(terms[i], weights[i]);
            mySize += terms[i].length() * 2 + 8;
            //add term to each possible prefix key in map
            for (int j = 0; j <= Math.min(MAX_PREFIX, currentTerm.getWord().length()); j++) {
                String prefix = currentTerm.getWord().substring(0, j);

                List<Term> termsAL = myMap.getOrDefault(prefix, new ArrayList<Term>());
                //if we had to add a new prefix (key) to the map, termsAL is empty
                if(termsAL.isEmpty()){
                    mySize += 2 * prefix.length();
                }

                termsAL.add(currentTerm);
                myMap.put(prefix, termsAL);
            }
        }
//        System.out.println(mySize);

        //once map values are added, we need to sort each list by weight, highest weight first
        for (List<Term> list : myMap.values()) {
            Collections.sort(list, Comparator.comparing(Term::getWeight).reversed());
        }
    }

    @Override
    public int sizeInBytes() {
        return mySize;
    }
}

