package pe.com.cayetano.see.empleado.model.projection;


import java.util.Date;

public interface EmpleadoProjection {

   Long getCodEmpresa();
   Long getCodEmp();
   Integer getTipdId();
   String getNumdId();
   String getNombre();
   String getApePat();
   String getApeMat();
   String getNomCompleto();
   Date getFecNac();
   String getSexo();
   Integer getCodCargo();
   Integer getCodEst();
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
