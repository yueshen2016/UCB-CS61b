package bearmaps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private ArrayList<PriorityNode> heap;
    private int size;
    private Map<T, Integer> map;

    public ArrayHeapMinPQ() {
        heap = new ArrayList<>();
        heap.add(null);
        size = 0;
        map = new HashMap<>();
    }

    private void swap(int i, int j) {
        map.put(heap.get(i).getItem(), j);
        map.put(heap.get(j).getItem(), i);
        PriorityNode temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    private void promote(int index) {
        while (index > 1) {
            if (heap.get(index).compareTo(heap.get(index / 2)) < 0) {
                swap(index, index / 2);
                index = index / 2;
            } else {
                break;
            }
        }
    }

    @Override
    public boolean contains(T item) {
        return map.containsKey(item);
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) throw new IllegalArgumentException();

        heap.add(new PriorityNode(item, priority));
        size++;
        map.put(item, size);
        promote(size);
    }

    @Override
    public T getSmallest() {
        if(size == 0) throw new NoSuchElementException();

        return heap.get(1).getItem();
    }

    private void demote(int index) {
        while (index * 2 <= size) {
            int changeIndex = 0;
            if (index * 2 + 1 <= size) {
                if (heap.get(index * 2).compareTo(heap.get(index * 2 + 1)) > 0) {
                    changeIndex = index * 2 + 1;
                } else {
                    changeIndex = index * 2;
                }
            } else {
                changeIndex = index * 2;
            }
            if (heap.get(index).compareTo(heap.get(changeIndex)) > 0) {
                swap(index, changeIndex);
                index = changeIndex;
            } else {
                break;
            }
        }
    }

    @Override
    public T removeSmallest() {
        if(size == 0) throw new NoSuchElementException();

        PriorityNode sNode = heap.get(1);
        swap(1, size);
        heap.remove(size);
        size--;
        map.remove(sNode.getItem());
        demote(1);
        return sNode.getItem();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) throw new NoSuchElementException();

        int index = map.get(item);
        heap.get(index).setPriority(priority);
        if (heap.get(index).compareTo(heap.get(index / 2)) < 0) {
            promote(index);
        } else demote(index);
    }

    private class PriorityNode implements Comparable<PriorityNode> {
        private T item;
        private double priority;

        PriorityNode(T e, double p) {
            this.item = e;
            this.priority = p;
        }

        T getItem() {
            return item;
        }

        double getPriority() {
            return priority;
        }

        void setPriority(double priority) {
            this.priority = priority;
        }

        @Override
        public int compareTo(PriorityNode other) {
            if (other == null) {
                return -1;
            }
            return Double.compare(this.getPriority(), other.getPriority());
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) return true;
            if (o == null || o.getClass() != this.getClass()) {
                return false;
            } else {
                return ((PriorityNode) o).getItem().equals(getItem());
            }
        }

        @Override
        public int hashCode() {
            return item.hashCode();
        }
    }
}
