package server.handlers.put;

import domain.http.request.Request;
import domain.http.response.Response;
import domain.http.response.StatusCode;
import domain.models.Student;
import server.dto.StudentDTO;
import server.handlers.Handler;

public class PutHandler implements Handler {
    @Override
    public Response action(Request request, StudentDTO studentDTO) {

        Response response;
        if (request.getParameters() == null) {
            response = new Response(
                    StatusCode.NOT_FOUND,
                    "text/plain",
                    0,
                    "Невозможно обновить без параметров"
            );
            response.computeContentLength();
            return response;
        }

        try {
            int id = Integer.parseInt(request.getParameters().get("id").toString());
            Student student = (Student) request.getObject();
            if (!studentDTO.update(id, student)) {
                throw new IndexOutOfBoundsException();
            }
            response = new Response(
                    StatusCode.OK,
                    "text/plain",
                    0,
                    "Студент успешно обновлён"
            );
        } catch (NumberFormatException numberFormatException) {
            response = new Response(
                    StatusCode.BAD_REQUEST,
                    "text/plain",
                    0,
                    "ID студента должно быть числом"
            );
            response.computeContentLength();

        }

        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            response = new Response(
                    StatusCode.NOT_FOUND,
                    "text/plain",
                    0,
                    "Студент с таким ID не найден"
            );
            response.computeContentLength();

        }
        return response;
    }
}
