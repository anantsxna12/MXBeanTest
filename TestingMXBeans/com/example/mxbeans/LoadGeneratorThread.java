package com.example.mxbeans;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class LoadGeneratorThread extends Thread {
    private final AtomicBoolean running = new AtomicBoolean(true);

    public abstract String getThreadType();

    public void setName() {
        this.setName("t_" + getThreadType());
    }

    List<String> strings;

    public void setStrings(List<String> strings) {
        this.strings = strings;
    }

    @Override
    public void run() {
        setName();
        strings = Arrays.asList("1AbcDefGhijK2LmNoPqRsT3UvWxYz4AaBbCcDdE5FfGgHhIi6JjKkLlMmN7nOpQqRrSs8TtUuVvWwXx9YyZz0",
                "QwErTyUiOpAsDfGhJkLzXcVbNm12B34V56M78Q9W0Ee5RtY67U8iO1P2",
                "3q4w5e6r7t8y9u0i1o2p3a4s5d6f7g8h9j0k1l2z3x4c5v6b7n8m9",
                "abcde12345fghijk6789lmnopqrstuvwxyz",
                "5a6b7c8d9e0f1g2h3i4j5k6l7m8n9o0p1q2r3s4t5u6v7w8x9y0z",
                "2ab3cd4ef5gh6ij7kl8mn9op0qr1st2uv3wx4yz",
                "67890abcdefghijklaAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ1234",
                "5abc6def7ghij8klmn9opqr0stuvwx1yz2",
                "a3b4c5d6e7f8g9h0i1j2k3l4m5n6o7p8q9r0s1t2u3v4w5x6y7z8",
                "abcdefghijklmnopqrstuvwxyzaabBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ1234",
                "0a1b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6q7r8s9t0u1v2w3x4y5z6",
                "7ab8cd9ef0gh1ij2kl3mn4op5qr6st7uv8wx9yz0",
                "a1b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6q7r8s9t0u1v2w3x4y5z6",
                "ab7c8de9f0gh1ij2kl3mn4op5qr6st7uv8wx9yz0",
                "abcdefghijklmnopqrstuvwxyzaabBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ1234",
                "5ab6cd7ef8gh9ij0kl1mn2op3qr4st5uv6wx7yz8",
                "abcdefghijklmnopqrstuvwxyz",
                "ab1c2d3e4f5gh6ij7kl8mn9op0qr1st2uv3wx4yz5",
                "67890abcdefghijklaAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ1234",
                "5a6b7c8d9e0f1g2h3i4j5k6l7m8n9o0p1q2r3s4t5u6v7w8x9y0z",
                "abcdefghijklmnopqrstuvwxyzaabBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ1234",
                "7ab8cd9ef0gh1ij2kl3mn4op5qr6st7uv8wx9yz0",
                "abcde12345fghijk6789lmnopqrstuvwxyz",
                "0a1b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6q7r8s9t0u1v2w3x4y5z6",
                "ab7c8de9f0gh1ij2kl3mn4op5qr6st7uv8wx9yz0",
                "5ab6cd7ef8gh9ij0kl1mn2op3qr4st5uv6wx7yz8",
                "67890abcdefghijklaAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ1234",
                "7ab8cd9ef0gh1ij2kl3mn4op5qr6st7uv8wx9yz0",
                "ab1c2d3e4f5gh6ij7kl8mn9op0qr1st2uv3wx4yz5",
                "abcdefghijklmnopqrstuvwxyz",
                "ab3c4d5e6f7g8h9i0j1k2l3m4n5o6p7q8r9s0t1u2v3w4x5y6z7",
                "a1b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6q7r8s9t0u1v2w3x4y5z6",
                "5a6b7c8d9e0f1g2h3i4j5k6l7m8n9o0p1q2r3s4t5u6v7w8x9y0z",
                "a3b4c5d6e7f8g9h0i1j2k3l4m5n6o7p8q9r0s1t2u3v4w5x6y7z8",
                "67890abcdefghijklaAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ1234",
                "abcdefghijklmnopqrstuvwxyzaabBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ1234",
                "5ab6cd7ef8gh9ij0kl1mn2op3qr4st5uv6wx7yz8",
                "67890abcdefghijklaAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ1234");
        this.setPriority(getPriorityByType()); // ALWAYS RETURNS 5 FOR NOW
        System.out.println(">" + this.getName());
        while (running.get()) {
            generateLoad(strings);
        }
    }

    private int getPriorityByType() {
        return 5;
    }

    public void stopThread() {
        System.out.println("<" + this.getName());
        running.set(false);
    }

    // Abstract method to generate load (to be implemented by subclasses)
    protected abstract void generateLoad(List<String> strings);
}

