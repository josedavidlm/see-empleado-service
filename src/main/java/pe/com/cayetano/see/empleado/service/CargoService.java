package pe.com.cayetano.see.empleado.service;


import pe.com.cayetano.see.empleado.model.id.CargoId;
import pe.com.cayetano.see.empleado.model.request.CargoListRequest;
import pe.com.cayetano.see.empleado.model.request.CargoRequest;
import pe.com.cayetano.see.empleado.model.response.ResponseBase;
import pe.com.cayetano.see.empleado.model.response.ResponseBasePage;

public interface CargoService {

  ResponseBase create(CargoRequest cargo);
  ResponseBase findById(CargoId cargoId);
  ResponseBase deleteById(CargoRequest cargo);
  ResponseBase update(CargoRequest cargo);
  ResponseBase findAll();
  ResponseBasePage listarCargo(CargoListRequest request);
}
