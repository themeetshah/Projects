class DoublyLinkedListJay {
    class Node {
        Payment data;
        Node next;

        Node(Payment data) {
            this.data = data;
            this.next = null;
        }
    }

    Node head = null;

    void addLast(Payment data) {
        Node n = new Node(data);
        if (head == null) {
            head = n;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = n;
        }
    }

    void printList() {
        Node current = head;
        while (current != null) {
            System.out.println(current.data.getPaymentId() + " - " + current.data.getPaymentMode() + " - "
                    + current.data.getLandlordId() + " - " + current.data.getPaymentId() + " - "
                    + current.data.getRoomId() + " - " + current.data.transactionamount + " - "
                    + current.data.getTransactionDate());
            current = current.next;
        }
    }
}