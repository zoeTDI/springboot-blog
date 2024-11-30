package note;

import DB.DBManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import readFile.MarkdownFileSyncer;
import readFile.MarkdownParser;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 18324
 */
public class NoteTest {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(NoteTest.class);
        MarkdownFileSyncer markdownFileSyncer = new MarkdownFileSyncer();
        markdownFileSyncer.setAllMdFiles();
        DBManager db = new DBManager();
        try {
            Connection conn = db.getConnection("jdbc:mysql://localhost:3306/blog", "root", "123456");
            String sql = "SELECT path FROM notes LIMIT 5";
            ResultSet resultSet = db.executeQuery(sql, conn);
            if (resultSet.isBeforeFirst()) {
                Map<String, Object>[] mbs = new Map[5];
                while (resultSet.next()) {
                    MarkdownParser markdownParser = new MarkdownParser(resultSet.getString("path"));
                    Map<String, Object> mb = markdownParser.getYaml();
                    mb.put("content", markdownParser.getContent());
                    logger.info(mb.toString());
                    mbs[resultSet.getRow() - 1] = mb;
                }
            } else {
                logger.info("Not data found.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            logger.error("Error connecting to database: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
