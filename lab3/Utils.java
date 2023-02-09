/**
* This class contains a method that replaces words in a given array of Strings.
* The words to be replaced are specified in a second array of Strings. The	
* replacement words are specified in a third array of Strings. The method
* returns a copy of the array of Strings where each word occurring in the
* second array has been replaced by the word occurring at the corresponding
* position in the third array.
*
* @author  Ismet Bilgic
*/

public class Utils {

    /**
     * Returns a copy of the array 'in' where each word occurring in the array
     * 'what' has been replaced by the word occurring in the same position
     * in the array 'with'.
     *
     * @param in an array of Strings;
     * @param what an array of words to be replaced;
     * @param with an array of replacement words;
     * @return a new array idententical to 'in' except that all the occurrences of words
     * found in 'what' have been replaced by the corresponding word from 'with'.
     */
    // Complete the implementation of the (static) class method String[] findAndReplace(String[] in, String[] what, String[] with) of the class Utils. The method returns a copy of the array in where each word occurring in the array what has been replaced by the word occurring at the corresponding position in the array with. The array designated by in must remain unchanged. If there are duplicate words in the array what, simply use the first instance you find.

    public static String[] findAndReplace( String[] in, String[] what, String[] with ) {

        String[] out = null; // The new array to be returned
	    boolean valid = true; // True if the pre-conditions are satistified

      	// Testing pre-conditions

      	if (in == null || what == null || with == null) {
      	    valid = false;
      	} 
		else {
      	    for (int index=0; index<in.length; index++){
      		      if (in[index] == null){
      			        valid = false;
      			        break;
      		      }
      	    }

      		for (int index=0; index<what.length; index++){
      			    if (what[index] == null){
      				 valid = false;
      				break;
      			    }
      		}


      		for (int index=0; index<with.length; index++){
      			    if (with[index] == null){
      				valid = false;
      				break;
      			    }
      		}

      		if (what.length != with.length){
      				valid = false;
      		}
      	}

      	if (valid){
                out = new String[in.length];
                for (int index=0; index<in.length; index++){
                    out[index] = in[index];
                    for (int wordIndex=0; wordIndex<what.length; wordIndex++){
                        if (out[index].equals(what[wordIndex])){
                            out[index] = with[wordIndex];
                            break;
                        }
                    }
                }
      	}
        // Returning a reference to the newly created array that
        // contains the same entries as 'in' except that all the
        // occurrences of words from 'what' have been replaced by
        // their corresponding occurrence from 'with'.
        return out;
    }
}
