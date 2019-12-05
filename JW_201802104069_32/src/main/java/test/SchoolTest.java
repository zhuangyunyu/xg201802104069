package test;

import util.JdbcHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SchoolTest {
    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement preparedStatement =null;
        try {
            connection = JdbcHelper.getConn();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO school(description,no)values (?,?)");
        preparedStatement.setString(1,"管理学院");
        preparedStatement.setString(2,"22");
        //执行第一条INSERT语句
        preparedStatement.executeUpdate();
        preparedStatement = connection.prepareStatement(
                "INSERT INTO school(description,no)values (?,?)");
        preparedStatement.setString(1,"土木学院");
        preparedStatement.setString(2,"02");
        preparedStatement.executeUpdate();
        //提交当前操作
        connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage()+"\n errorCode="+e.getErrorCode());
        try {
            //回滚当前链接所做的操作
            if (connection !=null){
                connection.rollback();
            }
        } catch (SQLException e1){
            e1.printStackTrace();
        }
        }finally {
            try {
                //恢复自动提交
                if (connection!=null){
                    connection.setAutoCommit(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //关闭资源
            JdbcHelper.close(preparedStatement,connection);
        }
    }
}
