import java.util.Random;

public class Customer {
    
        private int arrivalTime;
        private int initialnumberOfItems;
        private int numberOfItems;
        private int MAX_NUM_ITEMS = 25;

        public Customer(int arrivalTime) {
            this.arrivalTime = arrivalTime;
            Random random = new Random();
            initialnumberOfItems = random.nextInt(MAX_NUM_ITEMS-1) + 1;
            numberOfItems = initialnumberOfItems;
        }
    
        public int getArrivalTime() {
            return arrivalTime;
        }
    
        public int getNumberOfItems() {
            return numberOfItems;
        }
    
        public int getNumberOfServedItems() {
            return initialnumberOfItems - numberOfItems;
        }
    
        public void serve() {
            numberOfItems--;
        }
}
