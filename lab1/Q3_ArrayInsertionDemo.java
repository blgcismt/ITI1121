public class Q3_ArrayInsertionDemo{

	public static int[] insertIntoArray(int[] beforeArray, int indexToInsert, int valueToInsert){

        int[] newArray = new int[beforeArray.length + 1];

        for (int index = 0; index < indexToInsert; index++){
            newArray[index] = beforeArray[index];
        }

        newArray[indexToInsert] = valueToInsert;

        for (int index = indexToInsert + 1; index < newArray.length; index++){
            newArray[index] = beforeArray[index - 1];
        }

        return newArray;
	}

	public static void main(String[] args){
        
        
        System.out.println("Original Array: ");
        int[] array = new int[]{1,5,4,7,9,6};
        for (int index = 0; index < array.length; index++){
            System.out.print(array[index] + " ");
        }

        int[] newArray = insertIntoArray(array, 3, 15);


        System.out.println();

        System.out.println("New Array: ");
        for (int index = 0; index < newArray.length; index++){
            System.out.print(newArray[index] + " ");
        }
        
	}
}
