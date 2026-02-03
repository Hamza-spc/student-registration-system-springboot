package com.example.demo.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponse {
    private String token;
}

/*
🧠 Why return JWT immediately after register?

Because:

User is already authenticated

No need to login again

Better UX

Professional apps do this.
 */