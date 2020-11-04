# SEvaluator
SEvaluator or (Simple Evaluator) is an extensible infix expression evaluator.
**Note** this project is still in **alpha**.

### Table of contents
* [Download](#download)
* [Usage](#usage)
* [Operations](#operations)
    * [built-in operators](#built-in-operators)
    * [User defined operators](#user-defined-operators)
* [Functions](#functions)
    * [built-in functions](#built-in-operators)
    * [User defined functions](#user-defined-functions)

## Download
latest alpha release [v1.0.0-alpha](https://github.com/mohannadzidan/SEvaluator/raw/main/jar/sevaluator-v1.0.0-alpha.jar)

## Usage
```java
evaluator = new Evaluator(Evaluator.Initialization.ALL);
double x = evaluator.evaluate("65.2 / 3 + 5 * 3 + (1 + 1 / sin(0.5 * 3.14))");
```
## Operations
An operator defines the type of the operation that will be applied to the operands at its left and its right, the order of execution of the operations depends on the priority of the operations, operations with higher priorities is executed before the operations of lower one and from the left to right

### Built-in operators
The library already defines some number of operations at `sevaluator.builtin.operations` package.
Purpose | Operator | Priority
-------- | -------- | --------
Add | \+ | 0
Subtract | \- | 0
Multiply | \* | 1
Divide | \/ | 2
Power | \^ | 3

### User defined operators
The library internally uses instances of classes that implements `sevaluator.Operation` interface to define the operator, priority and behavior of each operation.
**the operators can only be special characters.**
```java
evaluator = new Evaluator(Evaluator.Initialization.ALL);
evaluator.addOperation(new Operation() {

    @Override
    public double apply(Evaluable lhs, Evaluable rhs) {
        return lhs.evaluate() % rhs.evaluate();
    }

    @Override
    public char getOperator() {
        return '%';
    }

    @Override
    public int getPriority() {
        return '2'; // as same as division
    }
});
```
...
```java
double r = evaluator.evaluate("9 % 2"); // evaluate infix of custom operator
```

## Functions
The functions are evaluated before operations and from left to right, for each occurnce of a function keyword the library will apply the behavior of the crossponding function to the `Evaluable` that is directly next to the keyword
### User defined functions
The library internally uses instances of classes that implements `sevaluator.Function` interface to define the keyword and behavior of each function.
 **function keywords can only consist of letters**
```java
evaluator = new Evaluator(Evaluator.Initialization.ALL);
evaluator.addFunction(new Function() {
    @Override
    public double apply(Evaluable value) {
        return Math.log(value.evaluate());
    }
    @Override
    public String getKeyword() {
        return "log";
    }
});
```
...
```java
double r = evaluator.evaluate("log(3)");
```

### Built-in functions
The library already defines some of the trigonemtric functions at `sevaluator.builtin.functions` package.
Keyword | Function
-------- | --------
sin | calculating the sin of an angle in rads
cos | calculating the cosine of an angle in rads
tan | calculating the tangent of an angle in rads

