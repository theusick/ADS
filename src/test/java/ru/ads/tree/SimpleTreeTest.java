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
    void testAddChild_NullParent() {
        SimpleTreeNode<Integer> newRoot = new SimpleTreeNode<>(20, null);
        tree.AddChild(null, newRoot);

        assertEquals(newRoot, tree.Root);
        assertEquals(0, newRoot.Level);
    }

    @Test
    void testAddChild() {
        SimpleTreeNode<Integer> child = new SimpleTreeNode<>(5, null);
        tree.AddChild(root, child);

        assertEquals(1, root.Children.size());
        assertEquals(child, root.Children.get(0));
        assertEquals(1, child.Level);  // Проверяем уровень
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
        assertEquals(1, root.Children.size());  // Остается только один ребенок
    }

    @Test
    void testDeleteNode_WithChildren() {
        SimpleTreeNode<Integer> child1 = new SimpleTreeNode<>(5, root);
        SimpleTreeNode<Integer> grandChild = new SimpleTreeNode<>(2, child1);
        tree.AddChild(root, child1);
        tree.AddChild(child1, grandChild);

        tree.DeleteNode(child1);

        assertFalse(root.Children.contains(child1));
        assertNull(grandChild.Parent);
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
    void testFindNodesByValue() {
        SimpleTreeNode<Integer> child1 = new SimpleTreeNode<>(5, root);
        SimpleTreeNode<Integer> child2 = new SimpleTreeNode<>(5, root);
        tree.AddChild(root, child1);
        tree.AddChild(root, child2);

        List<SimpleTreeNode<Integer>> result = tree.FindNodesByValue(5);
        assertEquals(2, result.size());
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
    void testCount() {
        SimpleTreeNode<Integer> child1 = new SimpleTreeNode<>(5, root);
        SimpleTreeNode<Integer> child2 = new SimpleTreeNode<>(15, root);
        tree.AddChild(root, child1);
        tree.AddChild(root, child2);

        assertEquals(3, tree.Count());
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

}
