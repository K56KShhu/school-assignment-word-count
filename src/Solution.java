import sun.text.resources.iw.FormatData_iw_IL;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.util.*;
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

    private Set<File> files = new HashSet<>();

    public static void main(String[] args) throws Exception {
        Solution s = new Solution();
        s.recursiveGetFiles("D:\\project\\wc\\sample", true);
        for (File f : s.files) {
            System.out.println(f.getName());
            System.out.println(f.getAbsolutePath());
        }
    }

    private void recursiveGetFiles(String path, boolean currentFolder) {
        File file = new File(path);
        if (file.exists()) {
            File[] fileInCurrentDir = file.listFiles();
            for (File f : fileInCurrentDir) {
                if (f.isDirectory()) {
                    if (!currentFolder) {
                        recursiveGetFiles(f.getAbsolutePath(), false);
                    }
                } else {
                    files.add(f);
                }
            }
        }
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
//            st = st.replaceAll("\\\"", "");
            int indexOfFirstQuote = st.indexOf("\"");
            int indexOfSecondQuote = st.indexOf("\"", indexOfFirstQuote + 1);
            System.out.println(indexOfFirstQuote + " " + indexOfSecondQuote);
            if (indexOfFirstQuote != -1 && indexOfSecondQuote != -1) {
                st = st.substring(0, indexOfFirstQuote) + st.substring(indexOfSecondQuote + 1, st.length() - 1);
            } else if (indexOfFirstQuote != -1 && indexOfSecondQuote == -1) {
                st = st.substring(0, indexOfFirstQuote);
            }
            System.out.println("处理后: " + st);

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
                    System.out.println("空白行");
                } else {
                    int indexOfDoubleSlash = st.indexOf("//");
                    int indexOfMultiLineCommentBegin = st.indexOf("/*");
                    if (indexOfDoubleSlash == -1 && indexOfMultiLineCommentBegin == -1) { // 不存在注释
                        codeLine++;
                        System.out.println("代码行");
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

/*
    class FileFilterImpl implements FileFilter {
        private String extension;

        public FileFilterImpl(String extension) {
            this.extension = extension;
        }

        @Override
        public boolean accept(File pathname) {
            return pathname.getName().endsWith(extension);
        }
    }
*/

}
