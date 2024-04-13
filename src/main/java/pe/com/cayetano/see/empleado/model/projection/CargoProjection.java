package pe.com.cayetano.see.empleado.model.projection;


import java.util.Date;

public interface CargoProjection {
   Long getCodEmpresa();
   Long getCodCargo();
   String getDesCargo();
   Long getCodEst();
   String getEstado();
   Boolean getActivo();
   String getDesActivo();
   Date getFecCreacion();
   Long getCodUsuarioCreacion();
   String getNomTerCreacion();
   Date getFecModificacion();
   Long getCodUsuarioModificacion();
   String getNomTerModificacion();
   Date getFecEliminacion();
   Long getCodUsuarioEliminacion();
   String getNomTerEliminacion();

}
