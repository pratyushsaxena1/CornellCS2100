package cs2110;

public class SortClient {

    public static void main(String[] args) {
        IntLinkedList list = new IntLinkedList();
        list.add(4);
        list.add(2);
        list.add(8);
        list.add(7);
        list.add(6);
        list.add(1);
        list.add(3);
        //list.add(5);
        System.out.println("Before: " + list);

        list.sort();
        System.out.println("After: " + list);
    }
}
