package cs2110;

public class DoublyLinkedListTest extends ListTest {
    @Override
    public <T> CS2110List<T> constructList() {
        return new DoublyLinkedList<>();
    }
}