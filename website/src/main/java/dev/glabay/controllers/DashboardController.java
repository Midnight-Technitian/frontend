package dev.glabay.controllers;

import dev.glabay.dtos.*;
import dev.glabay.models.device.DeviceType;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestClient;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @author Glabay | Glabay-Studios
 * @project frontend
 * @social Discord: Glabay
 * @since 2025-10-21
 */
@NullMarked
@Controller
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final RestClient restClient;

    @GetMapping
    public String getDashboard(HttpServletRequest request, Model model) {
        var email = request.getRemoteUser();
        // fetch the customer data object
        var customerDto = restClient.get()
            .uri("http://localhost:8080/api/v1/customers/email?email=".concat(email))
            .retrieve()
            .toEntity(new ParameterizedTypeReference<CustomerDto>() {})
            .getBody();
        // fetch a list of available services
        var services = restClient.get()
            .uri("http://localhost:8080/api/v1/services")
            .retrieve()
            .toEntity(new ParameterizedTypeReference<Collection<ServiceDto>>() {})
            .getBody();
        // fetch customer Open Service Tickets (up to a maximum of 6)
        var openTickets = restClient.get()
            .uri("http://localhost:8081/api/v1/tickets/customer?email=".concat(email))
            .retrieve()
            .toEntity(new ParameterizedTypeReference<List<ServiceTicketDto>>() {})
            .getBody();
        // fetch customer Devices (up to a maximum of 6)
        var devices = restClient.get()
            .uri("http://localhost:8080/api/v1/devices?email=".concat(email))
            .retrieve()
            .toEntity(new ParameterizedTypeReference<List<CustomerDeviceDto>>() {})
            .getBody();

        var deviceTypes = List.of(DeviceType.values());

        model.addAttribute("deviceTypes", deviceTypes);
        model.addAttribute("customerEmail", email);
        model.addAttribute("customer", customerDto);
        model.addAttribute("services", services);
        model.addAttribute("openTickets", openTickets);
        model.addAttribute("devices", devices);
        return "dashboards/customer/dashboard";
    }

    @GetMapping("/ticketing")
    @PreAuthorize("hasRole('TECHNICIAN')")
    public String getTicketingDashboard(HttpServletRequest request, Model model) {
        // get the email of the user to fetch their employee record
        var email = request.getRemoteUser();

        var employeeDto = restClient.get()
            .uri("http://localhost:8082/api/v1/employees?email=".concat(email))
            .retrieve()
            .toEntity(new ParameterizedTypeReference<EmployeeDto>() {})
            .getBody();

        var openTickets = restClient.get()
            .uri("http://localhost:8081/api/v1/tickets/unclaimed")
            .retrieve()
            .toEntity(new ParameterizedTypeReference<List<ServiceTicketDto>>() {})
            .getBody();

        var claimedTickets = restClient.get()
            .uri("http://localhost:8081/api/v1/tickets/claimed")
            .retrieve()
            .toEntity(new ParameterizedTypeReference<List<ServiceTicketDto>>() {})
            .getBody();

        model.addAttribute("employee", employeeDto);
        model.addAttribute("openTickets", Objects.isNull(openTickets) ? List.of() : openTickets);
        model.addAttribute("claimedTickets", Objects.isNull(claimedTickets) ? List.of() : claimedTickets);
        return "dashboards/tickets/dashboard";
    }


    @GetMapping("/admin")
    @PreAuthorize("hasRole('MANAGER')")
    public String getAdminDashboard(HttpServletRequest request, Model model) {
        var email = request.getRemoteUser();
        var employeeDto = restClient.get()
            .uri("http://localhost:8082/api/v1/employees?email=".concat(email))
            .retrieve()
            .toEntity(new ParameterizedTypeReference<EmployeeDto>() {})
            .getBody();

        var employees = restClient.get()
            .uri("http://localhost:8082/api/v1/employees/all")
            .retrieve()
            .toEntity(new ParameterizedTypeReference<List<EmployeeDto>>() {})
            .getBody();

        var customers = restClient.get()
            .uri("http://localhost:8080/api/v1/customers")
            .retrieve()
            .toEntity(new ParameterizedTypeReference<List<CustomerDto>>() {})
            .getBody();

        var recentTickets = restClient.get()
            .uri("http://localhost:8081/api/v1/tickets/recent")
            .retrieve()
            .toEntity(new ParameterizedTypeReference<List<ServiceTicketDto>>() {})
            .getBody();

        var openCount = restClient.get()
            .uri("http://localhost:8081/api/v1/tickets/open-count")
            .retrieve()
            .toEntity(Long.class)
            .getBody();

        var closedCount = restClient.get()
            .uri("http://localhost:8081/api/v1/tickets/closed-count")
            .retrieve()
            .toEntity(Long.class)
            .getBody();

        model.addAttribute("employee", employeeDto);
        model.addAttribute("employees", employees);
        model.addAttribute("customers", customers);
        model.addAttribute("recentTickets", recentTickets);
        model.addAttribute("activeTicketCount", openCount);
        model.addAttribute("resolvedTicketCount", closedCount);
        return "dashboards/admin/dashboard";
    }


}
