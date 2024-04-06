package pe.com.cayetano.see.empleado.model.response;


import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class EmpleadoResponse {
  private Long codempresa;
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
