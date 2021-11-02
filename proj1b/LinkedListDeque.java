public class LinkedListDeque<T> implements Deque<T> {
    private class TNode {
        private T value;
        private TNode next;
        private TNode prev;

        /** Constructor function.
         *
         * @param value value saved in the node itself
         * @param nextNode address of the next node
         * @param prevNode address of the previous node
         */
        public TNode(T value, TNode nextNode, TNode prevNode) {
            this.value = value;
            this.next = nextNode;
            this.prev = prevNode;
        }
    }

    private int size;
    private TNode sentinel;

    /** Construction function without parameter.
     */
    public LinkedListDeque() {
        this.size = 0;
        this.sentinel = new TNode(null, null, null);
        this.sentinel.next = this.sentinel;
        this.sentinel.prev = this.sentinel;
    }

    /** Construction function with another LinkedListDeque object as parameter.
     *
     * @param other the LinkedListDeque object that will be non-destructively copied
     */
    /*
    public LinkedListDeque(LinkedListDeque other) {
        this.sentinel =  new TNode(null, null, null);
        this.sentinel.next = this.sentinel;
        this.sentinel.prev = this.sentinel;
        this.size = other.size;
        TNode ptr = other.sentinel;
        TNode ptrNew = this.sentinel;
        while (ptr.next != other.sentinel) {
            ptrNew.next = new TNode(ptr.next.value, this.sentinel, ptrNew);
            ptr = ptr.next;
            ptrNew = ptrNew.next;
        }
        this.sentinel.prev = ptrNew;
    }
   */

    /** Adds an item of type T to the front of the deque.
     *
     * @param item item to be added
     */
    @Override
    public void addFirst(T item) {
        TNode ptr = this.sentinel.next;
        this.sentinel.next = new TNode(item, ptr, this.sentinel);
        ptr.prev = this.sentinel.next;
        this.size++;
    }

    /** Adds an item of type T to the back of the deque.
     *
     * @param item item to be added
     */
    @Override
    public void addLast(T item) {
        TNode ptr = this.sentinel.prev;
        this.sentinel.prev = new TNode(item, this.sentinel, ptr);
        ptr.next = this.sentinel.prev;
        this.size++;
    }

    /**Returns the number of items in the deque.
     *
     * @return size of the list
     */
    @Override
    public int size() {
        return this.size;
    }

    /**Prints the items in the deque from first to last, separated by a space.
     * Once all the items have been printed, print out a new line.
     *
     */
    @Override
    public void printDeque() {
        if (this.size == 0) {
            System.out.println("Empty Deque.");
        } else {
            TNode ptr = this.sentinel.next;
            while (ptr != sentinel) {
                System.out.print(ptr.value);
                System.out.print(" ");
                ptr = ptr.next;
            }
            System.out.println();
        }
    }

    /**Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.
     *
     * @return item at the front of the deque, return null if list is empty
     */
    @Override
    public T removeFirst() {
        if (this.size == 0) {
            return null;
        } else {
            TNode ptr = this.sentinel.next;
            this.sentinel.next = ptr.next;
            ptr.next.prev = this.sentinel;
            this.size--;
            return ptr.value;
        }
    }

    /**Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.
     *
     * @return item at the back of the deque, return null if list is empty
     */
    @Override
    public T removeLast() {
        if (this.size == 0) {
            return null;
        } else {
            TNode ptr = this.sentinel.prev;
            this.sentinel.prev = ptr.prev;
            ptr.prev.next = this.sentinel;
            this.size--;
            return ptr.value;
        }
    }

    /**Gets the item at the given index, where 0 is the front,
     * 1 is the next item, and so forth. If no such item exists, returns null.
     * Must not alter the deque!
     *
     * @param index the place in the sequence of the list to be returned
     * @return the value of the node at given index
     */
    @Override
    public T get(int index) {
        if (index >= this.size) {
            return null;
        }
        TNode ptr = this.sentinel.next;
        int i = 0;
        while (i < index) {
            ptr = ptr.next;
            i++;
        }
        return ptr.value;
    }

    /**Same as get, but uses recursion.
     *
     * @param index the place in the sequence of the list to be returned
     * @return the value of the node at given index
     */
    public T getRecursive(int index) {
        if (index >= this.size) {
            return null;
        }
        return returnIndexNodeValue(this.sentinel.next, index);
    }

    /** helper function for getRecursive function which is not recursive itself
     *
     * @param node the starting node of the list
     * @param index the place in the sequence of the list to be returned
     * @return the value of the node at given index
     */
    private T returnIndexNodeValue(TNode node, int index) {
        if (index == 0) {
            return node.value;
        } else {
            return returnIndexNodeValue(node.next, index - 1);
        }
    }
}
