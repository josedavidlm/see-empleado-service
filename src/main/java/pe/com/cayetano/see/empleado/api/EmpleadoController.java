package pe.com.cayetano.see.empleado.api;



import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import pe.com.cayetano.see.empleado.api.constant.Constantes;
import pe.com.cayetano.see.empleado.model.id.EmpleadoId;
import pe.com.cayetano.see.empleado.model.response.ResponseBase;
import pe.com.cayetano.see.empleado.model.response.ResponseBasePage;
import pe.com.cayetano.see.empleado.model.request.EmpleadoListRequest;
import pe.com.cayetano.see.empleado.model.request.EmpleadoRequest;
import pe.com.cayetano.see.empleado.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("empleado")
public class EmpleadoController {
    @Autowired
    private EmpleadoService empleadoService;

    @PostMapping(value ="save-empleado", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "registrar empleado",
        description = "registrar empleado")
    public ResponseBase create(@RequestBody EmpleadoRequest empleado)
    {
        empleado.setCodUsuarioCreacion(1L);
        empleado.setNomTerCreacion(Constantes.IP_TERMINAL);
        return empleadoService.create(empleado);
    }

    @PutMapping(value ="/update-empleado/{empleado_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "actualización de empleado",
        description = "actualización de empleado")
    public ResponseBase update(@PathVariable("empleado_id") Long empleadoId,@RequestBody EmpleadoRequest empleado)
    {
        empleado.setCodUsuarioModificacion(1L);
        empleado.setNomTerModificacion(Constantes.IP_TERMINAL);
        empleado.setCodemp(empleadoId);
        return empleadoService.update(empleado);
    }
    @DeleteMapping(value ="/delete-empleado/{empresa_Id}/{empleado_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "eliminación lógica de empleado",
        description = "eliminación lógica de empleado")
    public ResponseBase delete(@PathVariable("empresa_Id") Long empresaId,@PathVariable("empleado_id") Long empleadoId)
    {
        EmpleadoRequest empleado = new EmpleadoRequest();
        empleado.setCodEmpresa(empresaId);
        empleado.setCodemp(empleadoId);
        empleado.setCodUsuarioEliminacion(1L);
        empleado.setNomTerEliminacion(Constantes.IP_TERMINAL);

        return empleadoService.deleteById(empleado);
    }
    @GetMapping(value ="/get-empleado/{empresa_Id}/{empleado_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "obtener empleado Id",
        description = "obtener empleado Id")
    public ResponseBase buscarPorId(@PathVariable("empresa_Id") Long empresaId,@PathVariable("empleado_id") Long empleadoId)
    {
        return empleadoService.findById(new EmpleadoId(empresaId,empleadoId));
    }
    @GetMapping(value ="/list-empleado", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Lista de empleado",
        description = "Lista de empleado")
    public ResponseBase listar()
    {
        return empleadoService.findAll();
    }

    @GetMapping(value = "to-list-empleado", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Lista paginable empleado",
        description = "Lista paginable empleado")
    public ResponseBasePage listarBimestre (
        @RequestParam(name="empresa_Id", required = false) Long empresaId,
        @RequestParam(name="empleado_Id", required = false) Long empleadoId,
        @RequestParam(name="tip_doc_id", required = false) Integer tipdid,
        @RequestParam(name="num_doc_id", required = false) String numdid,
        @RequestParam(name="nombres", required = false) String nombre,
        @RequestParam(name="ape_pat", required = false) String apepat,
        @RequestParam(name="ape_mat", required = false) String apemat,
        @RequestParam(name="nom_completo", required = false) String nomcompleto,
        @RequestParam(name="page", defaultValue = "1") Integer page,
        @RequestParam(name="page_size", defaultValue = "10") Integer pageSize
    ) {
        var request = new EmpleadoListRequest();
        request.setCodempresa(empresaId);
        request.setCodemp(empleadoId);
        request.setTipdid(tipdid);
        request.setNumdid(numdid);
        request.setNombre(nombre);
        request.setApepat(apepat);
        request.setApemat(apemat);
        request.setNomcompleto(nomcompleto);

        request.setPage(page);
        request.setPageSize(pageSize);

        return empleadoService.listarEmpleado(request);
    }


}
