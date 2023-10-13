import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/InsertJurgens")
public class InsertJurgens extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public InsertJurgens() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String firstName = request.getParameter("firstName");
      String lastName = request.getParameter("lastName");
      String dob = request.getParameter("dob");
      String gender = request.getParameter("gender");
      String phone = request.getParameter("phone");
      String email = request.getParameter("email");
      String address = request.getParameter("address");
      String insurance = request.getParameter("insurance");

      Connection connection = null;
      String insertSql = " INSERT INTO MyTableTEJurgens (id, FIRST_NAME, LAST_NAME, DOB, GENDER, PHONE, EMAIL, ADDRESS, INSURANCE) values (default, ?, ?, ?, ?, ?, ?, ?, ?)";

      try {
         DBConnectionJurgens.getDBConnection();
         connection = DBConnectionJurgens.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
         preparedStmt.setString(1, firstName);
         preparedStmt.setString(2, lastName);
         preparedStmt.setString(3, dob);
         preparedStmt.setString(4, gender);
         preparedStmt.setString(5, phone);
         preparedStmt.setString(6, email);
         preparedStmt.setString(7, address);
         preparedStmt.setString(8, insurance);
         preparedStmt.execute();
         connection.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Insert Data to DB table";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h2 align=\"center\">" + title + "</h2>\n" + //
            "<ul>\n" + //

            "  <li><b>First Name</b>: " + firstName + "\n" + //
            "  <li><b>Last Name</b>: " + lastName + "\n" + //
            "  <li><b>Date of Birth</b>: " + dob + "\n" + //
            "  <li><b>Gender</b>: " + gender + "\n" + //
            "  <li><b>Phone Number</b>: " + phone + "\n" + //
            "  <li><b>Email Address</b>: " + email + "\n" + //
            "  <li><b>Physical Address</b>: " + address + "\n" + //
            "  <li><b>Insurance Provider</b>: " + insurance + "\n" + //

            "</ul>\n");

      out.println("<a href=/webproject-TechExercise-Jurgens/search_jurgens.html>Search Data</a> <br>");
      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
