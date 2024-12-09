class Stack1<T> {
	class Node<T> {
		Node<T> next;
		T data;
		Node(T d){
			data=d;
			next=null;
		}
	}
	Node<T> TOP = null;
	
    void empty() {
        TOP = null;
    }
    
    void display() {
        if(TOP==null){
            System.out.println("Stack is empty");
        } else {
            Node<T> temp = TOP;
            while(temp!=null){
                System.out.println(temp.data.toString());
                temp=temp.next;
            }
        }
    }
    
    void PUSH(T data) {
        Node<T> n1 = new Node<>(data);
		n1.next=TOP;
        TOP=n1;
    }
}