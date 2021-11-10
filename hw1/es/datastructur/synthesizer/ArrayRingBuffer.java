package es.datastructur.synthesizer;
import java.awt.geom.AffineTransform;
import java.util.Iterator;

//TODO: Make sure to that this class and all of its methods are public
//TODO: Make sure to add the override tag for all overridden methods
//TODO: Make sure to make this class implement BoundedQueue<T>

public class ArrayRingBuffer<T> implements BoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;
    private int capacity;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int cap) {
        // TODO: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        first = 0;
        last = 0;
        fillCount = 0;
        capacity = cap;
        rb = (T[]) new Object[capacity];
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    @Override
    public void enqueue(T x) {
        // TODO: Enqueue the item. Don't forget to increase fillCount and update
        //       last.
        if (isFull()) {
            throw new RuntimeException("Ring buffer overflow.");
        }
        rb[last] = x;
        last++;
        if (last == capacity()) {
            last = 0;
        }
        fillCount++;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T dequeue() {
        // TODO: Dequeue the first item. Don't forget to decrease fillCount and
        //       update first.
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow.");
        }
        T result = rb[first];
        first++;
        if (first == capacity()) {
            first = 0;
        }
        fillCount--;
        return result;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T peek() {
        // TODO: Return the first item. None of your instance variables should
        //       change.
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow.");
        }
        return rb[first];
    }

    @Override
    public int capacity() {
        return capacity;
    }

    @Override
    public int fillCount() {
        return fillCount;
    }

    // TODO: When you get to part 4, implement the needed code to support
    //       iteration and equals.
    @Override
    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }

    private class ArrayRingBufferIterator implements Iterator<T>{
        private int wizPos;

        public ArrayRingBufferIterator() {
            wizPos = first;
        }
        public boolean hasNext() {
            if (last >= first) {
                return (wizPos < last);
            }
            else {
                return (wizPos < last + capacity);
            }
        }

        public T next() {
            T returnItem = rb[wizPos];
            wizPos++;
            if (wizPos == capacity) {
                wizPos = 0;
            }
            return returnItem;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (o.getClass() !=  this.getClass()) {
            return false;
        }
        ArrayRingBuffer<T> oo = (ArrayRingBuffer<T>) o;
        if (oo.capacity() != this.capacity()) {
            return false;
        }
        if (oo.fillCount() != this.fillCount()) {
            return false;
        }
        for (T item : this) {
            boolean flag = false;
            for (T itemOther : oo) {
                if (item == itemOther) {
                    flag = true;
                }
            }
            if (!flag) {
                return false;
            }
        }
        return false;
    }
}
    // TODO: Remove all comments that say TODO when you're done.
