package aads.term_paper.binomial_heap;

import java.util.NoSuchElementException;


/* Класс BinomialHeap содержит основные операции для работы с биномиальной кучей. */
public class BinomialHeap {
    private BinomialHeapNode root;

    public BinomialHeap() {
        this.root = null;
    }

    /* Метод insert вставляет новый элемент с указанным ключом в кучу.
    Если куча пуста, новый элемент становится корневым узлом.
    Если куча не пуста, выполняется слияние корневых узлов с помощью метода mergeNodes */
    public void insert(int key) {
        BinomialHeapNode newNode = new BinomialHeapNode(key);
        if (root == null) {
            root = newNode;
        } else {
            BinomialHeapNode merged = mergeNodes(root, newNode);
            root = merged;
        }
    }

    /* Метод findMinimum находит и возвращает минимальный ключ в куче.
    Если куча пуста, выбрасывается исключение. */
    public int findMinimum() {
        if (root == null) {
            throw new NoSuchElementException("Heap is empty");
        }
        BinomialHeapNode minNode = findMinNode(root);
        return minNode.key;
    }

    /* Метод deleteMinimum удаляет минимальный узел из кучи.
     Если куча пуста, выбрасывается исключение.
     Сначала метод findMinNode используется для нахождения минимального узла.
     Затем минимальный узел удаляется из списка корневых узлов, а его дочерние узлы объединяются с корнем,
     чтобы поддерживать свойства биномиальной кучи. */
    public void deleteMinimum() {
        if (root == null) {
            throw new NoSuchElementException("Heap is empty");
        }
        BinomialHeapNode minNode = findMinNode(root);
        BinomialHeapNode minNodeChild = minNode.child;

        // Удаление минимального узла из списка корневых узлов
        if (minNode == root) {
            root = minNode.sibling;
        } else {
            BinomialHeapNode parent = minNode.parent;
            if (parent.child == minNode) {
                parent.child = minNode.sibling;
            } else {
                BinomialHeapNode sibling = parent.child;
                BinomialHeapNode prevSibling = null;
                while (sibling != null && sibling != minNode) {
                    prevSibling = sibling;
                    sibling = sibling.sibling;
                }
                if (sibling != null) {
                    prevSibling.sibling = sibling.sibling;
                }
            }
        }

        // Обратный порядок дочерних узлов минимального узла
        if (minNodeChild != null) {
            BinomialHeapNode reversedChild = reverseNodeOrder(minNodeChild);

            // Присоединение дочерних узлов к корню
            if (root == null) {
                root = reversedChild;
            } else {
                root = mergeNodes(root, reversedChild);
            }
        }
    }

    /* Приватный метод mergeNodes объединяет два узла биномиальной кучи.
    Если ключ node1 больше ключа node2, метод вызывает себя рекурсивно, меняя местами узлы.
    Затем узел node2 становится дочерним для узла node1, а степень узла node1 увеличивается. */
    private BinomialHeapNode mergeNodes(BinomialHeapNode node1, BinomialHeapNode node2) {
        if (node1.key > node2.key) {
            return mergeNodes(node2, node1);
        }
        node2.parent = node1;
        node2.sibling = node1.child;
        node1.child = node2;
        node1.degree++;
        return node1;
    }

    /* Приватный метод findMinNode находит узел с минимальным ключом в поддереве,
    начиная с указанного узла startNode. Он проходит по соседним узлам, сравнивает их ключи и возвращает
    узел с наименьшим ключом. */

    private BinomialHeapNode findMinNode(BinomialHeapNode startNode) {
        BinomialHeapNode minNode = startNode;
        BinomialHeapNode currentNode = startNode;
        while (currentNode != null) {
            if (currentNode.key < minNode.key) {
                minNode = currentNode;
            }
            currentNode = currentNode.sibling;
        }
        return minNode;
    }

    /* Приватный метод removeNode удаляет указанный узел из кучи.
    Сначала метод decreaseKey уменьшает ключ узла до минимального значения.
    Затем узел удаляется из списка корневых узлов, а его дочерние узлы объединяются с корнем для
    поддержания свойств кучи. */
    private void removeNode(BinomialHeapNode node) {
        decreaseKey(node, Integer.MIN_VALUE);
        if (node == root) {
            deleteMinimum();
        } else {
            BinomialHeapNode parent = node.parent;
            while (parent != null) {
                if (parent.child == node) {
                    parent.child = node.sibling;
                    break;
                }
                parent = parent.sibling;
            }
            BinomialHeapNode reversedNode = reverseNodeOrder(node.child);
            root = mergeNodes(root, reversedNode);
        }
    }

    /* Приватный метод reverseNodeOrder меняет порядок дочерних узлов указанного узла на обратный.
    Он используется для перестроения кучи после удаления минимального узла. */
    private BinomialHeapNode reverseNodeOrder(BinomialHeapNode node) {
        if (node == null || node.sibling == null) {
            return node;
        }
        BinomialHeapNode next = node.sibling;
        node.sibling = null;
        BinomialHeapNode reversed = reverseNodeOrder(next);
        next.sibling = node;
        return reversed;
    }


    /* Приватный метод decreaseKey уменьшает ключ указанного узла до нового значения newKey.
    Он сравнивает ключи узлов вверх по пути к корню и, если ключ родительского узла
    больше ключа текущего узла, меняет их местами. */
    private void decreaseKey(BinomialHeapNode node, int newKey) {
        node.key = newKey;
        BinomialHeapNode currentNode = node;
        while (currentNode.parent != null && currentNode.key < currentNode.parent.key) {
            swapValues(currentNode, currentNode.parent);
            currentNode = currentNode.parent;
        }
    }

    /* Приватный метод swapValues меняет значения ключей между двумя узлами. */
    private void swapValues(BinomialHeapNode node1, BinomialHeapNode node2) {
        int tempKey = node1.key;
        node1.key = node2.key;
        node2.key = tempKey;
    }

}