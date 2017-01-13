package com.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.app.model.Student;
import com.app.util.DBConnectionUtility;

public class StudentDaoImpl implements StudentDao{

	@Override
	public void addStudent(Student student) {
		try(Connection conn = DBConnectionUtility.getDBConnection()) {			
			String query = "insert into student (firstName, lastName, course, year) values (?,?,?,?)";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, student.getFirstName());
			preparedStatement.setString(2, student.getLastName());
			preparedStatement.setString(3, student.getCourse());
			preparedStatement.setInt(4, student.getYear());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteStudent(int studentId) {
		try(Connection conn = DBConnectionUtility.getDBConnection()) {			
			String query = "delete from student where studentId=?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setInt(1, studentId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateStudent(Student student) {
		try(Connection conn = DBConnectionUtility.getDBConnection()) {			
			String query = "update student set firstName=?, lastName=?, course=?, year=? where studentId=?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, student.getFirstName());
			preparedStatement.setString(2, student.getLastName());
			preparedStatement.setString(3, student.getCourse());
			preparedStatement.setInt(4, student.getYear());
			preparedStatement.setInt(5, student.getStudentId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Student> getAllStudents() {
		List<Student> students = new ArrayList<Student>();
		try(Connection conn = DBConnectionUtility.getDBConnection()) {			
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from student");
			while (resultSet.next()) {
				Student student = new Student();
				student.setStudentId(resultSet.getInt("studentId"));
				student.setFirstName(resultSet.getString("firstName"));
				student.setLastName(resultSet.getString("lastName"));
				student.setCourse(resultSet.getString("course"));
				student.setYear(resultSet.getInt("year"));
				students.add(student);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return students;
	}

	@Override
	public Student getStudentById(int studentId) {
		Student student = new Student();
		try(Connection conn = DBConnectionUtility.getDBConnection()) {			
			String query = "select * from student where studentId=?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setInt(1, studentId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				student.setStudentId(resultSet.getInt("studentId"));
				student.setFirstName(resultSet.getString("firstName"));
				student.setLastName(resultSet.getString("LastName"));
				student.setCourse(resultSet.getString("course"));
				student.setYear(resultSet.getInt("year"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return student;
	}
}
