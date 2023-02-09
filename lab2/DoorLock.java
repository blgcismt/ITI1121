public class DoorLock {

    // Constant.
    public static final int MAX_NUMBER_OF_ATTEMPTS = 3;

    // Instance variables.
    private Combination combination;
    private boolean open;
    private boolean activated;
    private int numberOfAttempts;

    // Constructor.
    public DoorLock( Combination combination) {
        this.combination = combination;
        this.open = false;
        this.activated = true;
        this.numberOfAttempts = 0;
    }

    

    // Access methods.

    public boolean isOpen() {
        return this.open;
    }

    public boolean isActivated() {
        return this.activated;
    }

    // Notice that numberOfAttempts is compared to
    // MAX_NUMBER_OF_ATTEMPTS only when its value has been
    // incremented, Also, numberOfAttempts should be set to zero when
    // activated is false.  Problems related to the combined action of
    // these two variables have caused problems for some students.

    public boolean open(Combination combination){
        if (this.activated){
            if (this.combination.equals(combination)) {
                this.open = true;     
                numberOfAttempts = 0;       
            } 
            else {
                numberOfAttempts++;
                if (numberOfAttempts == MAX_NUMBER_OF_ATTEMPTS) {
                    this.activated = false;
                }
            }
        }
        return open;
    }

    public void activate(Combination c) {
        if (c.equals(this.combination)) {
            this.activated = true;
        }
    }
}
