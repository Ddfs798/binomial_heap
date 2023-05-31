package aads.term_paper.binomial_heap;


/* Класс BinomialHeapNode представляет узел биномиальной кучи. */

public class BinomialHeapNode {
    int key; // ключ
    int degree; // степень
   BinomialHeapNode parent; // ссылка на родительский узел
   BinomialHeapNode child; // ссылка на дочерний узел
   BinomialHeapNode sibling; // ссылка на соседний узел

    public BinomialHeapNode(int key) {
        this.key = key;
        this.degree = 0;
        this.parent = null;
        this.child = null;
        this.sibling = null;
    }
}
