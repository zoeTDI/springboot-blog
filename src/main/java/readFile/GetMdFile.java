package readFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 18324
 */
public class GetMdFile {
    private final String dirPath = "D:\\MdFiles";
    private String[] filesPath;
    private List<String> allMdFiles;

    public void setAllMdFiles() {
        File dir = new File(this.dirPath);
//        检查目录是否存在
        if (dir.exists() && dir.isDirectory()) {
            listFiles(dir);
        } else {
            this.allMdFiles = null;
            System.out.println(this.dirPath + " 目录不存在");
        }
    }

    private void listFiles(File dir) {
        File[] files = dir.listFiles();
        String fileName;
        int lastDotIndex;
        assert files != null;
        for (File file : files) {
            if (file.isFile()) {
                fileName = file.getName();
                lastDotIndex = fileName.lastIndexOf(".");
                if (lastDotIndex != -1 && ".md".equals(fileName.substring(lastDotIndex))) {
                    if (this.allMdFiles == null) {
                        this.allMdFiles = new ArrayList<>();
                    }
                    this.allMdFiles.add(file.getAbsolutePath());
                }
            } else {
                listFiles(file);
            }
        }
    }
}
