package cn.edu.sdjzu.xg.bysj.dao;


import cn.edu.sdjzu.xg.bysj.domain.Department;
import cn.edu.sdjzu.xg.bysj.service.DepartmentService;
import cn.edu.sdjzu.xg.bysj.service.SchoolService;
import util.JdbcHelper;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;

public final class DepartmentDao {
	public static void main(String[] args) throws SQLException{
		Department departmentToAdd = DepartmentService.getInstance().find(3);
		System.out.println(departmentToAdd.getDescription());
		departmentToAdd.setDescription("艺术管理");
		System.out.println(departmentToAdd.getDescription());
		departmentDao.update(departmentToAdd);
		Department department1 = DepartmentService.getInstance().find(3);
		System.out.println("Description = " + department1.getDescription());
	}
	private static DepartmentDao departmentDao=new DepartmentDao();
	private DepartmentDao(){}

	public static DepartmentDao getInstance(){
		return departmentDao;
	}


	public Collection<Department> findAll() throws SQLException {
	 Collection<Department>departments = new HashSet<>();
	Connection connection = JdbcHelper.getConn();
	Statement stmt = connection.createStatement();
	ResultSet resultSet = stmt.executeQuery("select * from Department");
	//从数据库中取出数据
		while (resultSet.next()){
		//System.out.println(resultSet.getString("description"));
		departments.add(new Department(
				resultSet.getInt("id"),
				resultSet.getString("description"),
				resultSet.getString("no"),
				resultSet.getString("remarks"),
				SchoolService.getInstance().find(resultSet.getInt("school_id"))));
	}
		JdbcHelper.close(stmt,connection);
		return departments;
	}
	public Collection<Department> findAllBySchool(int schoolId) throws SQLException{
		Collection<Department> departmentsBySchoolId = new HashSet<>();
		Connection connection =JdbcHelper.getConn();
		Statement stmt = connection.createStatement();
		ResultSet resultSet = stmt.executeQuery("SELECT * from department");
		while (resultSet.next()){
			if (resultSet.getInt("school_id")==schoolId){
				departmentsBySchoolId.add(new Department(
						resultSet.getInt("id"),
						resultSet.getString("description"),
						resultSet.getString("no"),
						resultSet.getString("remarks"),
						SchoolService.getInstance().find(resultSet.getInt("school_id"))));
			}
		}
		return departmentsBySchoolId;
	}

	public Department find(Integer id) throws SQLException {
		Connection connection = JdbcHelper.getConn();
		String updateDepartment_sql = "SELECT * FROM department where id = ?";
		PreparedStatement pstmt = connection.prepareStatement(updateDepartment_sql);
		pstmt.setInt(1,id);
		ResultSet resultSet = pstmt.executeQuery();
		resultSet.next();
		Department department= new Department(resultSet.getInt("id"),
				resultSet.getString("description"),
				resultSet.getString("no"),
				resultSet.getString("remarks"),
				SchoolDao.getInstance().find(resultSet.getInt("school_id")));
		return department;
	}

	public boolean update(Department department)throws SQLException{
		Connection connection =JdbcHelper.getConn();
		String updateDepartment_sql ="UPDATE department SET description = ? where id = ?";
		PreparedStatement pstmt =connection.prepareStatement(updateDepartment_sql);
		pstmt.setString(1,department.getDescription());
		pstmt.setInt(2,department.getId());
		int affectedRowNum =pstmt.executeUpdate();
		System.out.println("修改了"+affectedRowNum+"行数据");
		JdbcHelper.close(pstmt,connection);
		return affectedRowNum>0;
	}

	public boolean add(Department department)throws SQLException{
		Connection connection =JdbcHelper.getConn();
		PreparedStatement preparedStatement =connection.prepareStatement("INSERT INTO department(description,no,remarks,school_id)values (?,?,?,?)");
		preparedStatement.setString(1,department.getDescription());
		preparedStatement.setString(2,department.getNo());
		preparedStatement.setString(3,department.getRemarks());
		preparedStatement.setInt(4,department.getSchool().getId());
		int affectedRowNum =preparedStatement.executeUpdate();
		System.out.println(affectedRowNum);
		JdbcHelper.close(preparedStatement,connection);
		return affectedRowNum>0;
	}

//	public boolean delete(Integer id)throws SQLException{
//
//	}

	public boolean delete(Department department) throws SQLException {
		Connection connection = JdbcHelper.getConn();
		String deleteDepartment_sql = "delete from department where id=?";
		PreparedStatement pstmt = connection.prepareStatement(deleteDepartment_sql);
		pstmt.setInt(1,department.getId());
		int affectedRowNum = pstmt.executeUpdate();
		return affectedRowNum>0;
	}
}


