public class Queue<T> {
    public int front = -1;
    public int rear = -1;
    public int size = 0;
    public int capacity = 250;
    public T[] elements = (T[]) new Object[capacity];

    public boolean isFull() {
        return size == capacity;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void enqueue(T element) {
        if (isFull()) {
            System.out.println("Queue is full");
            return;
        }
        elements[++rear] = element;
        if (front == -1) {
            front++;
        }
        size++;
    }

    public T dequeue() {
        if (isEmpty()) {
            System.out.println("Queue is empty");
            return null;
        }
        T element = elements[front++];
        size--;
        return element;
    }

    public int size() {
        return size;
    }
    
    public void display() {
        if (front == -1) {
            System.out.println("Queue is empty");
        } else {
            for (int i = front; i <= rear; i++) {
                System.out.println(elements[i].toString());
            }
        }
    }
    
    public void clear()
    {
        front=-1;
        rear=-1;
    }
}