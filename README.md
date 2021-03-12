# WordCloud
This is a project from my CS201 Data Structures class. This project uses a special implementation of a tree ADT to create a word cloud of the most frequent words in a manuscript

### Structure Explanation
This project uses a tree data structure called WordCountMap to store each of the words found in a manuscript and the number of times each word was found. In the tree, each node except for the root represents a String of length 1 (or a character if you prefer) as well as a count. The children of a node are characters that could come after the current character to represent a word. To find a word, you'll work down the tree until you reach the final character in the word, and then look at the count for that node.

To have a simplified example of the tree that works for a text doc, we will use 10 "the", 10 "cat" and 10 "there" words: *(c and t branch from "")*

"" - t (count = 0) - h (count = 0) - e (count = 10) - r (count = 0) - e (count = 10)

|_ c (count = 0) - a (count = 0) - t (count = 10)

WordCounter then uses WordCountMap to read a text file and keeps track of the words using the special tree ADT. The WordCounter excludes any common words included in [StopWords.txt](/StopWords.txt).

Then, WordCounter either prints out a list of words and the number of times they were repeated or the WordCloudMaker makes an html word cloud.

### Running Instructions:

1. Complile all: `javac \*.java`. 
2. There are two ways to then run the project.
  - The first: `java WordCounter textFileName`. This will output a list of words in the textfile (excluding stop words) and the number of times each word repeated. 

  - The second: `java WordCounter textFileName numberOfWordsToInclude outFileName`. For example, if you use the WutheringHeights file which exists in my repository, you could run `java WordCounter WutheringHeights.txt 50 WutheringHeightsWordCloud.html`.

If you use the repl.it link below to run the project, you must run all commands manually. The Run button remains unconfigured.

[![Run on Repl.it](https://repl.it/badge/github/Ave-Wat/WordCloud)](https://repl.it/github/Ave-Wat/WordCloud)
