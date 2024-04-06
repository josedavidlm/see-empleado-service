package pe.com.cayetano.see.empleado.api;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.com.cayetano.see.empleado.model.response.ResponseBase;
import pe.com.cayetano.see.empleado.service.EmpleadoService;

@RestController
@RequestMapping("persona")
public class PersonaController {
    @Autowired
    private EmpleadoService empleadoService;
    @GetMapping("/buscar-dni/{numero}")
    public ResponseBase getInfoReniec(@PathVariable String numero){

        return empleadoService.getInfoReniec(numero);
    }



}
