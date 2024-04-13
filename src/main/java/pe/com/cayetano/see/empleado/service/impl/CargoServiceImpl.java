package pe.com.cayetano.see.empleado.service.impl;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pe.com.cayetano.see.empleado.api.constant.Constantes;
import pe.com.cayetano.see.empleado.config.ConfigMessageProperty;
import pe.com.cayetano.see.empleado.model.entity.CargoEntity;
import pe.com.cayetano.see.empleado.model.enums.Estado;
import pe.com.cayetano.see.empleado.model.enums.EstadoRegistro;
import pe.com.cayetano.see.empleado.model.id.CargoId;
import pe.com.cayetano.see.empleado.model.request.CargoListRequest;
import pe.com.cayetano.see.empleado.model.request.CargoRequest;
import pe.com.cayetano.see.empleado.model.response.CargoResponse;
import pe.com.cayetano.see.empleado.model.response.ResponseBase;
import pe.com.cayetano.see.empleado.model.response.ResponseBasePage;
import pe.com.cayetano.see.empleado.service.CargoService;
import pe.com.cayetano.see.empleado.util.CustomPage;
import pe.com.cayetano.see.empleado.repository.CargoRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CargoServiceImpl implements CargoService {
    @Autowired
    private CargoRepository cargoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ConfigMessageProperty config;

    @Transactional
    @Override
    public ResponseBase create(CargoRequest cargo) {
        var lstResponse = new ArrayList<CargoResponse>();
        Optional<CargoEntity> cargoBd = cargoRepository.findByDesCargo(cargo.getDesCargo());


        if(cargoBd.isPresent())
        {
            lstResponse.add(modelMapper.map(cargoBd.get(), CargoResponse.class));
            return new ResponseBase(Constantes.API_STATUS_400,
                config.getMessage(Constantes.EXISTE),
                false,
                lstResponse);
        }
        CargoEntity entidad = modelMapper.map(cargo, CargoEntity.class);
        entidad.setCodEmpresa(cargo.getCodEmpresa());
        entidad.setCodCargo(cargoRepository.obtenerCargoId(cargo.getCodEmpresa()));
        entidad.setFecCreacion(LocalDateTime.now());
        entidad.setActivo(true);
        entidad.setCodEst(EstadoRegistro.REGISTRADO.getValue());
        CargoEntity cargoGuardado = cargoRepository.save(entidad);

        var obj = modelMapper.map(cargoGuardado, CargoResponse.class);

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
    public ResponseBase findById(CargoId cargoId) {

        var lstResponse = new ArrayList<CargoResponse>();
        Optional<CargoEntity> cargoBd = cargoRepository.findById(cargoId);

        var obj = modelMapper.map(cargoBd, CargoResponse.class);

        var estado = obj.getActivo();

        if( Boolean.TRUE.equals(estado)){
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

        if(cargoBd.isPresent())
        {
            return new ResponseBase(Constantes.API_STATUS_200, config.getMessage(Constantes.ENCONTRADO), true, lstResponse);
        }
        return new ResponseBase(Constantes.API_STATUS_404, config.getMessage(Constantes.NO_REGISTRO), false, null);
    }

    @Transactional
    @Override
    public ResponseBase deleteById(CargoRequest cargo) {
        var lstResponse = new ArrayList<CargoResponse>();
        CargoId cargoId = new CargoId();
        cargoId.setCodEmpresa(cargo.getCodEmpresa());
        cargoId.setCodCargo(cargo.getCodCargo());

        Optional<CargoEntity> empresaBd = cargoRepository.findById(cargoId);

        if (empresaBd.isPresent()){

            empresaBd.get().setActivo(false);
            empresaBd.get().setCodUsuarioEliminacion(cargo.getCodUsuarioEliminacion());
            empresaBd.get().setFecEliminacion(LocalDateTime.now());
            empresaBd.get().setNomTerEliminacion(cargo.getNomTerEliminacion());

            CargoEntity empresaDelete = cargoRepository.save(empresaBd.get());
            var obj = modelMapper.map(empresaDelete, CargoResponse.class);

            var estado = obj.getActivo();

            if( Boolean.TRUE.equals(estado)){
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
            return new ResponseBase(Constantes.API_STATUS_200,  config.getMessage(Constantes.ELIMINADO), true, lstResponse);
        }
        return new ResponseBase(Constantes.API_STATUS_404, config.getMessage(Constantes.NO_REGISTRO), false, null);
    }

    @Transactional
    @Override
    public ResponseBase update(CargoRequest cargo) {
        Optional<CargoEntity> cargoBd = cargoRepository.findById(new CargoId(cargo.getCodEmpresa(),cargo.getCodCargo()) );

        var lstResponse = new ArrayList<CargoResponse>();

        if(cargoBd.isEmpty())
        {
            return new ResponseBase(Constantes.API_STATUS_404,
                config.getMessage(Constantes.NO_REGISTRO),
                false,
                null);
        }


        CargoEntity entidad = modelMapper.map(cargoBd.get(), CargoEntity.class);
        entidad.setDesCargo(cargo.getDesCargo());
        entidad.setFecModificacion(LocalDateTime.now());
        entidad.setCodUsuarioModificacion(cargo.getCodUsuarioModificacion());
        entidad.setNomTerModificacion(cargo.getNomTerModificacion());
        CargoEntity empresaGuardado = cargoRepository.save(entidad);

        var obj = modelMapper.map(empresaGuardado, CargoResponse.class);

        var estado = obj.getActivo();

        if( Boolean.TRUE.equals(estado)){
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
            config.getMessage(Constantes.ACTUALIZADO),
            true,
            lstResponse);
    }

    @Override
    public ResponseBase findAll() {
        List<CargoEntity> lista = cargoRepository.findAll();

        var lstResponse = new ArrayList<CargoResponse>();

        lista.forEach(entidad -> {
            var obj = modelMapper.map(entidad, CargoResponse.class);
            if (entidad.getCodEmpresa() != null) {

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
    public ResponseBasePage listarCargo(CargoListRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getPageSize());
        var response =  new CustomPage(cargoRepository.listarCargo(request, pageable));
        if (response.getData().isEmpty()) {
            return new ResponseBasePage(Constantes.API_STATUS_404, config.getMessage(Constantes.NO_REGISTRO), false, response);
        }
        return new ResponseBasePage(Constantes.API_STATUS_200,  config.getMessage(Constantes.LISTA_ENCONTRADO) , true, response);

    }
}
