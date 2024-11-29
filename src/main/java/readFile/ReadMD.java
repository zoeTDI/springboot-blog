package readFile;

import java.io.*;
import java.sql.Array;
import java.util.*;

/**
 * @author 18324
 */
public class ReadMD {
    private String path;
    private Map<String, Object> yaml;
    private String content;

    public ReadMD(String path) throws IOException {
        this.path = path;
        setYaml();
        setContent();
    }

    public void setFilePath(String path) {
        this.path = path;
    }

    public String getFilePath(String path) {
        return path;
    }

    public void setYaml() throws IOException {
        this.yaml = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(path));
        StringBuilder yaml = new StringBuilder();
        StringBuilder content = new StringBuilder();
//        打印文件内容在控制台
        String tempString = null;
        if ("---".equals(tempString = reader.readLine())) {
            while (!"---".equals(tempString = reader.readLine())) {
                yaml.append(tempString).append("\n");
            }
        }
        String[] yamlArray = yaml.toString().split("\n");
        int yamlArrayLength = yamlArray.length;
        String[] kvArray = new String[2];
        String k = "";
        List<String> vArray = new ArrayList<>();
        int i = 0;
        while (i < yamlArrayLength) {
            kvArray = yamlArray[i].split(":");
            if (kvArray.length != 1) {
//                如果kvArray.length大于2，则把索引位置1及其之后的元素用:连接
                kvArray[1] = String.join(":", Arrays.copyOfRange(kvArray, 1, kvArray.length));
                this.yaml.put(kvArray[0], kvArray[1]);
                i++;
            } else {
                k = kvArray[0];
                i++;
                while (i < yamlArrayLength) {
                    vArray.add(yamlArray[i].trim().substring(1));
                    i++;
                    if (yamlArray[i].trim().split(":").length > 2 && !yamlArray[i].trim().split(":")[1].isEmpty()) {
                        break;
                    }
                }
                this.yaml.put(k, vArray);
                k = "";
                vArray = new ArrayList<>();
            }
        }
    }

    public Map<String, Object> getYaml() {
        return this.yaml;
    }

    public void setContent() throws IOException {
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
            }else {
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

//        this.content = "";
//        BufferedReader reader = new BufferedReader(new FileReader(this.path));
//        String templateString = reader.readLine();
//        if (templateString != null && "---".equals(templateString.trim())) {
//            if ((templateString = reader.readLine()) != null) {
//                while (!"---".equals(reader.readLine().trim())) {
//                    continue;
//                }
//            } else {
//                reader = new BufferedReader(new FileReader(this.path));
//            }
//        }
//        while ((templateString = reader.readLine()) != null) {
//            this.content += templateString + "\n";
//        }
    }

    public String getContent() {
        return this.content;
    }
}
