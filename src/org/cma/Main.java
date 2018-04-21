package org.cma;


public class Main {
    public static void main(String[] args){
	// write your code here
        new Main().run();
    }
    private void run() {
        InputReader.openDocument();
        Finder finder = new Finder(InputReader.getStartDir(),InputReader.getOutFileName());
        finder.mergeFiles();
    }
}
