# WordCloud
This is a project from my CS201 Data Structures class. This project uses a special implementation of a tree to create a word cloud of the most frequent words in a manuscript

This project uses a tree data structure called WordCountMap to store each of the words found in a manuscript and the number of times each word was found. In the tree, each node except for the root represents a String of length 1 (or a character if you prefer) as well as a count. The children of a node are characters that could come after the current character to represent a word. To find a word, you'll work down the tree until you reach the final character in the word, and then look at the count for that node.

To have a simplified version of the tree that works for a text doc, we will use 10 "the", 10 "cat" and 10 "there" words: *(c and t branch from "")*

"" - t (count = 0) - h (count = 0) - e (count = 10) - r (count = 0) - e (count = 10)

|_ c (count = 0) - a (count = 0) - t (count = 10)

WordCounter then uses WordCountMap to read a text file and output each unique word and the number of times the word was repeated. The WordCounter excludes any common words included in "StopWords.txt". 

The WordCloudMaker then makes an html word cloud.

Start by running the WordCounter file. First complile all: "javac \*.java". Then run "java WordCounter textFileName numberOfWordsToInclude outFileName". The repl.it run button is configured to use "java WordCounter WutheringHeights(testfile).txt 50 WutheringHeightsWordCloud.html". If you're using the run button in the repl.it link below, you still must manually compile before running.

[![Run on Repl.it](https://repl.it/badge/github/Ave-Wat/WordCloud)](https://repl.it/github/Ave-Wat/WordCloud)

