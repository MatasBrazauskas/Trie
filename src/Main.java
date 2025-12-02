public class Main {
    public static void main(String[] args) {
        var trie = new Trie(new char[] {'a', 'b', 'c', 'd'});

        trie.insert("abc");
        trie.insert("abd");
        trie.insert("cc");
        trie.insert("ab");



        var it = trie.new TrieIterator();
        var tempIt = trie.new TrieIterator();
        System.out.println(it.next());

        System.out.println("+++++++++++++++++++++++++++++++++++++");

        while(tempIt.indexes.isEmpty() == false){
            System.out.println(tempIt.indexes.pop() + " " + tempIt.stack.pop().toString());
        }

        //System.out.println(it.next());
    }
}