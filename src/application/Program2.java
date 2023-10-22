package application;

import java.util.Date;
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
		
		System.out.println("\n=== TESTE 3: Departmente Insert ===");
		Department newDepartment = new Department(null, "Drinks");
		departmenteDao.insert(newDepartment);
		System.out.println("Inserted! New id = " + newDepartment.getId());		
		
		sc.close();
		
	}

}
