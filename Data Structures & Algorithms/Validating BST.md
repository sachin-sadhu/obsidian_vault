 
```
public boolean isValidBST(TreeNode root) {

        return isValidBSTDFS(root, -1001, 1001);

    }

    public boolean isValidBSTDFS(TreeNode root, int min, int max) {

        if (root.val <= min || root.val >= max) return false;

        if (root.left == null && root.right == null)  return true;
		
        if (root.left == null) return isValidBSTDFS(root.right, root.val, max);

        if (root.right == null) return isValidBSTDFS(root.left, min, root.val);

        return isValidBSTDFS(root.right, root.val, max) && isValidBSTDFS(root.left, min, root.val);

    }
```

You cannot just check that child nodes values are less/greater than your values, you need to also check that they are less/greater than all values above them. That is why you need to pass parameters that track the range of values the node can have.