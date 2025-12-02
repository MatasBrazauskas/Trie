import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Stack;

public class Trie {

    public enum Type {
        //ASCII(256);
        LOWER(26),
        AUTO(0);
        //LOWER_AND_HIGHER(52),
        //LOWER_AND_NUMBERS(78);

        public final int alphabetSize;

        Type(int alphabetSize){
            this.alphabetSize = alphabetSize;
        }

        public int getAlphabetSize(){
            return alphabetSize;
        }
    }

    public class TrieNode {
        public TrieNode[] arr;
        public boolean endOfWord;

        public TrieNode(int size){
            arr = new TrieNode[size];
            Arrays.fill(arr, null);
            endOfWord = false;
        }
    }

    public class Temp{
        public int index;
        public TrieNode node;

        public Temp(int index, TrieNode node){
            this.index = index;
            this.node = node;
        }
    }

    public TrieNode root;
    public char[] characters;
    public final Type type;
    public int size;

    /*public Trie(Type type) {
        root = new TrieNode();
        this.type = type;
        alphabetSize = type.getAlphabetSize();
    }*/

    public Trie(char[] arr){
        this.root = new TrieNode(arr.length);
        this.type = Type.AUTO;

        characters = new char[arr.length];
        System.arraycopy(arr, 0, characters, 0, arr.length);
        Arrays.sort(characters);
    }

    public void insert(String word) {
        var node = root;

        for(char i : word.toCharArray()){
            int index = hashFunction(i);

            if(index < 0){
                throw new IllegalArgumentException("Character is not part of a character set");
            }

            if(node.arr[index] == null){
                node.arr[index] = new TrieNode(characters.length);
            }

            node = node.arr[index];
        }
        size++;
    }

    public boolean contains(String word) {
        var node = root;

        for(char i : word.toCharArray()){
            int index = hashFunction(i);

            if(node.arr[index] == null){
                return false;
            }
            node = node.arr[index];
        }

        return true;
    }

    private int hashFunction(char character){
        switch(this.type){
            case LOWER: return character - 'a';
            case AUTO: return Arrays.binarySearch(characters, character);
            default: throw new IllegalArgumentException("Invalid type");
        }
    }

    private Temp nextElement(int index, TrieNode node){
        for(; index < characters.length; index++){
            if(node.arr[index] != null){
                return new Temp(index, node.arr[index]);
            }
        }
        return null;
    }

    /*public Iterator<Character> iterator(){
        return new TrieIterator();
    }*/

    public class TrieIterator {

        public Stack<TrieNode> stack;
        public Stack<Integer> indexes;
        public ArrayList<Character> list;

        public TrieIterator(){
            stack = new Stack<>();
            indexes = new Stack<>();
            list = new ArrayList<>();

            Temp temp = nextElement(0, root);
            stack.push(root);
            indexes.push(temp.index);
            list.add(characters[temp.index]);

            dfs(temp.node, 0);
        }

        public boolean hasNext() {
            return !stack.isEmpty();
        }

        public String next() {

            Temp temp = nextElement(indexes.peek() + 1, stack.peek());

            System.out.println("This is the index of a inserted characeter "  + temp.index);

            /*while(temp == null){
                stack.pop();
                indexes.pop();
                list.removeLast();

                temp = nextElement(indexes.peek() + 1, stack.peek());
            }*/

            //indexes.pop();
            //indexes.push(temp.index + 1);
            list.removeLast();
            list.add(characters[temp.index]);
            indexes.pop();
            indexes.push(temp.index + 1);

            dfs(temp.node, temp.index + 1);

            return list.toString();
        }

        private void dfs(TrieNode node, int startIndex) {
            Temp temp;
            while((temp = nextElement(startIndex, node)) != null){
                startIndex = temp.index;

                stack.push(node);
                indexes.push(startIndex);
                list.add(characters[startIndex]);

                System.out.println(node.toString() + " " + startIndex);

                node = temp.node;
                startIndex = 0;
            }
        }
    }
}
