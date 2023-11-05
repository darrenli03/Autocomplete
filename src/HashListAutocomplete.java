import java.util.*;

public class HashListAutocomplete implements Autocompletor {
    private static final int MAX_PREFIX = 10;
    private Map<String, List<Term>> myMap;
    private int mySize;

    public HashListAutocomplete(String[] terms, double[] weights) {
        if (terms == null || weights == null) {
            throw new NullPointerException("One or more arguments null");
        }

        initialize(terms,weights);
    }

    @Override
    public List<Term> topMatches(String prefix, int k) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void initialize(String[] terms, double[] weights) {
        myMap = new HashMap<String, List<Term>>();
        //iterate over all terms (and corresponding weights)
        for(int i=0; i<terms.length; i++){
            Term currentTerm = new Term(terms[i], weights[i]);
            //add term to each possible prefix key in map
            for(int j=0; j <= Math.min(MAX_PREFIX, currentTerm.getWord().length()); j++){
                String prefix = (j==0) ? "" : currentTerm.getWord().substring(0, j);
                myMap.getOrDefault(prefix, new ArrayList<Term>()).add(currentTerm);
            }
        }
    }

    @Override
    public int sizeInBytes() {
        // TODO Auto-generated method stub
        return 0;
    }  
}

