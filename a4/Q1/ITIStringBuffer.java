public class ITIStringBuffer {

    private SinglyLinkedList<String> list = new SinglyLinkedList<>(); // initialize the list to be used to store the strings
    private int length; // initialize the length
    private String keepResult; // initialize the result of the toString method to be kept
    private boolean isNew; // initialize the isNew boolean to check for subsequent calls to toString()


    public ITIStringBuffer() {
        list.add(""); // add an empty string to the list
        length = 0; // set the length to 0
        isNew = true; // set the boolean to true
    }

    public ITIStringBuffer(String firstString) {
        if (firstString == null) { // if the string is null
            throw new IllegalArgumentException("String cannot be null"); // throw an exception
        }
        list.add(firstString); // add the first string to the list
        length = firstString.length(); // set the length
        isNew = true; // set the boolean to false
    }

    public void append(String nextString) {
        if (nextString == null) { // if the string is null
            throw new IllegalArgumentException("String cannot be null"); // throw an exception
        }
        isNew = true; // set the boolean to true
        list.add(nextString); // add the next string to the list
        length += nextString.length(); // add the length of the next string to the length
    }


    public String toString() {
        if (isNew) { // if the list is empty
            char[] result = new char[length]; // create a new char array with the length of the string
            int index = 0;
            for (String s : list) { // for each string in the list
                char[] chars = s.toCharArray(); // convert the string to a char array
                for (char c : chars) { // for each char in the char array
                    result[index] = c; // add the char to the result array
                    index++;
                }
            }
            keepResult = new String(result); // convert the char array to a string
            isNew = false; // set the boolean to false
        } 

        return keepResult; // return the result
        
    }
}
