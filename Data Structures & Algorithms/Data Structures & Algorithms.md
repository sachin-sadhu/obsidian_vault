# Sorting
## Insertion Sort
Assume portion of array is sorted, go to next unsorted element and try to place it where it should be amongst the sorted array. $O(n^2)$ average time complexity. $O(n^2)$ worst case time complexity where array is perfectly unsorted. $O(n)$ best case where array is already sorted.

# Sets & Maps
Sets are similar to arrays where some data is stored. Underlying data structure used to store these values is usually something like a binary search tree that allows for finding values in $O(n))$ time. Similar concept with **maps**, which allow to store key-value pairs. The underlying data structure for a map can also be a binary search tree.

# Heap/Priority Queue
Priority queue is simliar to a queue, difference however is that it is not FIFO, rather some value can dictate which items should be popped from the priority queue next. Usually implemented with a data structure called a binary heap. 

## Binary Heap
Similar to a binary tree, except a binary heap tree is complete, meaning every level is full except potentially the leaf level. The order of a binary heap depends on whether we want to use a min/max priority queue. If we are using a min priority queue, the minimum value should be the root node of the tree. This is recursive for every node in the tree.

Elements in a heap can be stored in an array, inserting the root element at the first index to allow the following math to work. For a given root node : leftNode = $index*2$, rightNode = $index*2+1$, parentNode = $index/2$ 

Binary heaps allow you to get the min/max of a tree in $O(1)$ time.

### Pushing to a binary heap
In order to push to a heap, you first find an empty node on the left level, insert it there, then to maintain the order property, you would keep on comparing the node to it's parent node, swapping it when necessary until you either reach the root node or the comparison fails.

### Popping from a binary heap
Popping is similar to pushing to a heap. Once the root element has been removed, you replace the root with a node from the leaf level in order to preserve the structure of the tree. Next you keep on swapping nodes downwards from the root until the leaf level to preserve the order of the tree.

# Graphs
### Matrices
Matrices can be used to illustrate a grid system eg.
```
[
[0,0,0],
 [0,1,1],
 [0,0,0]
]
```
0 could represent nodes. 1 could represent non-nodes.
### Adjacency Matrices
Value represents whether an edge between 2 vertices exist, or the distance of the edge.
```
[
 [0,4],
 [1,2]
]
```
Could mean that from node A to B there is a edge of weight 4.

### Adjacency List
Each node contains a list recording all of its neighbour.

# Bit Shifting
Bit shifting is manipulating bits. Bit shifiting to the left means shifting all bits to the left by however many spots, vice versa for shfiting to the right. For example, $010 << 2$ would mean shifting all bits to the left by 2 spots, resulting in $01000$ . Equivalent of multiplying by 2.

# Kadanes algorithm
Algorithm for finding max value of a non-empty subarray in an array. Has time complexity of $O(n)$ time.

Basically works by, looping through array, at each point if the previous subarray is positive, add the current integer to it. If the previous subarray is negative, leave it behind.

```
  public static int kadanes(int[] nums) {
        int max = nums[0];
        int previous_subarray = 0;
        for (int number : nums) {
            previous_subarray = getmax(previous_subarray, 0);
            int current = previous_subarray + number;
            previous_subarray = current;
            max = getmax(max, current);
        }
        return max;
    }
```

# Two Pointers
Method for lots of problems, basic idea is you have a left and right pointer that you manipulate according to the problem.

# Prefix/Postifx Values
Calculate the total of an array up to a certain element from the first item if prefix, last item if postfix. Can do other operations as well such as multiplication.
eg [1,2,3,4,5]. Prefix array is : [1,3,6,10,15]

# Prefix Trees/Tries
Very useful data structure for searching for words, inserting words and searching for prefix of words in constant time. Basically is a Tree where each node is a letter that contains a HashMap of all it's child letters. 

![[Pasted image 20240825121309.png]]

