
import java.util.Comparator;

class LL<T> {

    class Node<T> {

        Node<T> next;
        Node<T> prev;
        T data;

        Node(T d) {
            data = d;
            next = null;
            prev = null;
        }
    }
    Node<T> Head = null;

    //adding particular data at end of list
    void add(T data) {
        Node<T> n = new Node<>(data);
        if (Head == null) {
            Head = n;
        } else {
            Node<T> temp = Head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = n;
            n.prev = temp;
        }
    }

    //removing particular data
    boolean remove(T data) {
        if (Head == null) {
            System.out.println("List is empty. Nothing to delete");
            return false;
        } else if (Head.data.equals(data)) {
            Head = Head.next;
            return true;
        } else {
            Node<T> temp = Head;
            while (temp != null && !temp.data.equals(data)) {
                temp = temp.next;
            }
            if (temp == null) {
                System.out.println("Not Found!");
                return false;
            }
            if (temp.next != null && temp.prev != null) {
                temp.prev.next = temp.next;
                temp.next.prev = temp.prev;
            }
            temp.prev = null;
            temp.next = null;
            return true;
        }
    }

    void display() {
        Node<T> temp = Head;
        if (temp == null) {
            System.out.println("List is empty");
            return;
        }
        System.out.println("Display:");
        while (temp != null) {
            System.out.println(temp.data.toString());
            temp = temp.next;
        }
        System.out.println();
    }

    public int size() {
        int count = 0;
        Node<T> temp = Head;
        while (temp != null) {
            count++;
            temp = temp.next;
        }
        return count;
    }

    public void clear() {
        Node<T> current = Head;
        while (current != null) {
            Node<T> next = current.next;
            current.prev = null;
            current.next = null;
            current = next;
        }
        Head = null;
    }

}
