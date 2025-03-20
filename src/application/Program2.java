package application;

import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

		// ====================================================================
		System.out.println("=== TEST 1: department findAll ===");
		List<Department> list = departmentDao.findAll();
		for (Department obj : list) {
			System.out.println(obj);
		}
		System.out.println();
		// ====================================================================
		System.out.println("=== TEST 2: department findById ===");
		Department dep = departmentDao.findById(2);
		System.out.println(dep);
		System.out.println();
		// ====================================================================
		System.out.println("=== TEST 3: department insert ===");
		Department newDep = new Department(null, "Music");
//		departmentDao.insert(newDep);
		System.out.println("Inserted! New ID = " + newDep.getId());
		System.out.println();
		// ====================================================================
		System.out.println("=== TEST 4: department updateById ===");
		dep = departmentDao.findById(7);
		dep.setName("Musica");
		departmentDao.update(dep);
		System.out.println("Update completed");
		System.out.println();
		// ====================================================================
		System.out.println("=== TEST 5: department deleteById ===");
		System.out.println("Enter id for delete test: ");
		int id = sc.nextInt();
		departmentDao.deleteById(id);
		System.out.println("User with ID=(" + id + ") deleted successfully");
		sc.close();
	}
}
