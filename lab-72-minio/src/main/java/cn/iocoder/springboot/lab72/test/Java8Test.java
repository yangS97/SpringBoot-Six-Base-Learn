package cn.iocoder.springboot.lab72.test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * @author YS
 * @date 2023/6/29 11:45
 * @Description
 *
 * Consumer: 消费型接口，接收一个输入参数，无返回值。 void accept(T t);
 * Function: 函数式接口，接收一个输入参数，返回一个结果。 R apply(T t);
 * Predicate: 断言型接口，接收一个输入参数，返回一个布尔值结果。 boolean test(T t);
 * Supplier: 供给型接口，无输入参数，返回一个结果。 T get();
 * Comparator: 比较器接口，比较两个对象。 int compare(T o1, T o2);
 * BinaryOperator: 二元运算接口，接收两个同类型参数，返回一个同类型结果。 T apply(T t1, T t2);  第一次执行时，accumulator函数的第一个参数为流中的第一个元素，第二个参数为流中元素的第二个元素；第二次执行时，第一个参数为第一次函数执行的结果，第二个参数为流中的第三个元素；依次类推。
 *
 * BiConsumer: 消费型接口，接收两个输入参数，无返回值。 void accept(T t, U u);
 * Collectors: 收集器接口，将流中元素收集起来并转换成其他形式。 static <T> Collector<T, ?, List<T>> toList(),
 * static <T> Collector<T, ?, Set<T>> toSet(),
 * static <T> Collector<T, ?, Map<T, T>> toMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends U> valueMapper)
 *
 */

public class Java8Test {
    public static void main(String[] args) {
        //new Java8Test().demo02();
        //new Java8Test().reduceDemo();
        new Java8Test().parallelStreamDemo();
    }



    private static String apply(String s) {
        return s.replaceAll(",", "");
    }

    public void demo01(){
        List<String> list = Arrays.asList("a,b,c", "1,2,3");

        Function<String, String> replaceFunction = Java8Test::apply;
        Function<String, String> replaceFunction02 = sb -> apply(sb);
        //将每个元素转成一个新的且不带逗号的元素
        Stream<String> s1 = list.stream().map(s -> s.replaceAll(",",""));
        Stream<String> s2 = list.stream().map(Java8Test::apply);
        Stream<String> s3 = list.stream().map(replaceFunction);
    }


    public void demo02(){
        Student s1 = new Student("aa", 10);
        Student s2 = new Student("bb", 20);
        List<Student> studentList = Arrays.asList(s1, s2);

        studentList.stream()
                // 使用的是Consumer函数式接口  可以对对象进行修改
                .peek(o -> {
                    o.setAge(100);
                    o.setName("zhangsan");
                })
                .forEach(System.out::println);

//结果：
//        Student{name='aa', age=100}
//        Student{name='bb', age=100}

    }


    public void streamFinalDemo(){
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);

        boolean allMatch = list.stream().allMatch(e -> e > 10); //false
        boolean noneMatch = list.stream().noneMatch(e -> e > 10); //true
        boolean anyMatch = list.stream().anyMatch(e -> e > 4);  //true

        Integer findFirst = list.stream().findFirst().get(); //1
        Integer findAny = list.stream().findAny().get(); //1

