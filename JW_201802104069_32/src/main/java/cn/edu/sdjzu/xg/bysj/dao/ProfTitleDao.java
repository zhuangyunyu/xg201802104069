package cn.edu.sdjzu.xg.bysj.dao;

import cn.edu.sdjzu.xg.bysj.domain.ProfTitle;
import util.JdbcHelper;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;

public final class ProfTitleDao {
	private static ProfTitleDao profTitleDao=new ProfTitleDao();
	private ProfTitleDao(){}
	public static ProfTitleDao getInstance(){
		return profTitleDao;
	}
	public Collection<ProfTitle> findAll() throws SQLException {
		Collection<ProfTitle>profTitles = new HashSet<>();
		Connection connection = JdbcHelper.getConn();
		Statement stmt = connection.createStatement();
		ResultSet resultSet = stmt.executeQuery("select * from proftitle");
		//从数据库中取出数据
		while (resultSet.next()){
					profTitles.add(new ProfTitle(resultSet.getInt("id"),
					resultSet.getString("description"),
					resultSet.getString("no"),
					resultSet.getString("remarks")));
		}
		JdbcHelper.close(stmt,connection);
		return profTitles;
	}

	public ProfTitle find(Integer id) throws SQLException{
		Connection connection = JdbcHelper.getConn();
		String findProftitle = "SELECT * FROM proftitle where id = ?";
		PreparedStatement pstmt = connection.prepareStatement(findProftitle);
		pstmt.setInt(1,id);
		ResultSet resultSet = pstmt.executeQuery();
		ProfTitle profTitle =null;
		if (resultSet.next()){
			profTitle = new ProfTitle(
		 		resultSet.getInt("id"),
				resultSet.getString("description"),
				resultSet.getString("no"),
				resultSet.getString("remarks"));}
		JdbcHelper.close(pstmt,connection);
		return profTitle;
	}

	public boolean update(ProfTitle profTitle) throws SQLException {
		Connection connection = JdbcHelper.getConn();
		PreparedStatement pstmt = connection.prepareStatement("UPDATE proftitle SET description=?,no=?,remarks=? where id=?");
		pstmt.setString(1,profTitle.getDescription());
		pstmt.setString(2,profTitle.getNo());
		pstmt.setString(3,profTitle.getRemarks());
		pstmt.setInt(4,profTitle.getId());
		int affectedRowNum = pstmt.executeUpdate();
		System.out.println("更新了"+affectedRowNum+"条记录");
		JdbcHelper.close(pstmt,connection);
		return affectedRowNum>0;
	}

	public boolean add(ProfTitle profTitle)throws SQLException{
		//获得连接对象
		Connection connection = JdbcHelper.getConn();
		//创建sql语句
		String addProftitle_sql = "insert into proftitle (no,description,remarks) values (?,?,?)";
		PreparedStatement pstmt = connection.prepareStatement(addProftitle_sql);
		System.out.println(profTitle.getDescription());
		//为预编译赋值
		pstmt.setString(1,profTitle.getNo());
		pstmt.setString(2,profTitle.getDescription());
		pstmt.setString(3,profTitle.getRemarks());
		//获取添加记录的行数
		int affectedRowNum = pstmt.executeUpdate();
		System.out.println("添加了"+affectedRowNum+"条记录");
		return affectedRowNum>0;
	}

//	public boolean delete(Integer id) throws SQLException {
//		ProfTitle profTitle = this.find(id);
//		return this.delete(profTitle);
//	}

	public boolean delete(ProfTitle profTitle)throws SQLException{
		Connection connection = JdbcHelper.getConn();
		//删除数据
		String deleteProftitle_sql = "delete from proftitle where id=?";
		PreparedStatement pstmt = connection.prepareStatement(deleteProftitle_sql);
		//为预编译参数赋值
		pstmt.setInt(1,profTitle.getId());
		//执行预编译对象的executeUpdate方法,获取删除记录的行数
		int affectedRowNum = pstmt.executeUpdate();
		System.out.println("删除了"+affectedRowNum+"条记录");
		JdbcHelper.close(pstmt,connection);
		return affectedRowNum>0;
	}
}

