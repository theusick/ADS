package ru.ads.tree;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimpleTreeTest {

    private SimpleTree<Integer> tree;
    private SimpleTreeNode<Integer> root;

    @BeforeEach
    void setUp() {
        root = new SimpleTreeNode<>(10, null);
        tree = new SimpleTree<>(root);
    }

    @Test
    void testAddChildNullParent() {
        SimpleTreeNode<Integer> newRoot = new SimpleTreeNode<>(20, null);
        tree.AddChild(null, newRoot);

        assertEquals(newRoot, tree.Root);
        assertEquals(0, newRoot.Level);
    }

    @Test
    void testAddChildToRoot() {
        SimpleTreeNode<Integer> child = new SimpleTreeNode<>(5, null);
        tree.AddChild(root, child);

        assertEquals(1, root.Children.size());
        assertEquals(child, root.Children.getFirst());
        assertEquals(1, child.Level);
    }

    @Test
    void testAddChildToNode() {
        SimpleTreeNode<Integer> child = new SimpleTreeNode<>(5, null);
        tree.AddChild(root, child);

        SimpleTreeNode<Integer> grandChild = new SimpleTreeNode<>(15, null);
        tree.AddChild(child, grandChild);

        assertEquals(1, root.Children.size());
        assertEquals(child, root.Children.getFirst());

        assertEquals(1, root.Children.getFirst().Children.size());
        assertEquals(grandChild, root.Children.getFirst().Children.getFirst());
        assertEquals(1, child.Level);
        assertEquals(2, grandChild.Level);
    }

    @Test
    void testAddChildSeveralChildren() {
        SimpleTreeNode<Integer> child = new SimpleTreeNode<>(5, null);
        tree.AddChild(root, child);

        SimpleTreeNode<Integer> grandChild1 = new SimpleTreeNode<>(15, null);
        SimpleTreeNode<Integer> grandChild2 = new SimpleTreeNode<>(17, null);
        SimpleTreeNode<Integer> grandChild3 = new SimpleTreeNode<>(-1, null);
        tree.AddChild(child, grandChild1);
        tree.AddChild(child, grandChild2);
        tree.AddChild(child, grandChild3);

        assertEquals(1, root.Children.size());

        assertEquals(3, root.Children.getFirst().Children.size());
        assertEquals(1, child.Level);
        assertEquals(2, grandChild1.Level);
        assertEquals(2, grandChild2.Level);
        assertEquals(2, grandChild3.Level);
    }

    @Test
    void testDeleteNodeRoot() {
        SimpleTreeNode<Integer> child1 = new SimpleTreeNode<>(5, root);
        SimpleTreeNode<Integer> child2 = new SimpleTreeNode<>(15, root);
        tree.AddChild(root, child1);
        tree.AddChild(root, child2);

        tree.DeleteNode(root);

        assertNotNull(tree.Root);
        assertEquals(root, tree.Root);
        assertNull(root.Parent);
        assertEquals(2, root.Children.size());
    }

    @Test
    void testDeleteNode() {
        SimpleTreeNode<Integer> child1 = new SimpleTreeNode<>(5, root);
        SimpleTreeNode<Integer> child2 = new SimpleTreeNode<>(15, root);
        tree.AddChild(root, child1);
        tree.AddChild(root, child2);

        tree.DeleteNode(child1);

        assertNull(child1.Parent);
        assertFalse(root.Children.contains(child1));
        assertEquals(1, root.Children.size());
    }

    @Test
    void testDeleteNodeWithChildren() {
        SimpleTreeNode<Integer> child1 = new SimpleTreeNode<>(5, root);
        SimpleTreeNode<Integer> grandChild = new SimpleTreeNode<>(2, child1);
        tree.AddChild(root, child1);
        tree.AddChild(child1, grandChild);

        tree.DeleteNode(child1);

        assertFalse(root.Children.contains(child1));
        assertNull(grandChild.Parent);
    }

    @Test
    void testGetAllNodesEmptyTree() {
        SimpleTree<Integer> emptyTree = new SimpleTree<>(null);

        List<SimpleTreeNode<Integer>> nodes = emptyTree.GetAllNodes();
        assertTrue(nodes.isEmpty());
    }

    @Test
    void testGetAllNodesOnlyRoot() {
        List<SimpleTreeNode<Integer>> nodes = tree.GetAllNodes();
        assertEquals(1, nodes.size());
    }

    @Test
    void testGetAllNodes() {
        SimpleTreeNode<Integer> child1 = new SimpleTreeNode<>(5, root);
        SimpleTreeNode<Integer> child2 = new SimpleTreeNode<>(15, root);
        tree.AddChild(root, child1);
        tree.AddChild(root, child2);

        List<SimpleTreeNode<Integer>> nodes = tree.GetAllNodes();
        assertEquals(3, nodes.size());
    }

    @Test
    void testFindNodesByValueEmptyTree() {
        SimpleTree<Integer> emptyTree = new SimpleTree<>(null);

        List<SimpleTreeNode<Integer>> result = emptyTree.FindNodesByValue(5);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindNodesByValueEmpty() {
        SimpleTreeNode<Integer> child1 = new SimpleTreeNode<>(5, root);
        SimpleTreeNode<Integer> child2 = new SimpleTreeNode<>(5, root);
        tree.AddChild(root, child1);
        tree.AddChild(root, child2);

        List<SimpleTreeNode<Integer>> result = tree.FindNodesByValue(3);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindNodesByValue() {
        SimpleTreeNode<Integer> child1 = new SimpleTreeNode<>(5, root);
        SimpleTreeNode<Integer> child2 = new SimpleTreeNode<>(5, root);
        tree.AddChild(root, child1);
        tree.AddChild(root, child2);

        List<SimpleTreeNode<Integer>> result = tree.FindNodesByValue(5);
        assertEquals(2, result.size());
    }

    @Test
    void testFindNodesByValueWithChildren() {
        SimpleTreeNode<Integer> child1 = new SimpleTreeNode<>(4, root);
        SimpleTreeNode<Integer> child2 = new SimpleTreeNode<>(5, root);

        tree.AddChild(root, child1);
        tree.AddChild(root, child2);

        SimpleTreeNode<Integer> grandChild1 = new SimpleTreeNode<>(1, root);
        SimpleTreeNode<Integer> grandChild2 = new SimpleTreeNode<>(8, root);
        SimpleTreeNode<Integer> grandChild3 = new SimpleTreeNode<>(4, root);

        tree.AddChild(child1, grandChild1);
        tree.AddChild(child1, grandChild2);
        tree.AddChild(child2, grandChild3);

        List<SimpleTreeNode<Integer>> result = tree.FindNodesByValue(4);
        assertEquals(2, result.size());
    }

    @Test
    void testMoveNodeNullOriginalNode() {
        SimpleTreeNode<Integer> newParent = new SimpleTreeNode<>(20, null);
        tree.AddChild(root, newParent);

        tree.MoveNode(null, newParent);

        assertEquals(2, tree.Count());
        assertTrue(newParent.Children.isEmpty());
        assertTrue(root.Children.contains(newParent));
    }

    @Test
    void testMoveNodeNullNewParent() {
        SimpleTreeNode<Integer> child1 = new SimpleTreeNode<>(5, root);
        tree.AddChild(root, child1);

        tree.MoveNode(child1, null);

        assertEquals(2, tree.Count());
        assertTrue(root.Children.contains(child1));
        assertEquals(1, child1.Level);
    }

    @Test
    void testMoveNodeOriginalNodeIsAncestorOfNewParent() {
        SimpleTreeNode<Integer> child1 = new SimpleTreeNode<>(5, root);
        SimpleTreeNode<Integer> grandChild = new SimpleTreeNode<>(2, child1);

        tree.AddChild(root, child1);
        tree.AddChild(child1, grandChild);

        tree.MoveNode(child1, grandChild);

        assertEquals(3, tree.Count());
        assertTrue(child1.Children.contains(grandChild));
        assertEquals(1, child1.Level);
        assertEquals(2, grandChild.Level);
    }

    @Test
    void testMoveNodeOriginalNodeIsRoot() {
        SimpleTreeNode<Integer> child1 = new SimpleTreeNode<>(5, root);
        tree.AddChild(root, child1);

        tree.MoveNode(root, child1);

        assertEquals(2, tree.Count());
        assertTrue(root.Children.contains(child1));
        assertEquals(0, root.Level);
        assertEquals(1, child1.Level);
    }

    @Test
    void testMoveNode() {
        SimpleTreeNode<Integer> child1 = new SimpleTreeNode<>(5, root);
        SimpleTreeNode<Integer> child2 = new SimpleTreeNode<>(15, root);
        SimpleTreeNode<Integer> grandChild = new SimpleTreeNode<>(2, child1);

        tree.AddChild(root, child1);
        tree.AddChild(root, child2);
        tree.AddChild(child1, grandChild);

        tree.MoveNode(grandChild, child2);

        assertFalse(child1.Children.contains(grandChild));
        assertTrue(child2.Children.contains(grandChild));
        assertEquals(2, grandChild.Level);
    }

    @Test
    void testCountEmptyTree() {
        SimpleTree<Integer> emptyTree = new SimpleTree<>(null);

        assertEquals(0, emptyTree.Count());
    }

    @Test
    void testCountOnlyRoot() {
        assertEquals(1, tree.Count());
    }

    @Test
    void testCount() {
        SimpleTreeNode<Integer> child1 = new SimpleTreeNode<>(5, root);
        SimpleTreeNode<Integer> child2 = new SimpleTreeNode<>(15, root);

        tree.AddChild(root, child1);
        tree.AddChild(root, child2);

        assertEquals(3, tree.Count());
    }

    @Test
    void testLeafCountEmptyTree() {
        SimpleTree<Integer> emptyTree = new SimpleTree<>(null);

        assertEquals(0, emptyTree.LeafCount());
    }

    @Test
    void testLeafCountOnlyRoot() {
        assertEquals(1, tree.LeafCount());
    }

    @Test
    void testLeafCount() {
        SimpleTreeNode<Integer> child1 = new SimpleTreeNode<>(5, root);
        SimpleTreeNode<Integer> child2 = new SimpleTreeNode<>(15, root);
        SimpleTreeNode<Integer> grandChild = new SimpleTreeNode<>(20, child2);

        tree.AddChild(root, child1);
        tree.AddChild(root, child2);
        tree.AddChild(child2, grandChild);

        assertEquals(2, tree.LeafCount());
    }

    @Test
    void testAssignNodesLevels() {
        SimpleTreeNode<Integer> child1 = new SimpleTreeNode<>(5, root);
        SimpleTreeNode<Integer> child2 = new SimpleTreeNode<>(15, root);
        tree.AddChild(root, child1);
        tree.AddChild(root, child2);

        tree.AssignNodesLevels();

        assertEquals(0, root.Level);
        assertEquals(1, child1.Level);
        assertEquals(1, child2.Level);
    }


    @Test
    void testEvenTreesOnlyRoot() {
        List<Integer> result = tree.EvenTrees();
        assertTrue(result.isEmpty());
    }

    @Test
    void testEvenTreesNoCuts() {
        SimpleTreeNode<Integer> child1 = new SimpleTreeNode<>(5, root);
        SimpleTreeNode<Integer> child2 = new SimpleTreeNode<>(15, root);
        tree.AddChild(root, child1);
        tree.AddChild(root, child2);

        List<Integer> result = tree.EvenTrees();
        assertTrue(result.isEmpty());
    }

    @Test
    void testEvenTreesWithCuts() {
        SimpleTreeNode<Integer> child1 = new SimpleTreeNode<>(5, root);
        SimpleTreeNode<Integer> child2 = new SimpleTreeNode<>(15, root);
        SimpleTreeNode<Integer> grandChild = new SimpleTreeNode<>(20, child1);
        tree.AddChild(root, child1);
        tree.AddChild(root, child2);
        tree.AddChild(child1, grandChild);

        List<Integer> result = tree.EvenTrees();
        assertEquals(2, result.size());
        assertEquals(10, result.getFirst());
        assertEquals(5, result.getLast());

    }

    @Test
    void testEvenTreesMultipleCuts() {
        SimpleTreeNode<Integer> child1 = new SimpleTreeNode<>(5, root);
        SimpleTreeNode<Integer> child2 = new SimpleTreeNode<>(15, root);
        SimpleTreeNode<Integer> grandChild1 = new SimpleTreeNode<>(20, child1);
        SimpleTreeNode<Integer> grandChild2 = new SimpleTreeNode<>(25, child2);
        tree.AddChild(root, child1);
        tree.AddChild(root, child2);
        tree.AddChild(child1, grandChild1);
        tree.AddChild(child2, grandChild2);

        List<Integer> result = tree.EvenTrees();
        assertEquals(4, result.size());

        assertEquals(10, result.getFirst());
        assertEquals(5, result.get(1));

        assertEquals(10, result.get(2));
        assertEquals(15, result.getLast());
    }

    @Test
    void testBalanceEvenBSTSingleNodeTree() {
        assertFalse(tree.BalanceEvenBST());
        assertEquals(root, tree.Root);
        assertTrue(root.Children.isEmpty());
    }

    @Test
    void testBalanceEvenBSTBalancedTwoElemTree() {
        SimpleTreeNode<Integer> child1 = new SimpleTreeNode<>(5, root);
        tree.AddChild(root, child1);

        assertTrue(tree.BalanceEvenBST());
        assertEquals(5, tree.Root.NodeValue);
        assertEquals(1, tree.Root.Children.size());
        assertEquals(10, tree.Root.Children.getFirst().NodeValue);
    }

    @Test
    void testBalanceEvenBSTBalancedFourElemTree() {
        SimpleTreeNode<Integer> child1 = new SimpleTreeNode<>(5, root);
        SimpleTreeNode<Integer> child2 = new SimpleTreeNode<>(15, root);

        SimpleTreeNode<Integer> grandChild = new SimpleTreeNode<>(3, child1);

        tree.AddChild(root, child1);
        tree.AddChild(root, child2);
        tree.AddChild(child1, grandChild);

        assertTrue(tree.BalanceEvenBST());
        assertEquals(5, tree.Root.NodeValue);
        assertEquals(2, tree.Root.Children.size());
        assertEquals(3, tree.Root.Children.getFirst().NodeValue);
        assertEquals(10, tree.Root.Children.getLast().NodeValue);
        assertEquals(15, tree.Root.Children.getLast().Children.getFirst().NodeValue);
    }

    @Test
    void testBalanceEvenBSTUnbalancedTree() {
        SimpleTreeNode<Integer> child1 = new SimpleTreeNode<>(5, root);
        SimpleTreeNode<Integer> child2 = new SimpleTreeNode<>(15, root);

        SimpleTreeNode<Integer> grandChild1 = new SimpleTreeNode<>(3, child1);
        SimpleTreeNode<Integer> grandChild2 = new SimpleTreeNode<>(2, grandChild1);
        SimpleTreeNode<Integer> grandChild3 = new SimpleTreeNode<>(1, grandChild2);

        tree.AddChild(root, child1);
        tree.AddChild(root, child2);
        tree.AddChild(child1, grandChild1);
        tree.AddChild(grandChild1, grandChild2);
        tree.AddChild(grandChild2, grandChild3);

        assertTrue(tree.BalanceEvenBST());
        assertEquals(2, tree.Root.Children.size());
        assertEquals(3, tree.Root.NodeValue);
        assertEquals(1, tree.Root.Children.getFirst().NodeValue);
        assertEquals(10, tree.Root.Children.getLast().NodeValue);

        assertEquals(2, tree.Root.Children.getFirst().Children.getLast().NodeValue);
        assertEquals(5, tree.Root.Children.getLast().Children.getFirst().NodeValue);
        assertEquals(15, tree.Root.Children.getLast().Children.getLast().NodeValue);

    }

    @Test
    void testBalanceEvenBSTOddNodes() {
        SimpleTreeNode<Integer> child1 = new SimpleTreeNode<>(5, root);
        SimpleTreeNode<Integer> child2 = new SimpleTreeNode<>(15, root);
        SimpleTreeNode<Integer> child3 = new SimpleTreeNode<>(25, root);

        tree.AddChild(root, child1);
        tree.AddChild(root, child2);
        tree.AddChild(root, child3);

        assertFalse(tree.BalanceEvenBST());
        assertEquals(3, tree.Root.Children.size());
    }

    @Test
    void testCountEvenSubTreesSingleRoot() {
        assertEquals(0, tree.CountEvenSubTrees());
    }

    @Test
    void testCountEvenSubTreesRootEvenSubtree() {
        SimpleTreeNode<Integer> child = new SimpleTreeNode<>(5, root);
        tree.AddChild(root, child);

        assertEquals(1, tree.CountEvenSubTrees());
    }

    @Test
    void testCountEvenSubTreesNoEvenSubtrees() {
        SimpleTreeNode<Integer> child1 = new SimpleTreeNode<>(5, root);
        SimpleTreeNode<Integer> child2 = new SimpleTreeNode<>(15, root);

        tree.AddChild(root, child1);
        tree.AddChild(root, child2);

        assertEquals(0, tree.CountEvenSubTrees());
    }

    @Test
    void testCountEvenSubTreesMultipleEvenSubtrees() {
        SimpleTreeNode<Integer> child1 = new SimpleTreeNode<>(5, root);
        SimpleTreeNode<Integer> child2 = new SimpleTreeNode<>(15, root);

        SimpleTreeNode<Integer> grandChild1 = new SimpleTreeNode<>(3, child1);
        SimpleTreeNode<Integer> grandChild2 = new SimpleTreeNode<>(20, child2);

        tree.AddChild(root, child1);
        tree.AddChild(root, child2);
        tree.AddChild(child1, grandChild1);
        tree.AddChild(child2, grandChild2);

        assertEquals(2, tree.CountEvenSubTrees());
    }

    @Test
    void testCountEvenSubTreesOneEvenSubtree() {
        SimpleTreeNode<Integer> child = new SimpleTreeNode<>(5, root);
        tree.AddChild(root, child);

        SimpleTreeNode<Integer> grandChild = new SimpleTreeNode<>(15, child);
        tree.AddChild(child, grandChild);

        assertEquals(1, tree.CountEvenSubTrees());
    }

    @Test
    void testCountEvenSubTreesLargeTreeDepth4() {
        SimpleTreeNode<Integer> child1 = new SimpleTreeNode<>(5, root);
        SimpleTreeNode<Integer> child2 = new SimpleTreeNode<>(15, root);
        SimpleTreeNode<Integer> child3 = new SimpleTreeNode<>(20, root);

        SimpleTreeNode<Integer> grandChild1 = new SimpleTreeNode<>(3, child1);
        SimpleTreeNode<Integer> grandChild2 = new SimpleTreeNode<>(8, child2);

        SimpleTreeNode<Integer> greatGrandChild1 = new SimpleTreeNode<>(6, grandChild1);
        SimpleTreeNode<Integer> greatGrandChild2 = new SimpleTreeNode<>(2, grandChild2);

        tree.AddChild(root, child1);
        tree.AddChild(root, child2);
        tree.AddChild(root, child3);

        tree.AddChild(child1, grandChild1);
        tree.AddChild(child2, grandChild2);

        tree.AddChild(grandChild1, greatGrandChild1);
        tree.AddChild(grandChild2, greatGrandChild2);

        assertEquals(3, tree.CountEvenSubTrees());
    }

}
