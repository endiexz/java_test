package org.example;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Random;
public class tests {
    //测试AddEquation
    @Test
    public void testAddEquationCalculate() {
        //创建addequation 类
        AddEquation equation = new AddEquation((short) 2, (short) 3);
        //计算结果
        short result = equation.calculate();
        System.out.println("add calculate");
        //输出结果
        System.out.println(result);
        Assert.assertEquals(5, result);
    }

    @Test
    public void testSubEquationCalculate() {
        //创建subequation给定5, 2初始参数
        SubEquation equation = new SubEquation((short) 5, (short) 2);
        short result = equation.calculate();
        System.out.println("sub calculate");
        System.out.println(result);
        Assert.assertEquals(3, result);
    }

    @Test
    public void testEquationCheckerOfRangeCheck() {
        //创建checke 给定初始min 和max
        EuqationCheckerOfRange checker = new EuqationCheckerOfRange(0, 100);
        //获取一个addeuqtion用于测试
        IEquation equation1 = new AddEquation((short) 50, (short) 30);
        boolean result1 = checker.check(equation1);
        System.out.println("check");
        //获取测试结果
        System.out.println("addequation check");
        System.out.println(result1);
        Assert.assertTrue(result1);
        //获取一个subequation用于测试
        IEquation equation2 = new SubEquation((short) 80, (short) 90);
        //获取测试结果
        boolean result2 = checker.check(equation2);
        System.out.println("subequation check");
        System.out.println(result2);
        Assert.assertFalse(result2);
    }
    @Test
    public void testEquationCollectionSize() {
        //创建equation并向里面添加三个equation用于测试
        HashSet<IEquation> equations = new HashSet<>();
        equations.add(new AddEquation((short) 2, (short) 3));
        equations.add(new SubEquation((short) 5, (short) 2));
        equations.add(new AddEquation((short) 7, (short) 1));

        EquationCollection collection = new EquationCollection();
        collection.equations = equations;
        //测试countequation()并获取结果
        int expectedSize = equations.size();
        int actualSize = countEquations(collection);
        System.out.println("count test");
        System.out.println(actualSize);
        Assert.assertEquals(expectedSize, actualSize);
    }

    private int countEquations(EquationCollection collection) {
        int count = 0;
        Iterator<IEquation> iterator = collection.equations.iterator();
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        return count;
    }
}
