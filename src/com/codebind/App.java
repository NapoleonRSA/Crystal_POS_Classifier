package com.codebind;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Debug;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;


public class App extends JFrame {
    private JTextArea textArea1;
    private JButton button1;
    private JPanel jPanelMain;
    private JPanel menuPanel;
    private JMenuBar menuBar;
    private JMenu File;
    private JMenuItem exitMenuItem;
    private JMenuBar MnuBar = new JMenuBar();
    private JMenu MnuOne = new JMenu("File");
    private JMenuItem exitApp = new JMenuItem("Exit");
    public static final String DATASETPATH = "data/Weka_Data_NGram_Short.arff";
    public static final String MODElPATH = "data/model.bin";


    public App() {
        setJMenuBar(MnuBar);
        MnuBar.add(MnuOne);
        MnuOne.add(exitApp);


        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ModelGenerator mg = new ModelGenerator();

                Instances dataset = mg.loadDataset(DATASETPATH);
              //  System.out.println("1");
               // Filter filter = new Normalize();
                FilteredClassifier classifier = new FilteredClassifier();
                StringToWordVector filter = new StringToWordVector();

                // divide dataset to train dataset 80% and test dataset 20%
                int trainSize = (int) Math.round(dataset.numInstances() * 0.8);
                int testSize = dataset.numInstances() - trainSize;

                dataset.randomize(new Debug.Random(1));// if you comment this line the accuracy of the model will be droped from 96.6% to 80%

                /* Normalize dataset */
                try {
                    filter.setInputFormat(dataset);
                    System.out.println("2");
                   // classifier.setFilter(filter);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.out.println("Error");
                }
                Instances datasetnor = null;
                try {
                   // datasetnor = Filter.useFilter(dataset, filter);
                    datasetnor = Filter.useFilter(dataset, filter);
                    System.out.println("3");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                Instances traindataset = new Instances(datasetnor, 0, trainSize);
                Instances testdataset = traindataset;
                // Instances testdataset = new Instances(datasetnor, trainSize, testSize);
                System.out.println("4");
                // build classifier with train dataset
                MultilayerPerceptron ann = (MultilayerPerceptron) mg.buildClassifier(traindataset);

                // Evaluate classifier with test dataset
                String evalsummary = mg.evaluateModel(ann, traindataset, testdataset);
                textArea1.setText("Evaluation: " + evalsummary);
                System.out.println("Evaluation: " + evalsummary);
                //Save model
                mg.saveModel(ann, MODElPATH);

                //classifiy a single instance
                ModelClassifier cls = new ModelClassifier();
                String classname = null;
                try {
                  //  classname = cls.classifiy(Filter.useFilter(cls.createInstance("naaste jou said-kantoor", dataset ), filter), MODElPATH);
                    System.out.println("5");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.out.println("Error op Classify");
                }
                System.out.println("\n The class name for the instance with sentence = 'naaste jou said-kantoor' is  " +classname);
            }
        });
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea1.setText("Hello Danie");
            }
        });
    }

    public static void main(String[] args) throws Exception {
        SplashScreen splash = new SplashScreen(5000);
        splash.showSplashAndExit();
        JTextArea textArea = new JTextArea (25, 80);
        textArea.setEditable (false);
        JFrame jFrame = new JFrame("Crystal POS Classifier");
        ImageIcon icon = new ImageIcon("resources/download.jpg");
        jFrame.setIconImage(icon.getImage());
        jFrame.setContentPane(new App().jPanelMain);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setBounds(400,100,600,600);
        jFrame.pack();
        jFrame.setVisible(true);
    }

    public ImageIcon createImage(String path) {
        return new ImageIcon(path);
    }
}
