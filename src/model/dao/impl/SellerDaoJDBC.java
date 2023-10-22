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
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE seller.Id = ?"
					);
			st.setInt(1, id);
			
			rs = st.executeQuery();
			//tranformando os dados de tabela em objetos relacionados
			if (rs.next()) {
				Department dep = instantiateDepartment(rs); 									//criado metodo a parte para instanciação e adidiação dos dados no objeto, assim o codigo fica menos poluido
				Seller obj = instantiateSeller(rs, dep); 										//criado metodo a parte para instanciação e adidiação dos dados no objeto, assim o codigo fica menos poluido				
				return obj;				
			}
			return null;
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st); 
			DB.closeResultSet(rs);
		}
		
		
	}

	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller obj = new Seller();							//criado objeto Seller para ser populado com os dados colateados do banco
		obj.setId(rs.getInt("Id"));
		obj.setName(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setBaseSalary(rs.getDouble("BaseSalary"));
		obj.setBirthDate(rs.getDate("BirthDate"));
		obj.setDepartment(dep); 							// associação de objeto "dep" criado acima
		return obj;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department(); 					//Criando um objeto Departmento que sera populado com os dado do banco
		dep.setId(rs.getInt("DepartmentId")); 				//acessando a coluna correspondente ao Id do departamento no ResultSet rs e setando no objeto Department criado acima
		dep.setName(rs.getString("DepName")); 					//acessando a coluna correspondente ao Name do departamento no ResultSet rs e setando no objeto Department criado acima
		return dep;
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
								
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ " ON seller.DepartmentId = department.Id "					
					+ "ORDER BY Name"					
					);
						
			rs = st.executeQuery();
																								//tranformando os dados de tabela em objetos relacionados
			List<Seller> list = new ArrayList<Seller>();
			Map<Integer, Department> map  = new HashMap<Integer, Department>();					// controle para não deixar criar um novo objeto de departamente para cada Vendedor, o certo e sempre utilizar o mesmo objeto
						
			while (rs.next()) {
				
				Department dep = map.get(rs.getInt("DepartmentId"));
				
																								//com esse if controla-se a instanciação do departamento, que se será instanciado se ainda não existir uma instancia
				if (dep == null) {
					dep = instantiateDepartment(rs);  											//criado metodo a parte para instanciação e adidiação dos dados no objeto, assim o codigo fica menos poluido
					map.put(rs.getInt("DepartmentId"), dep);
				}		
													
				Seller obj = instantiateSeller(rs, dep); 										//criado metodo a parte para instanciação e adidiação dos dados no objeto, assim o codigo fica menos poluido				
				list.add(obj);
							
			}
			return list;
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
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
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ " ON seller.DepartmentId = department.Id "
					+ "WHERE DepartmentId = ? "
					+ "ORDER BY Name"					
					);
			st.setInt(1, department.getId());
			
			rs = st.executeQuery();
																								//tranformando os dados de tabela em objetos relacionados
			List<Seller> list = new ArrayList<Seller>();
			Map<Integer, Department> map  = new HashMap<Integer, Department>();					// controle para não deixar criar um novo objeto de departamente para cada Vendedor, o certo e sempre utilizar o mesmo objeto
						
			while (rs.next()) {
				
				Department dep = map.get(rs.getInt("DepartmentId"));
				
																								//com esse if controla-se a instanciação do departamento, que se será instanciado se ainda não existir uma instancia
				if (dep == null) {
					dep = instantiateDepartment(rs);  											//criado metodo a parte para instanciação e adidiação dos dados no objeto, assim o codigo fica menos poluido
					map.put(rs.getInt("DepartmentId"), dep);
				}		
													
				Seller obj = instantiateSeller(rs, dep); 										//criado metodo a parte para instanciação e adidiação dos dados no objeto, assim o codigo fica menos poluido				
				list.add(obj);
							
			}
			return list;
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st); 
			DB.closeResultSet(rs);
		}
		
	}

}
