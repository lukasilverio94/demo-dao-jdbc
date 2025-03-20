package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {
	
	private Connection conn;
	
	//constructor for connection
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override	
	public void update(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as depname "
					+ "FROM seller "
					+ "INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE seller.Id = ?"
					);
			st.setInt(1, id);
			rs = st.executeQuery();
			if(rs.next()) {
				Department dep = instantiateDepartment(rs);
				
				Seller obj = instantiateSeller(rs, dep);
				
				return obj;
				
			}
			return null;
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
	}

	// next two methods are using `throws SQLException` because
	// we are already handling exception above (on the block they are called it has a catch SQLException)
	// so we just need propagate using this throws keyword. 
	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		 Seller obj = new Seller();
			obj.setId(rs.getInt("id"));
			obj.setName(rs.getString("name"));
			obj.setEmail(rs.getString("email"));
			obj.setBaseSalary(rs.getDouble("baseSalary"));
			obj.setBirthDate(rs.getDate("birthDate"));
			obj.setDepartment(dep);			
			return obj;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("departmentid"));
		dep.setName(rs.getString("depname"));
		return dep;
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName " +
					"FROM seller INNER JOIN department " +
					"ON seller.DepartmentId = department.Id " +
					"ORDER BY Name"
					);
						
			rs = st.executeQuery();
			
			List<Seller> sellerList = new ArrayList<>();
			// Use Map to don't repeat department on the list
			// Because we need to point to the same dep all the sellers, to same reference
			Map<Integer, Department> map = new HashMap<>();
			while(rs.next()) {
				Department dep = map.get(rs.getInt("departmentid"));
							
				if(dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("departmentid"), dep);
				}
				
				Seller obj = instantiateSeller(rs, dep);
				sellerList.add(obj);					
			}			
			return sellerList;			
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName " +
					"FROM seller INNER JOIN department " +
					"ON seller.DepartmentId = department.Id " +
					" WHERE DepartmentId = ? " +
					"ORDER BY Name"
					);
			
			st.setInt(1, department.getId());
			
			rs = st.executeQuery();
			
			List<Seller> sellerList = new ArrayList<>();
			// Use Map to don't repeat department on the list
			Map<Integer, Department> map = new HashMap<>();
			while(rs.next()) {
				Department dep = map.get(rs.getInt("departmentid"));
				// check the Map if dep is null, just if is null we will instantiate it the Department				
				if(dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("departmentid"), dep);
				}
				
				Seller obj = instantiateSeller(rs, dep);
				sellerList.add(obj);					
			}			
			return sellerList;			
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
}