```
class TreeNode {

    public Map<Character, TreeNode> children;

    public boolean isWord;

  

    public TreeNode() {

        this.children = new HashMap<>();

        this.isWord = false;

    }

}

class Main {
	
    private TreeNode head;

    public Main() {

        this.head = new TreeNode();

    }

    public void insert(String word) {

        TreeNode currentNode = this.head;

         for (char c : word.toCharArray()) {

  

            if (currentNode.children.containsKey(c)) {

                currentNode = currentNode.children.get(c);

            } else {

                TreeNode newNode = new TreeNode();

                currentNode.children.put(c, newNode);

                currentNode = newNode;

            }

         }

        currentNode.isWord = true;

    }

  

    public boolean search(String word) {

        TreeNode currentNode = this.head;

  

        for (char c : word.toCharArray()) {

  

            if (!currentNode.children.containsKey(c)) return false;

  

            currentNode = currentNode.children.get(c);

        }

  

        return currentNode.isWord;

    }

  

    public boolean startsWith(String prefix) {

        TreeNode currentNode = this.head;

  

        for (char c : prefix.toCharArray()) {

            if (!currentNode.children.containsKey(c)) return false;

  

            currentNode = currentNode.children.get(c);

  

        }

  

        return true;

    }

  

    public static void main(String[] args) {

        Main test = new Main();

        test.insert("apple");

        System.out.println(test.search("apple"));

        System.out.println(test.search("app"));

    }

}
```

# Subsets
Generating subsets from an array is simple, basic idea is that for each element in the array, there is a decision to either include it in the current subset or not. For each element there are 2 choices, therefore there are a total of $2^n$ subsets where $n$ is the number of elements in the array.

Can use a simple DFS algorithm to generate this, basic idea of the algorithm is that there are 2 possible paths, one where you include it in the subset and one where you do not.

```
public static List<List<Integer>> getSubsets(int[] arr) {

  
        List<List<Integer>> subsets = new ArrayList<>();

  

        List<Integer> currentSet = new ArrayList<>();

  

        DFSsubsets(arr, 0, subsets, currentSet);

  

        return subsets;

    }

  

    public static void DFSsubsets(int[] arr, int index, List<List<Integer>> subsets, List<Integer> currentSet) {

        /**

         * base case when index == arr.length just add current set to subsets and return

         */

        if (index == arr.length) {

            subsets.add(new ArrayList<>(currentSet));

            return;

        }

  

        int currentNumber = arr[index];

  

        // Add number to current set

        currentSet.add(currentNumber);

        DFSsubsets(arr, index+1, subsets, currentSet);

        // Dont add number to currentSet

        currentSet.removeLast();

        DFSsubsets(arr, index+1, subsets, currentSet);

    }
```

# Combinations
Obtaining combinations is very similar to a subset, basically at each decision point we decide whether we want to include the number or not. Different is combination might have a maximum size, so we should check if the current set has already reached the target size. eg Algorithm to find all combinations of size 2 between numbers 1 to 5

```
public static List<List<Integer>> combine(int n, int k) {

        List<List<Integer>> subsets = new ArrayList<>();

        List<Integer> currentSet = new ArrayList<>();
		
        DFSsubsets(1, n, k, subsets, currentSet);

        return subsets;
    }

  

    public static void DFSsubsets(int currentNumber, int n, int k, List<List<Integer>> subsets, List<Integer> currentSet) {

        if (currentSet.size() == k) {

            subsets.add(new ArrayList<>(currentSet));

            return;
        }
		
        if (currentNumber > n) return;
		
        // Add number to current set

        currentSet.add(currentNumber);

        DFSsubsets(currentNumber+1, n, k, subsets, currentSet);

        // Dont add number to currentSet

        currentSet.remove(currentSet.size()-1);

        DFSsubsets(currentNumber+1, n, k, subsets, currentSet);
    }
```

# Permutations
Finding permutations works by recursively inserting the current number into all possible places in all possible arrays. For example to find all permutations of 1 2 3. You would first find how many permutations there are of 3, which is {{3}}, then you would insert 2 into all possible positions, which is just {{2,3}, {3,2}}. Next you would do this with 1 resulting in : {{1,2,3}, {2,1,3}, {2,3,1}, {1,3,2}, {3,1,2}, {3,2,1}}. 

# Dynamic Programming
Problems that are recursive and that have repeated work done can be solved using dynamic programming technique. For example, calculating the fibbonaci sequence can be done recursively and there is repeated work done.
