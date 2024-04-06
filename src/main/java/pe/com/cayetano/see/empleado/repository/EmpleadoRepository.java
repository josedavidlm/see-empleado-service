package pe.com.cayetano.see.empleado.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import pe.com.cayetano.see.empleado.model.entity.EmpleadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.com.cayetano.see.empleado.model.id.EmpleadoId;
import pe.com.cayetano.see.empleado.model.projection.EmpleadoProjection;
import pe.com.cayetano.see.empleado.model.request.EmpleadoListRequest;

import java.util.Optional;


public interface EmpleadoRepository extends JpaRepository<EmpleadoEntity, EmpleadoId> {

  @Query(value = """            
      SELECT COALESCE(Max(codemp),0)+1
      FROM dse.empleado
      WHERE codempresa = :codEmpresa
      
      """, nativeQuery = true)
  Long obtenerEmpleadoId(Long codEmpresa);
  Optional<EmpleadoEntity> findByTipdidAndNumdid(Integer tipdid, String numdid);

  String QUERY_TABLA = """
                WHERE (CAST(:#{#rq.codempresa}  AS INTEGER) IS NULL OR codempresa = :#{#rq.codempresa})
                  AND (CAST(:#{#rq.desempresa} AS TEXT) IS NULL OR desempresa LIKE '%'||:#{#rq.desempresa}||'%')
                  AND (CAST(:#{#rq.deaempresa} AS TEXT) IS NULL OR deaempresa LIKE '%'||:#{#rq.deaempresa}||'%' )
      """;
  @Query(value = """
            SELECT
                codempresa,
                desempresa,
                deaempresa,
                activo,
                CASE WHEN activo THEN 'ACTIVO' ELSE 'INACTIVO' END as desActivo,
                feccreacion,
                codusuariocreacion,
                nomtercreacion,
                codusuariomodificacion,
                fecmodificacion,
                nomtermodificacion,
                codusuarioeliminacion,
                feceliminacion,
                nomtereliminacion
            FROM dse.empresa            
          """ + QUERY_TABLA,
      countQuery = """
          SELECT COUNT(1)
          """ + QUERY_TABLA,
      nativeQuery = true)
  Page<EmpleadoProjection> listarEmpresa(
      @Param("rq") EmpleadoListRequest rq,
      @PageableDefault(page = 0, size = 10) Pageable pageable
  );


}