import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class WordCountMap{
  private MapNode root;
  private int wordCount;

  private class MapNode{
    private String key = "";
    private int count = 0;
    private ArrayList<MapNode> children = new ArrayList<MapNode>();

    public MapNode(String key){
      this.key = key;
    }
  }
  
  //Constructs an empty WordCountMap.        
  public WordCountMap(){
    root = new MapNode("");
    wordCount = 0;
  }
  
  // Adds 1 to the existing count for word, or adds word to the WordCountMap with a count of 1 if it was not already present.
  public void incrementCount(String word){
    addWord(word, this.root);
  }
  
  //recursive helper method for increment count
  public void addWord(String word, MapNode rt){
    if(word.length() == 0){
      rt.count ++;
    }else{
      boolean contains = false;
      for(MapNode node : rt.children){
        if(word.substring(0,1).equals(node.key)){
          contains = true;
          addWord(word.substring(1), node);
        }
      }
      if(!contains){
        MapNode newNode = new MapNode(word.substring(0,1));
        rt.children.add(newNode);
        addWord(word.substring(1), newNode);
      }
    }
  }

  /*Removes 1 to the existing count for word. If word is not present, does nothing. If word is present and this decreases its count to 0, removes any nodes in the tree that are no longer necessary to represent the remaining words.*/
  public void decrementCount(String word){
    decWord(word, this.root);
  }

  //recursive helper method for decrementCount
  public void decWord(String word, MapNode rt){
    if(word.length() == 0){
      rt.count --;
    }else{
      for(MapNode node : rt.children){
        if(word.substring(0,1).equals(node.key)){
          decWord(word.substring(1), node);
          if(node.children.size() == 0 && node.count == 0){
            rt.children.remove(node);
          }
        }
      }
    }
  }
  
  
  //Returns true if word is stored in this WordCountMap with a count greater than 0, and false otherwise.
  public boolean contains(String word){
    return containsHelp(word, this.root);
  }

  public boolean containsHelp(String word, MapNode rt){
    if(word.length() == 0){
      return true;
    }else{
      for(MapNode node : rt.children){
        if(word.substring(0,1).equals(node.key)){
          return containsHelp(word.substring(1), node);
        }
      }
    }
    return false;
  }
  
  // Returns the count of word, or -1 if word is not in the WordCountMap.
  public int getCount(String word){
    return countHelp(word, this.root);
  }

  public int countHelp(String word, MapNode rt){
    if(word.length() == 0){
      return rt.count;
    }else{
      for(MapNode node : rt.children){
        if(word.substring(0,1).equals(node.key)){
          return countHelp(word.substring(1), node);
        }
      }
    }
    return -1;
  }
  
  /** 
  Returns a list of WordCount objects, one per word stored in this WordCountMap, sorted in decreasing order by count. I apologize for the inefficient and ugly code.
  */
  public List<WordCount> getWordCountsByCount(){
    ArrayList<MapNode> visited = new ArrayList<MapNode>();
    ArrayList<MapNode> nodesInOut = new ArrayList<MapNode>();
    ArrayList<WordCount> outList = new ArrayList<WordCount>();

    while(!visited.contains(this.root)){
      String word = "";
      MapNode cur = this.root;
      while(cur != null){
        word += cur.key;
        MapNode next = null;

        if(cur.children.isEmpty()){
          WordCount output = new WordCount(word, cur.count);
          outList.add(output);
          visited.add(cur);

        }else{
          if(cur.count != 0 && !nodesInOut.contains(cur)){
            WordCount output = new WordCount(word, cur.count);
            outList.add(output);
            nodesInOut.add(cur);
          }

          boolean childrenVisited = true;
          for(MapNode node : cur.children){
            if(!visited.contains(node)){
              next = node;
              childrenVisited = false;
            }
          }
          if(childrenVisited){
            visited.add(cur);
          }
        }
        cur = next;
      }
    }
    List<WordCount> ou = selectionSort(outList);
    return ou;
  }

  //sorts the list greates to least from getWordCountsByCount() using selection sort
  public ArrayList<WordCount> selectionSort(ArrayList<WordCount> input) {
    for (int i = 0; i < input.size(); i++) {
      int pos = i;
      for (int j = i; j < input.size(); j++) {
        if (input.get(j).getCount() > input.get(pos).getCount()){
          pos = j;
        }
      }
      WordCount temp = input.get(pos);
      input.set(pos, input.get(i));
      input.set(i, temp);
    }
    return input;
  }
  
  //Returns a count of the total number of nodes in the tree. 
  public int getNodeCount(){
    ArrayList<MapNode> visited = new ArrayList<MapNode>();
    int nodeCount = 0;

    while(!visited.contains(this.root)){
      MapNode cur = this.root;
      while(cur != null){
        MapNode next = null;

        boolean childrenVisited = true;
        for(MapNode node : cur.children){
          if(!visited.contains(node)){
            next = node;
            childrenVisited = false;            
          }
        }
        if(childrenVisited){
          nodeCount ++;
          visited.add(cur);
        }
        cur = next;
      }
    }
    return nodeCount;
  }

  //the main runs a test using the specific file "testFile.txt"
  public static void main(String[] args){
    String filename = "testFile.txt";
    if(args.length == 1){
      filename = args[0];
    }

    WordCounter counter = new WordCounter();

    WordCountMap myTree = new WordCountMap();

    counter.load(filename, myTree);
    myTree.decrementCount("ran");
    System.out.println(myTree.contains("ran"));
    System.out.println(myTree.contains("off"));
    System.out.println(myTree.getCount("ran"));
    System.out.println(myTree.getCount("off"));
    List<WordCount> words = myTree.getWordCountsByCount();
    for(WordCount word : words){
      System.out.print(word.getWord() + ":");
      System.out.println(word.getCount());
    }
    System.out.println(myTree.getNodeCount());
  }
}