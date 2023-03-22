import java.util.NoSuchElementException;

public class Dictionary implements Map<String, Integer> {

    private final static int INITIAL_CAPACITY = 10;
    private final static int INCREMENT = 5;
    private int count;

    private Pair[] elems;

    public int getCount() {
      return count;
    }

    public int getCapacity() {
      return elems.length;
    }

    public Dictionary() {
        elems = new Pair[INITIAL_CAPACITY];
        count = 0;
    }

    @Override
    public void put(String key, Integer value) {
        if (key == null || value == null) {
            throw new NullPointerException("key or value is null");
        }
        if (count >= this.getCapacity()) {
            this.increaseCapacity();
        }
        elems[count] = new Pair(key,value);
        count++;
    }

    private void increaseCapacity() {
        Pair[] temp = new Pair[this.getCapacity()+INCREMENT];
        for (int i = 0; i<elems.length; i++) {
            temp[i] = elems[i];
        }
        elems = temp;
    }

    @Override
    public boolean contains(String key) {
        if (key == null) {
            throw new NullPointerException("key is null");
        }
        for (int i = 0; i<count; i++) {
            if (elems[i].getKey().equals(key)) {
                return true;
            }
        }
        return false;

    }

    @Override
    public Integer get(String key) throws NoSuchElementException {
        if (key == null) {
            throw new NullPointerException("key is null");
        }
        for (int i = count-1; i>=0; i--) {
            if (elems[i].getKey().equals(key)) {
                return elems[i].getValue();
            }
        }
        throw new NoSuchElementException("key not found");
    }

    @Override
    public void replace(String key, Integer value) throws NoSuchElementException {
        if (key == null || value == null) {
            throw new NullPointerException("key or value is null");
        }
        boolean found = false;
        for (int i = 0; i<count; i++) {
            if (elems[i].getKey().equals(key)) {
                elems[i].setValue(value);
                found = true;
            }
        }
        if (!found) {
            throw new NoSuchElementException("key not found");
        }
    }

    @Override
    public Integer remove(String key) throws NoSuchElementException{
        if (key == null) {
            throw new NullPointerException("key is null");
        }
        boolean found = false;
        int index = 0;
        for (int i = 0; i<count; i++) {
            if (elems[i].getKey().equals(key)) {
                found = true;
                index = i;
            }
        }
        if (!found) {
            throw new NoSuchElementException("key not found");
        }
        Integer res = elems[index].getValue();
        for (int i = index; i<count-1; i++) {
            elems[i] = elems[i+1];
        }
        count--;
        return res;
    }

    @Override
    public String toString() {
      String res;
      res = "Dictionary: {elems = [";
      for (int i = count-1; i >= 0 ; i--) {
          res += elems[i];
          if(i > 0) {
              res += ", ";
          }
      }
      return res +"]}";
    }

}
