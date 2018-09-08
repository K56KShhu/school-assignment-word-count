import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zkyyo
 * @since 2018-09-07 20:53
 **/
public class Solution {
    private int totalCharacters = 0;
    private int totalWords = 0;
    private int totalLines = 0;

    private int blankLine = 0;
    private int commentLine = 0;
    private int codeLine = 0;

    private boolean hasMultiLineComment = false;


    public static void main(String[] args) throws Exception {
        new Solution().solution();
    }

    public void solution() throws Exception {
        String path = "D:\\project\\wc\\a.txt";
        File file = new File(path);
        BufferedReader br = new BufferedReader(new FileReader(file));

        String st;

        while ((st = br.readLine()) != null) {
            System.out.println(">" + st);

            // 基本功能
            // 匹配字符数
            totalCharacters += st.length();

            // 匹配函数
            totalLines++;

            // 匹配单词
            int words = 0;
            Pattern pattern = Pattern.compile("\\w+");
            Matcher matcher = pattern.matcher(st);
            while (matcher.find()) {
                words++;
            }
            totalWords += words;
        }

        System.out.println("characters: " + totalCharacters);
        System.out.println("words: " + totalWords);
        System.out.println("lines: " + totalLines);
        System.out.println("blank lines: " + blankLine);
        System.out.println("comment lines: " + commentLine);
        System.out.println("code lines: " + codeLine);
    }
}
