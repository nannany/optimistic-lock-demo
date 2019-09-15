package nannany.optimistic.demo.util;

import com.google.common.hash.Hashing;

import static java.nio.charset.StandardCharsets.UTF_8;

public final class DemoUtils {

    @SuppressWarnings("UnstableApiUsage")
    public static String getHash(String origin) {

        return Hashing.sha256().hashString(origin, UTF_8).toString();

    }

}
