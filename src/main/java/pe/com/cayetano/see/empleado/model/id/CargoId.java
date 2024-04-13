package pe.com.cayetano.see.empleado.model.id;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CargoId {
  private Long codEmpresa;
  private Long codCargo;
}
