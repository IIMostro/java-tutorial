package org.ilmostro.basic.leetcode.top100;

/**
 * @author li.bowei
 */
public class Trie {

    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.insert("a");
        boolean hello = trie.search("a");
        System.out.println(hello);
        boolean hell = trie.startsWith("a");
        System.out.println(hell);
    }

    static class Node{
        boolean isEnd;
        Node[] next;

        public Node() {
            next = new Node[26];
        }
    }

    Node[] root;

    public Trie() {
        root = new Node[26];
    }

    public void insert(String word) {
        if(word == null || word.length() <= 0) return;
        Node curr = root[word.charAt(0) - 'a'];
        if (curr == null) {
            Node node = new Node();
            if(word.length() == 1) node.isEnd = true;
            root[word.charAt(0) - 'a'] = node;
            curr = node;
        }
        for (int i = 1; i < word.length(); i++) {
            char letter = word.charAt(i);
            if(curr.next[letter - 'a'] == null){
                Node node = new Node();
                if(i == word.length() - 1) node.isEnd = true;
                curr.next[letter - 'a'] = node;
            }else{
                if(i == word.length() - 1) curr.next[letter - 'a'].isEnd = true;
            }
            curr = curr.next[word.charAt(i) - 'a'];
        }
    }

    public boolean search(String word) {
        if(word == null || word.length() <= 0) return false;
        Node curr = root[word.charAt(0) - 'a'];
        if(curr == null) return false;
        if (word.length() == 1) return curr.isEnd;
        for (int i = 1; i < word.length(); i++) {
            Node next = curr.next[word.charAt(i) - 'a'];
            if(i == word.length() - 1 && next != null && next.isEnd) return true;
            if (next == null) {
                return false;
            }
            curr = next;
        }
        return false;
    }

    public boolean startsWith(String prefix) {
        if(prefix == null || prefix.length() <= 0) return false;
        Node curr = root[prefix.charAt(0) - 'a'];
        if(curr == null) return false;
        for (int i = 1; i < prefix.length(); i++) {
            Node next = curr.next[prefix.charAt(i) - 'a'];
            if(next == null) return false;
            curr = next;
        }
        return true;
    }
}
