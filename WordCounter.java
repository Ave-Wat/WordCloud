import java.util.*;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;

public class WordCounter{
  private ArrayList<String> stopWords;
  public WordCounter(){
    stopWords = new ArrayList<String>();
    loadStopWords("StopWords.txt");
  }

  //loads the words from "StopWords.txt"; the program doesn't include these words in the WordCountMap
  public void loadStopWords(String filename){
    try{
      Scanner scan = new Scanner(new File(filename));
      while(scan.hasNext()){
        String line = scan.nextLine();
        this.stopWords.add(line);
      }
    }catch(FileNotFoundException e){
      System.err.println(e);
    }
  }

  //loads the inputted file and puts the words and the number of their occurances into a WordCountMap
  public void load(String filename, WordCountMap tree){
    try{
      Scanner scan = new Scanner(new File(filename));

      while(scan.hasNext()){
        String line = scan.nextLine();
        String[] words = line.split(" ");

        for(int j = 0; j < words.length; j ++){
          words[j] = words[j].toLowerCase();
          //System.out.println(words[j]);
          String[] letters = words[j].split("");
          String result = "";

          for(int i = 0; i < letters.length; i ++){
            if(i == 0 || i == letters.length - 1){
              letters[i] = letters[i].replaceAll("[^a-zA-Z0-9]", "");
            }
            result = result + letters[i];          
          }
          words[j] = result;
        }

        for(int i = 0; i < words.length; i ++){
          if(!this.stopWords.contains(words[i])){
            if(words[i].length() != 0){
              tree.incrementCount(words[i]);
            }
          }
        }
      }
    }catch(FileNotFoundException e){
      System.err.println(e);
    }
  }

  public static void main(String[] args){
    String filename = "";
    if(args.length == 1){
      filename = args[0];
      WordCounter counter = new WordCounter();
      WordCountMap myTree = new WordCountMap();
      counter.load(filename, myTree);
      List<WordCount> words = myTree.getWordCountsByCount();
    }else if(args.length == 3){
      filename = args[0];
      int includeNum = Integer.parseInt(args[1]);
      String outfilename = args[2];

      WordCounter counter = new WordCounter();
      WordCountMap myTree = new WordCountMap();
      counter.load(filename, myTree);
      List<WordCount> words = myTree.getWordCountsByCount();
      List<WordCount> correctLengthWords = new ArrayList<WordCount>();
      for(int i = 0; i < includeNum; i ++){
        correctLengthWords.add(words.get(i));
      }


      PrintWriter toFile = null;
      try{
        toFile = new PrintWriter(outfilename);
        toFile.println(WordCloudMaker.getWordCloudHTML("Wuthering Heights", correctLengthWords));
        toFile.close();
      }catch (FileNotFoundException e){
        System.out.println(e.getMessage());
      }
    }else{
      System.out.println("For the program to print out the number of occurances of each word, you must select a file to read from. If you wish to create a Word Cloud, you must input a file to read from, the number of words in your Word Cloud, and an HTML file to write to.");
    }
  }

}