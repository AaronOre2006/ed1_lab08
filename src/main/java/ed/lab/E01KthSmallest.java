package ed.lab;
import java.util.Stack;

public class E01KthSmallest {

    public int kthSmallest(TreeNode<Integer> root, int k) {
        int position = 0;
        Stack<TreeNode<Integer>> st = new Stack<>();
        TreeNode<Integer> current = root;

        while (current != null || !st.isEmpty()){
            while(current != null){
                st.push(current);
                current = current.left;
            }
            current = st.pop();
            position += 1;
            if(position == k){
                return current.value;
            }
            current = current.right;
        }
        return -1;
    }

}