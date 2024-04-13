package pe.com.cayetano.see.empleado.model.response;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CargoResponse {

  private Long codEmpresa;
  private Long codCargo;
  private String desCargo;
  private Boolean activo;
  private String desActivo;
  private Long codEst;
  private String estado;
  private LocalDateTime fecCreacion;
  private Long codUsuarioCreacion;
  private  String nomTerCreacion;
  private LocalDateTime fecModificacion;
  private Long codUsuarioModificacion;
  private  String nomTerModificacion;
  private LocalDateTime fecEliminacion;
  private Long codUsuarioEliminacion;
  private  String nomTerEliminacion;

}
