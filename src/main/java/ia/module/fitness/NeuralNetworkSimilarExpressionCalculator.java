package ia.module.fitness;

import ia.module.extension.FixedKohonen;
import ia.module.extension.FixedNeuron;
import ia.module.parser.Parser;
import ia.module.parser.tree.ExpressionNode;
import org.neuroph.core.Connection;
import org.neuroph.core.Layer;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.Neuron;
import org.neuroph.core.data.DataSet;
import org.neuroph.nnet.Kohonen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static ia.module.config.NeuralNetworkConfig.*;
import static io.jenetics.util.IO.object;

public class NeuralNetworkSimilarExpressionCalculator extends SimilarExpressionCalculator {

    private FixedKohonen network;
    public double[] originalExpressionOutput;

    public NeuralNetworkSimilarExpressionCalculator(String original) {
        super(original);
        trainNetwork();
        try {
            originalExpressionOutput = calculateOutput(new Parser().parse(original), original);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Double similarityWith(String otherExpression) {
        double[] otherExpressionOutput = new double[0];
        try {
            otherExpressionOutput = calculateOutput(new Parser().parse(otherExpression), otherExpression);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return similarity(originalExpressionOutput, otherExpressionOutput);
                /*
        double baseSimilarity = baseSimilarity(originalExpressionOutput, otherExpressionOutput);
        double fineSimilarity = fineSimilarity(originalExpressionOutput, otherExpressionOutput, 1 - baseSimilarity);
        return baseSimilarity + fineSimilarity;*/
    }

    private double similarity(double[] output1, double[] output2) {
        double total = 0.0;
        for (int i = 0; i < OUTPUTS; i++) {
            if (output1[i] == output2[i]) {
                total += 1;
            }
        }
        return total / (double) OUTPUTS;
    }

    private void trainNetwork() {
        network = new FixedKohonen(INPUTS, OUTPUTS);
        network.learn(generateTrainingSet());
    }

    private DataSet generateTrainingSet() {
        DataSet ds = new DataSet(INPUTS);
        //TODO: ver si conviene dejar esto, o directamente se entrena con el archivo creado
        /*for (int example = 0; example < TRAINING_EXAMPLES; example++) {
            TreeNode<Op<Double>> exampleExpression = TreeNode.ofTree(CHROMOSOME.newInstance().getGene());
            double[] features = new double[0];
            try {
                features = new Parser().parse(exampleExpression).extractFeaturesForExpression();
            } catch (Exception e) {
                e.printStackTrace();
            }
            ds.addRow(features);
        }*/
        return this.trainWithExamples(ds);
    }

    private DataSet trainWithExamples(DataSet ds){
        try {
            File file = new File("resources/training/patterns.text");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                ExpressionNode expressionNode = new Parser().parse(line);
                ds.addRow(expressionNode.extractFeaturesForExpression());
            }
            fileReader.close();
            return ds;
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return ds;
        }
    }

    private double[] calculateOutput(ExpressionNode expression, String expressionString) {
        double[] input = expression.extractFeaturesForExpression();
        network.setInput(input);
        network.calculate();
        return network.getOutput();
    }

    private double baseSimilarity(double[] output1, double[] output2) {
        int category1 = category(output1);
        if (category1 == category(output2)) {
            return BASE_SIMILARITY_MAIN_CATEOGORY;
        }
        if (output2[category1] <= SIMILAR_CATEGORY_LIMIT) {
            return BASE_SIMILARITY_SECONDARY_CATEGORY;
        }
        return BASE_SIMILARITY_OTHER_CATEGORY;
    }

    private double fineSimilarity(double[] output1, double[] output2, double maxSimilarity) {
        double difference = 0;
        for (int i = 0; i < OUTPUTS; i++) {
            difference += Math.abs(output1[i] - output2[i]);
        }
        return (1 - (difference / (double) OUTPUTS)) * maxSimilarity;
    }

    private int category(double[] output) {
        for (int i = 0; i < OUTPUTS; i++) {
            if (output[i] == 0.0) {
                return i;
            }
        }
        throw new RuntimeException("Unrecognized category"); // Shouldn't happen
    }

}
