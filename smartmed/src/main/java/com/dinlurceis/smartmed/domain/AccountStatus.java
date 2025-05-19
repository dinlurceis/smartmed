package com.dinlurceis.smartmed.domain;

public enum AccountStatus {
    PENDING_VERIFICATION, // created but not yet verified
    ACTIVE, // active and in good standing
    SUSPENDED, // temporarily suspended
    DEACTIVATED, // user chose to do
    BANNED, // permanently banned due to severe violations
    CLOSED // permanently closed (user request)
}
