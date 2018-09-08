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
        String path = "D:\\project\\wc\\sample\\a.txt";
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

            // 扩展功能
            if (hasMultiLineComment) {
                commentLine++;
                System.out.println("多行注释");
                int indexOfMultiLineCommentEnd = st.indexOf("*/");
                if (indexOfMultiLineCommentEnd >= 0) {
                    //  /*
                    //  abc
                    // >*/
                    hasMultiLineComment = false;
                    System.out.println("多行注释 结束");
                }
            } else {
                if (st.trim().length() <= 1) { // 判断空行
                    blankLine++;
                    System.out.println("[blank line]");
                } else {
                    int indexOfDoubleSlash = st.indexOf("//");
                    int indexOfMultiLineCommentBegin = st.indexOf("/*");
                    if (indexOfDoubleSlash == -1 && indexOfMultiLineCommentBegin == -1) { // 不存在注释
                        codeLine++;
                        System.out.println("[code line]");
                    } else if (indexOfDoubleSlash != -1 && indexOfMultiLineCommentBegin == -1) { // 只存在单行注释
                        commentLine++;
                        System.out.println("单行注释");
                    } else if (indexOfDoubleSlash == -1 && indexOfMultiLineCommentBegin != -1) { // 只存在多行注释
                        commentLine++;
                        System.out.println("多行注释");
                        hasMultiLineComment = true;
                        if (st.indexOf("*/") > indexOfMultiLineCommentBegin) { // 多行注释结束于同一行
                            System.out.println("多行注释在同一行");
                            hasMultiLineComment = false;
                        }
                    } else if (indexOfDoubleSlash != -1 && indexOfMultiLineCommentBegin != -1) { // 存在单行注释和多行注释
                        if (indexOfDoubleSlash < indexOfMultiLineCommentBegin) { // 单行注释在前
                            commentLine++;
                            System.out.println("单行注释");
                        } else { // 多行注释在前
                            commentLine++;
                            System.out.println("多行注释");
                            hasMultiLineComment = true;
                            if (st.indexOf("*/") > indexOfMultiLineCommentBegin) { // 多行注释结束于同一行
                                System.out.println("多行注释在同一行");
                                hasMultiLineComment = false;
                            }
                        }
                    }
                }
            }
        }

        System.out.println("characters: " + totalCharacters);
        System.out.println("words: " + totalWords);
        System.out.println("lines: " + totalLines);
        System.out.println("blank lines: " + blankLine);
        System.out.println("comment lines: " + commentLine);
        System.out.println("code lines: " + codeLine);
    }


}
