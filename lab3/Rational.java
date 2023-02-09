public class Rational {

    private int numerator;
    private int denominator;

    // constructors

    public Rational(int numerator) {
	    this.numerator = numerator;
        this.denominator = 1;
    }

    public Rational(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
        reduce();
    }

    // getters

    public int getNumerator() {
	     return numerator;
    }

    public int getDenominator() {
	     return denominator;
    }

    // instance methods

    public Rational plus(Rational other) {
        return plus(this, other);
    }

    public static Rational plus(Rational a, Rational b) {
        int numerator = (a.numerator * b.denominator) + (b.numerator * a.denominator);
        int denominator = a.denominator * b.denominator;
        return new Rational(numerator, denominator);
    }

    // Transforms this number into its reduced form

    private void reduce() {
        int gcd = gcd(numerator, denominator);
        numerator /= gcd;
        denominator /= gcd;
    }

    // Euclid's algorithm for calculating the greatest common divisor
    private int gcd(int a, int b) {
      // Note that the loop below, as-is, will time out on negative inputs.
      // The gcd should always be a positive number.
      // Add code here to pre-process the inputs so this doesn't happen.
  
        if (a < 0){
            a = -a;
        }
        if (b < 0){
            b = -b;
        }
        while (a != b) {
            if (a > b) {
                a = a - b;
            } 
            else {
                b = b - a;
            }
        }
        return a;
    }

    public int compareTo(Rational other) {
        int compResult = 0;
        int a = numerator * other.denominator;
        int b = denominator * other.numerator;
        if (a < b) {
            compResult = -1;
        } else if (a > b) {
            compResult = 1;
        }
        return compResult;
    }

    public boolean equals(Rational other) {
        return compareTo(other) == 0;
    }

    public String toString() {
    	String fraction;
    	if (denominator == 1) {
            fraction = "" + numerator;
    	} 
        else {
    	    fraction = numerator + "/" + denominator;
    	}
    	return fraction;
    }

}
