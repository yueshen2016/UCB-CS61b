public class ArrayDeque<T> {
    private int size;
    private int firstIndex;
    private int lastIndex;
    private T[] items;

    /**Constructor.
     *
     */
    public ArrayDeque() {
        this.firstIndex = 0;
        this.lastIndex = 1;
        size = 0;
        items = (T[]) new Object[8];
    }

    /** Return the real index in the array given the nominal index (nominal
     * index might exceed the length of the array)
     *
     * @param nominalIndex nominal index
     * @param arrayLength length of the array
     * @return real index
     */
    private int realIndex(int nominalIndex, int arrayLength) {
        int mod = nominalIndex % arrayLength;
        if (mod < 0) {
            return mod + arrayLength;
        }
        return mod;
    }

    /**Creates a deep copy of other object.
     *
     * @param other Array list to be copied from
     */

    /*
    public ArrayDeque(ArrayDeque other) {
        this.items = (T[]) new Object[other.items.length];
        this.firstIndex = other.firstIndex;
        this.lastIndex = other.lastIndex;
        this.size = other.size;

        int i = 1;
        while (i <= this.size) {
            items[realIndex(firstIndex + i, items.length)] =
                    (T) other.items[realIndex(firstIndex + i, other.items.length)];
        }
    }
     */

    /** Resize is a helper function, just to make the length of the array two times of before.
     *
     */
    private void resize() {
        T[] ptr = this.items;
        this.items = (T[]) new Object[this.items.length * 2];
        for (int i = 1; i <= this.size; i++) {
            items[realIndex(firstIndex + i, items.length)] =
                    ptr[realIndex(firstIndex + i, ptr.length)];
        }
        this.lastIndex = realIndex(this.firstIndex + this.size + 1, this.items.length);
    }

    /**Adds an item of type T to the front of the deque.
     *
     * @param item to be added
     */
    public void addFirst(T item) {
        if (size == this.items.length) {
            resize();
        }
        this.items[firstIndex] = item;
        this.firstIndex = realIndex(this.firstIndex - 1, this.items.length);
        this.size++;
    }

    /** Adds an item of type T to the back of the deque.
     *
     * @param item the item to be inserted in
     */
    public void addLast(T item) {
        if (size == items.length) {
            resize();
        }
        this.items[lastIndex] = item;
        this.lastIndex = realIndex(this.lastIndex + 1, this.items.length);
        size++;
    }

    /** Returns true if deque is empty, false otherwise.
     *
     * @return true if the array is empty, false otherwise
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /** Returns the number of items in the deque.
     *
     * @return the number of the items
     */
    public int size() {
        return size;
    }

    /** Prints the items in the deque from first to last, separated by a space.
     * Once all the items have been printed, print out a new line.
     *
     */
    public void printDeque() {
        if (size == 0) {
            System.out.print("Empty Deque.");
        }
        for (int i = 1; i <= size; i++) {
            System.out.print(items[realIndex(firstIndex + i, items.length)]);
            System.out.print(" ");
        }
        System.out.println();
    }

    /** Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.
     *
     * @return item if exists, null otherwise
     */
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T item = items[realIndex(firstIndex + 1, items.length)];
        items[realIndex(firstIndex + 1, items.length)] = null;
        firstIndex = realIndex(firstIndex + 1, items.length);
        size--;
        if (items.length >= 16 && (double) size / items.length < 0.25) {
            shrinkSize();
        }
        return item;
    }

    /** Helper function shrinkSize help to shrink the length of former array to half.
     *
     */
    private void shrinkSize() {
        T[] ptr = items;
        items = (T[]) new Object[ptr.length / 2];
        for (int i = 1; i <= size; i++) {
            items[realIndex(firstIndex + i, items.length)] =
                    ptr[realIndex(firstIndex + i, ptr.length)];
        }
        firstIndex = realIndex(firstIndex, items.length);
        lastIndex = realIndex(firstIndex + size + 1, items.length);
    }

    /** Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.
     *
     * @return the item at the front of the deque
     */
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T item = items[realIndex(lastIndex - 1, items.length)];
        items[realIndex(lastIndex - 1, items.length)] = null;
        lastIndex = realIndex(lastIndex - 1, items.length);
        size--;
        if (items.length >= 16 && (double) size / items.length < 0.25) {
            shrinkSize();
        }
        return item;
    }

    /** Gets the item at the given index, where 0 is the front,
     * 1 is the next item, and so forth. If no such item exists, returns null.
     * Must not alter the deque!
     *
     * @param index given index of the item in the sequence
     * @return item
     */
    public T get(int index) {
        if (index >= size) {
            return null;
        }
        return items[realIndex(index + firstIndex + 1, items.length)];
    }

}
