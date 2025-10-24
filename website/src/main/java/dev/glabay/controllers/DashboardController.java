package dev.glabay.controllers;

import dev.glabay.dtos.CustomerDeviceDto;
import dev.glabay.dtos.CustomerDto;
import dev.glabay.dtos.ServiceDto;
import dev.glabay.models.request.ServiceRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestClient;

import java.util.Collection;

/**
 * @author Glabay | Glabay-Studios
 * @project frontend
 * @social Discord: Glabay
 * @since 2025-10-21
 */
@Controller
@RequiredArgsConstructor
public class DashboardController {
    private final RestClient restClient;

    @GetMapping("/dashboard")
    public String getDashboard(HttpServletRequest request, Model model) {
        var email = request.getRemoteUser();
        // fetch the customer data object
        var customerDto = restClient.get()
            .uri("/v1/customers/email?email=".concat(email))
            .retrieve()
            .toEntity(new ParameterizedTypeReference<CustomerDto>() {})
            .getBody();

        model.addAttribute("customer", customerDto);

        // fetch a list of available services
        var services = restClient.get()
            .uri("/v1/services")
            .retrieve()
            .toEntity(new ParameterizedTypeReference<Collection<ServiceDto>>() {})
            .getBody();
        model.addAttribute("services", services);
        // TODO: fetch customer Open Service Tickets (up to a maximum of 6)

        // fetch customer Devices (up to a maximum of 6)
//        var devices = restClient.get()
//            .uri("/v1/devices?email=".concat(email))
//            .retrieve()
//            .toEntity(new ParameterizedTypeReference<CustomerDeviceDto>() {})
//            .getBody();
//        model.addAttribute("devices", devices);
        return "customer/dashboard";
    }
}
