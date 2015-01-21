package ua.com.amicablesoft.commons.ofu.sample;

import ua.com.amicablesoft.commons.ofu.Updater;

/**
 * Created by Tymoshenko Bogdan <bogdan.tymoshenko@gmail.com> on 1/21/15.
 */
public class Main {

    public static void main(String[] args) {
        Model model1 = new Model();
        model1.setIntValue(1);
        model1.setStringValue("test 1");

        Model model2 = new Model();
        model2.setIntValue(2);
        model2.setStringValue("test 2");

        System.out.println("Model 1 Int value "+model1.getIntValue());
        System.out.println("Model 1 String value "+model1.getStringValue());

        System.out.println("Model 2 Int value "+model2.getIntValue());
        System.out.println("Model 2 String value "+model2.getStringValue());

        System.out.println("Perform model 1 update from model 2");
        Updater.update(model1, model2);

        System.out.println("Model 1 Int value "+model1.getIntValue());
        System.out.println("Model 1 String value "+model1.getStringValue());

        System.out.println("Model 2 Int value "+model2.getIntValue());
        System.out.println("Model 2 String value "+model2.getStringValue());
    }
}
