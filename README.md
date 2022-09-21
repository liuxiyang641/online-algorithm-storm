# 基于Storm的在线算法

## Requirements

- JDK: 1.8
- Storm: 1.2.2

## Content

基于Storm平台实现了三种在线算法已经对应的离线算法

| 在线算法           | 对应的离线算法     | 文件                                         |
| ------------------ | ------------------ | -------------------------------------------- |
| 插入排序           | 插入排序-离线版    | src/main/java/insertsort/InserSortMain.java  |
| Misra-Gries算法    | 最频繁元素查找算法 | src/main/java/misragries/MisraGriesMain.java |
| 感知器在线训练算法 | --                 | src/main/java/perception/PerceptionMain.java |

对应的数据集：

|                | 位置                                  | 生成文件                            |
| -------------- | ------------------------------------- | ----------------------------------- |
| 随机整数数据集 | src/main/resources/sortnumbers.txt    | src/main/java/dataset/InsertSortPre |
| 随机字符数据集 | src/main/resources/frequencychars.txt | src/main/java/dataset/MisraGriesPre |

## Run with Intellij

1. File->open
2. Import as Maven project
3. Select `pom.xml`, right click `maven->reimport`
4. Choose one Main java file, click Run

## Reference

This program is free software: you can redistribute it and/or modify it as you wish ONLY for legal and ethical purposes.

Code adapted from:

- https://github.com/nsadawi/perceptron
- https://github.com/Oren84/Misra-Gries

