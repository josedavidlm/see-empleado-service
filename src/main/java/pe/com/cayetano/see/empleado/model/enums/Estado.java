package pe.com.cayetano.see.empleado.model.enums;

public enum Estado {

    ACTIVO(true),
    INACTIVO(false);

    private final boolean value;

    Estado(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return this.value;
    }
}
