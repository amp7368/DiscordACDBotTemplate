package apple.template.template.io;

import apple.utilities.request.AppleRequestService;

public class FileIOService extends AppleRequestService {
    private static final FileIOService instance = new FileIOService();

    public static FileIOService get() {
        return instance;
    }

    @Override
    public int getRequestsPerTimeUnit() {
        return 10;
    }

    @Override
    public int getTimeUnitMillis() {
        return 0;
    }

    @Override
    public int getSafeGuardBuffer() {
        return 0;
    }
}
