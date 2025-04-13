package com.practicenetwork;
import java.util.Arrays;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        if (true) {
            Network001 network = new Network001(2, 2, 2, 4, 0.1);
            double[] input = {0, 1};
            double[] output = network.run(input);
            // System.out.println(Arrays.toString(output));

            double[][] inputs = new double[10][2];
            double[][] outputs = new double[10][2];
            for (int i = 0; i < 10; i +=2) {
                inputs[i][0] = 0;
                inputs[i][1] = 1;
                outputs[i][0] = 1;
                outputs[i][1] = 0;
            }
            for (int i = 1; i < 10; i +=2) {
                inputs[i][0] = 1;
                inputs[i][1] = 0;
                outputs[i][0] = 0;
                outputs[i][1] = 1;
            }
            double[][] inputs2 = new double[10][2];
            double[][] outputs2 = new double[10][2];
            for (int i = 0; i < 10; i +=2) {
                inputs2[i][0] = 0;
                inputs2[i][1] = 0;
                outputs2[i][0] = 1;
                outputs2[i][1] = 1;
            }
            for (int i = 1; i < 10; i +=2) {
                inputs2[i][0] = 1;
                inputs2[i][1] = 1;
                outputs2[i][0] = 0;
                outputs2[i][1] = 0;
            }
            for (int j = 0; j < 1000000; j++) {
                network.train(inputs, outputs);
                network.train(inputs2, outputs2);
            }

        Scanner scanner = new Scanner(System.in);

        int cont = 1;

        while (cont == 1) {
            System.out.println("Enter the first bit (1 or 0)");
            double bit1 = scanner.nextInt();
    
            System.out.println("Enter the second bit (1 or 0)");
            int bit2 = scanner.nextInt();
    
            double[] choosenInput = new double[2];
            choosenInput[0] = bit1;
            choosenInput[1] = bit2;
            output = network.run(choosenInput);
            if (output[0] > 0.5) output[0] = 1; else output[0] = 0;
            if (output[1] > 0.5) output[1] = 1; else output[1] = 0;
    
            System.out.println("\nYour output for (" + (int) bit1 + " " + (int) bit2 + ") is: " + (int) output[0] + " " + (int) output[1]);
            System.out.println("Do you want to try again? (1==yes, 0==no)");
            cont = scanner.nextInt();
        }
        scanner.close();

        if (false) {
            output = network.run(input);
            System.out.println(Arrays.toString(output));
            double[] secondInput = {1, 0};
            output = network.run(secondInput);
            System.out.println(Arrays.toString(output));


            double[] i3 = {0, 0};
            output = network.run(i3);
            System.out.println(Arrays.toString(output));
            double[] i4 = {1, 1};
            output = network.run(i4);
            System.out.println(Arrays.toString(output));
        }

        } else {
            Network001 network = new Network001(1, 2, 2, 4, 0.1);
            double[][] inputs = {{0}, {1}, {2}, {0}, {1}, {2}, {0}, {1}, {2}, {0}, {1}, {2}};
            double[][] outputs = {{0, 0}, {0, 1}, {1, 0}, {0, 0}, {0, 1}, {1, 0}, {0, 0}, {0, 1}, {1, 0}, {0, 0}, {0, 1}, {1, 0}};
            for (int j = 0; j < 1000000; j++) {
                network.train(inputs, outputs);
            }
            for (int i = 0; i < 3; i++) {
                double[] output = network.run(inputs[i]);
                System.out.println(Arrays.toString(output));
            }
        }

    }
}