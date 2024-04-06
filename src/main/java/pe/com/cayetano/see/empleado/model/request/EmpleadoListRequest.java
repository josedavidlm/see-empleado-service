package pe.com.cayetano.see.empleado.model.request;



import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EmpleadoListRequest {

  private Long codempresa;
  private Long codemp;
  private Integer tipdid;
  private String numdid;
  private String nombre;
  private String apepat;
  private String apemat;
  private String nomcompleto;
  private Integer page;
  private Integer pageSize;
}
