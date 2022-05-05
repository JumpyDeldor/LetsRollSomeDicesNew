package com.example.letsrollsomedicesnew;


import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Dice {
    private final ArrayList<String> dices= new ArrayList<>();
    private final ArrayList<String> modifications =new ArrayList<>();
    private int result=0;
    private String throwing;
    //Очищаем результат предыдущего броска
    private void refreshResult(String input){
        result=0;
        throwing="Request:"+'\n'+input+'\n';
    }
    //Вот "стартовый метод", в результате получаем лист кубов и модификаторов
    public void doTheDiceRoll(String input){
        refreshResult(input);
        String string=input;
        String diceTemplate="[+-][\\d]+d[\\d]+";//Шаблон для поиска кубиков
        String modificationTemplate="[+-][^d+-]+";//Шаблон для поиска модификаторов
        Pattern pattern=Pattern.compile(diceTemplate);
        Matcher matcher = pattern.matcher(string);
        while (matcher.find()) {
            dices.add(string.substring(matcher.start(), matcher.end()));//Заполняем лист кубиков
        }
        string=matcher.replaceAll("");//После нахождения всех кубов, удаляем их
        pattern=Pattern.compile(modificationTemplate);
        matcher = pattern.matcher(string);
        while (matcher.find()) {
            modifications.add(string.substring(matcher.start(), matcher.end()));//Заполняем лист модификаторов
        }
        addModificationsToResult();//Вызываем следующий метод
    }
    //Метод, который формирует строку результата и добавляет в него все модификторы
    private void addModificationsToResult(){
        for (String modification : modifications) result += Integer.parseInt(modification);

        throwing+="Modification sum ="+result+'\n';
        addDicesToResult();
    }
    //Метод, который делает все броски кубов и записывает всё это в финальную строку результата
    private void addDicesToResult(){
        String dice;//Здесь будет сам бросок одной размерности кубов
        int amount=0;//Кол-во кубов
        int sides=0;//Их размерность
        int sign=0;//Какой знак перед броском
        Random random = new Random();
        int roll=0;//Результат броска
        String amount_of_dices="[\\d]+d";//Шаблон для поиска кол-ва кубов
        String amount_of_sides="d[\\d]+";//Для поиска кол-ва сторон
        Pattern pattern_for_amount=Pattern.compile(amount_of_dices);
        Pattern pattern_for_sides=Pattern.compile(amount_of_sides);
        for (String s : dices) {//Берём по очереди каждый бросок кубов из листа бросков кубов
            dice = s;//Здесь храниться единичный бросок
            if (dice.charAt(0) == '-')//Определяем знак
                sign = -1;
            else
                sign = 1;
            Matcher matcher_for_amount = pattern_for_amount.matcher(dice);
            while (matcher_for_amount.find()) {//Ищем кол-во бросков по шаблону
                amount = Integer.parseInt(dice.substring(matcher_for_amount.start(), matcher_for_amount.end() - 1));
            }
            Matcher matcher_for_sides = pattern_for_sides.matcher(dice);
            while (matcher_for_sides.find()) {//Ищем кол-во сторон оп шаблону
                sides = Integer.parseInt(dice.substring(matcher_for_sides.start() + 1, matcher_for_sides.end()));
            }
            for (int j = 0; j < amount; j++) {//Делаем сами броски
                roll = (1 + random.nextInt(sides)) * sign;
                result += roll;//Добавляем в результат
                throwing += "d" + sides + "=" + roll + '\n';//Записываем для каждого броска результат
            }
        }
        throwing+="result:"+result+ '\n'+ '\n';
    }
    //Выводим результат
    public String showResult(){
        return throwing;
    }
}

