public class Iterative {

    public static BitList complement( BitList in ) {

        BitList out = new BitList();

        Iterator inIterator = in.iterator();
        Iterator outIterator = out.iterator();

        if (!inIterator.hasNext()) {
            outIterator.add(BitList.ONE);
        } 
        else {
            while (inIterator.hasNext()) {
                int bit = inIterator.next();
                if (bit == BitList.ONE) {
                    outIterator.add(BitList.ZERO);
                } 
                else {
                    outIterator.add( BitList.ONE );
                }
            }

        }
        return out;
    }

    public static BitList or( BitList a, BitList b ) {

        BitList out = new BitList();

        Iterator inIterator = a.iterator();
        Iterator otherIterator = b.iterator();

        Iterator outIterator = out.iterator();

        if (!inIterator.hasNext()) {
             throw new IllegalArgumentException("An empty list cannot be ORed");
        }
        if (!otherIterator.hasNext()){
             throw new IllegalArgumentException("An empty list cannot be ORed");
        }

        while (inIterator.hasNext()) {

            if (!otherIterator.hasNext()) {
                throw new IllegalArgumentException("List b is shorter than list a");
            }

            int inBit = inIterator.next();
            int otherBit = otherIterator.next();

            if (inBit == BitList.ONE || otherBit == BitList.ONE) {
                outIterator.add(BitList.ONE);
            } 
            else {
                outIterator.add(BitList.ZERO);
            }
        }

        if (otherIterator.hasNext()) {
            throw new IllegalArgumentException("List b is longer than list a");
        }
        return out;
    }

	public static BitList and( BitList a, BitList b ) {
            
            BitList out = new BitList();
    
            Iterator inIterator = a.iterator();
            Iterator otherIterator = b.iterator();
    
            Iterator outIterator = out.iterator();
    
            if (!inIterator.hasNext()) {
                throw new IllegalArgumentException("An empty list cannot be ANDed");
            }
            if (!otherIterator.hasNext()){
                throw new IllegalArgumentException("An empty list cannot be ANDed");
            }
    
            while (inIterator.hasNext()) {
    
                if (!otherIterator.hasNext()) {
                    throw new IllegalArgumentException("List b is shorter than list a");
                }
    
                int inBit = inIterator.next();
                int otherBit = otherIterator.next();
    
                if (inBit == BitList.ONE && otherBit == BitList.ONE) {
                    outIterator.add(BitList.ONE);
                } 
                else {
                    outIterator.add(BitList.ZERO);
                }
            }
    
            if (otherIterator.hasNext()) {
                throw new IllegalArgumentException("List b is longer than list a");
            }

            return out;
    }

	public static BitList xor( BitList a, BitList b ) {

        BitList out = new BitList();

        Iterator inIterator = a.iterator();
        Iterator otherIterator = b.iterator();

        Iterator outIterator = out.iterator();

        if (!inIterator.hasNext()) {
            throw new IllegalArgumentException("An empty list cannot be XORed");
        }

        if (!otherIterator.hasNext()){
            throw new IllegalArgumentException("An empty list cannot be XORed");
        }

        while (inIterator.hasNext()) {

            if (!otherIterator.hasNext()) {
                throw new IllegalArgumentException("List b is shorter than list a");
            }

            int inBit = inIterator.next();
            int otherBit = otherIterator.next();

            if (inBit == BitList.ONE && otherBit == BitList.ZERO) {
                outIterator.add(BitList.ONE);
            } 
            else if (inBit == BitList.ZERO && otherBit == BitList.ONE) {
                outIterator.add(BitList.ONE);
            } 
            else {
                outIterator.add(BitList.ZERO);
            }
        }

        if (otherIterator.hasNext()) {
            throw new IllegalArgumentException("List b is longer than list a");
        }

        return out;
    }
}

