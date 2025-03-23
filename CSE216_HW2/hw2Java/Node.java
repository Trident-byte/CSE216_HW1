public class Node<T extends Number> extends BinaryTree<T> {
    private Operator operation;
    private BinaryTree<T> left;
    private BinaryTree<T> right;
    public Node(Operator op, BinaryTree<T> left, BinaryTree<T> right) {
        operation = op;
        this.left = left;
        this.right = right;
    }

    public Operator getOperation() {
        return operation;
    }

    public BinaryTree<T> getLeft() {
        return left;
    }

    public BinaryTree<T> getRight() {
        return right;
    }
}
