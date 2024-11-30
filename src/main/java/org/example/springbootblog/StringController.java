package org.example.springbootblog;

import DB.DBManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import readFile.MarkdownFileSyncer;
import readFile.MarkdownParser;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 18324
 */
@RestController
public class StringController {
    @GetMapping("/api/getNotes")
    public List<Map<String, Object>> getNotes(@RequestParam(value = "page", defaultValue = "1") int page,
                                        @RequestParam(value = "size", defaultValue = "5") int size) {
        Logger logger = LoggerFactory.getLogger(StringController.class);
        MarkdownFileSyncer markdownFileSyncer = new MarkdownFileSyncer();
        markdownFileSyncer.setAllMdFiles();
        DBManager db = new DBManager();
        List<Map<String, Object>> mbs = null;
        try {
            Connection conn = db.getConnection("jdbc:mysql://localhost:3306/blog", "root", "123456");
            String sql = "SELECT path FROM notes LIMIT " + size + " OFFSET " + page + ";";
            ResultSet resultSet = db.executeQuery(sql, conn);
            if (resultSet.isBeforeFirst()) {
                mbs = new ArrayList<>();
                while (resultSet.next()) {
                    String filePath = resultSet.getString("path");
                    File file = new File(filePath);
                    if (file.exists()) {
                        MarkdownParser markdownParser = new MarkdownParser(filePath);
                        Map<String, Object> mb = markdownParser.getYaml();
                        mb.put("content", markdownParser.getContent());
//                        logger.info(mb.toString());
                        mbs.add(mb);
                    } else {
//                        从数据库中删除该数据
                        sql = "DELETE FROM notes WHERE path = '" + filePath.replace("\\", "\\\\") + "';";
                        if (db.executeInsert(sql, conn)) {
                            logger.info("Delete successfully.");
                        } else {
                            logger.info("Delete failed.");
                        }
                    }
                }

            } else {
                logger.info("Not data found.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            logger.error("Error connecting to database: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return mbs;
    }
}
