package pe.com.cayetano.see.empleado.feignclient;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import pe.com.cayetano.see.empleado.model.response.ReniecResponse;


@FeignClient(name = "reniec-client", url = "https://api.apis.net.pe/v2/reniec/")
public interface ReniecClient {

  //https://api.apis.net.pe/v2/reniec/dni?numero=06798431
  //dni?numero=06798431
  @GetMapping("/dni")
  ReniecResponse getInfo(@RequestParam("numero") String numero,
                         @RequestHeader("Authorization") String token);


}
