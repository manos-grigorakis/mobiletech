package com.mgrigorakis.mobiletech.dto;

import com.mgrigorakis.mobiletech.model.enums.PaymentProviderType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

public record OrderRequest(
        @NotBlank(message = "First name is required")
        @Size(min = 2, max = 100)
        String firstName,

        @NotBlank(message = "Last name is required")
        @Size(min = 2, max = 100)
        String lastName,

        @NotBlank(message = "Email is required")
        @Email
        @Size(max = 320)
        String email,

        @NotBlank(message = "Phone is required")
        @Size(max = 30)
        String phone,

        @NotBlank(message = "Address is required")
        @Size(max = 255)
        String address,

        @NotBlank(message = "City is required")
        @Size(max = 100)
        String city,

        @NotBlank(message = "Postal code is required")
        @Size(max = 20)
        String postalCode,

        @NotBlank(message = "Country is required")
        @Size(max = 100)
        String country,

        @NotNull(message = "Payment provider is required")
        PaymentProviderType paymentProvider,

        @NotEmpty(message = "At least one order item is required")
        @Valid
        List<OrderItemRequest> orderItems
) {}
