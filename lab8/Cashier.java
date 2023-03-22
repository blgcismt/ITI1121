public class Cashier {
    
    private Queue<Customer> queue;
    private Customer currentCustomer;
    private int totalCustomerWaitTime;
    private int totalItemsServed;
    private int totalCustomersServed;
    
    public Cashier() {
        queue = new ArrayQueue<Customer>();
        currentCustomer = null;
        totalCustomerWaitTime = 0;
        totalItemsServed = 0;
        totalCustomersServed = 0;
    }

    public void addCustomer(Customer c){
        queue.enqueue(c);
    }
    
    public int getQueueSize(){
        return queue.size();
    }

    public void serveCustomers(int currentTime){
        if (currentCustomer == null) {
            if (!queue.isEmpty()) {
                currentCustomer = queue.dequeue();
                totalCustomerWaitTime += currentTime - currentCustomer.getArrivalTime();
            }
        }
        if (currentCustomer != null) {
            currentCustomer.serve();
            if (currentCustomer.getNumberOfItems() == 0) {
                totalItemsServed += currentCustomer.getNumberOfServedItems();
                totalCustomersServed++;
                currentCustomer = null;
            }
        }
    }
    
    public int getTotalCustomerWaitTime() {
        return totalCustomerWaitTime;
    }
    
    public int getTotalItemsServed() {
        return totalItemsServed;
    }
    
    public int getTotalCustomersServed() {
        return totalCustomersServed;
    }

    public String toString() {
        String info = "";
        info += "The total number of customers served is " + totalCustomersServed + "\n";
        info += "The average number of items per customer was " + (totalItemsServed / totalCustomersServed) + "\n";
        info += "The average waiting time (in seconds) was " + (totalCustomerWaitTime / totalCustomersServed) + "\n";
        return info;
    }
}
