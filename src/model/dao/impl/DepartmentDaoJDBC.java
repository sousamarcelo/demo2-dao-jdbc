package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {
	
	Connection conn;
	
	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Department obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
						
			String sql = 
					"SELECT * FROM department "
					+ "WHERE department.Id = ?";
			
			st = conn.prepareStatement(sql);
			
			st.setInt(1, id);
			
			rs = st.executeQuery();
			
			if (rs.next()) {
				Department dep = instantiateDepartmente(rs);
				return dep;
			}
			return null;
			
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);			
		}
		
	}

	private Department instantiateDepartmente(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("Id"));
		dep.setName(rs.getString("Name"));
		return dep;
	}

	@Override
	public List<Department> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			
			String sql = "SELECT * FROM department ORDER BY department.Name";
			
			st = conn.prepareStatement(sql);
			
			rs = st.executeQuery();
			
			List<Department> list = new ArrayList<Department>();
			
			while (rs.next()) {
				Department dep = instantiateDepartmente(rs);
				list.add(dep);				
			}
			return list;
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	

}
