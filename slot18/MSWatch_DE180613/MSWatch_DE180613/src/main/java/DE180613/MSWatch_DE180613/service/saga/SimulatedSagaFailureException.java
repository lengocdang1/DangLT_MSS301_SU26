package DE180613.MSWatch_DE180613.service.saga;

/** Lỗi GIẢ LẬP (fault injection) - chỉ dùng để test/demo cơ chế compensation. */
// [TODO-6.12]
public class SimulatedSagaFailureException extends RuntimeException {
    public SimulatedSagaFailureException(String message) {
        super(message);
    }
}

