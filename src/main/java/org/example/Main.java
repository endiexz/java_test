package org.example;

import org.junit.Test;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Random;
interface IEquation{
    short getFirst();
    short getSecond();
    char getOp();
    void setFirst(short first);
    void setSecond(short second);
    void setOp(char op);
    short calculate();
}
interface EquationChecker{
    boolean check(IEquation equation);

}
class EuqationCheckerOfRange implements EquationChecker{
    int max, min;
    int log = 0;
    EuqationCheckerOfRange(int min, int max){
        if(log == 0){
            this.max = max;
            this.min = min;
        }
        else{
            if (max>this.max) this.max = max;
            if (min<this.min) this.min = min;
        }
    }
    @Override
    public boolean check(IEquation equation) {
        if(equation.getFirst()<min||equation.getFirst()>max||equation.getSecond()<min||equation.getSecond()>max||equation.calculate()>max||equation.calculate()<min) return false;
        else return true;
    }
}

abstract class AbstractEquation implements IEquation{
    short first, second;
    char op;
    AbstractEquation(short first, short second, char op){
        this.first = first;
        this.second = second;
        this.op = op;
    }
    @Override
    public short getFirst() {
        return this.first;
    }
    @Override
    public short getSecond() {
        return this.second;
    }
    @Override
    public char getOp() {
        return this.op;
    }
    @Override
    public int hashCode() {
        return this.first*this.second*this.op;
    }
    @Override
    public void setFirst(short first) {
        this.first = first;
    }
    @Override
    public void setSecond(short second) {
        this.second = second;
    }
    @Override
    public void setOp(char op) {
        this.op = op;
    }
    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(obj.hashCode() == this.hashCode()) return true;
        else return false;
    }

}
class AddEquation extends AbstractEquation{
    AddEquation(short first, short second){
        super(first, second, '+');
    }
    @Override
    public short calculate() {
        return (short)(this.first+this.second);
    }
    class AddEquationBuilder{

    }
}
class SubEquation extends AbstractEquation{
    SubEquation(short first, short second){
        super(first, second, '-');
    }
    @Override
    public short calculate() {
        return (short)(this.first-this.second);
    }
    class SubEquationBuilder{

    }

}

class EquationFactory{
    IEquation getEquationRandom(String type){
        Random random = new Random();
        int first, second, oper;
        IEquation equation;
        if(type.matches("random")){
            oper = random.nextInt(2);
            first  = random.nextInt(101);
            if(oper == 0){
                second = random.nextInt(101-first);
                equation = new AddEquation((short)first, (short)second);
            }
            else{
                second = random.nextInt(first+1);
                equation = new SubEquation((short)first, (short)second);
            }
        }
        else if(type.matches("add")){
            first = random.nextInt(101);
            second = random.nextInt(101-first);
            equation = new AddEquation((short)first, (short)second);
        }
        else{
            first = random.nextInt(101);
            second = random.nextInt(first+1);
            equation = new SubEquation((short)first, (short)second);
        }
        return equation;
    }
}

class EquationCollection{
    public HashSet<IEquation> equations = new HashSet<>();
    void generator(int n, EquationChecker checker, String type){
        EquationFactory ef = new EquationFactory();
        IEquation equation;
        boolean log;
        for(int i=0;i<n;i++){
            equation = ef.getEquationRandom(type);
            if(checker.check(equation)==false){
                i-=1;
                continue;
            }
            log = true;
            for(IEquation cequation:equations){
                if(equation.equals(cequation)){
                    i-=1;
                    log = false;
                    break;
                }
            }
            if(log == true){
                equations.add(equation);
                System.out.println(equation.hashCode());
            }
        }
        System.out.println(equations.size());
    }
}

class Printer{
    HashSet<IEquation> equations;
    Printer(HashSet<IEquation> equations){
        this.equations = equations;
    }
    void printer_a(){
        int i=1;
        short first, second;
        char op;
        int re;
        System.out.println("have answer\n");
        for(IEquation equation:equations){
            first = equation.getFirst();
            second = equation.getSecond();
            op = equation.getOp();
            re = equation.calculate();
            System.out.format("%d.%d %c %d = %d\n",i, first, op, second, re);
            i++;
        }
    }
    void printer(){
        int i=1;
        short first, second;
        char op;
        int re;
        System.out.println("have no answer\n");
        for(IEquation equation:equations){
            first = equation.getFirst();
            second = equation.getSecond();
            op = equation.getOp();
            re = equation.calculate();
            System.out.format("%d.%d %c %d\n",i, first, op, second, re);
            i++;
        }
    }

}


public class Main {
    public static void main(String[] args) {
        System.out.println("please input a num");
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        EuqationCheckerOfRange ECOR = new EuqationCheckerOfRange(0, 100);
        EquationCollection generator = new EquationCollection();
        generator.generator(n, ECOR, "random");
        System.out.println(generator.equations.size());
        Printer printer = new Printer(generator.equations);
        printer.printer();
        printer.printer_a();
        input.close();
    }
}
