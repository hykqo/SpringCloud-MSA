package org.example;

public class Main {
    public static void main(String[] args) {
        TestInfo test = new TestInfo();
        System.out.println("".equals(test.getA()));
    }

    public static class TestInfo{
        private String a;

        public String getA() {
            return a;
        }
    }


}