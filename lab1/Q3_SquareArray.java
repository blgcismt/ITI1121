public class Q3_SquareArray{

	public static int[] createArray(int size) {
        int[] anArray = new int[size];
        for (int i = 0; i < size; i++) {
            anArray[i] = i * i;
        }
        return anArray;
	}

	public static void main(String[] args){
        createArray(12);
	}
}
