import java.util.Comparator;

/**
 * Factor pattern for obtaining PrefixComparator objects
 * without calling new. Users simply use
 * <p>
 * Comparator<Term> comp = PrefixComparator.getComparator(size)
 *
 * @author owen astrachan
 * @date October 8, 2020
 */
public class PrefixComparator implements Comparator<Term> {

    private int myPrefixSize; // size of prefix

    /**
     * private constructor, called by getComparator
     *
     * @param prefix is prefix used in compare method
     */
    private PrefixComparator(int prefix) {
        myPrefixSize = prefix;
    }


    /**
     * Factory method to return a PrefixComparator object
     *
     * @param prefix is the size of the prefix to compare with
     * @return PrefixComparator that uses prefix
     */
    public static PrefixComparator getComparator(int prefix) {
        return new PrefixComparator(prefix);
    }


    @Override
    /**
     * Note: implementation is slightly different from one described in readMe, should have same efficiency
     *
     * Use at most myPrefixSize characters from each of v and w
     * to return a value comparing v and w by words. Comparisons
     * should be made based on the first myPrefixSize chars in v and w.
     * @return < 0 if v < w, == 0 if v == w, and > 0 if v > w
     */
    public int compare(Term v, Term w) {

        String wordV = v.getWord();
        String wordW = w.getWord();

        int shorterLength = Math.min(wordV.length(), wordW.length());

        if(shorterLength < myPrefixSize){
            return wordV.compareTo(wordW);
        }

        for(int i=0; i<myPrefixSize; i++){

            char charV = wordV.charAt(i);
            char charW = wordW.charAt(i);

            //detects char where prefixes are not equal, determines which word should go first
            if(charV!=charW){
                return charV - charW;
            }

        }
        //if both prefixes are equal
        return 0;

/*
        String wordV = v.getWord();
//        System.out.println(wordV);
        String wordW = w.getWord();
//        System.out.println(wordW + "\n");

        // if either word is shorter than prefix, compare the words directly
        if (wordV.length() < myPrefixSize || wordW.length() < myPrefixSize) {
//            System.out.println(v.compareTo(w));
//            if(wordV.length() < myPrefixSize && wordW.length() < myPrefixSize){
//                return v.compareTo(w);
//            } else if(wordV.length() < myPrefixSize){
//                return wordV.compareTo(wordW.substring(0,myPrefixSize));
//            } else //wordW.length < myPrefixSize
//            return wordW.compareTo(wordV.substring(0,myPrefixSize));
            return  wordV.compareTo(wordW);

        } else {//prefix shorter or same size as both words
            String prefixV = v.getWord().substring(0, myPrefixSize);
            String prefixW = w.getWord().substring(0, myPrefixSize);
//            System.out.println("used prefixes: " + prefixV.compareTo(prefixW));
            return prefixV.compareTo(prefixW);
        }
*/
    }
}
