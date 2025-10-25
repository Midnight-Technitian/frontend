package dev.glabay.controllers;

import dev.glabay.dtos.CustomerDeviceDto;
import dev.glabay.dtos.CustomerDto;
import dev.glabay.dtos.ServiceDto;
import dev.glabay.dtos.ServiceTicketDto;
import dev.glabay.models.request.ServiceRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestClient;

import java.util.Collection;
import java.util.List;

/**
 * @author Glabay | Glabay-Studios
 * @project frontend
 * @social Discord: Glabay
 * @since 2025-10-21
 */
@NullMarked
@Controller
@RequiredArgsConstructor
public class DashboardController {
    private final RestClient restClient;

    @GetMapping("/dashboard")
    public String getDashboard(HttpServletRequest request, Model model) {
        var email = request.getRemoteUser();
        // fetch the customer data object
        var customerDto = restClient.get()
            .uri("http://localhost:8080/api/v1/customers/email?email=".concat(email))
            .retrieve()
            .toEntity(new ParameterizedTypeReference<CustomerDto>() {})
            .getBody();
        model.addAttribute("customerEmail", email);
        model.addAttribute("customer", customerDto);

        // fetch a list of available services
        var services = restClient.get()
            .uri("http://localhost:8080/api/v1/services")
            .retrieve()
            .toEntity(new ParameterizedTypeReference<Collection<ServiceDto>>() {})
            .getBody();
        model.addAttribute("services", services);

        // fetch customer Open Service Tickets (up to a maximum of 6)
        var openTickets = restClient.get()
            .uri("http://localhost:8081/api/v1/tickets/customer?email=".concat(email))
            .retrieve()
            .toEntity(new ParameterizedTypeReference<List<ServiceTicketDto>>() {})
            .getBody();
        model.addAttribute("openTickets", openTickets);

        // fetch customer Devices (up to a maximum of 6)
        var devices = restClient.get()
            .uri("http://localhost:8080/api/v1/devices?email=".concat(email))
            .retrieve()
            .toEntity(new ParameterizedTypeReference<List<CustomerDeviceDto>>() {})
            .getBody();
        model.addAttribute("devices", devices);
        return "customer/dashboard";
    }

    @PostMapping("/api/tickets")
    private String post(@RequestBody ServiceRequest body) {

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
}
