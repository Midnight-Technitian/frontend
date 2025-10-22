package dev.glabay.controllers;

import dev.glabay.dtos.CustomerDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestClient;

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
        var username = request.getRemoteUser();
        var customerDto = restClient.get()
            .uri("/v1/customers/e/".concat(username))
            .retrieve()
            .toEntity(new ParameterizedTypeReference<CustomerDto>() {})
            .getBody();

        model.addAttribute("customer", customerDto);
        return "customer/dashboard";
    }
}
