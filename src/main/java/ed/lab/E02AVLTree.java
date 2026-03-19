package ed.lab;

import java.util.Comparator;

public class E02AVLTree<T> {

    private final Comparator<T> comparator;

    private Node<T> root;
    private int size;

    public E02AVLTree(Comparator<T> comparator) {
        this.comparator = comparator;
        this.root = null;
        this.size = 0;
    }

    public void insert(T value) {
        this.root = insert(root, value);
    }

    public void delete(T value) {
        if (search(value) != null) {
            root = delete(root, value);
            size--;
        }
    }

    private Node<T> delete(Node<T> root, T value) {
        if (root == null) {
            return null;
        }

        int compare = comparator.compare(value, root.value);

        if (compare < 0) {
            root.left = delete(root.left, value);
        } else if (compare > 0) {
            root.right = delete(root.right, value);
        } else {

            if (root.left == null) {
                return root.right;
            }

            if (root.right == null) {
                return root.left;
            }

            Node<T> successor = root.right;
            while (successor.left != null) {
                successor = successor.left;
            }

            root.value = successor.value;
            root.right = delete(root.right, successor.value);
        }

        updateHeight(root);
        int balance = getBalance(root);

        if (balance < -1) {
            if (getBalance(root.left) > 0) {
                root.left = rotateLeft(root.left);
            }
            return rotateRight(root);
        }

        if (balance > 1) {
            if (getBalance(root.right) < 0) {
                root.right = rotateRight(root.right);
            }
            return rotateLeft(root);
        }

        return root;
    }

    public T search(T value) {
        Node<T> root = search(this.root, value);

        if (root == null) {
            return null;
        }

        return root.value;
    }

    public int height() {
        if (this.root == null)
            return 0;
        return this.root.height;
    }

    public int size() {
        return this.size;
    }

    private Node<T> search(Node<T> root, T value) {
        if (root == null) {
            return null;
        }

        int compare = comparator.compare(value, root.value);

        if (compare == 0) {
            return root;
        }

        if (compare < 0) {
            return search(root.left, value);
        }

        return search(root.right, value);
    }

    private Node<T> insert(Node<T> root, T value) {
        if (root == null) {
            this.size++;
            return new Node<>(value);
        }

        int compare = comparator.compare(value, root.value);

        if (compare < 0) {
            root.left = insert(root.left, value);
        }
        else if (compare > 0) {
            root.right = insert(root.right, value);
        } else {
            return root;
        }

        root.height = 1 + Math.max(getHeight(root.left), getHeight(root.right));

        int balance = getBalance(root);

        if (balance < -1 && comparator.compare(value, root.left.value) > 0) {
            root.left = rotateLeft(root.left);
            return rotateRight(root);
        }

        if (balance > 1 && comparator.compare(value, root.right.value) < 0) {
            root.right = rotateRight(root.right);
            return rotateLeft(root);
        }

        if (balance < -1) {
            return rotateRight(root);
        }

        if (balance > 1) {
            return rotateLeft(root);
        }

        return root;
    }

    private int getBalance(Node<T> root) {
        if (root == null)
            return 0;
        return getHeight(root.right) - getHeight(root.left);
    }

    private Node<T> rotateLeft(Node<T> root) {
        if (root == null) return null;

        Node<T> newRoot = root.right;
        root.right = newRoot.left;
        newRoot.left = root;

        updateHeight(root);
        updateHeight(newRoot);

        return newRoot;
    }

    private Node<T> rotateRight(Node<T> root) {
        if (root == null) return null;

        Node<T> newRoot = root.left;
        root.left = newRoot.right;
        newRoot.right = root;

        updateHeight(root);
        updateHeight(newRoot);

        return newRoot;
    }

    private void updateHeight(Node<T> root) {
        if (root == null) return;

        root.height = 1 + Math.max(getHeight(root.left), getHeight(root.right));
    }

    private int getHeight(Node<T> root) {
        if (root == null)
            return 0;

        return root.height;
    }

    private static class Node<T> {
        protected T value;
        protected Node<T> left;
        protected Node<T> right;
        protected int height;

        public Node(T value) {
            this.value = value;
            this.height = 1;
        }
    }
}


/*
 ANALISIS

 1. ¿Cuál es la complejidad de tiempo y espacio de cada una de las funciones?

insert(value): Tiempo: O(log n) Espacio: O(log n)

delete(value): Tiempo: O(log n) Espacio: O(log n)

search(value): Tiempo: O(log n) Espacio: O(log n)

height(): Tiempo: O(1) Espacio: O(1)

size(): Tiempo: O(1) Espacio: O(1)


2. ¿Cuál es la variación de las complejidades respecto a un árbol binario de búsqueda común? ¿Por qué?

La diferencia principal es que en un BST normal las operaciones pueden llegar a ser O(n), mientras que en un AVL casi siempre se mantienen en O(log n).

Esto pasa porque un BST común puede desbalancearse fácilmente, por ejemplo si insertas los datos en orden. En ese caso el árbol termina pareciéndose a una lista, y las operaciones dejan de ser eficientes.

En cambio, el AVL se encarga de rebalancearse después de cada inserción o eliminación usando rotaciones.
Por eso su altura se mantiene controlada y sus operaciones principales siguen siendo O(log n).


 */