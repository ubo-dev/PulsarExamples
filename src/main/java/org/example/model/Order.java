package org.example.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private long invoiceId;
    private long lineItemId;
    private long userId;
    private long itemId;
    private String itemName;
    private String itemCategory;
    private double price;
    private long createdAt;
    private long paidAt;

}
