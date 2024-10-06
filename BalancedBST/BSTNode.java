import java.util.*;

class BSTNode {
    public int NodeKey;
    public BSTNode Parent;
    public BSTNode LeftChild;
    public BSTNode RightChild;
    public int Level;

    public BSTNode(int key, BSTNode parent) {
        NodeKey = key;
        Parent = parent;
        LeftChild = null;
        RightChild = null;
    }
}

class BalancedBST {
    public BSTNode Root;

    public BalancedBST() {
        Root = null;
    }

    public void GenerateTree(int[] a) {
        if ((a == null) || (a.length == 0)) {
            return;
        }

        int[] sorted = Arrays.copyOf(a, a.length);
        Arrays.sort(sorted);

        Root = GenerateTreeRoot(null, sorted, 0, sorted.length - 1);
    }

    private BSTNode GenerateTreeRoot(BSTNode parent, int[] sorted, int startIndex, int endIndex) {
        if (startIndex > endIndex) {
            return null;
        }

        int currentRootValueIndex = (startIndex + endIndex) / 2;

        BSTNode currentRootNode = new BSTNode(sorted[currentRootValueIndex], parent);
        currentRootNode.Level = (parent == null) ? 0 : parent.Level + 1;

        currentRootNode.LeftChild =
                GenerateTreeRoot(currentRootNode, sorted, startIndex, currentRootValueIndex - 1);
        currentRootNode.RightChild =
                GenerateTreeRoot(currentRootNode, sorted, currentRootValueIndex + 1, endIndex);

        return currentRootNode;
    }

    public boolean IsBalanced(BSTNode root_node) {
        return GetTreeLevel(root_node) != -1;
    }

    private int GetTreeLevel(BSTNode node) {
        if (node == null) {
            return 0;
        }

        int leftHeight = GetTreeLevel(node.LeftChild);
        if (leftHeight == -1) {
            return -1;
        }

        int rightHeight = GetTreeLevel(node.RightChild);
        if (rightHeight == -1) {
            return -1;
        }

        if ((Math.abs(leftHeight - rightHeight) > 1) || !AreChildrenBalanced(node)) {
            return -1;
        }
        return Math.max(leftHeight, rightHeight) + 1;
    }

    private boolean AreChildrenBalanced(BSTNode node) {
        boolean isLeftChildSmaller =
                (node.LeftChild == null) || (node.LeftChild.NodeKey < node.NodeKey);
        boolean isRightChildBigger =
                (node.RightChild == null) || (node.RightChild.NodeKey > node.NodeKey);
        return isLeftChildSmaller && isRightChildBigger;
    }

}
