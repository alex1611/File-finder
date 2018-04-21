package org.cma;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class Finder implements FileVisitor<Path> {
    private Comparator<Path> comparator = new Comparator<Path>() {
        @Override
        public int compare(Path o1, Path o2) {
            String strPath1 = o1.getFileName().toString();
            String strPath2 = o2.getFileName().toString();
            return strPath1.compareTo(strPath2);
        }
    };
    //множество файлов
    private List<Path> files = new ArrayList<>();
    private Path rootDir;
    private String rootName;
    private Path outFile;
    private String outName;
    public Finder(String dirName, String outName){
        try {
            this.rootName = dirName;
            this.rootDir = Paths.get(dirName);
            this.outName = outName;
            this.outFile = Paths.get(outName);
            Files.write(outFile,"".getBytes());
        } catch (IOException e){
            System.err.println("File openning error");
            e.printStackTrace();
        }

    }
    //склеивает файлы в один
    public void mergeFiles(){
        find();
        files.stream().sorted(comparator).flatMap(lines -> {
            try{
                return Files.lines(lines);
            } catch(IOException e){
                e.printStackTrace();
            }
            return null;
        }).forEach(line -> {
            try {
                Files.write(outFile,(line + System.lineSeparator()).getBytes(StandardCharsets.UTF_8),StandardOpenOption.APPEND);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    //поиск всех текстовых файлов
    public void find(){
        try {
            Files.walkFileTree(rootDir, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        return  FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (file.toString().toLowerCase().endsWith(".txt")) {
            files.add(file);
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

}
