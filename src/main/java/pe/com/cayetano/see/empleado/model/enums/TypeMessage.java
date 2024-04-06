package pe.com.cayetano.see.empleado.model.enums;

public enum TypeMessage {
  DANGER(0),
  WARNING(1),
  INFO(2);

  private final Integer value;

  private TypeMessage(Integer value) {
    this.value = value;
  }

  public Integer getValue() {
    return this.value;
  }
}