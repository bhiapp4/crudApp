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

import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;

@WebServlet("/StudentServlet.do")
public class StudentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public static final String LIST_STUDENT = "/listStudents.jsp";
	public static final String INSERT_OR_EDIT = "/student.jsp";
	private final StudentDao dao = new StudentDaoImpl();
	private static final String STUDENT_ID = "studentId";
	private static final String STUDENTS = "students";
	private static final String SERVLET_EXCEPTION = "servlet exception: ";

	private static Logger log = LogManager.getRootLogger();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String forward = "";
		String action = request.getParameter("action");

		if (action.equalsIgnoreCase("delete")) {
			forward = LIST_STUDENT;
			int studentId = parseString(request.getParameter(STUDENT_ID));
			dao.deleteStudent(studentId);
			request.setAttribute(STUDENTS, dao.getAllStudents());
		} else if (action.equalsIgnoreCase("edit")) {
			forward = INSERT_OR_EDIT;
			int studentId = parseString(request.getParameter(STUDENT_ID));
			Student student = dao.getStudentById(studentId);
			request.setAttribute("student", student);
		} else if (action.equalsIgnoreCase("insert")) {
			forward = INSERT_OR_EDIT;
		} else {
			forward = LIST_STUDENT;
			request.setAttribute(STUDENTS, dao.getAllStudents());
		}
		RequestDispatcher view = request.getRequestDispatcher(forward);
		try {
			view.forward(request, response);
		} catch (Exception e) {
			log.error(SERVLET_EXCEPTION , e);
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Student student = new Student();
		student.setFirstName(request.getParameter("firstName"));
		student.setLastName(request.getParameter("lastName"));
		student.setCourse(request.getParameter("course"));
		student.setYear(parseString(request.getParameter("year")));
		String studentId = request.getParameter(STUDENT_ID);

		if (studentId == null || studentId.isEmpty())
			dao.addStudent(student);
		else {
			student.setStudentId(parseString(studentId));
			dao.updateStudent(student);
		}
		RequestDispatcher view = request.getRequestDispatcher(LIST_STUDENT);
		request.setAttribute(STUDENTS, dao.getAllStudents());
		try {
			view.forward(request, response);
		} catch (Exception e) {
			log.error(SERVLET_EXCEPTION , e);
		}
	}

	private int parseString(String input){
		int res = 0;
		try{
			 res = Integer.parseInt(input);
		} catch (NumberFormatException nfe){
			log.error("parsingInt exception: " , nfe);
		}
	return res;
	}
}
