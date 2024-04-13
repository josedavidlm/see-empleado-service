package pe.com.cayetano.see.empleado.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import pe.com.cayetano.see.empleado.model.entity.CargoEntity;
import pe.com.cayetano.see.empleado.model.id.CargoId;
import pe.com.cayetano.see.empleado.model.projection.CargoProjection;
import pe.com.cayetano.see.empleado.model.request.CargoListRequest;

import java.util.Optional;


public interface CargoRepository extends JpaRepository<CargoEntity, CargoId> {

  @Query(value = """
        SELECT COALESCE(Max(codcargo),0)+1
        FROM dse.cargo
        WHERE codempresa = :codEmpresa
      """, nativeQuery = true)
  Long obtenerCargoId(Long codEmpresa);
  Optional<CargoEntity> findByDesCargo(String desCargo);


  String QUERY_TABLA = """
  WHERE codempresa = :#{#rq.codempresa}
        AND (CAST(:#{#rq.codcargo}  AS INTEGER) IS NULL OR codcargo = :#{#rq.codcargo})
        AND (CAST(:#{#rq.descargo} AS TEXT) IS NULL OR descargo LIKE '%'||:#{#rq.descargo}||'%')        
        AND (CAST(:#{#rq.codest}  AS INTEGER) IS NULL OR codest = :#{#rq.codest})
  """;
  @Query(value = """
          SELECT 
            codempresa, 
            codcargo, 
            descargo,
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
          FROM dse.Cargo 
          """ + QUERY_TABLA,
          countQuery = """
          SELECT COUNT(1)
          """ + QUERY_TABLA,
      nativeQuery = true)
  Page<CargoProjection> listarCargo(
      @Param("rq") CargoListRequest rq,
      @PageableDefault(page = 0, size = 10) Pageable pageable
  );



}