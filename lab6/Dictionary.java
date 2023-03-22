import java.security.DrbgParameters.Reseed;

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
        throw new IllegalArgumentException("Key and value must not be null");
      }
      if (count == elems.length) {
        increaseCapacity();
      }
      elems[count] = new Pair(key, value);
      count++;
    }

    private void increaseCapacity() {
      Pair[] newElems = new Pair[elems.length + INCREMENT];
      for (int i = 0; i < elems.length; i++) {
        newElems[i] = elems[i];
      }
      elems = newElems;
    }

    @Override
    public boolean contains(String key) {
      if (key == null) {
        throw new IllegalArgumentException("Key must not be null");
      }
      for (int i = count - 1; i >= 0; i--) {
        if (elems[i].getKey().equals(key)) {
          return true;
        }
      }
      return false;
    }

    @Override

    public Integer get(String key) {
      if (key == null) {
        throw new IllegalArgumentException("Key must not be null");
      }
      for (int i = count - 1; i >= 0; i--) {
        if (elems[i].getKey().equals(key)) {
          return elems[i].getValue();
        }
      }
      return null;
    }

    @Override
    public void replace(String key, Integer value) {
      if (key == null || value == null) {
        throw new IllegalArgumentException("Key and value must not be null");
      }
      for (int i = count - 1; i >= 0; i--) {
        if (elems[i].getKey().equals(key)) {
          elems[i].setValue(value);
        }
      }
    }

    @Override
    public Integer remove(String key) {
      if (key == null) {
        throw new IllegalArgumentException("Key must not be null");
      }
      for (int i = count - 1; i >= 0; i--) {
        if (elems[i].getKey().equals(key)) {
          Integer value = elems[i].getValue();
          elems[i] = null;
          count--;
          return value;
        }
      }
      return null;
    }

    @Override
    public String toString() {
      String result = "";
      for (int i = count - 1; i >= 0; i--) {
        result += elems[i].toString() + " ";
      }
      return result;
    }
}
