package pe.com.cayetano.see.empleado.model.request;



import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CargoListRequest {

  private Long codempresa;
  private Long codcargo;
  private String descargo;
  private String feccreacioncadena;
  private Long codest;
  private Integer page;
  private Integer pageSize;
}
