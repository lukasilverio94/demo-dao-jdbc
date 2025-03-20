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
		departmentDao.insert(newDep);
		System.out.println("Inserted! New ID = " + newDep.getId());
		System.out.println();

		sc.close();
	}
}
