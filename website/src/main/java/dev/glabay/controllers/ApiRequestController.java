package dev.glabay.controllers;

import dev.glabay.dtos.CustomerDeviceDto;
import dev.glabay.dtos.ServiceTicketDto;
import dev.glabay.models.device.RegisteringDevice;
import dev.glabay.models.request.ServiceRequest;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestClient;

/**
 * @author Glabay | Glabay-Studios
 * @project frontend
 * @social Discord: Glabay
 * @since 2025-10-26
 */
@NullMarked
@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiRequestController {
    private final RestClient restClient;

    @PostMapping("/tickets")
    private String postNewTicket(@RequestBody ServiceRequest body) {
        var ticket = restClient.post()
            .uri("http://localhost:8081/api/v1/tickets")
            .body(body)
            .retrieve()
            .toEntity(new ParameterizedTypeReference<ServiceTicketDto>() {})
            .getBody();

        if (ticket == null)
            return "redirect:/error";

        return "redirect:/dashboard";
    }

    @PostMapping("/device")
    private String postNewDevice(@RequestBody RegisteringDevice body) {
        var deviceDto = restClient.post()
            .uri("http://localhost:8080/api/v1/devices")
            .body(body)
            .retrieve()
            .toEntity(new ParameterizedTypeReference<CustomerDeviceDto>() {})
            .getBody();

        if (deviceDto == null)
            return "redirect:/error";

        return "redirect:/dashboard";
    }
}
