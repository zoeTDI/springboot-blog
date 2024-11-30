package DB;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * DBManager 类提供了数据库连接和操作的方法。
 *
 * @author 18324
 */
public class DBManager {
    // 初始化 SLF4J 的 Logger
    private static final Logger logger = LoggerFactory.getLogger(DBManager.class);

    /**
     * 获取数据库连接。
     *
     * @param url      数据库连接 URL。
     * @param user     数据库用户名。
     * @param password 数据库密码。
     * @return 返回 Connection 对象，如果连接失败则返回 null。
     * @throws SQLException       如果连接数据库时发生 SQL 异常。
     * @throws ClassNotFoundException 如果找不到数据库驱动类。
     */
    public Connection getConnection(String url, String user, String password) throws SQLException, ClassNotFoundException {
        // 加载 MySQL 数据库驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 建立与数据库的连接
        Connection connection = DriverManager.getConnection(url, user, password);
        logger.info("数据库连接成功");
        return connection;
    }

    /**
     * 关闭数据库连接。
     *
     * @param conn 要关闭的数据库连接对象。
     * @throws SQLException 如果关闭连接时发生 SQL 异常。
     */
    public void closeConnection(Connection conn) throws SQLException {
        conn.close();
    }

    /**
     * 执行 SQL 查询并返回结果集。
     *
     * @param sql        SQL 查询语句。
     * @param connection 数据库连接对象。
     * @return 返回包含查询结果的 ResultSet 对象，如果查询失败则返回 null。
     * @throws SQLException 如果执行查询时发生 SQL 异常。
     */
    public ResultSet executeQuery(String sql, Connection connection) throws SQLException {
        // 创建用于执行 SQL 语句的 Statement 对象
        Statement statement = connection.createStatement();
        // 执行 SQL 查询并获取结果集
        ResultSet resultSet = statement.executeQuery(sql);
        logger.info("查询执行: " + sql);
        return resultSet;
    }

    /**
     * 执行 SQL 插入操作。
     *
     * @param sql  SQL 插入语句。
     * @param conn 数据库连接对象。
     * @return 如果插入操作成功则返回 true，否则返回 false。
     * @throws SQLException 如果执行插入时发生 SQL 异常。
     */
    public Boolean executeInsert(String sql, Connection conn) throws SQLException {
        // 创建用于执行 SQL 语句的 Statement 对象
        Statement stmt = conn.createStatement();
        // 执行 SQL 插入语句并获取受影响的行数
        int rowsAffected = stmt.executeUpdate(sql);
        // 关闭 Statement 对象
        stmt.close();
        // 如果插入操作影响的行数大于 0，则返回 true，否则返回 false
        return rowsAffected > 0;
    }
}