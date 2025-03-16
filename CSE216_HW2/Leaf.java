public class Leaf<T extends Number> extends BinaryTree<T> {
    private T value;
    public Leaf(T value) {
        this.value = value;
    }
    public T getValue() {
        return value;
    }
}
