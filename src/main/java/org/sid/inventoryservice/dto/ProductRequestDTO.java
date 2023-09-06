package org.sid.inventoryservice.dto;

import lombok.Data;

public record ProductRequestDTO (
         String id,
         String name,
         double price,
         int quantity,
         Long categoryId
){}



