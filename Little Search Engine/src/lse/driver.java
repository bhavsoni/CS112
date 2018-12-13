package lse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class driver {
	public static void main(String args[]) throws IOException {
        String docsFile = "docs.txt";
        String noiseWords = "noisewords.txt";

        LittleSearchEngine searchEngine = new LittleSearchEngine();

        searchEngine.makeIndex(docsFile, noiseWords);

        String kw1 = "deep";
        String kw2 = "world";

        System.out.println(searchEngine.top5search(kw1, kw2));
    }
}
