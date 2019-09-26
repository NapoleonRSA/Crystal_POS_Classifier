package com.codebind;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.Classifier;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.*;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class ModelClassifier extends FilteredClassifier {

    private Attribute sentence;

    private ArrayList attributes;
    private ArrayList classVal;
    private Instances dataRaw;


    public ModelClassifier() {
        StringToWordVector filter = new StringToWordVector();
        sentence = new Attribute("sentence");
        attributes = new ArrayList();
        classVal = new ArrayList();
        classVal.add("AOA");
        classVal.add("ASA");
        classVal.add("ASP");
        classVal.add("BS");
        classVal.add("KN");
        classVal.add("KO");
        classVal.add("LB");
        classVal.add("LO");
        classVal.add("NA");
        classVal.add("NSE");
        classVal.add("NSED");
        classVal.add("PB");
        classVal.add("PDOENP");
        classVal.add("PDOEW");
        classVal.add("PTEB");
        classVal.add("PTEDP");
        classVal.add("RK");
        classVal.add("RV");
        classVal.add("RWD");
        classVal.add("SVS");
        classVal.add("TRAB");
        classVal.add("UPI");
        classVal.add("UPO");
        classVal.add("UPS");
        classVal.add("UPW");
        classVal.add("VTHOG");
        classVal.add("VTHOK");
        classVal.add("VTUOM");
        classVal.add("VTUOP");
        classVal.add("VUOT");
        classVal.add("VVHOG");

        attributes.add(sentence);

        attributes.add(new Attribute("class", classVal));
        dataRaw = new Instances("TestInstances", attributes,200);
        dataRaw.setClassIndex(dataRaw.numAttributes() - 1);
        System.out.println("Toets: " +  (dataRaw.numAttributes() - 1));
    }


    public Instances createInstance(String text , Instances data) {
        System.out.println("Instance  Created");
        // dataRaw.clear();
        Instance instance = null;
        Attribute sentenceAt = data.attribute("sentence");
       // ArrayList instanceValue1 = new ArrayList[] {sentence};
        instance.setValue(sentenceAt, sentenceAt.addStringValue(text));
        dataRaw.add(instance);
        return dataRaw;
    }


    public String classifiy(Instances insts, String path) {
        String result = "Not classified!!";
        Classifier cls = null;
        try {
            cls = (MultilayerPerceptron) SerializationHelper.read(path);
            result = String.valueOf(classVal.get((int) cls.classifyInstance(insts.firstInstance())));
        } catch (Exception ex) {
            Logger.getLogger(ModelClassifier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }


    public Instances getInstance() {
        return dataRaw;
    }


}

