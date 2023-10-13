import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SearchJurgens")
public class SearchJurgens extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public SearchJurgens() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String keyword = request.getParameter("keyword");
      search(keyword, response);
   }

   void search(String keyword, HttpServletResponse response) throws IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Database Result";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
            "transitional//en\">\n"; //
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h1 align=\"center\">" + title + "</h1>\n");

      Connection connection = null;
      PreparedStatement preparedStatement = null;
      try {
         DBConnectionJurgens.getDBConnection();
         connection = DBConnectionJurgens.connection;

         if (keyword.isEmpty()) {
            String selectSQL = "SELECT * FROM MyTableTEJurgens";
            preparedStatement = connection.prepareStatement(selectSQL);
         } else {
            String selectSQL = "SELECT * FROM MyTableTEJurgens WHERE LAST_NAME LIKE ?";
            String theLastName = "%" + keyword + "%";
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, theLastName);
         }
         ResultSet rs = preparedStatement.executeQuery();

         while (rs.next()) {
            int id = rs.getInt("id");
            String firstName = rs.getString("first_name").trim();
            String lastName = rs.getString("last_name").trim();
            String dob = rs.getString("dob").trim();
            String gender = rs.getString("gender").trim();
            String phone = rs.getString("phone").trim();
            String email = rs.getString("email").trim();
            String address = rs.getString("address").trim();
            String insurance = rs.getString("insurance").trim();

            if (keyword.isEmpty() || lastName.contains(keyword)) {
               out.println("ID: " + id + ", ");
               out.println("First Name: " + firstName + ", ");
               out.println("Last Name: " + lastName + ", ");
               out.println("Date of Birth: " + dob + ", ");
               out.println("Gender: " + gender + ", ");
               out.println("Phone Number: " + phone + ", ");
               out.println("Email Address: " + email + ", ");
               out.println("Physical Address: " + address + ", ");
               out.println("Insurance Provider: " + insurance + "<br>");
            }
         }
         out.println("<a href=/webproject-TechExercise-Jurgens/search_jurgens.html>Search Data</a> <br>");
         out.println("</body></html>");
         rs.close();
         preparedStatement.close();
         connection.close();
      } catch (SQLException se) {
         se.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            if (preparedStatement != null)
               preparedStatement.close();
         } catch (SQLException se2) {
         }
         try {
            if (connection != null)
               connection.close();
         } catch (SQLException se) {
            se.printStackTrace();
         }
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
