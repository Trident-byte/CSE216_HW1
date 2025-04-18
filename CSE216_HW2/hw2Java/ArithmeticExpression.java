public class ArithmeticExpression {
    public static double evaluate(BinaryTree tree) {
        if(tree instanceof Node){
            Node curNode = (Node) tree;
            double left = evaluate(curNode.getLeft());
            double right = evaluate(curNode.getRight());
            switch (curNode.getOperation()){
                case ADD:
                    return left + right;
                case SUBTRACT:
                    return left - right;
                case MULTIPLY:
                    return left * right;
                case DIVIDE:
                    return left / right;
            }
        }
        else{
            Leaf leaf = (Leaf) tree;
            return leaf.getValue().doubleValue();
        }
        return 0.0;
    }

    public static void main(String[] args) {
        BinaryTree<Integer> expressionTree = new Node<>(
                Operator.ADD,
                new Leaf<>(1),
                new Node<>(Operator.MULTIPLY, new Leaf<>(2), new Leaf<>(3))
        );
        double result = evaluate(expressionTree);
        System.out.println("Result of expression: " + result);
// Constructing another expression: (1.5 + 0.75) - 0.25
        BinaryTree<Double> secondTree = new Node<>(
                Operator.SUBTRACT,
                new Node<>(Operator.ADD, new Leaf<>(1.5), new Leaf<>(0.75)),
                new Leaf<>(0.25)
        );
        double secondResult = evaluate(secondTree);
        System.out.println("Result of another expression: " + secondResult);
// Constructing for expression: (5 / 2)
        BinaryTree<Integer> thirdTree = new Node<>(
                Operator.DIVIDE,
                new Leaf<>(5),
                new Leaf<>(2)
        );
        double thirdResult = evaluate(thirdTree);
        System.out.println("Result of third expression: " + thirdResult);
// it is ok if the output is 2.5 in this third test
    }
}
