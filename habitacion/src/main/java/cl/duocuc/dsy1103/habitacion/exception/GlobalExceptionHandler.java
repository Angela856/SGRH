package cl.duocuc.dsy1103.habitacion.exception;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
@ControllerAdvice // Indica que esta clase captura excepciones de todos los controladores [cite: 71]
public class GlobalExceptionHandler {

// Captura errores de validación de Bean Validation (@Valid) [cite: 64, 66]
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        
        // Retorna un JSON con los errores y código HTTP 400 [cite: 70]
        return ResponseEntity.badRequest().body(errors);
    }

    // Captura errores generales para que la app no muestre código interno
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception ex) {
        return ResponseEntity.status(500).body("Error interno en el servidor: " + ex.getMessage());
    }


}
