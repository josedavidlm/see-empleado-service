package pe.com.cayetano.see.empleado.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pe.com.cayetano.see.empleado.model.id.EmpleadoId;

import java.io.Serializable;
import java.util.Date;


@Setter
@Getter
@Entity
@Table(name = "empresa", schema = "dse")
@IdClass(EmpleadoId.class)
public class EmpleadoEntity extends  AuditoriaEntidadEntity implements Serializable {

  @Id
  @Column(name = "codempresa")
  private Long codempresa;

  @Id
  @Column(name = "codemp")
  private Long codemp;

  @Column(name = "tipdid")
  private Integer tipdid;

  @Column(name = "numdid")
  private String numdid;

  @Column(name = "nombre")
  private String nombre;

  @Column(name = "apepat")
  private String apepat;

  @Column(name = "apemat")
  private String apemat;

  @Column(name = "nomcompleto")
  private String nomcompleto;

  @Column(name = "fecnac")
  private Date fecnac;

  @Column(name = "sexo")
  private String sexo;

  @Column(name = "codcargo")
  private Long codcargo;

  @Column(name = "codest")
  private Long codest;

  @Column(name = "activo")
  private Boolean activo;



}
