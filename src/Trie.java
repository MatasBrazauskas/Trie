import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.Iterator;

public class Trie implements Iterable<String> {
    public class TrieNode {
        public TrieNode[] arr;
        public int size;
        public boolean endOfWord;

        public TrieNode(int size){
            arr = new TrieNode[size];
            Arrays.fill(arr, null);
            endOfWord = false;
        }

        public String toString(){
            var str = new StringBuilder("[");

            for(int i = 0; i < arr.length; i++){
                if(arr[i] == null){
                    str.append(" NULL, ");
                }else{
                    str.append(" " + i + ", ");
                }
            }
            str.append("]");

            return "Size: " + size + " End of Word: " + endOfWord + " Array: " +  str.toString();
        }
    }

    public TrieNode root;
    public char[] characters;
    public int size;

    public Trie(char[] arr){
        this.root = new TrieNode(arr.length);

        characters = new char[arr.length];
        System.arraycopy(arr, 0, characters, 0, arr.length);
        Arrays.sort(characters);
    }

    public void insert(String word) {
        var node = root;

        for(int i = 0; i < word.length(); i++){
            int index = hashFunction(word.charAt(i));

            if(index < 0){
                throw new IllegalArgumentException("Character is not part of a character set");
            }

            if(node.arr[index] == null){
                node.arr[index] = new TrieNode(characters.length);
                node.size++;
            }

            node = node.arr[index];
        }

        node.endOfWord = true;
        size++;
    }

    public void remove(String word){
        var node = root;
    }

    public ArrayList<String> prefixSeach(String prefix){
        var node = root;

        for(int i = 0; i < prefix.length(); i++){
            int index = hashFunction(prefix.charAt(i));

            if(index < 0){
                throw new IllegalArgumentException("Character is not part of a character set");
            }

            if(node.arr[index] == null){
                throw new IllegalArgumentException("Prefix does not exist in trie");
            }

            node = node.arr[index];
        }

        var arr = new ArrayList<String>();

        if(node.endOfWord){
            arr.add(prefix);
        }

        var it = new TrieIterator(node);
        StringBuilder str;

        while(it.hasNext()){
            str = new StringBuilder(prefix);
            str.append(it.next().toString());
            arr.add(str.toString());
        }

        return arr;
    }

    public boolean contains(String word){
        var node = root;

        for(int i = 0; i < word.length(); i++){
            int index = hashFunction(word.charAt(i));

            if(index < 0){
                return false;
            }

            if(node.arr[index] == null){
                return false;
            }

            node = node.arr[index];
        }
        return node.endOfWord;
    }

    private int hashFunction(char character){
        return Arrays.binarySearch(characters, character);
    }

    private int nextIndex(TrieIterator.Frame frame){
        for(int i = frame.index; i < characters.length; i++){
            if(frame.node.arr[i] != null){
                return i;
            }
        }
        return characters.length + 1;
    }

    @Override
    public Iterator<String> iterator(){
        return new TrieIterator();
    }

    public class TrieIterator implements Iterator<String> {
        public static class Frame {
            public TrieNode node;
            public int index;

            public Frame(TrieNode node, int index){
                this.node = node;
                this.index = index;
            }

            @Override
            public String toString(){
                return "Node: " + node.toString() + " Index: " + index;
            }
        }

        public Stack<Frame> stack;
        public ArrayList<Character> list;

        public TrieIterator(){
            stack = new Stack<>();
            list = new ArrayList<>();

            stack.push(new Frame(root, 0));
            dfs();
        }

        public TrieIterator(TrieNode node){
            stack = new Stack<>();
            list = new ArrayList<>();

            stack.push(new Frame(node, 0));
            dfs();
        }

        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public String next() {
            final var str = list.toString();

            Frame frame = stack.peek();
            int index = nextIndex(frame);

            while(frame.node.size == 0 && index >= characters.length){
                stack.pop();

                if(list.isEmpty() == false) {
                    list.removeLast();
                }

                if(stack.isEmpty()) return str;
                index = nextIndex(stack.peek());
            }

            dfs();
            return str;
        }

        public void dfs() {
            TrieNode node = stack.peek().node;
            int index = nextIndex(stack.peek());

            while(index < characters.length && node.arr[index].endOfWord == false) {
                stack.peek().index = index + 1;
                list.add(characters[index]);

                stack.push(new Frame(node.arr[index], 0));
                node = node.arr[index];

                index = nextIndex(stack.peek());
            }

            if(index < characters.length){
                stack.peek().index = index + 1;
                stack.push(new Frame(node.arr[index], 0));

                list.add(characters[index]);
            }
        }
    }
}
