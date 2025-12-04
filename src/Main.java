public class Main {
    public static void main(String[] args) {
        var trie = new Trie(new char[] {'a', 'b', 'c', 'd'});

        trie.insert("abc");
        trie.insert("abd");
        trie.insert("cc");
        trie.insert("ab");
        trie.insert("abcd");
        trie.insert("bbc");
        trie.insert("bcc");
        trie.insert("bbbb");

        var it = trie.new TrieIterator();

        /*while(it.hasNext()){
            System.out.println(it.next());
        }*/

        for(var i : trie){
            System.out.println(i);
        }

        var arr = trie.prefixSeach("ab");

        for(var i : arr){
            System.out.println(i);
        }

        //System.out.println(trie.contains("cc"));
    }
}