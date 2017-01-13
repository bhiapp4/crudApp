package com.app.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.dao.StudentDao;
import com.app.dao.StudentDaoImpl;
import com.app.model.Student;

@WebServlet("/StudentServlet.do")
public class StudentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public static final String lIST_STUDENT = "/listStudents.jsp";
	public static final String INSERT_OR_EDIT = "/student.jsp";
	private StudentDao dao = new StudentDaoImpl();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String forward = "";
		String action = request.getParameter("action");

		if (action.equalsIgnoreCase("delete")) {
			forward = lIST_STUDENT;
			int studentId = Integer.parseInt(request.getParameter("studentId"));
			dao.deleteStudent(studentId);
			request.setAttribute("students", dao.getAllStudents());
		} else if (action.equalsIgnoreCase("edit")) {
			forward = INSERT_OR_EDIT;
			int studentId = Integer.parseInt(request.getParameter("studentId"));
			Student student = dao.getStudentById(studentId);
			request.setAttribute("student", student);
		} else if (action.equalsIgnoreCase("insert")) {
			forward = INSERT_OR_EDIT;
		} else {
			forward = lIST_STUDENT;
			request.setAttribute("students", dao.getAllStudents());
		}
		RequestDispatcher view = request.getRequestDispatcher(forward);
		view.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Student student = new Student();
		student.setFirstName(request.getParameter("firstName"));
		student.setLastName(request.getParameter("lastName"));
		student.setCourse(request.getParameter("course"));
		student.setYear(Integer.parseInt(request.getParameter("year")));
		String studentId = request.getParameter("studentId");

		if (studentId == null || studentId.isEmpty())
			dao.addStudent(student);
		else {
			student.setStudentId(Integer.parseInt(studentId));
			dao.updateStudent(student);
		}
		RequestDispatcher view = request.getRequestDispatcher(lIST_STUDENT);
		request.setAttribute("students", dao.getAllStudents());
		view.forward(request, response);
	}
}
