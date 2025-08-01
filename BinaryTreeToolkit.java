import java.util.*;

public class BinaryTreeToolkit {
    static class Node {
        int data;
        Node left, right;

        public Node(int data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    public static Node buildTreeLevelOrder(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter root Value:");
        int val = sc.nextInt();
        if(val==-1){
            return null;
        }
        Node root = new Node(val);
        Queue<Node> q = new LinkedList<>();
        q.add(root);

        while(!q.isEmpty()){
            Node currNode=q.poll();

            System.out.println("Enter the left child of" + currNode.data + "(-1 for null):" );
            int leftVal = sc.nextInt();
            if(leftVal != -1){
                currNode.left=new Node(leftVal);
                q.add(currNode.left);
            }

            System.out.println("Enter the right Child of" + currNode.data + "(-1 for null)");
            int rightVal = sc.nextInt();
            if(rightVal != -1){
                currNode.right = new Node (rightVal);
                q.add(currNode.right);
            }
        }
        return root;

    }

    public static void inorder(Node root){
        if(root == null){
            return;
        }

        inorder(root.left);
        System.out.print(root.data+" ");
        inorder(root.right);
    }

    
    public static void preorder(Node root){
        if(root == null){
            return;
        }

        System.out.print(root.data+" ");
        preorder(root.left);
        preorder(root.right);
    }

    public static void postorder(Node root){
        if(root == null){
            return;
        }
    
        postorder(root.left);
        postorder(root.right);
        System.out.print(root.data+" ");
    }

    public static int countNodes(Node root){
        if(root == null){
            return 0;
        }
        return 1 + countNodes(root.left) + countNodes(root.right);
    }

    public static int sumNodes(Node root){
        if(root == null){
            return 0;
        }
        return root.data + sumNodes(root.left) + sumNodes(root.right);
    }

    public static int height(Node root){
        if(root==null){
            return 0;
        }

        int lh=height(root.left);
        int rh=height(root.right);

        return Math.max(lh,rh) + 1;

    }

    public static boolean validBST(Node root,Node min ,Node max){
        if(root==null){
            return true;
        }
        if(min!=null && root.data<=min.data){
            return false;

        }
        else if(max!=null && root.data>=max.data){
            return false;
        }

        return validBST(root.left, min, root) && validBST(root.right, root, max);
    }

    public static void inorderList(Node root, List<Integer> list){
        if(root==null){
            return;
        }
        inorderList(root.left,list);
        list.add(root.data);
        inorderList(root.right,list);
    }


    public static Node buildBST(List<Integer> list, int st, int end) {
        if (st > end) {
            return null;
        }

        int mid = (st + end) / 2;
        Node root = new Node(list.get(mid));
        root.left = buildBST(list, st, mid - 1);
        root.right = buildBST(list, mid + 1, end);
        return root;
    }

    public static Node balanceBST(Node root) {
        List<Integer> inorderList = new ArrayList<>();
        inorderList(root, inorderList);
        return buildBST(inorderList, 0, inorderList.size() - 1);
    }

    public static Node createMirror(Node root){//O(n)
        if(root==null){
            return null; 
        }

        Node leftMirror = createMirror(root.left);
        Node rightMirror = createMirror(root.right);

        root.left = rightMirror;
        root.right = leftMirror;
        return root;
    } 

    static class DInfo{
        int diam;
        int ht;

        public DInfo(int diam, int ht){
            this.diam=diam;
            this.ht=ht;
        }
    }

    public static DInfo diameterInfo(Node root){
        if(root==null){
            return new DInfo(0,0);
        }
        DInfo leftInfo=diameterInfo(root.left);
        DInfo rightInfo=diameterInfo(root.right);

        int diam = Math.max(Math.max(leftInfo.diam,rightInfo.diam),leftInfo.ht+rightInfo.ht +1);
        int ht = Math.max(leftInfo.ht,rightInfo.ht)+1;
        
        return new DInfo(diam,ht);

    }

     static class Info{
        Node node;
        int hd;

        public Info(Node node,int hd){
            this.node=node;
            this.hd=hd;
        }
    }
    public static void topView(Node root){
        //Level Order
        Queue<Info> q = new LinkedList<>();
        HashMap<Integer,Node> map = new HashMap<>();

        int min =0,max=0;

        q.add(new Info(root, 0));
        q.add(null);

        while(!q.isEmpty()){
            Info curr =q.remove();
            if(curr == null){
                if(q.isEmpty()){
                    break;
                }else{
                    q.add(null);
                }
            }else{
                 if(!map.containsKey(curr.hd)){
                map.put(curr.hd,curr.node);
            }

            if(curr.node.left!=null){
                q.add(new Info(curr.node.left, curr.hd-1));
                min = Math.min(min,curr.hd-1); 
            }

            if(curr.node.right!=null){
                q.add(new Info(curr.node.right, curr.hd+1));
                max = Math.max(max,curr.hd+1); 
            }
            }
        }       

        for(int i=min;i<=max;i++){
            System.out.print(map.get(i).data+" ");
        }
        System.out.println();
    }

    public static Node lca(Node root, int n1,int n2){
        if(root==null || root.data==n1 || root.data==n2){
            return root;
        }

        Node leftLca = lca(root.left, n1, n2); 
        Node rightLca = lca(root.right, n1, n2);
        
        if(rightLca==null){
            return leftLca;
        }

        
        if(leftLca==null){
            return rightLca;
        }

        return root;
    }

     public static int KthAncestor(Node root, int n,int k){
        if(root==null){
            return -1;
        }

        if(root.data==n){
            return 0;
        }

        int leftDist = KthAncestor(root.left, n, k);
        int rightDist = KthAncestor(root.right, n, k);

        if(leftDist == -1 && rightDist == -1){
            return -1;
        }

        int max = Math.max(leftDist,rightDist);
            if(max+1==k){
                System.out.println(root.data);
            }
        return max+1;
    }


    public static int lcaDist(Node root,int n){
        if(root==null){
            return -1;
        }

        if(root.data==n){
            return 0;
        }

        int leftDist= lcaDist(root.left, n);
        int rightDist=lcaDist(root.right, n);

        if(leftDist == -1 && rightDist == -1){
            return -1;
        }else if(leftDist==-1){
            return rightDist +1;
        }else{
            return leftDist+1;
        }

    }
    public static int minDist(Node root,int n1,int n2){
        Node LCA = lca(root, n1, n2);

        int dist1 = lcaDist(LCA,n1);
        int dist2 = lcaDist(LCA,n2);

        return dist1 + dist2;
    }

    public static int SumTree(Node root){
        if(root==null){
            return 0;
        }
        int leftchild = SumTree(root.left);
        int rightchild = SumTree(root.right);
        
        int data = root.data;
        int newLeft = root.left==null? 0 : root.left.data;
        int newRight = root.right==null? 0 : root.right.data;
        root.data = newLeft + leftchild + newRight + rightchild;
        return data;
    }

    public static boolean getpath(Node root,int n,ArrayList<Node> path){
        if(root==null){
            return false;
        }
        
        
        path.add(root);


        if(root.data==n){
            return true;
        }

        boolean foundLeft=getpath(root.left, n, path);
        boolean foundRight=getpath(root.right, n, path);

        if(foundLeft || foundRight){
            return true;
        }

        path.remove(path.size()-1);
        return false;
    }

    public static Node insertBST(Node root,int val){
        if(root==null){
            root = new Node(val);
            return root;
        }

        if(root.data<val){
            root.right=insertBST(root.right, val);
        }

        
        if(root.data>val){
            root.left=insertBST(root.left, val);
        }

        return root;
    }

    public static boolean search(Node root, int val){
        if(root==null){
            return false;
        }
        if(root.data==val){
            return true;
        }

        if(root.data<val){
            return search(root.right, val);
        }else{
            return search(root.left, val);
        }
    }



    public static Node delete(Node root, int val){
        if(root.data>val){
            root.left=delete(root.left, val);
        }else if(root.data<val){
            root.right=delete(root.right, val);
        }else{
            //Case-1 leaf Node
            if(root.left==null && root.right==null){
                return null;
            }
            //Case-2 one child
            if(root.left==null){
                return root.right;
            }else if(root.right==null){
                return root.left;
            }
            //Case-3 Both Children
            Node IS=inorderSuccessor(root.right);
            root.data=IS.data;
            root.right=delete(root.right, IS.data);
        }
        return root;
    }

    public static Node inorderSuccessor(Node root){
        while(root.left != null){
            root = root.left;
        }
        return root;
    }
    
    public static void printInrange(Node root,int K1,int K2){
        if(root==null){
            return;
        }
        if(root.data>=K1 && root.data<=K2){
            printInrange(root.left, K1, K2);
            System.out.print(root.data+ " ");
            printInrange(root.right, K1, K2);
        }else if(root.data<K1){
            printInrange(root.left, K1, K2);
        }else{
            printInrange(root.right, K1, K2);
        }
    }

    public static void printPath(ArrayList<Integer> path){
        for(int i=0;i<path.size();i++){
            System.out.print(path.get(i)+"->");
        }
        System.out.println("Null");
    }
    public static void printRoot2Leaf(Node root,ArrayList<Integer> path){
        if(root==null){
            return;
        }

        path.add(root.data);
        if(root.left==null && root.right==null){
            printPath(path);
        }

        printRoot2Leaf(root.left,path);
        printRoot2Leaf(root.right,path);
        path.remove(path.size()-1);

    }

    public static Node createBST(ArrayList<Integer>arr,int st, int end){
        if(st>end){
            return null;
        }
        
        int mid=(st+end)/2;
        Node root = new Node(arr.get(mid));
        root.left = createBST(arr, st, mid-1);
        root.right = createBST(arr, mid+1, end);
        return root;
    }

    public static Node sortedBST(Node root){
        ArrayList inorder = new ArrayList<>();

        inorderList(root, inorder);
        root = createBST(inorder, 0, inorder.size()-1);
        return root;
    }

    static class MaxInfo{
        boolean isBST;
        int size;
        int min;
        int max;

        public MaxInfo(boolean isBST, int size, int min, int max){
            this.isBST = isBST;
            this.size = size;
            this.min = min;
            this.max = max;
        }
    }
    
    public static int maxBST=0;

    public static MaxInfo largestBST(Node root){
        if(root==null){
            return new MaxInfo(true,0,Integer.MAX_VALUE,Integer.MIN_VALUE);
        }
        MaxInfo leftInfo = largestBST(root.left);
        MaxInfo rightInfo = largestBST(root.right);
        int size = leftInfo.size + rightInfo.size +1;
        int min = Math.min(root.data,Math.min(leftInfo.min,rightInfo.min));
        int max = Math.max(root.data,Math.max(leftInfo.max,rightInfo.max));
        
        if(root.data <= leftInfo.max || root.data >= rightInfo.min){
            return new MaxInfo(false,size,min,max);
        }

        if(leftInfo.isBST && rightInfo.isBST){
            maxBST = Math.max(maxBST,size);
            return new MaxInfo(true , size, min,max);
        }

        return new MaxInfo(false,size,min,max);
    }
    


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Node root = buildTreeLevelOrder();
        System.out.println("\n✅ Binary Tree built successfully!");

        while (true) {
            System.out.println("\n📘 Choose an operation:");
            System.out.println("1. Inorder Traversal");
            System.out.println("2. Preorder Traversal");
            System.out.println("3. Postorder Traversal");
            System.out.println("4. Count Nodes");
            System.out.println("5. Sum of Nodes");
            System.out.println("6. Height of Tree");
            System.out.println("7. Check if Valid BST");
            System.out.println("8. Create Mirror Tree");
            System.out.println("9. Top View");
            System.out.println("10. Diameter of Tree");
            System.out.println("11. LCA of two nodes");
            System.out.println("12. Kth Ancestor");
            System.out.println("13. Min Distance Between Two Nodes");
            System.out.println("14. Convert to Sum Tree");
            System.out.println("15. Print Root to Leaf Paths");
            System.out.println("16. Insert into BST");
            System.out.println("17. Search in BST");
            System.out.println("18. Delete from BST");
            System.out.println("19. Print values in range (K1, K2)");
            System.out.println("20. Balance BST");
            System.out.println("21. Print Largest BST Size");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> {
                    System.out.print("Inorder: ");
                    inorder(root);
                    System.out.println();
                }
                case 2 -> {
                    System.out.print("Preorder: ");
                    preorder(root);
                    System.out.println();
                }
                case 3 -> {
                    System.out.print("Postorder: ");
                    postorder(root);
                    System.out.println();
                }
                case 4 -> System.out.println("Total Nodes: " + countNodes(root));
                case 5 -> System.out.println("Sum of Nodes: " + sumNodes(root));
                case 6 -> System.out.println("Height: " + height(root));
                case 7 -> System.out.println("Is Valid BST: " + validBST(root, null, null));
                case 8 -> {
                    root = createMirror(root);
                    System.out.println("Tree mirrored.");
                }
                case 9 -> {
                    System.out.println("Top View:");
                    topView(root);
                }
                case 10 -> System.out.println("Diameter: " + diameterInfo(root).diam);
                case 11 -> {
                    System.out.print("Enter two node values: ");
                    int n1 = sc.nextInt(), n2 = sc.nextInt();
                    Node lcaNode = lca(root, n1, n2);
                    System.out.println("LCA: " + (lcaNode != null ? lcaNode.data : "Not Found"));
                }
                case 12 -> {
                    System.out.print("Enter node and k: ");
                    int n = sc.nextInt(), k = sc.nextInt();
                    System.out.print(k + "th Ancestor: ");
                    KthAncestor(root, n, k);
                }
                case 13 -> {
                    System.out.print("Enter two node values: ");
                    int a = sc.nextInt(), b = sc.nextInt();
                    System.out.println("Minimum Distance: " + minDist(root, a, b));
                }
                case 14 -> {
                    SumTree(root);
                    System.out.println("Converted to Sum Tree.");
                }
                case 15 -> {
                    System.out.println("Root to Leaf Paths:");
                    printRoot2Leaf(root, new ArrayList<>());
                }
                case 16 -> {
                    System.out.print("Enter value to insert: ");
                    int val = sc.nextInt();
                    root = insertBST(root, val);
                }
                case 17 -> {
                    System.out.print("Enter value to search: ");
                    int val = sc.nextInt();
                    System.out.println("Found? " + search(root, val));
                }
                case 18 -> {
                    System.out.print("Enter value to delete: ");
                    int val = sc.nextInt();
                    root = delete(root, val);
                }
                case 19 -> {
                    System.out.print("Enter K1 and K2: ");
                    int k1 = sc.nextInt(), k2 = sc.nextInt();
                    System.out.print("Values in range: ");
                    printInrange(root, k1, k2);
                    System.out.println();
                }
                case 20 -> {
                    root = balanceBST(root);
                    System.out.println("BST balanced.");
                }
                case 21 -> {
                    maxBST = 0;
                    largestBST(root);
                    System.out.println("Largest BST size: " + maxBST);
                }
                case 0 -> {
                    System.out.println("🚪 Exiting...");
                    return;
                }
                default -> System.out.println("❌ Invalid choice!");
            }
        }
    }


}