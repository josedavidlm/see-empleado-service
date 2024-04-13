package pe.com.cayetano.see.empleado.model.request;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CargoRequest {
  private Long codEmpresa;
  @JsonIgnore
  private Long codCargo;
  private String desCargo;
  @JsonIgnore
  private Boolean activo;
  @JsonIgnore
  private Long codEst;
  @JsonIgnore
  private LocalDateTime fecCreacion;
  @JsonIgnore
  private Long codUsuarioCreacion;
  @JsonIgnore
  private  String nomTerCreacion;
  @JsonIgnore
  private LocalDateTime fecModificacion;
  @JsonIgnore
  private Long codUsuarioModificacion;
  @JsonIgnore
  private  String nomTerModificacion;
  @JsonIgnore
  private LocalDateTime fecEliminacion;
  @JsonIgnore
  private Long codUsuarioEliminacion;
  @JsonIgnore
  private  String nomTerEliminacion;
  @JsonIgnore
  private String fecCreacionCadena;
  @JsonIgnore
  private Integer page;
  @JsonIgnore
  private Integer pageSize;
}
