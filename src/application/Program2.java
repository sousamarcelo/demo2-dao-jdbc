package application;

import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;
import model.entities.Seller;

public class Program2 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		DepartmentDao departmenteDao = DaoFactory.createDepartmente();
		
		System.out.println("=== TESTE 1: Departmente findById ===");
		Department department = departmenteDao.findById(4);	
		System.out.println(department);
		
		System.out.println("\n=== TESTE 2: Departmente findAll ===");		
		List<Department> list = departmenteDao.findAll();
		for(Department obj : list) {
			System.out.println(obj);
		}
		
		
		sc.close();
		
	}

}
