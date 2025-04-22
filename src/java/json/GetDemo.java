package json;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class GetDemo extends HttpServlet {

    /* Fire it with following Curl Command
    
    curl -X GET "http://localhost:8080/JsonDemo/GetDemo?rollno=1001&name=Rohit"
    
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Set the response's content type to application/json
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // We need to build a JSON
        JsonObject json = Json.createObjectBuilder()
                .add("rollno", 1001)
                .add("name", "Rohit")
                .build();

        // Now we will send the JSON to the response
        try (PrintWriter out = response.getWriter()) {
            out.print(json.toString());
            out.flush();
        }
    }


    /* Fire it with following Curl Command
    
    curl -X POST http://localhost:8080/JsonDemo/GetDemo -H "Content-Type: application/json" -d '{"rollno": 1001, "name": "Rohit"}'
        
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // This is try-with-resources. You can also use it.
        try (JsonReader jsonReader = Json.createReader(request.getInputStream())) {

            JsonObject jsonRequest = jsonReader.readObject();

            int rollno = jsonRequest.getInt("rollno");
            String name = jsonRequest.getString("name");
            System.out.println("RollNo: " + rollno + ", Name: " + name);

            // Build response JSON
            JsonObject jsonResponse = Json.createObjectBuilder()
                    .add("recievedRollNo", rollno)
                    .add("receivedName", name)
                    .build();

            try (PrintWriter out = response.getWriter()) {
                out.print(jsonResponse.toString());
                out.flush();
            }
        }
    }
    

    /* Fire it with following Curl Command
    
    curl -X PUT http://localhost:8080/JsonDemo/GetDemo -H "Content-Type: application/json" -d '{"rollno": 1001, "name": "Rohit Sharma"}'
    
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (JsonReader reader = Json.createReader(request.getInputStream())) {
            JsonObject json = reader.readObject();

            int rollno = json.getInt("rollno");
            String name = json.getString("name");

            // Simulating a Database Update operation
            System.out.println("Updating Student with Roll No: " + rollno + ", New Name: " + name);

            JsonObject result = Json.createObjectBuilder()
                    .add("message", "Student updated successfully")
                    .add("updatedRollNo", rollno)
                    .add("updatedName", name)
                    .build();

            try (PrintWriter out = response.getWriter()) {
                out.print(result.toString());
                out.flush();
            }
        }
    }

    /* Fire it with following Curl Command
    
    curl -X DELETE "http://localhost:8080/JsonDemo/GetDemo?rollno=1001"
    
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String rollnoParam = request.getParameter("rollno");

        if (rollnoParam == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            JsonObject error = Json.createObjectBuilder()
                    .add("error", "Missing 'rollno' parameter")
                    .build();

            try (PrintWriter out = response.getWriter()) {
                out.print(error.toString());
                out.flush();
            }
            return;
        }

        int rollno = Integer.parseInt(rollnoParam);

        // Simulating a Database delete operation.
        System.out.println("Deleting Student with Roll No: " + rollno);

        JsonObject result = Json.createObjectBuilder()
                .add("message", "Student deleted successfully")
                .add("deletedRollNo", rollno)
                .build();

        try (PrintWriter out = response.getWriter()) {
            out.print(result.toString());
            out.flush();
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
