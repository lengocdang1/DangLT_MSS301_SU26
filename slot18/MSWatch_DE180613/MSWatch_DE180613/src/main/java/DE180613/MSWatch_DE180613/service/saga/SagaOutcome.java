package DE180613.MSWatch_DE180613.service.saga;

public record SagaOutcome(String sagaId, Integer watchId, SagaStatus status) {
}

