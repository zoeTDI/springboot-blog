package readFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Markdown解析器类，用于解析包含YAML前导信息的Markdown文件
 * 该类负责读取文件，解析YAML头部信息，并提取Markdown内容
 *
 * @author 18324
 */
public class MarkdownParser {
    // 文件路径
    private final String path;
    // 存储解析后的YAML信息
    private Map<String, Object> yaml;
    // Markdown内容
    private String content;
    private static final Logger logger = LoggerFactory.getLogger(MarkdownParser.class);

    /**
     * 构造函数，初始化MarkdownParser实例
     *
     * @param path Markdown文件的路径
     * @throws IOException 如果文件读取过程中发生错误
     */
    public MarkdownParser(String path) throws IOException {
        this.path = path;
        setYaml();
        setContent();
    }

    /**
 * 解析YAML头部信息的方法
 * 读取文件，提取并解析YAML信息到Map对象中
 *
 * @throws IOException 如果文件读取过程中发生错误
 */
private void setYaml() throws IOException {
    this.yaml = new HashMap<>();
    BufferedReader reader = new BufferedReader(new FileReader(this.path));
    List<String> yamlLines = new ArrayList<>();
    String currentLine;
    // 检查文件是否以 "---" 开头
    if ("---".equals(reader.readLine())) {
        // 读取YAML信息，直到再次遇到 "---"
        while (!"---".equals(currentLine = reader.readLine())) {
            yamlLines.add(currentLine);
        }
    }
    // 分析YAML字符串，将其解析为键值对
    int numberOfLines = yamlLines.size();
    String[] keyValueArray;
    String key;
    List<String> valueList = new ArrayList<>();
    int index = 0;
    while (index < numberOfLines) {
        keyValueArray = yamlLines.get(index).split(":");
        if (keyValueArray.length > 1) {
            keyValueArray[1] = String.join(":", Arrays.copyOfRange(keyValueArray, 1, keyValueArray.length)).trim();
            if (keyValueArray[1].startsWith("\"") && keyValueArray[1].endsWith("\"")) {
                keyValueArray[1] = keyValueArray[1].substring(1, keyValueArray[1].length() - 1);
            }
            this.yaml.put(keyValueArray[0], keyValueArray[1]);
//            logger.info("current line: " + yamlLines.get(index));
            index++;
        } else {
            key = keyValueArray[0];
            index++;
            while (index < numberOfLines) {
                if (yamlLines.get(index).trim().split(":").length > 2 && !yamlLines.get(index).trim().split(":")[1].isEmpty()) {
                    break;
                }
                valueList.add(yamlLines.get(index).trim().substring(1));
//                logger.info("current line: " + yamlLines.get(index));
                index++;
            }
            this.yaml.put(key, valueList);
            key = "";
            valueList = new ArrayList<>();
        }
    }
}


    /**
     * 获取解析后的YAML信息
     *
     * @return 包含YAML信息的Map对象
     */
    public Map<String, Object> getYaml() {
        return this.yaml;
    }

    /**
     * 提取Markdown内容的方法
     * 读取文件，跳过YAML头部信息，提取剩余的Markdown内容
     *
     * @throws IOException 如果文件读取过程中发生错误
     */
    private void setContent() throws IOException {
        this.content = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.path));
            String line;

            // 检查文件是否以 "---" 开头
            if ("---".equals((line = reader.readLine()).trim())) {
                // 跳过头部信息
                while ((line = reader.readLine()) != null && !"---".equals(line.trim())) {
                    continue;
                }
            } else {
                reader = new BufferedReader(new FileReader(this.path));
            }

            // 读取文件内容
            StringBuilder contentBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
            this.content = contentBuilder.toString();
        } catch (FileNotFoundException e) {
            throw new IOException("文件未找到: " + this.path, e);
        } catch (IOException e) {
            throw new IOException("读取文件时发生错误: " + this.path, e);
        }
    }

    /**
     * 获取Markdown内容
     *
     * @return Markdown内容的字符串表示
     */
    public String getContent() {
        return this.content;
    }
}

