package readFile;

import DB.DBManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 18324
 *
 * GetMdFile类用于获取和管理Markdown文件
 * 它从指定目录中查找所有的Markdown文件，并与数据库中的记录进行同步
 */
public class MarkdownFileSyncer {
    // 存储所有Markdown文件路径的列表
    private List<String> allMdFiles;
    // 数据库管理对象
    private DBManager db;
    // 数据库连接对象
    private Connection conn;
    private static final Logger logger = LoggerFactory.getLogger(MarkdownFileSyncer.class);

    /**
     * 构造函数初始化类的成员变量，并从文件系统和数据库中同步Markdown文件路径
     */
    public MarkdownFileSyncer() {
        this.allMdFiles = new ArrayList<>();
        if (this.getMdFiles()) {
            this.db = new DBManager();
            try {
                this.conn = db.getConnection("jdbc:mysql://localhost:3306/blog", "root", "123456");
            } catch (SQLException | ClassNotFoundException e) {
                logger.error("Error connecting to database: " + e.getMessage());
            }
            if (!setAllMdFiles()) {
                updateAllMdFiles();
            }
            this.allMdFiles = new ArrayList<>();
            try {
                db.closeConnection(conn);
            } catch (SQLException e) {
//                e.printStackTrace();
                logger.error("Error closing connection: " + e.getMessage());
            }
        } else {
            logger.error("笔记数据同步失败。");
        }
    }

    /**
     * 从指定目录中获取所有的Markdown文件路径
     */
    private boolean getMdFiles() {
        // 检查目录是否存在且为目录
        String dirPath = "D:\\MdFiles";
        File dir = new File(dirPath);
        if (dir.exists() && dir.isDirectory()) {
            // 如果目录存在，则列出目录中的Markdown文件
            this.allMdFiles = new ArrayList<>();
            listFiles(dir);
            return true;
        } else {
            return dir.mkdirs();
        }
    }

    /**
     * 将所有Markdown文件路径设置到数据库中
     *
     * @return 如果成功插入至少一条记录，则返回true；否则返回false
     */
    public boolean setAllMdFiles() {
        try {
            int count = 0;
            // 查询数据库中的笔记记录
            ResultSet rs = db.executeQuery("SELECT * FROM notes", conn);
            // 如果查询结果为空，则将所有Markdown文件路径插入数据库
            if (!rs.next()) {
                for (String allMdFile : this.allMdFiles) {
                    String sql = "INSERT INTO notes (path) VALUES ('" + allMdFile + "');";
                    if (db.executeInsert(sql, conn)) {
                        System.out.println("插入成功：" + allMdFile);
                        count++;
                    } else {
                        System.out.println("插入失败：" + allMdFile);
                    }
                }
                logger.info("共插入 " + count + " 条记录");
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error executing query: " + e.getMessage());
        }
        return false;
    }

    /**
     * 更新数据库中的Markdown文件路径
     */
    public void updateAllMdFiles() {
        try {
            int count = 0;
            for (String mdFile : this.allMdFiles) {
                String selectString = "SELECT path FROM notes WHERE path = '" + mdFile + "';";
                if (!db.executeQuery(selectString, conn).next()) {
                    String insertString = "INSERT INTO notes (path) VALUES ('" + mdFile + "');";
                    db.executeInsert(insertString, conn);
                    if (db.executeInsert(insertString, conn)) {
                        System.out.println("插入成功：" + mdFile);
                        count++;
                    } else {
                        System.out.println("插入失败：" + mdFile);
                    }
                }
            }
            System.out.println("共插入 " + count + " 条记录");
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e.getMessage());
        }
    }

    /**
     * 递归地列出指定目录下的所有Markdown文件
     *
     * @param dir 目录文件对象
     */
    private void listFiles(File dir) {
        // 获取目录下的所有文件和子目录
        File[] files = dir.listFiles();
        // 确保listFiles()返回的结果不为空
        assert files != null;
        // 遍历目录下的每个文件或子目录
        for (File file : files) {
            // 如果是文件而非目录
            if (file.isFile()) {
                String fileName = file.getName();
                // 查找文件名中的最后一个"."的索引
                int lastDotIndex = fileName.lastIndexOf(".");
                // 检查文件扩展名是否为.md
                if (lastDotIndex != -1 && ".md".equals(fileName.substring(lastDotIndex))) {
                    // 将.md文件的绝对路径添加到列表中
                    this.allMdFiles.add(file.getAbsolutePath().replace("\\", "\\\\"));
                }
            } else {
                // 如果是子目录，则递归调用listFiles方法
                listFiles(file);
            }
        }
    }
}
