package pe.com.cayetano.see.empleado.model.request;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class EmpleadoRequest {


  private Long codEmpresa;
  @JsonIgnore
  private Long codemp;
  private Integer tipdid;
  private String numdid;
  private String nombre;
  private String apepat;
  private String apemat;
  private String nomcompleto;
  private Date fecnac;
  private String sexo;
  private Long codcargo;
  @JsonIgnore
  private Boolean activo;
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
}
