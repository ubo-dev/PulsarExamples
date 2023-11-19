package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private long id;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private long createdAt;
    private long deletedAt;
    private long mergedAt;
    private long parentUserId;
}
