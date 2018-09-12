package service;

import org.apache.tools.ant.DirectoryScanner;
import view.MainView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zkyyo
 * @since 2018-09-07 20:53
 **/
public class WordCount {
    private String basicPath = System.getProperty("user.dir");

    private int totalCharacters = 0;
    private int totalWords = 0;
    private int totalLines = 0;

    private int blankLine = 0;
    private int commentLine = 0;
    private int codeLine = 0;

    private boolean hasMultiLineComment = false;
    private Set<File> files = new HashSet<>();

    private StringBuilder text = new StringBuilder();
    private List<String> countResult = new LinkedList<>();

    public static void main(String[] args) throws Exception {
        // 处理输入参数
        if (args.length == 0) {
            System.out.println("请输入参数");
            System.exit(-1);
        }
        Set<String> ops = new HashSet<>();
        String path = null;
        for (String arg : args) {
            if (arg.startsWith("-")) {
                ops.add(arg);
            } else {
                if (path == null) {
                    path = arg;
                } else {
                    System.out.println("参数错误 重复地址");
                    System.exit(-1);
                }
            }
        }
        if (path == null && !ops.contains("-x")) {
            System.out.println("参数错误 路径为空");
            System.exit(-1);
        }
        if (ops.contains("-x")) {
//            wc.getCountResult().add("字符数: " + wc.getTotalCharacters());
//            wc.getCountResult().add("单词数: " + wc.getTotalWords());
//            wc.getCountResult().add("行数: " + wc.getTotalLines());
//            wc.getCountResult().add("");
//            wc.getCountResult().add("空行数: " + wc.getTotalLines());
//            wc.getCountResult().add("代码行: " + wc.getCodeLine());
//            wc.getCountResult().add("注释行数: " + wc.getCommentLine());
            new MainView(new LinkedList<>(), new StringBuilder()).draw();
            return;
        }

        // 计数统计
        WordCount wc = new WordCount();
        wc.filterFiles(path);
        for (File f : wc.getFiles()) {
            if (!f.getAbsolutePath().endsWith(".jar")) {
                System.out.println("count " + f.getAbsolutePath());
                wc.solution(f);
            }
        }

        if (ops.contains("-c")) {
            System.out.println("字符数: " + wc.getTotalCharacters());
        }
        if (ops.contains("-w")) {
            System.out.println("单词数: " + wc.getTotalWords());
        }
        if (ops.contains("-l")) {
            System.out.println("行数: " + wc.getTotalLines());
        }
        if (ops.contains("-a")) {
            System.out.println("空行数: " + wc.getBlankLine());
            System.out.println("代码行: " + wc.getCodeLine());
            System.out.println("注释行数: " + wc.getCommentLine());
        }

        System.out.println();

    }

    private void filterFiles(String path) {
        DirectoryScanner scanner = new DirectoryScanner();
        scanner.setBasedir(basicPath);
        scanner.setIncludes(new String[]{path});
        scanner.setCaseSensitive(false);
        scanner.scan();
        for (String file : scanner.getIncludedFiles()) {
            files.add(new File(basicPath + "\\" + file));
        }
    }

    public void solution(File f) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(f));

        String st;

        while ((st = br.readLine()) != null) {
            text.append(st).append("\n");

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
            if (!f.getAbsolutePath().endsWith(".txt")) {
                int indexOfFirstQuote = st.indexOf("\"");
                int indexOfSecondQuote = st.indexOf("\"", indexOfFirstQuote + 1);
                if (indexOfFirstQuote != -1 && indexOfSecondQuote != -1) {
                    st = st.substring(0, indexOfFirstQuote) + st.substring(indexOfSecondQuote + 1, st.length() - 1);
                } else if (indexOfFirstQuote != -1 && indexOfSecondQuote == -1) {
                    st = st.substring(0, indexOfFirstQuote);
                }

                if (hasMultiLineComment) {
                    commentLine++;
                    int indexOfMultiLineCommentEnd = st.indexOf("*/");
                    if (indexOfMultiLineCommentEnd >= 0) {
                        //  /*
                        //  abc
                        // >*/
                        hasMultiLineComment = false;
                    }
                } else {
                    if (st.trim().length() <= 1) { // 判断空行
                        blankLine++;
                    } else {
                        int indexOfDoubleSlash = st.indexOf("//");
                        int indexOfMultiLineCommentBegin = st.indexOf("/*");
                        if (indexOfDoubleSlash == -1 && indexOfMultiLineCommentBegin == -1) { // 不存在注释
                            codeLine++;
                        } else if (indexOfDoubleSlash != -1 && indexOfMultiLineCommentBegin == -1) { // 只存在单行注释
                            commentLine++;
                        } else if (indexOfDoubleSlash == -1 && indexOfMultiLineCommentBegin != -1) { // 只存在多行注释
                            commentLine++;
                            hasMultiLineComment = true;
                            if (st.indexOf("*/") > indexOfMultiLineCommentBegin) { // 多行注释结束于同一行
                                hasMultiLineComment = false;
                            }
                        } else if (indexOfDoubleSlash != -1 && indexOfMultiLineCommentBegin != -1) { // 存在单行注释和多行注释
                            if (indexOfDoubleSlash < indexOfMultiLineCommentBegin) { // 单行注释在前
                                commentLine++;
                            } else { // 多行注释在前
                                commentLine++;
                                hasMultiLineComment = true;
                                if (st.indexOf("*/") > indexOfMultiLineCommentBegin) { // 多行注释结束于同一行
                                    hasMultiLineComment = false;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public int getTotalCharacters() {
        return totalCharacters;
    }

    public int getTotalWords() {
        return totalWords;
    }

    public int getTotalLines() {
        return totalLines;
    }

    public int getBlankLine() {
        return blankLine;
    }

    public int getCommentLine() {
        return commentLine;
    }

    public int getCodeLine() {
        return codeLine;
    }

    public StringBuilder getText() {
        return text;
    }

    public Set<File> getFiles() {
        return files;
    }

    public List<String> getCountResult() {
        return countResult;
    }
}
