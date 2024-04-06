package pe.com.cayetano.see.empleado.service;

import pe.com.cayetano.see.empleado.model.id.EmpleadoId;
import pe.com.cayetano.see.empleado.model.response.ResponseBase;
import pe.com.cayetano.see.empleado.model.response.ResponseBasePage;
import pe.com.cayetano.see.empleado.model.request.EmpleadoListRequest;
import pe.com.cayetano.see.empleado.model.request.EmpleadoRequest;

public interface EmpleadoService {

  ResponseBase create(EmpleadoRequest empresa);
  ResponseBase findById(EmpleadoId empleadoId);
  ResponseBase deleteById(EmpleadoRequest empresa);
  ResponseBase update(EmpleadoRequest empresa);
  ResponseBase findAll();
  ResponseBasePage listarEmpleado(EmpleadoListRequest request);

  ResponseBase getInfoReniec(String numero);
}
