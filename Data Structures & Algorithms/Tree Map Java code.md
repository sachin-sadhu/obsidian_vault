```
class Node {

  

int key;

int value;

Node leftNode;

Node rightNode;

  

public Node(int key, int value, Node leftNode, Node rightNode) {

this.key = key;

this.value = value;

this.leftNode = leftNode;

this.rightNode = rightNode;

}

  

public Node(int key, int value) {

this(key, value, null, null);

}

  

}

  

class TreeMap {

  

private Node root;

  

public int get(int key) {

if (this.root == null) return -1;

  

return getHelper(key, this.root);

}

  

public void insert(int key, int val) {

if (this.root == null) {

this.root = new Node(key, val);

return;

};

  

insertHelper(key, val, root);

}

  

public int getMin() {

if (this.root == null) return -1;

  

Node currNode = this.root;

while (currNode.leftNode != null) currNode = currNode.leftNode;

  

return currNode.value;

}

  

public int getMax() {

if (this.root == null) return -1;

  

Node currNode = this.root;

while (currNode.rightNode != null) currNode = currNode.rightNode;

  

return currNode.value;

}

  

public List<Integer> getInorderKeys() {

List<Integer> keys = new ArrayList<>();

  

if (this.root == null) return keys;

  

return getInorderKeysHelper(keys, this.root);

}

  

public void remove(int key) {

  

if (this.root == null || this.get(key) == -1) return;

  

this.root = removeHelper(key, this.root);

  

}

  

private Node findMinNode(Node root) {

Node currentNode = root;

while (currentNode.leftNode != null) currentNode = currentNode.leftNode;

return currentNode;

}

  

private Node removeHelper(int key, Node root) {

if (root == null) return null;

  

if (key < root.key) {

root.leftNode = removeHelper(key, root.leftNode);

} else if (key > root.key) {

root.rightNode = removeHelper(key, root.rightNode);

} else {

if (root.leftNode == null) {

return root.rightNode;

} else if (root.rightNode == null) {

return root.leftNode;

} else {

Node minNode = findMinNode(root.rightNode);

root.key = minNode.key;

root.value = minNode.value;

root.rightNode = removeHelper(minNode.key, root.rightNode);

}

}

return root;

}

  

private List<Integer> getInorderKeysHelper(List<Integer> keys, Node root) {

if (root.leftNode != null) {

getInorderKeysHelper(keys, root.leftNode);

}

  

keys.add(root.key);

  

if (root.rightNode != null) {

getInorderKeysHelper(keys, root.rightNode);

}

  

return keys;

}

  

private void insertHelper(int key, int val, Node root) {

if (key < root.key) {

if (root.leftNode == null) {

root.leftNode = new Node(key, val);

} else {

insertHelper(key, val, root.leftNode);

}

} else if (key > root.key) {

if (root.rightNode == null) {

root.rightNode = new Node(key, val);

} else {

insertHelper(key, val, root.rightNode);

}

} else {

root.key = key;

root.value = val;

}

}

  

private int getHelper(int key, Node root) {

if (root == null) { return -1; }

  

if (key < root.key) {

return getHelper(key, root.leftNode);

} else if (key > root.key) {

return getHelper(key, root.rightNode);

}

  

return root.value;

}

}
```