package dev.glabay.controllers;

import dev.glabay.dtos.ServiceTicketDto;
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
 * @since 2025-10-28
 */
@NullMarked
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/tickets")
public class TicketController {
    private final RestClient restClient;

    @PostMapping
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
}