        long count = list.stream().count(); //5
        Integer max = list.stream().max(Integer::compareTo).get(); //5
        Integer min = list.stream().min(Integer::compareTo).get(); //1
    }

    public void reduceDemo(){
        //经过测试，当元素个数小于24时，并行时线程数等于元素个数，当大于等于24时，并行时线程数为16
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24);

        Integer v = list.stream().reduce((x1, x2) -> x1 + x2).get();
        System.out.println(v);   // 300

        Integer v1 = list.stream().reduce(10, (x1, x2) -> x1 + x2);
        System.out.println(v1);  //310   第一个参数为identity，而第二个参数为流中的第一个元素。

        Integer v2 = list.stream().reduce(0,
                (x1, x2) -> {
                    System.out.println("stream accumulator: x1:" + x1 + "  x2:" + x2);
                    return x1 - x2;
                },
                (x1, x2) -> {
                    System.out.println("stream combiner: x1:" + x1 + "  x2:" + x2);
                    return x1 * x2;
                });
        System.out.println(v2); // -300

        Integer v3 = list.parallelStream().reduce(0,
                (x1, x2) -> {
                    System.out.println("parallelStream accumulator: x1:" + x1 + "  x2:" + x2);
                    return x1 - x2;
                },
                (x1, x2) -> {
                    System.out.println("parallelStream combiner: x1:" + x1 + "  x2:" + x2);
                    return x1 * x2;
                });
        System.out.println(v3); //197474048
    }

    public void parallelStreamDemo(){
        //写一段代码, 测试parallelStream并行流和串行流在执行相同任务时的效率对比。
        //并行流
        long start = System.currentTimeMillis();
        Long sum = LongStream.rangeClosed(0, 100000000000L).parallel().reduce(0, Long::sum);
        System.out.println(sum);
        long end = System.currentTimeMillis();
        System.out.println("并行流执行时间：" + (end - start)); //并行流执行时间：1041
        //串行流
        start = System.currentTimeMillis();
        sum = LongStream.rangeClosed(0, 100000000000L).sequential().reduce(0, Long::sum);
        System.out.println(sum);
        end = System.currentTimeMillis();
        System.out.println("串行流执行时间：" + (end - start)); //串行流执行时间：1974
    }


    public void CollectorDemo() {
        Student s1 = new Student("aa", 10, 1);
        Student s2 = new Student("bb", 20, 2);
        Student s3 = new Student("cc", 10, 3);
        List<Student> list = Arrays.asList(s1, s2, s3);

        //装成list
        List<Integer> ageList = list.stream().map(Student::getAge).collect(Collectors.toList()); // [10, 20, 10]

        //转成set
        Set<Integer> ageSet = list.stream().map(Student::getAge).collect(Collectors.toSet()); // [20, 10]

        //转成map,注:key不能相同，否则报错
        Map<String, Integer> studentMap = list.stream().collect(Collectors.toMap(Student::getName, Student::getAge)); // {cc=10, bb=20, aa=10}

        //字符串分隔符连接
        String joinName = list.stream().map(Student::getName).collect(Collectors.joining(",", "(", ")")); // (aa,bb,cc)

        //聚合操作
        //1.学生总数
        Long count = list.stream().collect(Collectors.counting()); // 3
        //2.最大年龄 (最小的minBy同理)
        Integer maxAge = list.stream().map(Student::getAge).collect(Collectors.maxBy(Integer::compare)).get(); // 20
        //3.所有人的年龄
        Integer sumAge = list.stream().collect(Collectors.summingInt(Student::getAge)); // 40
        //4.平均年龄
        Double averageAge = list.stream().collect(Collectors.averagingDouble(Student::getAge)); // 13.333333333333334
        // 带上以上所有方法
        DoubleSummaryStatistics statistics = list.stream().collect(Collectors.summarizingDouble(Student::getAge));
        System.out.println("count:" + statistics.getCount() + ",max:" + statistics.getMax() + ",sum:" + statistics.getSum() + ",average:" + statistics.getAverage());

        //分组
        Map<Integer, List<Student>> ageMap = list.stream().collect(Collectors.groupingBy(Student::getAge));
        //多重分组,先根据类型分再根据年龄分
        Map<Integer, Map<Integer, List<Student>>> typeAgeMap = list.stream().collect(Collectors.groupingBy(Student::getType, Collectors.groupingBy(Student::getAge)));

        //分区
        //分成两部分，一部分大于10岁，一部分小于等于10岁
        Map<Boolean, List<Student>> partMap = list.stream().collect(Collectors.partitioningBy(v -> v.getAge() > 10));

        //规约
        Integer allAge = list.stream().map(Student::getAge).collect(Collectors.reducing(Integer::sum)).get(); //40
    }

    //创建一个内部类Student
    class Student {
        private String name;
        private Integer age;

        private Integer type;
        //构造方法、get、set方法略

        public Student(String name, Integer age, Integer type) {
            this.name = name;
            this.age = age;
            this.type = type;
        }

        public Student(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public Student() {
        }

        public String getName() {
            return name;
        }

        public Integer getAge() {
            return age;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", type=" + type +
                    '}';
        }
    }

}
