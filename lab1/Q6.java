public class Q6{
	public static void main(String[] args){
        double[] notes = new double[10];
        for(int index = 0; index < notes.length; index++){
            System.out.println("Enter the note of the student " + (index+1));
            notes[index] = Double.parseDouble(System.console().readLine());
        }
        System.out.println("The average is " + calculateAverage(notes));
        System.out.println("The median  is " + calculateMedian(notes));
        System.out.println("The number of students that failed is " + calculateNumberFailed(notes));
        System.out.println("The number of students that passed is " + calculateNumberPassed(notes));


	}

	public static double calculateAverage(double[] notes){
        double sum = 0;
        for(int index = 0; index < notes.length; index++){
            sum += notes[index];
        }
        return sum/notes.length;
	}

	public static double calculateMedian(double[] notes){
        double median = 0;
        for(int index = 0; index < notes.length; index++){
            for(int index2 = 0; index2 < notes.length; index2++){
                if(notes[index] < notes[index2]){
                    double temp = notes[index];
                    notes[index] = notes[index2];
                    notes[index2] = temp;
                }
            }
        }
        if(notes.length % 2 == 0){
            median = (notes[notes.length/2] + notes[notes.length/2 - 1])/2;
        } else {
            median = notes[notes.length/2];
        }
        return median;

	}

	public static int calculateNumberFailed(double[] notes){
        int count = 0;
        for(int index = 0; index < notes.length; index++){
            if(notes[index] < 50){
                count++;
            }
        }
        return count;

	}

	public static int calculateNumberPassed(double[] notes){
        int count = 0;
        for(int index = 0; index < notes.length; index++){
            if(notes[index] >= 50){
                count++;
            }
        }
        return count;

	}

}
