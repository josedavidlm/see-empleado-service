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
                  AND (CAST(:#{#rq.codemp} AS INTEGER) IS NULL OR codemp = :#{#rq.codemp})
                  AND (CAST(:#{#rq.tipdid} AS INTEGER) IS NULL OR codemp = :#{#rq.tipdid})                  
                  AND (CAST(:#{#rq.numdid} AS TEXT) IS NULL OR numdid LIKE '%'||:#{#rq.numdid}||'%')
                  AND (CAST(:#{#rq.nombre} AS TEXT) IS NULL OR nombre LIKE '%'||:#{#rq.nombre}||'%')                  
                  AND (CAST(:#{#rq.apepat} AS TEXT) IS NULL OR apepat LIKE '%'||:#{#rq.apepat}||'%')
                  AND (CAST(:#{#rq.apemat} AS TEXT) IS NULL OR apemat LIKE '%'||:#{#rq.apemat}||'%')
                  AND (CAST(:#{#rq.nomcompleto} AS TEXT) IS NULL OR nomcompleto LIKE '%'||:#{#rq.nomcompleto}||'%')
                  
      """;
  @Query(value = """
            SELECT
                codempresa, 
                codemp,
                tipdid,
                numdid,
                nombre,
                apepat,
                apemat,
                nomcompleto,
                fecnac,
                sexo,
                codcargo,                
                codest, 
                CASE WHEN codest = 1 THEN 'ACTIVO' WHEN codest = 2 THEN 'EN USO' WHEN codest = 3 THEN 'ANULADO' END as estado, 
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
            FROM dse.empleado          
          """ + QUERY_TABLA,
      countQuery = """
          SELECT COUNT(1)
          """ + QUERY_TABLA,
      nativeQuery = true)
  Page<EmpleadoProjection> listarEmpleado(
      @Param("rq") EmpleadoListRequest rq,
      @PageableDefault(page = 0, size = 10) Pageable pageable
  );


}