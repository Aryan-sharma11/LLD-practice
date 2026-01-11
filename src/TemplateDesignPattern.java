
abstract class ModelTrainer{
    // template method
    public final void trainModel(String path){
        loadData(path);
        preprocessData();
        train();
        evaluateModel();
        saveData();
    }

    protected abstract void loadData(String path);

    protected abstract void preprocessData();

    protected abstract void train();

    protected abstract void evaluateModel();
    
    protected abstract void saveData();
}
class NeuralNetworkTrainer extends ModelTrainer{
    @Override
    protected void loadData(String path) {
        System.out.println("Loading data for Neural Network from " + path);
    }

    @Override
    protected void preprocessData() {
        System.out.println("Preprocessing data for Neural Network");
    }

    @Override
    protected void train() {
        System.out.println("Training Neural Network model");
    }

    @Override
    protected void evaluateModel() {
        System.out.println("Evaluating Neural Network model");
    }
    @Override
    protected void saveData() {
        System.out.println("Saving Neural Network model data");
    }
}
class DecisionTreeTrainer extends ModelTrainer{
    @Override
    protected void loadData(String path) {
        System.out.println("Loading data for Decision Tree from " + path);
    }

    @Override
    protected void preprocessData() {
        System.out.println("Preprocessing data for Decision Tree");
    }

    @Override
    protected void train() {
        System.out.println("Training Decision Tree model");
    }

    @Override
    protected void evaluateModel() {
        System.out.println("Evaluating Decision Tree model");
    }

    @Override
    protected void saveData() {
        System.out.println("Saving Decision Tree model data");
    }
}
public class TemplateDesignPattern {


    public static void main(String[] args) {
        ModelTrainer nnTrainer = new NeuralNetworkTrainer();
        nnTrainer.trainModel("data/neural_network_data.csv");

        System.out.println();

        ModelTrainer dtTrainer = new DecisionTreeTrainer();
        dtTrainer.trainModel("data/decision_tree_data.csv");
    }
    
}
