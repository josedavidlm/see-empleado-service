package pe.com.cayetano.see.empleado.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import pe.com.cayetano.see.empleado.api.constant.Constantes;
import pe.com.cayetano.see.empleado.config.ConfigMessageProperty;
import pe.com.cayetano.see.empleado.feignclient.ReniecClient;
import pe.com.cayetano.see.empleado.model.enums.EstadoRegistro;
import pe.com.cayetano.see.empleado.model.id.EmpleadoId;
import pe.com.cayetano.see.empleado.model.response.EmpleadoResponse;
import pe.com.cayetano.see.empleado.model.response.ReniecResponse;
import pe.com.cayetano.see.empleado.model.response.ResponseBase;
import pe.com.cayetano.see.empleado.model.response.ResponseBasePage;
import pe.com.cayetano.see.empleado.model.entity.EmpleadoEntity;
import pe.com.cayetano.see.empleado.model.enums.Estado;
import pe.com.cayetano.see.empleado.model.request.EmpleadoListRequest;
import pe.com.cayetano.see.empleado.model.request.EmpleadoRequest;
import pe.com.cayetano.see.empleado.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.cayetano.see.empleado.service.EmpleadoService;
import pe.com.cayetano.see.empleado.util.CustomPage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    @Autowired
    ReniecClient reniecClient;

    @Autowired
    private ConfigMessageProperty config;

    @Value("${token.api}")
    private String tokenApi;


    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public ResponseBase create(EmpleadoRequest empleado) {
        var lstResponse = new ArrayList<EmpleadoResponse>();
        Optional<EmpleadoEntity> empresaBd = empleadoRepository.findByTipdidAndNumdid(empleado.getTipdid(),empleado.getNumdid());


        if(empresaBd.isPresent())
        {
            lstResponse.add(modelMapper.map(empresaBd.get(), EmpleadoResponse.class));
            return new ResponseBase(Constantes.API_STATUS_400,
                config.getMessage(Constantes.EXISTE),
                false,
                lstResponse);
        }
        EmpleadoEntity entidad = modelMapper.map(empleado, EmpleadoEntity.class);
        entidad.setCodempresa(empleado.getCodEmpresa());
        entidad.setCodemp(empleadoRepository.obtenerEmpleadoId(empleado.getCodEmpresa()));
        entidad.setFecCreacion(LocalDateTime.now());
        entidad.setActivo(true);
        entidad.setCodest(EstadoRegistro.REGISTRADO.getValue());
        EmpleadoEntity empresaGuardado = empleadoRepository.save(entidad);

        var obj = modelMapper.map(empresaGuardado, EmpleadoResponse.class);

        var estado = obj.getActivo();

        if(Boolean.TRUE.equals(estado)){
            obj.setDesActivo(Estado.ACTIVO.name());
        }else{
            obj.setDesActivo(Estado.INACTIVO.name());
        }

        if(obj.getCodEst().equals(EstadoRegistro.REGISTRADO.getValue())){
            obj.setEstado(EstadoRegistro.REGISTRADO.name());
        }else if(obj.getCodEst().equals(EstadoRegistro.ENUSO.getValue())){
            obj.setEstado(EstadoRegistro.ENUSO.name());
        }else if(obj.getCodEst().equals(EstadoRegistro.ANULADO.getValue())){
            obj.setEstado(EstadoRegistro.ANULADO.name());
        }

        lstResponse.add(obj);

        return new ResponseBase(Constantes.API_STATUS_200,
            config.getMessage(Constantes.CREADO) ,
            true,
            lstResponse);
    }

    @Override
    public ResponseBase findById(EmpleadoId empleadoId) {

        var lstResponse = new ArrayList<EmpleadoResponse>();
        Optional<EmpleadoEntity> empleadoBd = empleadoRepository.findById(empleadoId);

        var obj = modelMapper.map(empleadoBd, EmpleadoResponse.class);

        if(empleadoBd.isPresent())
        {
            var estado = obj.getActivo();

            if( Boolean.TRUE.equals(estado)){
                obj.setEstado(Estado.ACTIVO.name());
            }else{
                obj.setEstado(Estado.INACTIVO.name());
            }
            lstResponse.add(obj);

            return new ResponseBase(Constantes.API_STATUS_200, config.getMessage(Constantes.ENCONTRADO), true, lstResponse);
        }
        return new ResponseBase(Constantes.API_STATUS_404, config.getMessage(Constantes.NO_REGISTRO), false, null);
    }

    @Override
    public ResponseBase deleteById(EmpleadoRequest empleado) {
        var lstResponse = new ArrayList<EmpleadoResponse>();
        EmpleadoId empleadoId = new EmpleadoId();
        empleadoId.setCodempresa(empleado.getCodEmpresa());
        empleadoId.setCodemp(empleado.getCodemp());
        Optional<EmpleadoEntity> empleadoBd = empleadoRepository.findById(empleadoId);

        if(empleadoBd.isPresent())
        {
        empleadoBd.get().setActivo(false);
        empleadoBd.get().setCodUsuarioEliminacion(empleado.getCodUsuarioEliminacion());
        empleadoBd.get().setFecEliminacion(LocalDateTime.now());
        empleadoBd.get().setNomTerEliminacion(empleado.getNomTerEliminacion());

        EmpleadoEntity empresaDelete = empleadoRepository.save(empleadoBd.get());

        var obj = modelMapper.map(empresaDelete, EmpleadoResponse.class);

        var estado = obj.getActivo();

        if( Boolean.TRUE.equals(estado)){
            obj.setEstado(Estado.ACTIVO.name());
        }else{
            obj.setEstado(Estado.INACTIVO.name());
        }

        lstResponse.add(obj);
        return new ResponseBase(Constantes.API_STATUS_200,  config.getMessage(Constantes.ELIMINADO), true, lstResponse);
        }
        return new ResponseBase(Constantes.API_STATUS_404, config.getMessage(Constantes.NO_REGISTRO), false, null);
    }

    @Override
    public ResponseBase update(EmpleadoRequest empleado) {
        Optional<EmpleadoEntity> empresaBd = empleadoRepository.findById(new EmpleadoId(empleado.getCodEmpresa(),empleado.getCodemp()));

        var lstResponse = new ArrayList<EmpleadoResponse>();

        if(empresaBd.isEmpty())
        {
            return new ResponseBase(Constantes.API_STATUS_404,
                config.getMessage(Constantes.NO_REGISTRO),
                false,
                null);
        }
        // verificamos que el dni tenga 8 digitos y que el correo tenga @

        EmpleadoEntity entidad = modelMapper.map(empresaBd.get(), EmpleadoEntity.class);
        entidad.setTipdid(empleado.getTipdid());
        entidad.setNumdid(empleado.getNumdid());
        entidad.setNombre(empleado.getNombre());
        entidad.setApepat(empleado.getApepat());
        entidad.setApemat(empleado.getApemat());
        entidad.setNomcompleto(empleado.getNomcompleto());
        entidad.setFecnac(empleado.getFecnac());
        entidad.setSexo(empleado.getSexo());
        entidad.setCodcargo(empleado.getCodcargo());
        entidad.setFecModificacion(LocalDateTime.now());
        entidad.setCodUsuarioModificacion(empleado.getCodUsuarioModificacion());
        entidad.setNomTerModificacion(empleado.getNomTerModificacion());

        EmpleadoEntity empresaGuardado = empleadoRepository.save(entidad);

        var obj = modelMapper.map(empresaGuardado, EmpleadoResponse.class);

        var estado = obj.getActivo();

        if( Boolean.TRUE.equals(estado)){
            obj.setEstado(Estado.ACTIVO.name());
        }else{
            obj.setEstado(Estado.INACTIVO.name());
        }

        lstResponse.add(obj);

        return new ResponseBase(Constantes.API_STATUS_200,
            config.getMessage(Constantes.ACTUALIZADO),
            true,
            lstResponse);
    }

    @Override
    public ResponseBase findAll() {
        List<EmpleadoEntity> lista = empleadoRepository.findAll();

        var lstResponse = new ArrayList<EmpleadoResponse>();

        lista.forEach(entidad -> {
            var obj = modelMapper.map(entidad, EmpleadoResponse.class);
            if (entidad.getCodempresa() != null) {

                var estadoEntidad = entidad.getActivo();
                if(Boolean.TRUE.equals(estadoEntidad)){
                    obj.setDesActivo(Estado.ACTIVO.name());
                }else{
                    obj.setDesActivo(Estado.INACTIVO.name());
                }

                if(obj.getCodEst().equals(EstadoRegistro.REGISTRADO.getValue())){
                    obj.setEstado(EstadoRegistro.REGISTRADO.name());
                }else if(obj.getCodEst().equals(EstadoRegistro.ENUSO.getValue())){
                    obj.setEstado(EstadoRegistro.ENUSO.name());
                }else if(obj.getCodEst().equals(EstadoRegistro.ANULADO.getValue())){
                    obj.setEstado(EstadoRegistro.ANULADO.name());
                }
            }
            lstResponse.add(obj);
        });

        if(!lista.isEmpty())
        {
            return new ResponseBase(Constantes.API_STATUS_200,  config.getMessage(Constantes.ENCONTRADO) , true, lstResponse);
        }
        return new ResponseBase(Constantes.API_STATUS_404, config.getMessage(Constantes.NO_REGISTRO), false, null);
    }

    @Override
    public ResponseBasePage listarEmpleado(EmpleadoListRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getPageSize());
        var response =  new CustomPage(empleadoRepository.listarEmpleado(request, pageable));
        if (response.getData().isEmpty()) {
            return new ResponseBasePage(Constantes.API_STATUS_404, config.getMessage(Constantes.NO_REGISTRO), false, response.getData());
        }
        return new ResponseBasePage(Constantes.API_STATUS_200,  config.getMessage(Constantes.LISTA_ENCONTRADO) , true, response.getData());

    }

    @Override
    public ResponseBase getInfoReniec(String numero) {
        ReniecResponse response = null;
        try {
            response = getExecution(numero);
        } catch (Exception e) {

            return  new ResponseBase(Constantes.API_STATUS_400,
                config.getMessage(Constantes.ERROR) + " "+ e.getMessage() ,
                false,
                null);
        }

        if (response!=null){
            return new ResponseBase(Constantes.API_STATUS_200,
                config.getMessage(Constantes.CREADO) ,
                true,
                Optional.of(response));
        }else{
            return  new ResponseBase(Constantes.API_STATUS_400,
                config.getMessage(Constantes.ERROR) ,
                false,
                Optional.of(response));


        }
    }

    private ReniecResponse getExecution(String numero) throws Exception{
        String authorization = "Bearer "+ tokenApi;
        return  reniecClient.getInfo(numero,authorization);

    }
}
