package pe.com.cayetano.see.empleado.api;


import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pe.com.cayetano.see.empleado.api.constant.Constantes;
import pe.com.cayetano.see.empleado.model.id.CargoId;
import pe.com.cayetano.see.empleado.model.request.CargoListRequest;
import pe.com.cayetano.see.empleado.model.request.CargoRequest;
import pe.com.cayetano.see.empleado.model.response.ResponseBase;
import pe.com.cayetano.see.empleado.model.response.ResponseBasePage;
import pe.com.cayetano.see.empleado.service.CargoService;

@RestController
@RequestMapping("cargo")
public class CargoController {
    @Autowired
    private CargoService cargoService;



    @PostMapping(value ="save-Cargo", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "registrar Cargo",
            description = "registrar Cargo")
    public ResponseBase create(@RequestBody CargoRequest cargo)
    {
        cargo.setCodUsuarioCreacion(1L);
        cargo.setNomTerCreacion(Constantes.IP_TERMINAL);
        return cargoService.create(cargo);
    }

    @PutMapping(value ="/update-Cargo/{Cargo_Id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "actualización de Cargo",
            description = "actualización de Cargo")
    public ResponseBase update(@PathVariable("Cargo_Id") Long cargoId,@RequestBody CargoRequest cargo)
    {
        cargo.setCodUsuarioModificacion(1L);
        cargo.setNomTerModificacion(Constantes.IP_TERMINAL);
        cargo.setCodCargo(cargoId);
        return cargoService.update(cargo);
    }
    @DeleteMapping(value ="/delete-Cargo/{empresa_Id}/{Cargo_Id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "eliminación lógica de Cargo",
            description = "eliminación lógica de Cargo")
    public ResponseBase delete(@PathVariable("empresa_Id") Long empresaId,@PathVariable("Cargo_Id") Long cargoId)
    {
        CargoRequest cargo = new CargoRequest();
        cargo.setCodEmpresa(empresaId);
        cargo.setCodCargo(cargoId);
        cargo.setCodUsuarioEliminacion(1L);
        cargo.setNomTerEliminacion(Constantes.IP_TERMINAL);
        return cargoService.deleteById(cargo);
    }
    @GetMapping(value ="/get-Cargo/{empresa_Id}/{Cargo_Id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "obtener Cargo Id",
            description = "obtener Cargo Id")
    public ResponseBase buscarPorId(@PathVariable("empresa_Id") Long empresaId, @PathVariable("Cargo_Id") Long cargoId)
    {

        return cargoService.findById(new CargoId(empresaId,cargoId));
    }
    @GetMapping(value ="/list-Cargo", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Lista de Cargo",
            description = "Lista de Cargo")
    public ResponseBase listar()
    {
        return cargoService.findAll();
    }


    @GetMapping(value = "to-list-Cargo", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Lista paginable Cargo",
        description = "Lista paginable Cargo")
    public ResponseBasePage listarCargo (
        @RequestParam(name="empresa_Id", required = false) Long empresaId,
        @RequestParam(name="Cargo_Id", required = false) Long cargoId,
        @RequestParam(name="descripcion", required = false) String descripcion,
        @RequestParam(name="desCorta", required = false) String desCorta,
        @RequestParam(name="fecCreacion", required = false) String fecCreacion,
        @RequestParam(name="codEstado", required = false) Long codEstado,
        @RequestParam(name="page", defaultValue = "1") Integer page,
        @RequestParam(name="page_size", defaultValue = "10") Integer pageSize
    ) {
        var request = new CargoListRequest();
        request.setCodempresa(empresaId);
        request.setCodcargo(cargoId);
        request.setDescargo(descripcion);

        request.setFeccreacioncadena(fecCreacion);
        request.setCodest(codEstado);
        request.setPage(page);
        request.setPageSize(pageSize);

        return cargoService.listarCargo(request);
    }

}
