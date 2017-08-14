import java.util.LinkedList;
import java.util.Stack;

/**
 * Created by Allan Wong on 7/31/2017.
 */
//Referencing: algs4.cs.edu.princeton.TrieST


public class Trie<T> {
    private static final int R = 256;

    private Node root;
    private int N; //#keys in trie

    private static class Node{
        private LinkedList<Object> val;
        private Node[] next=new Node[R];
        public Node(){
            val=new LinkedList<>();
        }
    }

    public LinkedList<T> get(String key){
        Node x=get(root,key,0);
        if (x==null) return null;
        return (LinkedList<T>) x.val;
    }

    public boolean contains(String key) {
        return get(key) != null;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) return x;
        char c = key.charAt(d);
        return get(x.next[c], key, d+1);
    }

    public void put(String key, T val){
        if(val==null) delete(key);
        else root=put(root, key, val, 0);
    }

    private Node put(Node x, String key, T val, int d){
        if(x==null) x=new Node();
        if(d==key.length()){
            if (x.val.isEmpty()) N++;
            x.val.add(val);
            return x;
        }
        char c=key.charAt(d);
        if(c==8217)
            System.out.println("Break!");
        x.next[c]=put(x.next[c],key,val,d+1);
        return x;
    }

    public int size() {
        return N;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public Stack<String> keys(){
        return keysWithPrefix("");
    }

    public Stack<String> keysWithPrefix(String prefix){
        Stack<String> res=new Stack<>();
        Node x=get(root,prefix,0);
        collect(x,new StringBuilder(prefix),res);
        return res;
    }

    private void collect(Node x, StringBuilder prefix, Stack<String> res){
        if(x==null) return;
        if(x.val!=null) res.push(prefix.toString());
        for(char c=0;c<R;c++){
            prefix.append(c);
            collect(x.next[c],prefix,res);
            prefix.deleteCharAt(prefix.length()-1);
        }
    }



    public void delete(String key){
        root=delete(root,key,0);
    }

    private Node delete(Node x,String key, int d){
        if(x==null) return null;
        if(d==key.length()){
            if(x.val!=null) N--;
            x.val=null;
        }else{
            char c=key.charAt(d);
            x.next[c]=delete(x.next[c],key,d+1);
        }
        if(x.val!=null) return x;
        for(int c=0;c<R;c++)
            if(x.next[c]!=null)
                return x;
        return null;
    }



}
